package com.coachhe.chapter05_DataStreamAPI.transform.reduce;

import com.coachhe.chapter05_DataStreamAPI.Event;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author: CoachHe
 * @Date: 2022/11/26 17:16
 */
public class TransformReduceTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStreamSource<Event> stream = env.fromElements(new Event("Mary", "./home", 1000L),
                new Event("Bob", "./cart", 2000L),
                new Event("Alice", "./prod?id=100", 3000L),
                new Event("Bob", "./prod?id=1", 3300L),
                new Event("Alice", "./prod?id=200", 3200L),
                new Event("Bob", "./home", 3500L),
                new Event("Bob", "./prod?id=2", 3800L),
                new Event("Bob", "./prod?id=3", 4200L)
        );

        // 1.统计每个用户的访问频次
        SingleOutputStreamOperator<Tuple2<String, Long>> clickByUser = stream
                // 首先使用map方法将stream中的event挨个转换成Tuple2<String, Long>的形式,其中第一个元素是user，第二个元素固定是1L
                .map((MapFunction<Event, Tuple2<String, Long>>) event -> Tuple2.of(event.user, 1L))
                // 然后将Tuple2<String, Long>类型的所有data根据其f0元素（也就是Tuple2的第一个元素event.user）分组，获得一个KeyedStream
                .keyBy(data -> data.f0)
                // 最后将keyStream进行reduce操作，对每两个元素(Tuple2<String, Long>类型)执行函数，最终获得Tuple2类型的一个结果,第一个元素是user，第二个元素是1L之和，其实就是有多少个点击次数
                .reduce((ReduceFunction<Tuple2<String, Long>>) (value1, value2) -> Tuple2.of(value1.f0, value1.f1 + value2.f1)
                );

        // 2. 获取当前最活跃的用户
        SingleOutputStreamOperator<Tuple2<String, Long>> result = clickByUser
                // 首先根据对应的key（也就是用户）进行分组
                .keyBy(data -> "key")
                // 然后比较每个值的f1，也就是点击量之和，最终输出最大值
                .reduce((ReduceFunction<Tuple2<String, Long>>) (value1, value2) -> value1.f1 > value2.f1 ? value1 : value2);

        result.print();

        env.execute();
    }


}
