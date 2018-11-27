import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

trait Context {
  val conf = new SparkConf().setMaster("local[4]").setAppName("CdacProject")
  val spark = SparkSession.builder.config(conf).getOrCreate()
  val sc=spark.sparkContext
  val SQLContext=spark.sqlContext
}
