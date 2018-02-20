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
@Warmup(iterations = 5, time = 3)
@Measurement(iterations = 10, time = 3)
@Fork(1)
@State(Scope.Benchmark)
public class ArrayIterationBenchmark {

    private int[] array;

    @Param({"10", "100", "1000", "10000", "100000"})
    private int n;

    @Setup
    public void setup() {
        array = new int[n];
    }

    @Benchmark
    public void arrayForiLoop(final Blackhole blackhole) {
        for (int i = 0; i < array.length; i++) {
            blackhole.consume(array[i]);
        }
    }

    @Benchmark
    public void arrayForiLoopWithCachedLength(final Blackhole blackhole) {
        final int length = array.length;
        for (int i = 0; i < length; i++) {
            blackhole.consume(array[i]);
        }
    }

    @Benchmark
    public void arrayForeachLoop(final Blackhole blackhole) {
        for (int element : array) {
            blackhole.consume(element);
        }
    }

    @Benchmark
    public void arrayStreamLoop(final Blackhole blackhole) {
        Arrays.stream(array).forEach(blackhole::consume);
    }
}
