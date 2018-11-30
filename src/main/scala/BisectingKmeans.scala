import org.apache.spark.ml.clustering.BisectingKMeans

object BisectingKmeans extends Context {
  def clusterByBlock(path:String): Unit = {
    val dsWithSchema = spark.read.format("libsvm").load(path)
    for (x <- 4 to 8){
      val bkm = new BisectingKMeans().setK(x).setSeed(1L)
      val model = bkm.fit(dsWithSchema)
      val cost = model.computeCost(dsWithSchema)
      println("**********************************************")
      println("for k:"+x)
      println(s"Within Set Sum of Squared Errors = $cost")
      println("Cluster Centers: ")
      val centers = model.clusterCenters
      centers.foreach(println)
      //model.summary.predictions.toJavaRDD.saveAsTextFile("/home/dbda/Desktop/Day001-blockClusteringh/"+x+"h.out")
      println("**********************************************")
      // to get the predictions
    //  model.summary.predictions.show
    }



  }

  def clusterByCustomer(path:String): Unit = {
    val dsWithSchema = spark.read.format("libsvm").load(path)
    for (x <- 4 to 8){
      val bkm = new BisectingKMeans().setK(x).setSeed(1L)
      val model = bkm.fit(dsWithSchema)
      val cost = model.computeCost(dsWithSchema)
      println("**********************************************")
      println("for k:"+x)
      println(s"Within Set Sum of Squared Errors = $cost")
      println("Cluster Centers: ")
      val centers = model.clusterCenters
      centers.foreach(println)
      println("**********************************************")
      // to get the predictions
      //predictions.toJavaRDD.saveAsTextFile("/home/dbda/Desktop/Rochan/final_clustering/clusterbycustomer/outputforbisectingkmeasn")

    }

  }
}
