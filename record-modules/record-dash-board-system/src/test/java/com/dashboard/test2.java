package com.dashboard;

import com.caucho.hessian.io.Hessian2Output;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
@State(Scope.Thread)
public class test2 {
    private final Kryo kryo = new Kryo();
    private final List<Unit> unitList=new ArrayList<>();


    public static void main(String[] args) throws RunnerException {
        // 创建一个OptionsBuilder实例，用于设置JMH的运行选项
        Options opt = new OptionsBuilder()
                .include(".*" + test2.class.getSimpleName() + ".*") // 匹配你的基准测试类
                .warmupIterations(3) // 设置预热迭代次数
                .measurementIterations(10) // 设置测量迭代次数
                .forks(0) // 设置fork的数量
                .threads(16) // 设置线程数量
                .mode(Mode.AverageTime) // 设置基准测试模式
                .timeUnit(TimeUnit.MILLISECONDS) // 设置时间单位
                .build();

        // 使用Runner来运行基准测试
        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        kryo.register(Unit.class);
        kryo.register(BaseBo.class);
        kryo.register(ArrayList.class);
        kryo.register(Byte.class);
        for (int i = 0; i < 200; i++) {
            Unit unit = new Unit();
            unit.setName("斤");
            unit.setId("99933240653ff9e50165551b44db09a3");
            unit.setEntityId("99933240");
            unit.setSelfEntityId("99933240");
            unit.setSortCode(0);
            unit.setCreateTime(20180820101255131L);
            unit.setOpUserId("999337238b5b3ce2018b656ec470000a");
            unit.setOpUserName("青蓼");
            unit.setIsValid((short) 1);
            unit.setLastVer(1);
            unitList.add(unit);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public byte[] serialize() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Output output = new Output(bos);
        kryo.writeClassAndObject(output,unitList);
        byte[] serializedData = output.toBytes();
        output.close();
        return serializedData;
    }

//    @Benchmark
//    @BenchmarkMode(Mode.AverageTime)
//    @OutputTimeUnit(TimeUnit.NANOSECONDS)
//    public Object deserialize() {
//        ByteArrayInputStream bos = new ByteArrayInputStream (serializedData);
//        Input input = new Input(bos);
//        Object deserializedObject = kryo.readClassAndObject(input);
//        input.close();
//        return deserializedObject;
//    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public byte[] serializeHessian() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Hessian2Output ho = new Hessian2Output(bos);
        ho.writeObject(unitList);
        byte[] serializedData = bos.toByteArray();
        bos.close();
        return serializedData;
    }

}

