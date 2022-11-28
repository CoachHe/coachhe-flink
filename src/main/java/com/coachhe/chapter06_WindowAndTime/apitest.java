package com.coachhe.chapter06_WindowAndTime;

import org.apache.flink.api.common.state.StateTtlConfig;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author CoachHe
 * @date 2022/11/29 01:52
 **/
public class apitest {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置为处理时间
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
    }
}
