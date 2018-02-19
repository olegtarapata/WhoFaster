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

import static java.util.UUID.randomUUID;

/**
 * @author Oleg Tarapata (oleh.tarapata@gmail.com)
 */
@Fork(value = 1)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 10, time = 1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class RegexBenchmark {

    @Benchmark
    public boolean parseEmail(PatternHolder patternHolder) {
        return patternHolder.pattern.matcher("test@email.com").matches();
    }

    @Benchmark
    public String[] splitByRegex(PatternHolder patternHolder) {
        return patternHolder.toParse.split(patternHolder.delimiter);
    }

    @Benchmark
    public String[] splitByIndexOf(PatternHolder patternHolder) {
        final String[] result = new String[patternHolder.n];
        int i = 0;
        final String toParse = patternHolder.toParse;
        final int length = toParse.length();
        int index = 0;
        int nextIndex;
        while (index < length) {
            nextIndex = toParse.indexOf(patternHolder.delimiter, index);
            if (nextIndex < 0) {
                nextIndex = length;
            }
            result[i] = toParse.substring(index, nextIndex);
            i++;
            index = nextIndex + 1;
        }
        return result;
    }

    @State(Scope.Benchmark)
    public static class PatternHolder {
        final Pattern pattern = Pattern.compile(".*@.*\\..*");
        final int n = 10;
        final String delimiter = ",";
        final String toParse;

        public PatternHolder() {
            final StringBuilder builder = new StringBuilder();
            builder.append(randomUUID().toString());
            for (int i = 0; i < n - 1; i++) {
                builder.append(",");
                builder.append(randomUUID().toString());
            }
            toParse = builder.toString();
        }
    }
}
