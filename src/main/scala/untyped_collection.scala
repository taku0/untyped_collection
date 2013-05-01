import org.json._

object UntypedCollectionImplicits {
  implicit class RichAny(any: Any) {
    def apply(key: String): Any = any match {
      case map: Map[_, _] => map.asInstanceOf[Map[String, _]](key)
      case javaMap: java.util.Map[_, _] => javaMap.get(key)
      case jsonObject: JSONObject => jsonObject.get(key)
      case _ => throw new RuntimeException
    }

    def apply(index: Int): Any = any match {
      case array: Array[_] => array(index)
      case seq: Seq[_] => seq(index)
      case javaList: java.util.List[_] => javaList.get(index)
      case jsonArray: JSONArray => jsonArray.get(index)
      case _ => throw new RuntimeException
    }

    def apply(key: Any): Any = key match {
      case key: String => apply(key)
      case index: Int => apply(index)
      case index: Double => apply(index.toInt)
      // 他は省略
      case _ => throw new RuntimeException
    }

    def filter(p: (Any, Any) => Boolean): Any = any match {
      case map: Map[_, _] => map.filter(p)
      case javaMap: java.util.Map[_, _] => ??? // 省略
      case jsonObject: JSONObject => ??? // 省略
      case _ => throw new RuntimeException
    }

    def filter(p: Any => Boolean): Any = any match {
      case array: Array[_] => array.filter(p)
      case seq: Seq[_] => seq.filter(p)
      case javaList: java.util.List[_] => ??? // 省略
      case jsonArray: JSONArray => ??? // 省略
      case _ => throw new RuntimeException
    }

    // map等省略

    def asInt: Int = any match {
      case int: Int => int
      case double: Double => double.toInt
      // 他は省略
      case _ => throw new RuntimeException
    }

    def asDouble: Double = any match {
      case int: Int => int.toDouble
      case double: Double => double
      // 他は省略
      case _ => throw new RuntimeException
    }

    def asString: String = any match {
      case string: CharSequence => string.toString
      case _ => throw new RuntimeException
    }

    // asBoolean等省略
  }

  implicit class AnyToPartiallyOrdered(any: Any) extends PartiallyOrdered[Any] {
    override def tryCompareTo[B >: Any](that: B)
                             (implicit ev: B => PartiallyOrdered[B]):
        Option[Int] = any match {
      case int: Int => Some(int compare that.asInt)
      case double: Double => Some(double compare that.asDouble)
      case string: String => Some(string compare that.asString)
      // 他は省略
      case _ => None
    }
  }
}
