package org.whofaster.loop;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Oleg Tarapata (oleh.tarapata@gmail.com)
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 10, time = 1)
@Fork(1)
@State(Scope.Benchmark)
public class ListMapBenchmark {

    @Param({"10", "100", "1000", "10000", "100000"})
    private int n;

    private List<Integer> origin;

    @Setup
    public void setup() {
        origin = new ArrayList<>(Collections.nCopies(n, 0));
    }

    @Benchmark
    public Object forLoop() {
        final List<Integer> result = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            result.set(i, i + 1);
        }
        return result;
    }

    @Benchmark
    public Object stream() {
        return origin.stream().map(i -> i + 1).collect(Collectors.toList());
    }
}
