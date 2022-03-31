import openpablo.koddit.*

suspend fun main() {
    var id=System.getenv("id")
    var secret=System.getenv("secret")
    var username =System.getenv("username")
    var password =System.getenv("password")
    var reddit = Koddit(id, secret)
    reddit.login(username,password)
    var threads = reddit.getBestThreads("belgium", 5, 10)
    threads.forEach{thread->
        println("printing ${thread.title}")
        thread.posts?.forEach{ post ->
            println("UPVOTES: ${post.ups} BODY:${post.body}")
        }
    }
    reddit.close()
}
