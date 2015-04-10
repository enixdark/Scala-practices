import chat.{Subscribe, ChatRoom, User}

/**
 * Created by cqshinn on 4/10/15.
 */

object Main extends App{
  val chatroom = new ChatRoom
  chatroom.start()
  val user1 = User("pop")
  val user2 = User("quan")
//  chatroom ! Subscribe(user1)
  chatroom !? Subscribe(user2) match {
    case response:String => println(response + "!")
  }

  val future = chatroom !! Subscribe(user1)
  println(future)



}
