package adentischallengescala
package adentischallengescala.entities

import java.time.LocalDateTime

class Order (
  var customerName: String,
  var customerContact: String,
  var shippingAddress: String,
  var grandTotal: Double,
  var orderPlacedDate: LocalDateTime,
  var items: List[Item])
