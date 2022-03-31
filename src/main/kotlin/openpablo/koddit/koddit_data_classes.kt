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
        var threads: MutableList<RedditThread> = ArrayList()
        this.data?.children?.forEach {
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
    var approved_at_utc: String? = null,
    var subreddit: String? = null,
    var selftext: String? = null,
    var author_fullname: String? = null,
    var saved: Boolean? = null,
    var mod_reason_title: String? = null,
    var gilded: Int? = null,
    var clicked: Boolean? = null,
    var title: String? = null,
    var link_flair_richtext: ArrayList<String> = arrayListOf(),
    var subreddit_name_prefixed: String? = null,
    var hidden: Boolean? = null,
    var pwls: Int? = null,
    var link_flair_css_class: String? = null,
    var downs: Int? = null,
    var top_awarded_type: String? = null,
    var hide_score: Boolean? = null,
    var name: String? = null,
    var quarantine: Boolean? = null,
    var link_flair_text_color: String? = null,
    var upvote_ratio: Double? = null,
    var author_flair_background_color: String? = null,
    var subreddit_type: String? = null,
    var ups: Int? = null,
    var total_awards_received: Int? = null,
    var author_flair_template_id: String? = null,
    var is_original_content: Boolean? = null,
    var user_reports: ArrayList<String> = arrayListOf(),
    var is_reddit_media_domain: Boolean? = null,
    var is_meta: Boolean? = null,
    var category: String? = null,
    var link_flair_text: String? = null,
    var can_mod_post: Boolean? = null,
    var score: Int? = null,
    var approved_by: String? = null,
    var is_created_from_ads_ui: Boolean? = null,
    var author_premium: Boolean? = null,
    var thumbnail_height: String? = null,
    var thumbnail_width: String? = null,
    var thumbnail: String? = null,
    var author_flair_css_class: String? = null,
    var author_flair_richtext: ArrayList<String> = arrayListOf(),
    var content_categories: String? = null,
    var is_self: Boolean? = null,
    var mod_note: String? = null,
    var created: Double? = null,
    var link_flair_type: String? = null,
    var wls: Int? = null,
    var removed_by_category: String? = null,
    var banned_by: String? = null,
    var author_flair_type: String? = null,
    var domain: String? = null,
    var allow_live_comments: Boolean? = null,
    var selftext_html: String? = null,
    var likes: String? = null,
    var suggested_sort: String? = null,
    var banned_at_utc: String? = null,
    var view_count: String? = null,
    var archived: Boolean? = null,
    var no_follow: Boolean? = null,
    var is_crosspostable: Boolean? = null,
    var pinned: Boolean? = null,
    var over_18: Boolean? = null,
    var awarders: ArrayList<String> = arrayListOf(),
    var media_only: Boolean? = null,
    var can_gild: Boolean? = null,
    var spoiler: Boolean? = null,
    var locked: Boolean? = null,
    var author_flair_text: String? = null,
    var treatment_tags: ArrayList<String> = arrayListOf(),
    var visited: Boolean? = null,
    var removed_by: String? = null,
    var num_reports: String? = null,
    var distinguished: String? = null,
    var subreddit_id: String? = null,
    var author_is_blocked: Boolean? = null,
    var mod_reason_by: String? = null,
    var removal_reason: String? = null,
    var link_flair_background_color: String? = null,
    var id: String? = null,
    var is_robot_indexable: Boolean? = null,
    var report_reasons: String? = null,
    var author: String? = null,
    var discussion_type: String? = null,
    var num_comments: Int? = null,
    var send_replies: Boolean? = null,
    var whitelist_status: String? = null,
    var contest_mode: Boolean? = null,
    var mod_reports: ArrayList<String> = arrayListOf(),
    var author_patreon_flair: Boolean? = null,
    var author_flair_text_color: String? = null,
    var permalink: String? = null,
    var parent_whitelist_status: String? = null,
    var stickied: Boolean? = null,
    var url: String? = null,
    var subreddit_subscribers: Int? = null,
    var created_utc: Double? = null,
    var num_crossposts: Int? = null,
    var is_video: Boolean? = null,
    var posts: List<RedditPost>? = null
)


//Posts data classes!
@Serializable
data class PostsResponse(
    var kind: String? = null,
    var data: PostsData
) {
    fun getPosts(): MutableList<RedditPost> {
        var posts: MutableList<RedditPost> = ArrayList()
        this.data?.children?.forEach {
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
    var approved_at_utc: String? = null,
    var subreddit: String? = null,
    var selftext: String? = null,
    var user_reports: ArrayList<String> = arrayListOf(),
    var saved: Boolean? = null,
    var mod_reason_title: String? = null,
    var gilded: Int? = null,
    var clicked: Boolean? = null,
    var title: String? = null,
    var link_flair_richtext: ArrayList<String> = arrayListOf(),
    var subreddit_name_prefixed: String? = null,
    var hidden: Boolean? = null,
    var pwls: Int? = null,
    var link_flair_css_class: String? = null,
    var downs: Int? = null,
    var thumbnail_height: Int? = null,
    var top_awarded_type: String? = null,
    var parent_whitelist_status: String? = null,
    var hide_score: Boolean? = null,
    var name: String? = null,
    var quarantine: Boolean? = null,
    var link_flair_text_color: String? = null,
    var upvote_ratio: Double? = null,
    var author_flair_background_color: String? = null,
    var subreddit_type: String? = null,
    var ups: Int? = null,
    var total_awards_received: Int? = null,
    var thumbnail_width: Int? = null,
    var author_flair_template_id: String? = null,
    var is_original_content: Boolean? = null,
    var author_fullname: String? = null,
    var is_reddit_media_domain: Boolean? = null,
    var is_meta: Boolean? = null,
    var category: String? = null,
    var link_flair_text: String? = null,
    var can_mod_post: Boolean? = null,
    var score: Int? = null,
    var approved_by: String? = null,
    var is_created_from_ads_ui: Boolean? = null,
    var author_premium: Boolean? = null,
    var thumbnail: String? = null,
    var author_flair_css_class: String? = null,
    var author_flair_richtext: ArrayList<String> = arrayListOf(),
    var post_hint: String? = null,
    var content_categories: String? = null,
    var is_self: Boolean? = null,
    var mod_note: String? = null,
    var created: Double? = null,
    var link_flair_type: String? = null,
    var wls: Int? = null,
    var removed_by_category: String? = null,
    var banned_by: String? = null,
    var author_flair_type: String? = null,
    var domain: String? = null,
    var allow_live_comments: Boolean? = null,
    var selftext_html: String? = null,
    var likes: String? = null,
    var suggested_sort: String? = null,
    var banned_at_utc: String? = null,
    var url_overridden_by_dest: String? = null,
    var view_count: String? = null,
    var archived: Boolean? = null,
    var no_follow: Boolean? = null,
    var is_crosspostable: Boolean? = null,
    var pinned: Boolean? = null,
    var over_18: Boolean? = null,
    var awarders: ArrayList<String> = arrayListOf(),
    var media_only: Boolean? = null,
    var can_gild: Boolean? = null,
    var spoiler: Boolean? = null,
    var locked: Boolean? = null,
    var author_flair_text: String? = null,
    var treatment_tags: ArrayList<String> = arrayListOf(),
    var visited: Boolean? = null,
    var removed_by: String? = null,
    var num_reports: String? = null,
    var distinguished: String? = null,
    var subreddit_id: String? = null,
    var author_is_blocked: Boolean? = null,
    var mod_reason_by: String? = null,
    var removal_reason: String? = null,
    var link_flair_background_color: String? = null,
    var id: String? = null,
    var is_robot_indexable: Boolean? = null,
    var num_duplicates: Int? = null,
    var report_reasons: String? = null,
    var author: String? = null,
    var discussion_type: String? = null,
    var num_comments: Int? = null,
    var send_replies: Boolean? = null,
    var contest_mode: Boolean? = null,
    var author_patreon_flair: Boolean? = null,
    var author_flair_text_color: String? = null,
    var permalink: String? = null,
    var whitelist_status: String? = null,
    var stickied: Boolean? = null,
    var url: String? = null,
    var subreddit_subscribers: Int? = null,
    var created_utc: Double? = null,
    var num_crossposts: Int? = null,
    var mod_reports: ArrayList<String> = arrayListOf(),
    var is_video: Boolean? = null,
    var body: String?  = null,
    var body_html: String?  = null
)