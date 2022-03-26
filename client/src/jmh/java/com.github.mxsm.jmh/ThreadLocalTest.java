package com.github.mxsm.jmh;

import io.netty.util.concurrent.FastThreadLocal;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author mxsm
 * @date 2022/1/22 8:47
 * @Since 1.0.0
 */

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 5)
@Threads(4)
@Fork(1)
@State(value = Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ThreadLocalTest {


    private FastThreadLocal<Long> fastThreadLocal = new FastThreadLocal();

    private ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    private long a = 1000;

    @Benchmark
    public void fastThreadLocal(Blackhole blackhole) {

        for(int i = 0; i < 100000;++i){
            fastThreadLocal.set(a);
            fastThreadLocal.get();
        }
        fastThreadLocal.remove();
        blackhole.consume(a);
    }

    @Benchmark
    public void threadLocal(Blackhole blackhole) {
        for(int i = 0; i < 100000;++i){
            threadLocal.set(a);
            threadLocal.get();
        }
        threadLocal.remove();
        blackhole.consume(a);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(ThreadLocalTest.class.getSimpleName())
            .result("result.json")
            .resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
    }


}
