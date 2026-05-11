package com.pao.laboratory11.exercise3;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Transaction> data = Arrays.asList(
                new Transaction(1, new BigDecimal("1200.00"), LocalDate.of(2026, 5, 1), "RO", "WEB"),
                new Transaction(2, new BigDecimal("90.00"), LocalDate.of(2026, 5, 1), "RU", "ATM"),
                new Transaction(3, new BigDecimal("6000.00"), LocalDate.of(2026, 5, 2), "NG", "APP"),
                new Transaction(4, new BigDecimal("1200.00"), LocalDate.of(2026, 5, 3), "RO", "POS"),
                new Transaction(5, new BigDecimal("50.00"), LocalDate.of(2026, 5, 4), "RO", "APP")
        );

        Snapshot snap = data.stream().collect(CustomCollectors.toSnapshot(3));

        System.out.println("1. Suma totala procesata:");
        System.out.println("Total: " + snap.getTotalAmount() + "\n");

        System.out.println("2. Top tranzactii ca valoare:");
        snap.getTopTransactions().forEach(System.out::println);
        System.out.println();

        System.out.println("3. Numar de tranzactii per canal (sortat):");
        snap.getCountByChannel().entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue() + " tranzactii"));
    }
}