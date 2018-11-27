import java.io.File

object Driver extends App{

  createTable1()
  createTable2()
  createTable3()
  KmeansModel.clusterByBlock("/home/dbda/Desktop/Rochan/final_clustering/clusterbyblock/input.txt")
  KmeansModel.clusterByCustomer("/home/dbda/Desktop/Rochan/final_clustering/clusterbycustomer/input.txt")
  BisectingKmeans.clusterByBlock("/home/dbda/Desktop/Rochan/final_clustering/clusterbyblock/input.txt")
  BisectingKmeans.clusterByCustomer("/home/dbda/Desktop/Rochan/final_clustering/clusterbycustomer/input.txt")
  ArimaModel.runModel("/home/dbda/Desktop/Rochan/Final_arima/arima_data.csv")
  RFModel.runModel("file:/home/dbda/Anup/ProjCode/Weather_consumption.csv")
  Correlations
  }
