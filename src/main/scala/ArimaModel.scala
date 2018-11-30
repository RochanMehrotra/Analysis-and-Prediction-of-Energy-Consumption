
import com.cloudera.sparkts.models.ARIMA


import org.apache.spark.mllib.linalg.Vectors
object ArimaModel extends Context {
  def runModel(path:String){
  val data = sc.textFile(path)
  val traindata= data.toLocalIterator.toList.splitAt(700)._1
  val testdata= data.toLocalIterator.toList.splitAt(700)._2
  val train=Vectors.dense(traindata.map(_.toDouble).toIterator.toArray)
  val test=Vectors.dense(testdata.map(_.toDouble).toIterator.toArray)


  // running loop for getting values where x<-p, y<-d and z<-q
  for(x <-1 to 10){
    for(y <-0 to 5){
      for(z <-0 to 2){
        //fitting model on our data
        val arimamodel = ARIMA.fitModel(x,y,z,train)
        //forecasting 128 values
        val num_forecast=128
        // here we will predict 128
        val predicted = arimamodel.forecast(train, num_forecast)
        //printing the values of p,q,d on which model was build
        println("p="+arimamodel.p+"  d="+arimamodel.d+"  q="+arimamodel.q)
        //Sum of square of error
        var totalErrorSquare =0.0
        var MAPE_error=0.0
        for (i <- 700 until data.count.toInt) {
          var errorSquare = Math.pow(predicted(i) - train(i-700), 2)
          totalErrorSquare =totalErrorSquare+ errorSquare
          var error = Math.abs((train(i-700)-predicted(i))/train(i-700))
          MAPE_error = MAPE_error+error
        }
        println("totalErrorSquare "+totalErrorSquare)
        // root mean square error
        var RMSE= Math.sqrt(totalErrorSquare/test.size)
        println("RMSE "+RMSE)
        //mean absolute percentage error
        var MAPE =(MAPE_error/test.size)*100
        println("MAPE "+MAPE)
        println("AIC "+arimamodel.approxAIC(test))
        println("********************************************************")
      }
    }
  }
  }

}
