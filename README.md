# Flink Tutorial

Instructions:

To run KafkaStreamingJob,
first start kafka zookeeper server and broker
`cd ~/dev/kafka_2.11-2.0.0/`
`./bin/zookeeper-server-start.sh ./config/zookeeper.properties`
`./bin/kafka-server-start.sh ./config/server.properties`

verify it works by the console:
`./bin/kafka-console-producer.sh --topic test --broker-list localhost:9092`
`./bin/kafka-console-consumer.sh --topic test --bootstrap-server localhost:9092`

run kafkaStreamingJob with program argument:
`--topic test --bootstrap.servers localhost:9092 --zookeeper.connect localhost:2181 --group.id myGroup`
produce something in producer console, verify console prints the expected aggregation.







Source: \n
Basic batch/stream processing
https://github.com/apache/flink/tree/master/flink-examples/flink-examples-streaming/src

Flink with Kafka:
https://data-artisans.com/blog/kafka-flink-a-practical-how-to

Exactly once kafka consuming:
https://flink.apache.org/features/2018/03/01/end-to-end-exactly-once-apache-flink.html


