package com.coachhe.chapter05_DataStreamAPI.datatype;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author CoachHe
 * @date 2022/11/28 01:28
 **/
public class BasicDataType {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // 1. 基本数据类型
        DataStreamSource<Long> stream = env.fromElements(1L, 2L, 3L, 4L);
        stream.map(n -> n + 1).print();
        env.execute();

    }
}
