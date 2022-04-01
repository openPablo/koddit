import com.mongodb.client.MongoCollection
import openpablo.koddit.*
import org.litote.kmongo.*
import com.mongodb.client.model.InsertOneOptions
import com.mongodb.client.model.UpdateOptions
import kotlin.system.exitProcess

suspend fun main() {
    val id = System.getenv("id")
    val secret = System.getenv("secret")
    val username = System.getenv("username")
    val password = System.getenv("password")
    val subRedditList = arrayOf("AskReddit", "AmItheAsshole", "relationship_advice", "trueOffMyChest")

    var reddit = Koddit(id, secret)
    reddit.login(username, password)

    //Main fun
    watchSubreddits(reddit, subRedditList)
    reddit.close()
}


suspend fun watchSubreddits(reddit: Koddit, subRedditList: Array<String>) {
    val mongoCollection = initMongo(System.getenv("mongoConnStr")) ?: exitProcess(1)
    var threads: MutableList<RedditThread> = ArrayList()
    subRedditList.forEach { subReddit ->
        threads.addAll(reddit.getTopThreads(subReddit, 8, 500))
    }
    writeThreads(threads, mongoCollection)
}

fun writeThreads(threads: MutableList<RedditThread>, mongoCollection: MongoCollection<RedditThread>?) {
    threads.forEach {
        mongoCollection?.save(it)
    }
}

fun initMongo(mongoConn: String): MongoCollection<RedditThread>? {
    return try {
        val mongoClient = KMongo.createClient(mongoConn)
        val database = mongoClient.getDatabase("myFirstDatabase")
        database.getCollection<RedditThread>()
    } catch (e: Throwable) {
        println("Failed initializing database, connection string: $mongoConn")
        null
    }
}