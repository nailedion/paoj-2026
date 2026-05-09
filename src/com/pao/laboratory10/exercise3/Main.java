package com.pao.laboratory10.exercise3;

import com.pao.laboratory10.exercise1.TipTranzactie;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Tranzactie> tranzactii = Arrays.asList(
                new Tranzactie(1, 1500.00, "2024-01-05", TipTranzactie.CREDIT, "RO01BANC123"),
                new Tranzactie(2, 250.50,  "2024-01-12", TipTranzactie.DEBIT,  "RO01BANC123"),
                new Tranzactie(3, 100.00,  "2024-01-20", TipTranzactie.DEBIT,  "RO02BANC456"),
                new Tranzactie(4, 3400.00, "2024-02-01", TipTranzactie.CREDIT, "RO03BANC789"),
                new Tranzactie(5, 120.00,  "2024-02-14", TipTranzactie.DEBIT,  "RO01BANC123"),
                new Tranzactie(6, 450.00,  "2024-02-28", TipTranzactie.DEBIT,  "RO04BANC000"),
                new Tranzactie(7, 2000.00, "2024-03-05", TipTranzactie.CREDIT, "RO01BANC123"),
                new Tranzactie(8, 75.00,   "2024-03-10", TipTranzactie.DEBIT,  "RO02BANC456"),
                new Tranzactie(9, 850.00,  "2024-03-15", TipTranzactie.DEBIT,  "RO03BANC789"),
                new Tranzactie(10, 500.00, "2024-03-22", TipTranzactie.CREDIT, "RO05BANC111")
        );

        System.out.println("Tranzactii de tip CREDIT:");
        tranzactii.stream()
                .filter(t -> t.getTip() == TipTranzactie.CREDIT)
                .forEach(System.out::println);

        double total = tranzactii.stream().mapToDouble(Tranzactie::getSuma).sum();
        System.out.printf(Locale.US, "\nTotal procesat: %.2f RON\n", total);

        System.out.println("\nSume grupate pe luna:");
        tranzactii.stream()
                .collect(Collectors.groupingBy(Tranzactie::getLuna, TreeMap::new, Collectors.summingDouble(Tranzactie::getSuma)))
                .forEach((luna, suma) -> System.out.printf(Locale.US, "%s: %.2f RON\n", luna, suma));

        System.out.println("\nTop 3 tranzactii:");
        tranzactii.stream()
                .sorted(Comparator.comparingDouble(Tranzactie::getSuma).reversed())
                .limit(3)
                .forEach(System.out::println);

        List<String> conturi = tranzactii.stream()
                .map(Tranzactie::getContSursa)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("\nConturi sursa unice: " + conturi);

        double media = tranzactii.stream().mapToDouble(Tranzactie::getSuma).average().orElse(0.0);
        System.out.printf(Locale.US, "\nSuma medie: %.2f RON\n\n", media);

        tranzactii.stream()
                .collect(Collectors.groupingBy(Tranzactie::getLuna, TreeMap::new, Collectors.toList()))
                .forEach((luna, lista) -> {
                    double sumaLuna = lista.stream().mapToDouble(Tranzactie::getSuma).sum();
                    System.out.printf(Locale.US, "EXTRAS DE CONT - %s: %d tranzactii, total: %.2f RON\n",
                            luna, lista.size(), sumaLuna);
                });
    }
}