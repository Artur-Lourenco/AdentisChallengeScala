package adentischallengescala
package adentischallengescala.test

import java.time.{LocalDate, LocalDateTime, LocalTime, Month}
import adentischallengescala.entities.Order
import scala.collection.mutable.ListBuffer

object TestCreation {

  private val FIRST = 200
  private val SECOND = 150
  private val THIRD = 50
  private val FOURTH = 20

  def createFictionalOrders: List[Order] = {
    val orders = new ListBuffer[Order]

    val firstDate = createTimes(LocalDate.of(2018, Month.OCTOBER, 1), LocalTime.of(0, 0, 0))
    val secondDate = createTimes(LocalDate.of(2018, Month.AUGUST, 1), LocalTime.of(0, 0, 0))
    val thirdDate = createTimes(LocalDate.of(2018, Month.MARCH, 1), LocalTime.of(0, 0, 0))
    val fourthDate = createTimes(LocalDate.of(2017, Month.OCTOBER, 1), LocalTime.of(0, 0, 0))

    val firstItem = ItemCreation.createItems(firstDate)
    val secondItem = ItemCreation.createItems(secondDate)
    val thirdItem = ItemCreation.createItems(thirdDate)
    val fourthItem = ItemCreation.createItems(fourthDate)

    for(i <- 1 to FIRST)
      orders += new Order("José", "jose@gmail.com", "Rua A", 35.32, firstDate, firstItem)

    for(i <- 1 to SECOND)
      orders += new Order("Carlos", "carlos@gmail.com", "Rua B", 31.99, firstDate, secondItem)

    for(i <- 1 to THIRD)
      orders += new Order("João", "joao@gmail.com", "Rua C", 49.99, firstDate, thirdItem)

    for(i <- 1 to FOURTH)
      orders += new Order("Mário", "mario@gmail.com", "Rua D", 19.99, firstDate, fourthItem)

    for(i <- 1 to FOURTH)
      orders += new Order("Zeca", "zeca@gmail.com", "Rua E", 5.99, fourthDate, fourthItem)

    return orders.toList
  }

  private def createTimes(date: LocalDate, time: LocalTime): LocalDateTime = {
    val res = LocalDateTime.of(date, time)
    return res
  }

}
