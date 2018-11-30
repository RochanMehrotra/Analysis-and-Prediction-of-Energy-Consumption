import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.tree.RandomForest

object RFModel extends Context {
  def runModel(path:String){
  var df = sc.textFile(path)
  var first = df.first
  var df_headless = df.filter( x=> x != first && ! x.contains("null"))
  //Split the data
  var df_headless_split = df_headless.toLocalIterator.toList.splitAt(700)
  val train_ls = df_headless_split._1
  val test_ls = df_headless_split._2
  //labeling Training data
  val df1 = train_ls.map(x =>{
    val field = x.split(",")
    LabeledPoint(field(21).toDouble, Vectors.dense(field(1).toDouble,field(2).toDouble,field(4).toDouble,field(5).toDouble,field(6).toDouble,field(7).toDouble,field(8).toDouble,field(10).toDouble,field(11).toDouble,field(12).toDouble,field(13).toDouble,field(14).toDouble,field(15).toDouble,field(16).toDouble,field(17).toDouble,field(19).toDouble,field(20).toDouble)) })
  val df_train = sc.parallelize(df1).toJavaRDD
  //labeling test data
  val df2 = test_ls.map(x =>{
    val field = x.split(",")
    LabeledPoint(field(21).toDouble, Vectors.dense(field(1).toDouble,field(2).toDouble,field(4).toDouble,field(5).toDouble,field(6).toDouble,field(7).toDouble,field(8).toDouble,field(10).toDouble,field(11).toDouble,field(12).toDouble,field(13).toDouble,field(14).toDouble,field(15).toDouble,field(16).toDouble,field(17).toDouble,field(19).toDouble,field(20).toDouble)) })
  val df_test = sc.parallelize(df2).toJavaRDD
  val categoricalFeaturesInfo = Map[Int, Int]()
  val numTrees = 500 // Use more in practice.
  val featureSubsetStrategy = "auto" // Let the algorithm choose.
  val impurity = "variance"
  val maxDepth = 4
  val maxBins = 32
  //Model Building and Predicting
  val model = RandomForest.trainRegressor(df_train, categoricalFeaturesInfo,numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)
  val ft = df_test.rdd.map(_.features)
  val prd = model.predict(ft)
  val dif = prd.zip(df_test.rdd.map(_.label))
  //ERRORS
  val totalErrSquare = dif.map(x => Math.pow((x._1 - x._2),2).toDouble).sum
  val Mape = dif.map(x => Math.abs((x._1 - x._2)/x._2).toDouble).sum
  val RMSEErr = Math.sqrt(totalErrSquare/dif.count)
  }

}
