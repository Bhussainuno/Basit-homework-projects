
package com.stephengware.java.games.chess.bot;

import java.io.IOException;
import com.stephengware.java.games.chess.Tournament;
import com.stephengware.java.games.chess.gui.ChessDisplay;
import com.stephengware.java.games.chess.gui.Piece;

/**
 * Progression test harness:
 * Runs bhussai2 (MyBot) against Random, Greedy, Novice, Beginner, then Intermediate,
 * printing a clear header and the result for each tournament.
 */
public class Test {

    public static void main(String[] args) throws IOException {
        Piece.load();
        Bot my = new MyBot();

        runMatch(my, new RandomBot(),       6);
        runMatch(my, new GreedyBot(),       6);
        runMatch(my, new NoviceBot(),       8);
        runMatch(my, new BeginnerBot(),     8);
        runMatch(my, new IntermediateBot(), 8);
    }

    private static void runMatch(Bot a, Bot b, int games) throws IOException {
        String aName = getBotName(a);
        String bName = getBotName(b);

        System.out.println("\n==================================================");
        System.out.println("Match: " + aName + " vs. " + bName + " (" + games + " games)");
        System.out.println("==================================================");

        Tournament t = new Tournament(games, new Bot[]{ a, b });
        t.play();

        String result = t.toString();
        ChessDisplay.getInstance().console.append(
            "\n\n=== " + aName + " vs. " + bName + " (" + games + " games) ===\n" + result
        );
        System.out.println(result);
    }

    private static String getBotName(Bot bot) {
        try {
            java.lang.reflect.Field f = bot.getClass().getField("name");
            Object o = f.get(bot);
            if (o != null) return String.valueOf(o);
        } catch (Exception ignore) {}
        try {
            java.lang.reflect.Method m = bot.getClass().getMethod("getName");
            Object o = m.invoke(bot);
            if (o != null) return String.valueOf(o);
        } catch (Exception ignore) {}
        return bot.getClass().getSimpleName();
    }
}
