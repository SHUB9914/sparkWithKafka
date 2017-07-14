/**
  * Created by shubham on 7/13/17.
  */

import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._


object SparkWithKafka extends App{
  val topics = Map("mytopic"->1)
  val conf = new SparkConf().setMaster("local[2]").setAppName("simpleApp")
  val ssc = new StreamingContext(conf, Seconds(10))

  val lines = KafkaUtils.createStream(ssc , "localhost:2181","default",topics).map(_._2)
  val result =  lines.flatMap(x=>x.split(" ")).map((_,1)).reduceByKey(_ + _)
  result.print

  ssc.start()
  ssc.awaitTermination()

}
