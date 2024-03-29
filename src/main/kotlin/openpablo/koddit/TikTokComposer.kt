import openpablo.koddit.*
import java.io.File


val workingDir = "/home/pablo/tiktok"

fun composeVideo(thread: RedditThread, snapper: createScreenshot) {
    val backGroundVid = pickRandomVideo("$workingDir/raw_videos")
    val tiktok = VideoComposer(backGroundVid, 9.00 / 16.00, 300.00)

    generateAttributes(thread, snapper, true)
    addRedditToVid(tiktok, thread)
    if (tiktok.duration < 10.00) { //if large OP, make larger video
        tiktok.maxDuration = 59.00
    }
    var i = 0
    while (!tiktok.vidIsFull) {
        generateAttributes(thread.posts[i], snapper, true)
        addRedditToVid(tiktok, thread.posts[i])
        i += 1
    }
    tiktok.renderClip(true, "$workingDir/composed_videos/${thread.name}.mp4")
    saveId("done.txt", thread._id)
}

fun generateAttributes(target: RedditObject, screenshotObject: createScreenshot, randomizeVoice: Boolean) {
    val musicFile = "$workingDir/voice/${target.name}.wav"
    textToSpeech(target.text, musicFile, randomizeVoice)
    screenshotObject.snap(target.name, target.permalink, "$workingDir/screenshots")
}

fun addRedditToVid(tiktok: VideoComposer, target: RedditObject): Double {
    var length = tiktok.addAudio("$workingDir/voice/${target.name}.wav")
    if (length != 0.00) {
        tiktok.addImage("$workingDir/screenshots/${target.name}.png", length)
    }
    return length
}

fun pickRandomVideo(path: String): String {
    val fileList = File(path).list().filter { s -> ".part" !in s }
    return "$path/${fileList.random()}"
}

