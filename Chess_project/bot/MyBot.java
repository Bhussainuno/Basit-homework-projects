package com.stephengware.java.games.chess.bot;

import java.util.*;
import java.lang.reflect.*;

import com.stephengware.java.games.chess.state.State;
import com.stephengware.java.games.chess.state.Piece;

public class MyBot extends Bot {

    private static final String BOT_NAME = "bhussai2";

    // ---------- SPEED / SAFETY (anti-freeze) ----------
    private static final int   MAX_DEPTH     = 5;        // keep 5 for Novice
    private static final int   NODE_LIMIT    = 200_000;  // HARD cap per move (prevents 20-min stalls)
    private static final long  TIME_LIMIT_MS = 650;      // HARD time cap per move

    // Cap move generation per node to prevent slow branching
    private static final int   MAX_MOVES_PER_NODE = 80;
    private static final int   MAX_QUIET_NOISY    = 35;

    // ---------- PIECE VALUES ----------
    private static final int V_PAWN   = 100;
    private static final int V_KNIGHT = 320;
    private static final int V_BISHOP = 330;
    private static final int V_ROOK   = 500;
    private static final int V_QUEEN  = 900;

    private static final int CHECKMATE_SCORE  = 100_000;
    private static final int IN_CHECK_PENALTY = 450;

    // ---------- SIMPLE POSITIONAL TERMS ----------
    private static final int CENTER_PAWN_BONUS   = 12;
    private static final int CENTER_KNIGHT_BONUS = 18;

    // Opening blunder preventers (Novice punishes early queen/king nonsense)
    private static final int EARLY_QUEEN_PENALTY = 45;  // per distance from home
    private static final int EARLY_KING_PENALTY  = 60;  // king off back rank early
    private static final int OPENING_PLIES       = 10;

    private long nodes;

    public MyBot() { super(BOT_NAME); }

    @Override
    protected State chooseMove(State root) {
        nodes = 0;
        final long start = System.nanoTime();

        List<State> children = collectChildrenCapped(root, start, MAX_MOVES_PER_NODE);
        if (children.isEmpty()) return root;

        final int rootMat = materialWB(root);

        // Root move ordering: checks > captures/promos > (FIXED) -evaluate(child)
        ArrayList<ScoredMove> rootMoves = new ArrayList<>(children.size());
        for (State c : children) rootMoves.add(new ScoredMove(c, orderScore(root, c, rootMat)));
        rootMoves.sort((a,b) -> Integer.compare(b.order, a.order));

        State bestMove = rootMoves.get(0).s;
        int bestScore = Integer.MIN_VALUE / 2;

        // Iterative deepening
        for (int depth = 1; depth <= MAX_DEPTH; depth++) {
            if (limitReached(root, start)) break;

            int alpha = Integer.MIN_VALUE / 2 + 1;
            int beta  = Integer.MAX_VALUE / 2 - 1;

            int iterBest = Integer.MIN_VALUE / 2;
            State iterBestMove = bestMove;

            for (ScoredMove sm : rootMoves) {
                if (limitReached(root, start)) break;

                int val = -negamax(sm.s, depth - 1, -beta, -alpha, start);

                if (val > iterBest) { iterBest = val; iterBestMove = sm.s; }
                if (val > alpha) alpha = val;
            }

            bestMove = iterBestMove;
            bestScore = iterBest;

            // PV-first next iteration
            final State pv = bestMove;
            rootMoves.sort((a,b) ->
                    (a.s == pv ? -1 : (b.s == pv ? 1 : Integer.compare(b.order, a.order))));
        }

        System.out.printf("%s: depth<=%d nodes=%d time=%dms score=%d%n",
                BOT_NAME, MAX_DEPTH, nodes, elapsedMs(start), bestScore);

        return bestMove;
    }

    // ---------------- SEARCH ----------------

    private int negamax(State s, int depth, int alpha, int beta, long start) {
        if (limitReached(s, start)) return evaluate(s);
        nodes++;

        if (isTerminal(s)) return evaluate(s);

        // CHECK EXTENSION (helps vs Novice tactics)
        if (boolField(s, "check") && depth < 2) depth = 2;

        if (depth <= 0) {
            return quiescence(s, alpha, beta, start);
        }

        List<State> kids = collectChildrenCapped(s, start, MAX_MOVES_PER_NODE);
        if (kids.isEmpty()) return evaluate(s);

        final int sMat = materialWB(s);

        ArrayList<ScoredMove> ordered = new ArrayList<>(kids.size());
        for (State c : kids) ordered.add(new ScoredMove(c, orderScore(s, c, sMat)));
        ordered.sort((a,b) -> Integer.compare(b.order, a.order));

        for (ScoredMove sm : ordered) {
            if (limitReached(s, start)) break;

            int val = -negamax(sm.s, depth - 1, -beta, -alpha, start);

            if (val > alpha) alpha = val;
            if (alpha >= beta) break;
        }

        return alpha;
    }

    private int quiescence(State s, int alpha, int beta, long start) {
        if (limitReached(s, start)) return evaluate(s);

        int stand = evaluate(s);
        if (stand >= beta) return beta;
        if (stand > alpha) alpha = stand;

        final int baseMat = materialWB(s);

        List<State> kids = collectChildrenCapped(s, start, MAX_QUIET_NOISY);
        if (kids.isEmpty()) return stand;

        ArrayList<ScoredMove> noisy = new ArrayList<>();
        for (State c : kids) {
            boolean isCheck = boolField(c, "check");
            boolean matChange = (materialWB(c) != baseMat);
            if (isCheck || matChange) {
                // (FIXED) -evaluate(c) because child is opponent-to-move
                int ord = (isCheck ? 60_000 : 0)
                        + 6 * Math.abs(materialWB(c) - baseMat)
                        + (-evaluate(c));
                noisy.add(new ScoredMove(c, ord));
            }
        }
        if (noisy.isEmpty()) return stand;

        noisy.sort((a,b) -> Integer.compare(b.order, a.order));

        for (ScoredMove sm : noisy) {
            if (limitReached(s, start)) break;

            int score = -quiescence(sm.s, -beta, -alpha, start);

            if (score >= beta) return beta;
            if (score > alpha) alpha = score;
        }

        return alpha;
    }

    // ---------------- MOVE ORDERING ----------------

    private int orderScore(State parent, State child, int parentMatWB) {
        int score = 0;

        boolean givesCheck = boolField(child, "check");
        if (givesCheck) score += 80_000;

        int childMatWB = materialWB(child);
        int delta = Math.abs(childMatWB - parentMatWB);
        score += delta * 10; // captures/promotions earlier

        // IMPORTANT FIX:
        // evaluate(child) is from the side-to-move perspective (opponent after our move),
        // so for ordering we want -evaluate(child)
        score += -evaluate(child);

        return score;
    }

    // ---------------- EVALUATION (side-to-move) ----------------

    private int evaluate(State s) {
        boolean over  = boolField(s, "over");
        boolean check = boolField(s, "check");

        if (over) {
            if (check) return -CHECKMATE_SCORE;
            return 0;
        }

        int ply = estimatePly(s);
        int scoreWminusB = 0;

        // Track king ranks for early king-walk penalty
        int wKr = -1, bKr = -1;

        for (Piece p : s.board) {
            if (p == null) continue;
            String sym = String.valueOf(p);
            if (sym.isEmpty()) continue;

            char c = sym.charAt(0);
            boolean white = Character.isUpperCase(c);
            char up = Character.toUpperCase(c);

            int mat = 0;
            int pos = 0;

            int[] fr = getFileRank(p);
            int f = fr != null ? fr[0] : -1;
            int r = fr != null ? fr[1] : -1;

            switch (up) {
                case 'P':
                    mat = V_PAWN;
                    if (f == 3 || f == 4) pos += CENTER_PAWN_BONUS;
                    break;
                case 'N':
                    mat = V_KNIGHT;
                    if (f >= 2 && f <= 5 && r >= 2 && r <= 5) pos += CENTER_KNIGHT_BONUS;
                    break;
                case 'B': mat = V_BISHOP; break;
                case 'R': mat = V_ROOK; break;
                case 'Q':
                    mat = V_QUEEN;
                    if (ply <= OPENING_PLIES && fr != null) {
                        int homeFile = 3;                 // d-file
                        int homeRank = white ? 7 : 0;      // back rank
                        int dist = Math.abs(f - homeFile) + Math.abs(r - homeRank);
                        pos -= EARLY_QUEEN_PENALTY * dist;
                    }
                    break;
                case 'K':
                    mat = 0;
                    if (fr != null) {
                        if (white) wKr = r; else bKr = r;
                    }
                    break;
                default:
                    mat = 0;
            }

            int pieceScore = mat + pos;
            scoreWminusB += white ? pieceScore : -pieceScore;
        }

        // Penalize early king movement
        if (ply <= OPENING_PLIES) {
            if (wKr != -1 && wKr != 7) scoreWminusB -= EARLY_KING_PENALTY;
            if (bKr != -1 && bKr != 0) scoreWminusB += EARLY_KING_PENALTY;
        }

        if (check) scoreWminusB -= IN_CHECK_PENALTY;

        // side-to-move perspective
        if (!isWhiteToMove(s)) scoreWminusB = -scoreWminusB;
        return scoreWminusB;
    }

    // Material W-B (not side-to-move)
    private int materialWB(State s) {
        int wb = 0;
        for (Piece p : s.board) {
            if (p == null) continue;
            String sym = String.valueOf(p);
            if (sym.isEmpty()) continue;
            char c = sym.charAt(0);
            boolean white = Character.isUpperCase(c);
            char up = Character.toUpperCase(c);

            int v = 0;
            switch (up) {
                case 'P': v = V_PAWN; break;
                case 'N': v = V_KNIGHT; break;
                case 'B': v = V_BISHOP; break;
                case 'R': v = V_ROOK; break;
                case 'Q': v = V_QUEEN; break;
                default: v = 0;
            }
            wb += white ? v : -v;
        }
        return wb;
    }

    // ---------------- CHILD GEN ----------------

    private List<State> collectChildrenCapped(State s, long start, int cap) {
        ArrayList<State> list = new ArrayList<>();
        Iterator<State> it = s.next().iterator();
        while (!s.searchLimitReached() && it.hasNext()) {
            if (limitReached(s, start)) break;
            list.add(it.next());
            if (list.size() >= cap) break;
        }
        return list;
    }

    // ---------------- LIMITS ----------------

    private boolean limitReached(State s, long start) {
        if (s.searchLimitReached()) return true;
        if (nodes >= NODE_LIMIT) return true;
        if (elapsedMs(start) >= TIME_LIMIT_MS) return true;
        return false;
    }

    private long elapsedMs(long startNano) {
        return (System.nanoTime() - startNano) / 1_000_000L;
    }

    // ---------------- TERMINAL DETECTION ----------------

    private boolean isTerminal(State s) {
        Boolean b = callBoolMethod(s, "isEnd");
        if (b != null) return b.booleanValue();
        b = callBoolMethod(s, "isGameOver");
        if (b != null) return b.booleanValue();
        b = callBoolMethod(s, "isFinished");
        if (b != null) return b.booleanValue();
        return boolField(s, "over");
    }

    // ---------------- PLY ESTIMATE ----------------

    private int estimatePly(State s) {
        int ply = 0;
        Object cur = s;
        for (int i = 0; i < 200; i++) {
            Object prev = tryObjectField(cur, "previous");
            if (prev == null) prev = tryObjectMethod(cur, "getPrevious");
            if (prev == null) break;
            ply++;
            cur = prev;
        }
        return ply;
    }

    // ---------------- STRUCT ----------------
    private static final class ScoredMove {
        final State s;
        final int order;
        ScoredMove(State s, int order) { this.s = s; this.order = order; }
    }

    // ---------------- SIDE TO MOVE ----------------

    private boolean isWhiteToMove(State s) {
        Boolean w = getBoolField(s, "white");
        if (w == null) w = getBoolField(s, "whitesTurn");
        if (w == null) w = getBoolField(s, "whiteToMove");
        if (w == null) w = getBoolField(s, "turnWhite");
        if (w == null) w = callBoolMethod(s, "isWhite");
        if (w == null) w = callBoolMethod(s, "isWhitesTurn");
        if (w == null) w = callBoolMethod(s, "isWhiteToMove");
        return (w == null) ? true : w.booleanValue();
    }

    // ---------------- REFLECTION HELPERS ----------------

    private boolean boolField(Object obj, String fieldName) {
        Boolean b = getBoolField(obj, fieldName);
        return b != null && b.booleanValue();
    }

    private Boolean getBoolField(Object obj, String fieldName) {
        if (obj == null) return null;
        try {
            Field f;
            try { f = obj.getClass().getField(fieldName); }
            catch (NoSuchFieldException e) {
                f = obj.getClass().getDeclaredField(fieldName);
                f.setAccessible(true);
            }
            Object v = f.get(obj);
            if (v instanceof Boolean) return (Boolean) v;
        } catch (Exception ignore) {}
        return null;
    }

    private Boolean callBoolMethod(Object obj, String name) {
        if (obj == null) return null;
        try {
            Method m = obj.getClass().getMethod(name);
            Object v = m.invoke(obj);
            if (v instanceof Boolean) return (Boolean) v;
        } catch (Exception ignore) {}
        return null;
    }

    private Integer callIntMethod(Object obj, String name) {
        if (obj == null) return null;
        try {
            Method m = obj.getClass().getMethod(name);
            Object v = m.invoke(obj);
            if (v instanceof Number) return ((Number) v).intValue();
        } catch (Exception ignore) {}
        return null;
    }

    private Integer getIntField(Object obj, String name) {
        if (obj == null) return null;
        try {
            Field f;
            try { f = obj.getClass().getField(name); }
            catch (NoSuchFieldException e) {
                f = obj.getClass().getDeclaredField(name);
                f.setAccessible(true);
            }
            Object v = f.get(obj);
            if (v instanceof Number) return ((Number) v).intValue();
        } catch (Exception ignore) {}
        return null;
    }

    private Object tryObjectField(Object obj, String name) {
        if (obj == null) return null;
        try {
            Field f;
            try { f = obj.getClass().getField(name); }
            catch (NoSuchFieldException e) {
                f = obj.getClass().getDeclaredField(name);
                f.setAccessible(true);
            }
            return f.get(obj);
        } catch (Exception ignore) {}
        return null;
    }

    private Object tryObjectMethod(Object obj, String name) {
        if (obj == null) return null;
        try {
            Method m = obj.getClass().getMethod(name);
            return m.invoke(obj);
        } catch (Exception ignore) {}
        return null;
    }

    private int[] getFileRank(Piece p) {
        Integer f = callIntMethod(p, "getFile");
        Integer r = callIntMethod(p, "getRank");
        if (f != null && r != null) return new int[]{ clamp(f), clamp(r) };

        f = callIntMethod(p, "getColumn");
        r = callIntMethod(p, "getRow");
        if (f != null && r != null) return new int[]{ clamp(f), clamp(r) };

        f = callIntMethod(p, "getX");
        r = callIntMethod(p, "getY");
        if (f != null && r != null) return new int[]{ clamp(f), clamp(r) };

        f = getIntField(p, "file");
        r = getIntField(p, "rank");
        if (f != null && r != null) return new int[]{ clamp(f), clamp(r) };

        return null;
    }

    private int clamp(int v) { return Math.max(0, Math.min(7, v)); }
}
