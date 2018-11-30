import java.io.File


import org.apache.commons.lang.StringUtils

object Tables extends Context {
  //Table:1

  // creating an empty RDD of type String
  var f_df = sc.emptyRDD[String]


  def createTable1(path:String){
  // iterating over the directory containg  data files to get file names
    val files = (new File(path)).listFiles.filter(_.isFile).map(_.getAbsolutePath)


  //Customer level aggregation
  //for each file, remove header and then merge with empty RDD f_df
  for(file <- files) {
    println(file)
    var df = sc.textFile("file:"+file)
    var first = df.first
    var df_headless = df.filter(_ != first)
    var blockid = file.substring(43,file.length-4)
    var df_blk = df_headless.map(x => blockid+"-"+x)
    f_df = f_df.union(df_blk)
  }
  var data = f_df.filter(x=> !x.contains(",,,") && x.split(",")(1).contains("2013") && StringUtils.isNotEmpty(x.split(",")(7))).map(x => (x.split(",")(0),x.split(",")(7).toDouble)).reduceByKey(_+_).map(x => x._1.split("-")(0)+","+x._1.split("-")(1)+","+x._2)

  data.coalesce(1,true).saveAsTextFile("/home/rochan/cdacdata/table1")//file:/home/dbda/anup/projcode/cust_aggr
  }

 /* //Table:2
  def createTable2() {
    //Block level aggregation
    //for each file, remove header and then merge with empty RDD f_df
    for (file <- files) {
      println(file)
      var df = sc.textFile("file:" + file)
      var first = df.first
      var df_headless = df.filter(_ != first)
      var blockid = file.substring(43, file.length - 4)
      var data = df.filter(x => !x.contains(",,,") && x.split(",")(1).contains("2013") && StringUtils.isNotEmpty(x.split(",")(7))).map(x => (blockid, x.split(",")(7).toDouble)).reduceByKey(_ + _).map(x => x._1 + "," + x._2.toString)
      f_df = f_df.union(data)
    }

    f_df.coalesce(1, true).saveAsTextFile("file:/home/dbda/anup/projcode/out_block")

  }
  //Table:3
  def createTable3() {
    //Total consumption by day for all blocks
    //for each file, remove header and then merge with empty RDD f_df
    for (file <- files) {
      println(file)
      var df = sc.textFile("file:" + file)
      var first = df.first
      var df_headless = df.filter(_ != first)
      df_headless = df_headless.filter(x => !x.contains(",,,") && StringUtils.isNotEmpty(x.split(",")(7)))
      var data = df_headless.map(x => (x.split(",")(1).trim, x.split(",")(7).toDouble)).reduceByKey(_ + _).map(x => x._1 + "," + x._2.toString)
      f_df = f_df.union(data)
    }
    var data1 = f_df.map(x => (x.split(",")(0).trim, x.split(",")(1).toDouble)).reduceByKey(_ + _).map(x => x._1 + "," + x._2.toString)

    data1.coalesce(1, true).saveAsTextFile("file:/home/dbda/anup/projcode/all_block1")
  }*/
}
