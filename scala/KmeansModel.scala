import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.ml.evaluation.ClusteringEvaluator

object KmeansModel extends Context {
  def clusterByBlock(path:String): Unit ={
    val dsWithSchema = spark.read.format("libsvm").option("numFeatures", "1").load(path)
    // this loop runs 8 times
    for (x <- 4 to 8){
      //setting number of clusters for kmeans and setting a seed for producing same model(controlling randomness)
      val kmeans = new KMeans().setK(x).setSeed(1L)
      //fitting our model
      val model = kmeans.fit(dsWithSchema)
      //predicting clusters
      val predictions = model.transform(dsWithSchema)
      //creating an evaluator
      val evaluator = new ClusteringEvaluator()
      //evaluating our model
      val silhouette = evaluator.evaluate(predictions)
      println("**********************************************")
      println("for k:"+x)
      println("Cluster Centers: ")
      model.clusterCenters.foreach(println)
      println(s"Silhouette with squared euclidean distance = $silhouette")
    }

    }
  def clusterByCustomer(path:String): Unit ={
    val dsWithSchema = spark.read.format("libsvm").option("numFeatures", "1").load(path)

    for (x <- 4 to 8){
      val kmeans = new KMeans().setK(x).setSeed(1L)
      val model = kmeans.fit(dsWithSchema)
      val predictions = model.transform(dsWithSchema)
      val evaluator = new ClusteringEvaluator()
      val silhouette = evaluator.evaluate(predictions)
      println("**********************************************")
      println("for k:"+x)
      println("Cluster Centers: ")
      model.clusterCenters.foreach(println)
      println(s"Silhouette with squared euclidean distance = $silhouette")
      // to get the predictions
      //predictions.show()
    }
  }
}
