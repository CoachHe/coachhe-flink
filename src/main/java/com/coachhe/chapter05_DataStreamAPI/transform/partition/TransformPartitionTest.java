package com.coachhe.chapter05_DataStreamAPI.transform.partition;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

public class TransformPartitionTest {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        env.setParallelism(1);


//        DataStreamSource<Event> stream = env.fromElements(new Event("Mary", "./home", 1000L),
//                new Event("Bob", "./cart", 2000L),
//                new Event("Alice", "./prod?id=100", 3000L),
//                new Event("Alice", "./prod?id=200", 3500L),
//                new Event("Bob", "./prod?id=2", 2500L),
//                new Event("Alice", "./prod?id=300", 3600L),
//                new Event("Bob", "./home", 3000L),
//                new Event("Bob", "./prod?id=1", 2300L),
//                new Event("Bob", "./prod?id=3", 3300L));

        // 1. 随机分区
//        stream.shuffle().print().setParallelism(4);

        // 2. 轮询分区
//        stream.rebalance().print().setParallelism(4);

        // 3. rescale 重缩放分区
//        stream.rescale().print().setParallelism(4);

        // 演示rebalance和rescale的区别
        env.addSource(new RichParallelSourceFunction<Integer>() {
            @Override
            public void run(SourceContext<Integer> sourceContext) throws Exception {
                for (int i = 0; i < 8; i++) {
                    // 将偶数和基数分开，将奇偶数分别发送到0号和1号并行分区
                    if (i % 2 == getRuntimeContext().getIndexOfThisSubtask()) {
                        sourceContext.collect(i);
                    }
                }
            }

            @Override
            public void cancel() {

            }
        }).setParallelism(2)
//                .rescale()
//                .rebalance()
//                .broadcast()
                .global()
                .print()
                .setParallelism(4);

        env.execute();

    }
}
