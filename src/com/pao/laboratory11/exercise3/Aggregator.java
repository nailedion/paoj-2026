package com.pao.laboratory11.exercise3;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Aggregator {
    Map<String, Long> byCountry = new HashMap<>();
    Map<String, Long> byChannel = new HashMap<>();
    BigDecimal total = BigDecimal.ZERO;
    List<Transaction> allTransactions = new ArrayList<>();

    void accumulate(Transaction tx) {
        byCountry.merge(tx.getCountry(), 1L, Long::sum);
        byChannel.merge(tx.getChannel(), 1L, Long::sum);
        total = total.add(tx.getAmount());
        allTransactions.add(tx);
    }

    Aggregator combine(Aggregator other) {
        other.byCountry.forEach((k, v) -> this.byCountry.merge(k, v, Long::sum));
        other.byChannel.forEach((k, v) -> this.byChannel.merge(k, v, Long::sum));
        this.total = this.total.add(other.total);
        this.allTransactions.addAll(other.allTransactions);
        return this;
    }

    Snapshot finish(int topN) {
        List<Transaction> sortedTxs = new ArrayList<>(allTransactions);
        sortedTxs.sort(Comparator
                .comparing(Transaction::getAmount).reversed()
                .thenComparingInt(Transaction::getId));

        List<Transaction> topTxs = sortedTxs.stream()
                .limit(topN)
                .collect(Collectors.toList());

        return new Snapshot(byCountry, byChannel, total, topTxs);
    }
}