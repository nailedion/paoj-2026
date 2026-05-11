package com.pao.laboratory11.exercise3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CustomCollectors {
    public static Collector<Transaction, ?, Snapshot> toSnapshot(int topN) {
        return Collector.of(
                Aggregator::new,
                Aggregator::accumulate,
                Aggregator::combine,
                agg -> agg.finish(topN)
        );
    }
}