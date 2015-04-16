package FundametalPattern.Delegation.MarkerTrait.Singleton

/**
 * Created by cqshinn on 4/7/15.
 */
trait Session {
  val id:String
}

private trait InternalSession extends Session{
  def postCreate:Unit
  def preDestroy:Unit
}

private class SessionImpl(val id:String) extends InternalSession {
  def postCreate = println("postCreate")
  def preDestroy = println("preDestroy")
  override def toString = "session " + id + "singleton"
}

private class SessionDelegate() extends Session{
  override val id:String = {
    SessionManager.instance.id
  }
  override def toString = SessionManager.instance.toString
}

object SessionManager {
  private[this] var _instance: Option[InternalSession] = None
  def instance: Session = {
    if(_instance isEmpty){
      _instance = Option(new SessionImpl("1"))
      _instance.get.postCreate
    }
    return _instance.get
  }

  def destroy = {
    if(_instance nonEmpty){
      _instance.get.preDestroy
      _instance = None
    }
  }

  def session:Session = return new SessionDelegate()

}

object MainSingleton extends App{
  val s1 = SessionManager.session
  println(s1)
  println("=====================")
  val s2 = SessionManager.session
  println(s2)
  println("=====================")
  SessionManager.destroy
  println("=====================")
  println(s2)
  println("=====================")
  val s3 = SessionManager.session
  println("=====================")
  println(s3)

}
