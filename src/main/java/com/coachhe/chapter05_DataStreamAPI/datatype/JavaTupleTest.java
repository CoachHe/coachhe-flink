package com.coachhe.chapter05_DataStreamAPI.datatype;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author CoachHe
 * @date 2022/11/28 01:29
 **/
public class JavaTupleTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

//         Java元祖的使用
        Tuple2<String, Integer> personTuple = Tuple2.of("Alex", 42);
        Integer age = personTuple.getField(1); // 获取第2个元素，也就是age=42
        personTuple.f1 = 43; // 将第二个字段设为42
        personTuple.setField(42, 1); // 将第二个字段设为42


        // 2. Java和Scala元祖
        DataStreamSource<Tuple2<String, Integer>> personStream = env.fromElements(
                Tuple2.of("Adam", 17),
                Tuple2.of("Sarah", 23)
        );
//         过滤出那些年龄大于18的人
        personStream
                .filter(p -> p.f1 > 18)
                .print();
    }
}
