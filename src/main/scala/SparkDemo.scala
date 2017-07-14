/**
  * Created by shubham on 7/13/17.
  */

//import org.apache.spark.SparkContext
//import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
object SparkDemo extends App{
  val conf = new SparkConf().setMaster("local[2]").setAppName("simpleApp")
//  val sc = new SparkContext(conf)

  val ssc = new StreamingContext(conf, Seconds(10))
  val simpleData = ssc.sparkContext.textFile("/home/shubham/Documents/example").flatMap(x=>x.split(" ")).map((_,1)).reduceByKey(_ + _)
  println("====simple data is ====="+simpleData.collect.foreach(println))
  val dstream = ssc.textFileStream("/home/shubham/Documents/abc")
  val stremingData = dstream.flatMap(x=>x.split(" ")).map((_,1)).reduceByKey(_ + _)
   val  result = stremingData.transform{rdd=>rdd.join(simpleData).map{
     case(name,(v1,v2)) => println("(name,(v1,v2))=="+(name,(v1,v2)));(name , v1+v2)
   }}
  result.print
    ssc.start()
  ssc.awaitTermination()

}
