// Author: Basit Hussain
// Single threaded solution for Task1.java
// Goal: Determine how many nonstop flights arrive at each destination airport.
// Checks several potential column names to handle schema variances gracefully.
// Reads all *.csv files from a specified directory (default:./data).
// Contains fallback logic to identify flights that don't stop.
// Results are output in a tabular, readable manner that can be used for screenshots.
// Calculates the runtime in seconds and outputs it.

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Task1 {

    // Basic options container just the directory path as the moment 

    static class Options {
        Path dir;
    }

    public static void main(String[] args) throws Exception {
        // Begin timing the performance evaluation
        long t0 = System.nanoTime();

        // Use the default or parse the directory argument
        Options opt = new Options();
        opt.dir = Paths.get(args.length > 0 ? args[0] : "./data");

        // Gather every CSV file in the directory.
        List<Path> files = new ArrayList<>();
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(opt.dir, "*.csv")) {
            for (Path p : ds) files.add(p);
        }

        // If no files are located, exit
        if (files.isEmpty()) {
            System.err.println("No CSV files found in: " + opt.dir.toAbsolutePath());
            System.exit(2);
        }

        // Map to store destination airport -> number of flights that don't stop
        Map<String, Long> counts = new HashMap<>();

        // Process each CSV file
        for (Path f : files) {
            try (BufferedReader br = Files.newBufferedReader(f)) {
                String header = br.readLine();
                if (header == null) continue; // Skip empty files

                // Split header and building column index map
                String[] h = splitCSV(header);
                Map<String,Integer> idx = indexColumns(h);

                // Identifying the relevant columns like destination, segments, and flags
                int destIdx = find(idx,
                        "destinationairport","destination","dest","dest_airport",
                        "destination_airport","arrival_airport","iata_dest","to");
                int segArrIdx = idx.getOrDefault("segmentsarrivalairportcode", -1);
                int segDepIdx = idx.getOrDefault("segmentsdepartureairportcode", -1);

                // create columns for non stop detect with multiple possible names
                int flagIdx = find(idx,"isnonstop","nonstop","non_stop","is_nonstop","is_direct","direct","itinerary_type");
                int stopsIdx = find(idx,"stops","num_stops","stop_count","n_stops");

                String line;
                while ((line = br.readLine()) != null) {
                    if (line.isEmpty()) continue;
                    String[] row = splitCSV(line);

                    // Extracting the destination airport
                    String dest = null;
                    if (destIdx >= 0 && destIdx < row.length) {
                        dest = safe(row[destIdx]).toUpperCase(Locale.ROOT);
                    } else if (segArrIdx >= 0 && segArrIdx < row.length) {
                        dest = lastToken(row[segArrIdx]).toUpperCase(Locale.ROOT);
                    }
                    if (dest == null || dest.isEmpty()) continue;

                    // Determining if the flight is non stop
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
                        // Recourse: infer based on the number of segments (one segment = non-stop)
                        int segs = segmentCount(row, segArrIdx, segDepIdx);
                        nonstop = segs == 1;
                    }

                    // Updating the count if non stop
                    if (nonstop) counts.merge(dest, 1L, Long::sum);
                }
            }
        }

        // Print the outcomes in a clear format that is suitable for screenshots.
        System.out.printf("%-5s  %s%n", "IATA", "Non-Stop Incoming Flights");
        System.out.println("----------------------------------------------------");
        List<String> keys = new ArrayList<>(counts.keySet());
        Collections.sort(keys);
        for (String k : keys) System.out.printf("%-5s  %d%n", k, counts.get(k));

        // Printing the runtime in seconds
        double sec = (System.nanoTime() - t0) / 1_000_000_000.0;
        System.out.printf("%nRuntime: %.3f seconds%n", sec);
    }

    // Helper Methods
    // Finding the column index by checking multiple possible names
    static int find(Map<String,Integer> idx, String... names) {
        for (String n : names) {
            Integer i = idx.get(n);
            if (i != null) return i;
        }
        return -1;
    }

    // string trim
    static String safe(String s){ return s == null ? "" : s.trim(); }

    // Normalizing and string to lowercase
    static String norm(String s){ return s == null ? "" : s.trim().toLowerCase(Locale.ROOT); }

    // Using the '|' delimiter to count the segments in a flight
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

    // Extracting the last token from a pipe delimited string
    static String lastToken(String s){
        String t = safe(s);
        int k = t.lastIndexOf('|');
        return (k >= 0) ? t.substring(k+1) : t;
    }

    // Dividing the CSV line into fields and managing values that are quoted
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

    // Create a map connecting the names of the columns to their indexes.
    static Map<String,Integer> indexColumns(String[] header) {
        Map<String,Integer> map = new HashMap<>();
        for (int i=0;i<header.length;i++) {
            map.put(header[i].trim().toLowerCase(Locale.ROOT), i);
        }
        return map;
    }
}