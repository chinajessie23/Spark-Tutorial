package org.myorg.quickstart;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.util.Collector;

// tutorial from https://data-artisans.com/blog/kafka-flink-a-practical-how-to
public class KafkaStreamingJob
{
    //following example counts the words coming from a kafka stream in 5 second windows.
    public static void main(String[] args) throws Exception {

        //writing and reading from kafka
        //http://training.data-artisans.com/exercises/toFromKafka.html

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        ParameterTool parameterTool = ParameterTool.fromArgs(args);
        DataStream < String > messageStream = env.addSource(new FlinkKafkaConsumer011< >(parameterTool.getRequired("topic"), new SimpleStringSchema(), parameterTool.getProperties()));


        DataStream<Tuple2<String, Integer>> dataStream = messageStream
            // split up the lines in pairs (2-tuples) containing: (word,1)
            .flatMap(new Splitter())
            // group by the tuple field "0" and sum up tuple field "1"
            .keyBy(0)
            .timeWindow(Time.seconds(5))
            .sum(1)
            ;

        dataStream.print();

        env.execute("Kafka Streaming");
    }

    public static class Splitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String sentence, Collector<Tuple2<String, Integer>> out) throws Exception {
            for (String word: sentence.split(" ")) {
                out.collect(new Tuple2<String, Integer>(word, 1));
            }
        }
    }
}
