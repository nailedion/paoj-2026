package com.pao.laboratory11.exercise2;

import com.pao.laboratory11.exercise1.Main.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            run();
        } catch (IOException e) {
        }
    }

    private static void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String first = nextNonEmpty(br);
        if (first == null) {
            return;
        }

        int n = Integer.parseInt(first);

        List<Transaction> txs = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String line = nextNonEmpty(br);
            if (line == null) {
                return;
            }

            String[] p = line.split("\\s+");

            txs.add(new Transaction(
                    Integer.parseInt(p[0]),
                    Double.parseDouble(p[1]),
                    p[2],
                    p[3],
                    p[4],
                    p[5]
            ));
        }

        String qLine = nextNonEmpty(br);
        if (qLine == null) {
            return;
        }
        int q = Integer.parseInt(qLine);

        for (int i = 0; i < q; i++) {
            String line = nextNonEmpty(br);
            if (line == null) {
                return;
            }

            String[] p = line.split("\\s+");
            String op = p[0];

            switch (op) {
                case "REPORT_MONTH": {
                    String month = p[1];

                    double total = txs.stream()
                            .filter(tx -> tx.getDate().startsWith(month))
                            .mapToDouble(Transaction::getAmount)
                            .sum();

                    long count = txs.stream()
                            .filter(tx -> tx.getDate().startsWith(month))
                            .count();

                    System.out.printf(Locale.US, "MONTH %s total=%.2f count=%d%n", month, total, count);
                    break;
                }

                case "REPORT_ACCOUNT": {
                    String account = p[1];

                    double total = txs.stream()
                            .filter(tx -> account.equals(tx.getAccount()))
                            .mapToDouble(Transaction::getAmount)
                            .sum();

                    long count = txs.stream()
                            .filter(tx -> account.equals(tx.getAccount()))
                            .count();

                    System.out.printf(Locale.US, "ACCOUNT %s total=%.2f count=%d%n", account, total, count);
                    break;
                }

                case "TOP_CHANNELS": {
                    int k = Integer.parseInt(p[1]);

                    Map<String, Long> counts = txs.stream()
                            .collect(Collectors.groupingBy(Transaction::getChannel, Collectors.counting()));

                    List<Map.Entry<String, Long>> sortedChannels = counts.entrySet().stream()
                            .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                                    .thenComparing(Map.Entry.comparingByKey()))
                            .limit(k)
                            .collect(Collectors.toList());

                    if (sortedChannels.isEmpty()) {
                        System.out.println("NONE");
                        break;
                    }

                    for (Map.Entry<String, Long> e : sortedChannels) {
                        System.out.println(e.getKey() + " " + e.getValue());
                    }
                    break;
                }

                default:
                    break;
            }
        }
    }

    private static String nextNonEmpty(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                return line.trim();
            }
        }
        return null;
    }
}