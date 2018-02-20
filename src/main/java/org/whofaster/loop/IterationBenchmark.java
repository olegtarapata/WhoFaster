package org.whofaster.loop;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * @author Oleg Tarapata (oleh.tarapata@gmail.com)
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 3)
@Measurement(iterations = 10, time = 3)
@Fork(1)
@State(Scope.Benchmark)
public class IterationBenchmark {

    @Param({"10", "100", "1000", "10000", "100000"})
    private int n;

    @Benchmark
    public void foriLoop(final Blackhole blackhole) {
        for (int i = 0; i < n; i++) {
            blackhole.consume(i);
        }
    }

    @Benchmark
    public void foriLoopWithCachedLength(final Blackhole blackhole) {
        final int length = n;
        for (int i = 0; i < length; i++) {
            blackhole.consume(i);
        }
    }
}
