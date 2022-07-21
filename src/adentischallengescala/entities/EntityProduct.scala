package adentischallengescala
package adentischallengescala.entities

import java.time.LocalDateTime

class EntityProduct(
  var name: String,
  var category: String,
  var weight: Double,
  var price: Double,
  var creationDate: LocalDateTime)
