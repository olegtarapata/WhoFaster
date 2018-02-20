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
import org.openjdk.jmh.infra.Blackhole;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author Oleg Tarapata (oleh.tarapata@gmail.com)
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class LoopBenchmark {

    private int[] array;

    @Param({"10", "100", "1000", "10000", "100000"})
    private int n;

    @Setup
    public void setup() {
        array = new int[n];
    }

    @Benchmark
    public void usualLoop(final Blackhole blackhole) {
        for (int i = 0; i < 1000; i++) {
            blackhole.consume(i);
        }
    }

    @Benchmark
    public void arrayForeachLoop(final Blackhole blackhole) {
        for (int anArray : array) {
            blackhole.consume(anArray);
        }
    }

    @Benchmark
    public void arrayStreamLoop(final Blackhole blackhole) {
        Arrays.stream(array).forEach(blackhole::consume);
    }
}
