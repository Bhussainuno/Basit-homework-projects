// Author: Basit Hussain
// Task2.java multi threadedanded solution
// Purpose: Count the number of non stop incoming flights per destination airport.
// Uses multiple threads to process files in parallel for improved performance;
// robust schema handling (similar to Task 1): reads *.csv from./data by default
// destination: destinationAirport OR last token of segmentsArrivalAirportCode 

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class Task2 {

    // CLI options container
    static class Options {
        Path dir;
        int threads; // <=0 means auto
    }

    public static void main(String[] args) throws Exception {
        Options opt = parseArgs(args);

        // Once on the main thread, finding CSV files
        List<Path> files = new ArrayList<>();
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(opt.dir, "*.csv")) {
            for (Path p : ds) files.add(p);
        }
        if (files.isEmpty()) {
            System.err.println("No CSV files found in: " + opt.dir.toAbsolutePath());
            System.exit(2);
        }

        int cpu = Runtime.getRuntime().availableProcessors();
        int poolSize = opt.threads > 0 ? Math.min(opt.threads, files.size()) : Math.min(cpu, files.size());

        long t0 = System.nanoTime();

        // Each file should have a Callable that returns a local Map<String,Long>.
        ExecutorService pool = Executors.newFixedThreadPool(poolSize);
        List<Future<Map<String,Long>>> futures = new ArrayList<>(files.size());

        for (Path f : files) {
            futures.add(pool.submit(() -> processOneFile(f)));
        }

        // Combining all the partial maps into one final map
        Map<String, Long> counts = new HashMap<>();
        for (Future<Map<String,Long>> fut : futures) {
            Map<String, Long> part = fut.get(); // wait for completion
            for (Map.Entry<String, Long> e : part.entrySet()) {
                counts.merge(e.getKey(), e.getValue(), Long::sum);
            }
        }
        pool.shutdown();

        // Output results in a tabular format
        System.out.printf("%-5s  %s%n", "IATA", "Non-Stop Incoming Flights");
        System.out.println("----------------------------------------------------");
        List<String> keys = new ArrayList<>(counts.keySet());
        Collections.sort(keys);
        for (String k : keys) System.out.printf("%-5s  %d%n", k, counts.get(k));

        double sec = (System.nanoTime() - t0) / 1_000_000_000.0;
        System.out.printf("%nRuntime: %.3f seconds%n", sec);
    }

    // Per file worker method
    static Map<String, Long> processOneFile(Path f) throws IOException {
        Map<String, Long> local = new HashMap<>();

        try (BufferedReader br = Files.newBufferedReader(f)) {
            String header = br.readLine();
            if (header == null) return local;

            String[] h = splitCSV(header);
            Map<String,Integer> idx = indexColumns(h);

            int destIdx   = find(idx, "destinationairport","destination","dest","dest_airport",
                                       "destination_airport","arrival_airport","iata_dest","to");
            int segArrIdx = idx.getOrDefault("segmentsarrivalairportcode", -1);
            int segDepIdx = idx.getOrDefault("segmentsdepartureairportcode", -1);

            int flagIdx   = find(idx,"isnonstop","nonstop","non_stop","is_nonstop","is_direct","direct","itinerary_type");
            int stopsIdx  = find(idx,"stops","num_stops","stop_count","n_stops");

            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) continue;
                String[] row = splitCSV(line);

                // Destination
                String dest = null;
                if (destIdx >= 0 && destIdx < row.length) {
                    dest = safe(row[destIdx]).toUpperCase(Locale.ROOT);
                } else if (segArrIdx >= 0 && segArrIdx < row.length) {
                    dest = lastToken(row[segArrIdx]).toUpperCase(Locale.ROOT);
                }
                if (dest == null || dest.isEmpty()) continue;

                // Non stop detection logic with multiple fallbacks 
                boolean nonstop = false;
                if (flagIdx >= 0 && flagIdx < row.length) {
                    String v = norm(row[flagIdx]);
                    nonstop = v.equals("true") || v.equals("yes") || v.equals("y")
                           || v.equals("1") || v.equals("nonstop") || v.equals("non-stop") || v.equals("direct");
                } else if (stopsIdx >= 0 && stopsIdx < row.length) {
                    String s = safe(row[stopsIdx]);
                    if (!s.isEmpty()) {
                        try { nonstop = Integer.parseInt(s) == 0; }
                        catch (NumberFormatException e) { nonstop = "0".equals(s); }
                    }
                } else {
                    // As a backup, infer by the number of segments exactly one segment means non stop
                    int segs = segmentCount(row, segArrIdx, segDepIdx);
                    nonstop = segs == 1;
                }

                if (nonstop) local.merge(dest, 1L, Long::sum);
            }
        }
        return local;
    }

    // CLI helpers methods
    static Options parseArgs(String[] args) {
        Options o = new Options();
        o.dir = Paths.get(args.length > 0 ? args[0] : "./data");
        o.threads = 0; // auto
        for (int i = 1; i < args.length; i++) {
            if ("--threads".equals(args[i]) && i+1 < args.length) {
                try { o.threads = Integer.parseInt(args[++i]); }
                catch (NumberFormatException e) { System.err.println("Bad --threads value"); System.exit(1); }
            } else {
                System.err.println("Unknown option: " + args[i]);
                System.err.println("Usage: java Task2 [dir] [--threads N]");
                System.exit(1);
            }
        }
        if (!Files.isDirectory(o.dir)) {
            System.err.println("Directory not found: " + o.dir.toAbsolutePath());
            System.exit(1);
        }
        return o;
    }

    static int find(Map<String,Integer> idx, String... names) {
        for (String n : names) {
            Integer i = idx.get(n);
            if (i != null) return i;
        }
        return -1;
    }
    static String safe(String s){ return s == null ? "" : s.trim(); }
    static String norm(String s){ return s == null ? "" : s.trim().toLowerCase(Locale.ROOT); }

    static int segmentCount(String[] row, int segArrIdx, int segDepIdx) {
        String raw = null;
        if (segArrIdx >= 0 && segArrIdx < row.length) raw = row[segArrIdx];
        else if (segDepIdx >= 0 && segDepIdx < row.length) raw = row[segDepIdx];
        if (raw == null) return 1;
        String s = safe(raw);
        if (s.isEmpty()) return 1;
        int bars = 0;
        for (int i = 0; i < s.length(); i++) if (s.charAt(i) == '|') bars++;
        return bars + 1;
    }
    static String lastToken(String s){
        String t = safe(s);
        int k = t.lastIndexOf('|');
        return (k >= 0) ? t.substring(k+1) : t;
    }

    // CSV splitter that supports quoted fields 
    static String[] splitCSV(String line) {
        List<String> out = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i+1 < line.length() && line.charAt(i+1) == '"') { sb.append('"'); i++; }
                else inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) { out.add(sb.toString()); sb.setLength(0); }
            else sb.append(c);
        }
        out.add(sb.toString());
        return out.toArray(new String[0]);
    }
    static Map<String,Integer> indexColumns(String[] header) {
        Map<String,Integer> map = new HashMap<>();
        for (int i=0;i<header.length;i++) map.put(header[i].trim().toLowerCase(Locale.ROOT), i);
        return map;
    }
}
