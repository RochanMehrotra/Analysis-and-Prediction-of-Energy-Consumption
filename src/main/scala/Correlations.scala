import org.apache.spark.sql.functions._
object Correlations extends Context {
  //to get correlation b/w weatherIndex and consumptionavg

  val df1 = spark.read.format("csv").option("header", true).load("/home/dbda/Desktop/Rochan/correlation/Weather_consumption_normalize.csv")
  val cleaned = df1.drop("Date", " temperatureMax", " windBearing", " dewPoint", " cloudCover", " windSpeed", " pressure", " apparentTemperatureHigh", " visibility", " humidity", " apparentTemperatureLow", " apparentTemperatureMax", " temperatureLow", " temperatureMin", " temperatureHigh", " apparentTemperatureMin", " moonPhase", " Consumption", "consumptionavg")
  val consumption = df1.select("consumptionavg").rdd.toLocalIterator.toList
  val sumDF = cleaned.withColumn("TOTAL", cleaned.columns.map(c => col(c)).reduce((c1, c2) => c1 + c2)).rdd.toLocalIterator.toList
  val combined = consumption.zip(sumDF)

  val df2 = spark.read.format("csv").option("header", true).load("/home/dbda/Desktop/Rochan/correlation/corr.csv")
  val df3 = df2.selectExpr("cast(consumption as double) consumption", "cast(Total as double) Total")
  df3.stat.corr("consumption", "Total")
  sc.parallelize(combined).coalesce(1, true).saveAsTextFile("/home/dbda/Desktop/Rochan/correlation/output")



  //to get correlation b/w other weather features and consumptionavg


  val df4 = spark.read.format("csv").option("header", true).load("/home/dbda/Desktop/Rochan/correlation/Weather_consumption_normalize.csv")
  val other = df4.select("temperatureMax", "dewPoint", "cloudCover", "windSpeed", "pressure", "visibility", "humidity", "temperatureMin", "moonPhase", "consumptionavg")
  val colnames = other.columns
  for (x <- 0 until colnames.size - 1) {
    print(colnames(x))
    println(other.selectExpr("cast(consumptionavg as Double)", "cast(" + colnames(x) + " as Double)").stat.corr("consumptionavg", colnames(x)))
  }

}
