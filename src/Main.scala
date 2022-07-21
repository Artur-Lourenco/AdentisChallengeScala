package adentischallengescala

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import adentischallengescala.entities.Order
import adentischallengescala.test.TestCreation

import scala.collection.mutable.ListBuffer

object Main{

  private val FIXED_INTERVAL_N = 4
  private val FIXED_INTERVAL_1 = "1-3"
  private val FIXED_INTERVAL_2 = "4-6"
  private val FIXED_INTERVAL_3 = "7-12"
  private val FIXED_INTERVAL_4 = ">12"

  private var intervalStart = LocalDateTime.now();
  private var intervalEnd = LocalDateTime.now();

  def main(args: Array[String]): Unit = {
    if(args.length < 2) {
      println("Invalid arguments. Input should be a time interval of two dates")
      return
    }

    if(!checkIntervals(args(0),args(1))) return

    var orders = TestCreation.createFictionalOrders

    if(args.length == 2) {

      println("------------------------------")

      var counterArray = new Array[Long](FIXED_INTERVAL_N)

      for (i <- orders) {
        if (!i.orderPlacedDate.isAfter(intervalEnd) && !i.orderPlacedDate.isBefore(intervalStart)) {
          for(j <- 0 to i.items.size -1) {
            val product = i.items(j).product
            val creationDate = product.creationDate;
            if (defCustomIntervals(creationDate, FIXED_INTERVAL_1))
              counterArray(0) += 1
            if (defCustomIntervals(creationDate, FIXED_INTERVAL_2))
              counterArray(1) += 1
            if (defCustomIntervals(creationDate, FIXED_INTERVAL_3))
              counterArray(2) += 1
            if (defCustomIntervals(creationDate, FIXED_INTERVAL_4))
              counterArray(3) += 1
          }
        }
      }

      println(FIXED_INTERVAL_1 + " months: " + counterArray(0) + " orders")
      println(FIXED_INTERVAL_2 + " months: " + counterArray(1) + " orders")
      println(FIXED_INTERVAL_3 + " months: " + counterArray(2) + " orders")
      println(FIXED_INTERVAL_4 + " months: " + counterArray(3) + " orders")
      println("------------------------------")
    } else {
      val customIntervals = args.slice(2, args.length)
      if(!isValidInterval(customIntervals)) return

      println("------------------------------")
      var counterArray = new Array[Long](customIntervals.length)
      for(i <- orders) {
        for(j <- 0 to customIntervals.length-1) {
          var s = customIntervals(j)
          if (!i.orderPlacedDate.isAfter(intervalEnd) && !i.orderPlacedDate.isBefore(intervalStart)) {
            for(k <- 0 to i.items.size-1) {
              val product = i.items(k).product
              val creationDate = product.creationDate;
              if (defCustomIntervals(creationDate, s))
                counterArray(j) += 1
            }
          }
        }
      }
      for(i <- 0 to customIntervals.length-1) {
        if(customIntervals(i).contains("-")) {
          val split = customIntervals(i).split("-")
          println(split(0) + "-" + split(1) + " months: " + counterArray(i) + " orders")
        } else {
          val split = customIntervals(i).split(">")
          println(">" + split(1) + " months: " + counterArray(i) + " orders")
        }
      }
      println("------------------------------")
    }
  }

  private def checkIntervals(date1: String, date2: String): Boolean = {
      try{
        val formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        intervalStart = LocalDateTime.parse(date1, formatDate)
        intervalEnd = LocalDateTime.parse(date2, formatDate)

        true
      } catch {
        case exception: Exception =>
          println("Invalid date format. Dates should be of format yyyy-MM-dd HH:mm:ss")
          false
      }
  }

  private def isValidInterval(args: Array[String]): Boolean = {
    for(s <- args) {
      val type1 = s.contains("-")
      val type2 = s.contains(">")
      if(!type1 && !type2) {
        println("Valid intervals are of type X1-X2 or >X where X,X1 and X2 are numbers"
          + " and X2 is greater than X1.")
        return false
      };
      else if(type1 && type2) {
        println("Intervals can't be composed by '-' and '>' in the same interval.")
        return false;
      }
      if(type1) {
        val type1Array = s.split("-")
        if(type1Array.length != 2) {
          println("One interval can't have multiple '-'.")
          return false
        } else if(type1Array(0).toInt >= type1Array(1).toInt) {
          println("Intervals of type X1-X2 must have X2 greater than X1.")
          return false
        }
      } else {
        val type2Array = s.split(">")
        if(type2Array.length != 2) {
          println("One interval can't have multiple '>'.")
          return false
        } else if(!type2Array(0).equals("")) {
          println("Intervals of type '>X' can't have a number before '>'.")
          return false
        }
      }
    }
    return true
  }

  private def defCustomIntervals(creationDate: LocalDateTime, s: String): Boolean = {
    if(s.contains("-")) {
      val type1 = s.split("-")
      if(!creationDate.isAfter(intervalEnd.minusMonths((type1(0).toInt))) &&
        !creationDate.isBefore(intervalEnd.minusMonths(type1(1).toInt))) {
        return true
      } else
        return false
    } else {
      val type2 = s.split(">")
      if(creationDate.isBefore(intervalEnd.minusMonths(type2(1).toInt)))
        return true
      else
        return false
    }
  }
}
