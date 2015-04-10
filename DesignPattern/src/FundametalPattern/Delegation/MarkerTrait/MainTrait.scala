package FundametalPattern.Delegation.MarkerTrait
import java.util.Date
/**
 * Created by cqshinn on 4/7/15.
 */

trait Marker

case class Basic(val id:Int) extends Marker

case class Named(val id:String) extends Marker {}


trait Contract extends Marker

case class TradeContract(val buyer:String,
                          val seller:String,
                          val notational:Double,
                          val fixedRate:Double,
                          val floatingIndex:String,
                          val effectiveDate:Date,
                          val term:Int) extends Contract{
  var floatingRate:Double = 0.0
}

case class LoanContract(val lender:String,
                         val borrowers: Seq[String],
                         val amount:Double,
                         val rate:Double,
                         val startDate:Date,
                         val duration:Int,
                         val repayments:String) extends Contract{
  def totalRepayable = {
    amount*Math.pow((1+rate),duration)
  }
}



object MainTrait extends App{
  val c1 = LoanContract("Bank",Seq("Cq"),1000,.05,new Date(),3,"monthly")
  println(c1)
  println(c1.totalRepayable)
  println("===========================")
  val c2 = TradeContract("C","Q",100000,1.3,"L",new Date(),10)
  println(c2)
  c2.floatingRate = 0.1
}
