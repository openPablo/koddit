package openpablo.koddit

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//Data classes to hold json API responses
@Serializable
data class RedditSession(var access_token: String, var token_type: String, var expires_in: Int, var scope: String)

@Serializable
data class ThreadResponse(
    var kind: String? = null,
    var data: ResponseData
) {
    fun getThreads(): MutableList<RedditThread> {
        val threads: MutableList<RedditThread> = ArrayList()
        this.data.children.forEach {
            threads.add(it.data)
        }
        return threads
    }
}

@Serializable
data class ResponseData(
    var dist: Int? = null,
    var modhash: String? = null,
    var after: String? = null,
    var geo_filter: String? = null,
    var children: List<ResponseChildren>,
    var before: String? = null
)

@Serializable
data class ResponseChildren(
    var kind: String? = null,
    var data: RedditThread = RedditThread()
)

@Serializable
data class RedditThread(
    @SerialName("id")
    var _id: String? = null,
    var subreddit: String? = null,
    var title: String? = null,
    var selftext: String? = null,
    var selftext_html: String? = null,
    var score: Int? = null,
    var posts: List<RedditPost>? = null,
    var author_fullname: String? = null,
    var ups: Int? = null,
    var downs: Int? = null,
    var upvote_ratio: Double? = null,
    var gilded: Int? = null,
    var subreddit_name_prefixed: String? = null,
    var pwls: Int? = null,
    var name: String? = null,
    var quarantine: Boolean? = null,
    var subreddit_type: String? = null,
    var total_awards_received: Int? = null,
    var created: Double? = null,
    var wls: Int? = null,
    var domain: String? = null,
    var allow_live_comments: Boolean? = null,
    var likes: String? = null,
    var view_count: String? = null,
    var archived: Boolean? = null,
    var is_crosspostable: Boolean? = null,
    var pinned: Boolean? = null,
    var over_18: Boolean? = null,
    var media_only: Boolean? = null,
    var spoiler: Boolean? = null,
    var locked: Boolean? = null,
    var removed_by: String? = null,
    var num_reports: String? = null,
    var subreddit_id: String? = null,
    var author: String? = null,
    var num_comments: Int? = null,
    var permalink: String? = null,
    var stickied: Boolean? = null,
    var url: String? = null,
    var subreddit_subscribers: Int? = null,
    var created_utc: Double? = null,
    var num_crossposts: Int? = null,
    var is_video: Boolean? = null,

)


//Posts data classes!
@Serializable
data class PostsResponse(
    var kind: String? = null,
    var data: PostsData
) {
    fun getPosts(): MutableList<RedditPost> {
        val posts: MutableList<RedditPost> = ArrayList()
        this.data.children.forEach {
            posts.add(it.data)
        }
        return posts
    }
}

@Serializable
data class PostsData(
    var dist: Int? = null,
    var modhash: String? = null,
    var after: String? = null,
    var geo_filter: String? = null,
    var children: List<PostsChildren>,
    var before: String? = null
)

@Serializable
data class PostsChildren(
    var kind: String? = null,
    var data: RedditPost = RedditPost()
)

@Serializable
data class RedditPost(
    @SerialName("id")
    var _id: String? = null,
    var body: String?  = null,
    var body_html: String?  = null,
    var author: String? = null,
    var author_fullname: String? = null,
    var score: Int? = null,
    var ups: Int? = null,
    var downs: Int? = null,
    var upvote_ratio: Double? = null,
    var subreddit: String? = null,
    var selftext: String? = null,
    var gilded: Int? = null,
    var title: String? = null,
    var subreddit_name_prefixed: String? = null,
    var depth: Int? = null,
    var pwls: Int? = null,
    var name: String? = null,
    var quarantine: Boolean? = null,
    var subreddit_type: String? = null,
    var total_awards_received: Int? = null,
    var created: Double? = null,
    var wls: Int? = null,
    var domain: String? = null,
    var allow_live_comments: Boolean? = null,
    var selftext_html: String? = null,
    var likes: String? = null,
    var url_overridden_by_dest: String? = null,
    var view_count: String? = null,
    var archived: Boolean? = null,
    var is_crosspostable: Boolean? = null,
    var pinned: Boolean? = null,
    var over_18: Boolean? = null,
    var media_only: Boolean? = null,
    var spoiler: Boolean? = null,
    var locked: Boolean? = null,
    var removed_by: String? = null,
    var num_reports: String? = null,
    var subreddit_id: String? = null,
    var num_duplicates: Int? = null,
    var num_comments: Int? = null,
    var permalink: String? = null,
    var stickied: Boolean? = null,
    var url: String? = null,
    var subreddit_subscribers: Int? = null,
    var created_utc: Double? = null,
    var num_crossposts: Int? = null,
    var is_video: Boolean? = null,
)
