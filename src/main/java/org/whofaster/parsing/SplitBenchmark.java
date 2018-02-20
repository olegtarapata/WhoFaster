package org.whofaster.parsing;

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

import java.util.concurrent.TimeUnit;

import static java.util.UUID.randomUUID;

/**
 * @author Oleg Tarapata (oleh.tarapata@gmail.com)
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 10, time = 1)
@Fork(1)
@State(Scope.Benchmark)
public class SplitBenchmark {

    private static final String DELIMITER = ",";

    @Param({"10", "100", "1000", "10000", "100000"})
    private int n;

    private String toParse;

    @Setup
    public void setup() {
        final StringBuilder builder = new StringBuilder();
        builder.append(randomUUID().toString());
        for (int i = 0; i < n - 1; i++) {
            builder.append(DELIMITER);
            builder.append(randomUUID().toString());
        }
        toParse = builder.toString();
    }

    @Benchmark
    public String[] splitByRegex() {
        return toParse.split(DELIMITER);
    }

    @Benchmark
    public String[] splitByIndexOf() {
        final String[] result = new String[n];
        int i = 0;
        final int length = toParse.length();
        int index = 0;
        int nextIndex;
        while (index < length) {
            nextIndex = toParse.indexOf(DELIMITER, index);
            if (nextIndex < 0) {
                nextIndex = length;
            }
            result[i] = toParse.substring(index, nextIndex);
            i++;
            index = nextIndex + 1;
        }
        return result;
    }
}
