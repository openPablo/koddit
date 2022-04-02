package openpablo.koddit

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import kotlin.system.exitProcess
import org.litote.kmongo.*

class KodditDataHandler(mongoConn: String) {
    var mongoClient: MongoClient? = null
    var database: MongoDatabase? = null
    var collection: MongoCollection<RedditThread>? = null
    var res = "{\"batchSize\":null}"

    fun writeThreads(threads: MutableList<RedditThread>) {
        threads.forEach {
            collection?.save(it)
        }
        println("Wrote threads to database :^)")
    }

    fun sendThreads(threads: MutableList<RedditThread>) {
        println("send to queue! :)")
    }
    fun existsInDb(thread: RedditThread): Boolean{
        val mongoResult = collection?.countDocuments(RedditThread::_id eq thread._id)
        return if (mongoResult is Long){
            mongoResult > 0
        } else {
            false
        }
    }
    init {
        try{
            mongoClient = KMongo.createClient(mongoConn)
            database = mongoClient?.getDatabase("myFirstDatabase")
            collection = database?.getCollection()
        } catch (e: Throwable) {
            println("Failed initializing database, connection string: $mongoConn")
            exitProcess(1)
        }
    }
}