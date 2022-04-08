import openpablo.koddit.*
import java.io.File
import kotlin.random.Random

val workingDir = "/home/pablo/tiktok"
fun main() {
    val mongoConnStr = System.getenv("mongoConnStr")
    val db = RedditDataHandler(mongoConnStr)
    val thread = db.getThread("tvi4t3")

    if (thread != null) {
        composeVideo(thread)
    } else {
        println("Failed getting reddit thread")
    }

}

fun composeVideo(thread: RedditThread) {
    val backGroundVid = pickRandomVideo("$workingDir/raw_videos")
    val tiktok = VideoComposer(backGroundVid, 9.00 / 16.00, 120.00)
    val snapper = RedditScreenshot("/usr/bin/geckodriver")

    generateAttributes(thread, snapper)
    addRedditToVid(tiktok, thread)
    if (tiktok.duration > 55) { //if large OP, make larger video
        tiktok.maxDuration = 300.00
    }
    var i = 0
    while (!tiktok.vidIsFull) {
        generateAttributes(thread.posts[i], snapper)
        addRedditToVid(tiktok, thread.posts[i])
        i += 1
    }
    snapper.close()
    tiktok.renderClip(true, "$workingDir/composed_videos/${thread.name}.mp4")
}

fun generateAttributes(target: RedditObject, screenshotObject: RedditScreenshot) {
    val musicFile = "$workingDir/voice/${target.name}.wav"
    textToSpeech(target.text, musicFile)
    screenshotObject.snap(target.name, target.permalink, "$workingDir/screenshots")
}

fun addRedditToVid(tiktok: VideoComposer, target: RedditObject): Double {
    var length = tiktok.addAudio("$workingDir/voice/${target.name}.wav")
    tiktok.addImage("$workingDir/screenshots/${target.name}.png", length)
    return length
}

fun pickRandomVideo(path: String): String {
    return "$path/${File(path).list().random()}"
}