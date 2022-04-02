import openpablo.koddit.*

suspend fun main() {
    val id = System.getenv("id")
    val secret = System.getenv("secret")
    val username = System.getenv("username")
    val password = System.getenv("password")
    val mongoConnStr = System.getenv("mongoConnStr")
    val subRedditList = arrayOf("AskReddit", "AmItheAsshole", "relationship_advice", "trueOffMyChest")

    val db  = KodditDataHandler(mongoConnStr)
    val reddit = Koddit(id, secret)
    reddit.login(username, password)
    watchSubreddits(reddit, subRedditList, db, 10, 10)
    reddit.close()
}

suspend fun watchSubreddits(
    reddit: Koddit,
    subRedditList: Array<String>,
    db: KodditDataHandler,
    limitThread: Int,
    limitPosts: Int
) {
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
}

