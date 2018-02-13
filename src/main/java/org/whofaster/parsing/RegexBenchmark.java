package org.whofaster.parsing;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @author Oleg Tarapata (oleh.tarapata@gmail.com)
 */
public class RegexBenchmark {

    @Benchmark
    @Fork(value = 1)
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 10, time = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public boolean parseEmail(PatternHolder patternHolder) {
        return patternHolder.pattern.matcher("test@email.com").matches();
    }

    @State(Scope.Benchmark)
    public static class PatternHolder {
        Pattern pattern = Pattern.compile(".*@.*\\..*");
    }
}
