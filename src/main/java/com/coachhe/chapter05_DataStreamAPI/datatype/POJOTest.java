package com.coachhe.chapter05_DataStreamAPI.datatype;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author CoachHe
 * @date 2022/11/28 00:58
 **/
public class POJOTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // 3. POJO
        class Person {
            // 两个字段都是公有字段
            public String name;
            public int age;

            // 提供了默认构造函数
            public Person(){}

            public Person(String name, int age) {
                this.name = name;
                this.age = age;
            }
        }

        env.fromElements(
                new Person("Alex", 42),
                new Person("Wendy", 23)
        );


        env.execute();
    }
}
