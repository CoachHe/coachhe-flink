package com.coachhe.wc;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.operators.UnsortedGrouping;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;


/**
 * @author CoachHe
 * @date 2022/11/5 15:10
 **/
public class BatchWordCount {
    public static void main(String[] args) {
        // 1. 创建执行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // 2. 从文件中读取数据
        DataSource<String> lineDS = env.readTextFile("input/words.txt");

        // 3. 将每行数据进行分词，转换成二元祖类型
        FlatMapOperator<String, Tuple2<String, Long>> workAddOne = lineDS.flatMap((String line, Collector<Tuple2<String, Long>> out) -> {
            // 将一行文本进行分词
            String[] words = line.split(" ");
            // 将每个单词转换成二元祖
            for (String word : words) {
                out.collect(Tuple2.of(word, 1L));
            }
        }).returns(Types.TUPLE(Types.STRING, Types.LONG));

        //4. 按照word进行分组
        UnsortedGrouping<Tuple2<String, Long>> workAddOneGroup = workAddOne.groupBy(0);

        // 5. 分组内进行聚合统计
        AggregateOperator<Tuple2<String, Long>> sum = workAddOneGroup.sum(1);

        // 6. 结果的打印输出
        try {
            sum.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
