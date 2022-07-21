package adentischallengescala
package adentischallengescala.test

import java.time.LocalDateTime
import adentischallengescala.entities.Item
import adentischallengescala.entities.EntityProduct
import scala.collection.mutable.ListBuffer

object ItemCreation {

  def createItems(date: LocalDateTime): List[Item] = {
    val items = new ListBuffer[Item]()

    val product = new EntityProduct("product", "category", 15.3, 35.33, date)

    val item = new Item(35.33, 2.32, 5.45, product)

    items += item

    return items.toList
  }
}
