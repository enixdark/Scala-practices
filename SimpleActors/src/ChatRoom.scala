package chat
//import scala.actors._
import scala.actors.{TIMEOUT, Actor}
import scala.actors.Actor._

/**
 * Created by cqshinn on 4/10/15.
 */
case class User(name: String)

case class Subscribe(user: User)

case class Unsubsrcible(user : User)

case class Post(msg: String)

case class UserPost(user: User, post: Post)


class ChatRoom extends Actor{
  def act(): Unit ={
    var session = Map[User,Actor]()
    while(true){
      receive {
        case Subscribe(user) =>
          val subscribe = sender
          val sessionUser = actor {
            while(true){
              self.receiveWithin(1000 * 5){
                case Post(msg) => subscribe ! Post(msg)
                case TIMEOUT => self ! Unsubsrcible(user)
                  self.exit()
              }
            }
          }
          session = session + (user -> sessionUser)
//          println("add user " + user.name + " into room")
          reply( "add user " + user.name + " into room" )
        case Unsubsrcible(user) => println("remove user " + user.name + " into room")
        case UserPost(user,post) =>
          for( key <- session.keys; if key != user){
            session(key) ! post
          }
          println("user: " + user.name + " post message " + post.msg)
      }
    }
  }
}
