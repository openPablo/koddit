import openpablo.koddit.*
import java.io.File

suspend fun main() {
    val id = System.getenv("id")
    val secret = System.getenv("secret")
    val username = System.getenv("username")
    val password = System.getenv("password")
    val mongoConnStr = System.getenv("mongoConnStr")
    val limitPosts = System.getenv("limitPosts").toInt()
    val limitThread = System.getenv("limitThread").toInt()
    val subRedditList = "AskReddit relationship_advice amItheAsshole".split(" ").toTypedArray()



    val db  = RedditDataHandler(mongoConnStr)
    val reddit = RedditScraper(id, secret)
    reddit.login(username, password)
    val threads = scrapeSubreddits(reddit, subRedditList, db, limitThread, limitPosts)
    reddit.close()

    threads.forEach{thread->
        composeVideo(thread)
    }
}

suspend fun scrapeSubreddits(
    reddit: RedditScraper,
    subRedditList: Array<String>,
    db: RedditDataHandler,
    limitThread: Int,
    limitPosts: Int
): MutableList<RedditThread> {
    val threads: MutableList<RedditThread> = ArrayList()
    subRedditList.forEach { subReddit ->
        val newThreads = reddit.getTopThreads(subReddit, limitThread, limitPosts)
        newThreads.removeAll { thread ->
            db.existsInDb(thread)
        }
        threads.addAll(newThreads)
    }
    db.sendThreads(threads)
    db.writeThreads(threads)
    return threads
}

