import javax.sound.sampled.AudioFileFormat
import kotlin.math.ceil
import kotlin.math.roundToInt
import kotlin.system.measureTimeMillis
import java.io.File;
import java.io.IOException;
import java.io.SequenceInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

//Uses FFmpeg --> high performance + gpu accelerated

class VideoComposer(val filename: String, val aspect_ratio: Double, var maxDuration: Double) {
    private var audioFiles: MutableList<String> = mutableListOf()
    private var images: MutableMap<String, Double> = mutableMapOf()
    var height = 0
    var width = 0
    var duration = 0.00
    var vidIsFull = false      //stop accepting vids if attempted to surpass maxDuration

    fun addAudio(audioFile: String): Double {
        println("audifolie is: $audioFile")
        val clipLength =
            execute("ffprobe -i $audioFile -show_entries format=duration -v quiet -of csv=\"p=0\"").toDouble()
        if ((clipLength + duration) <= maxDuration) {
            println("added audiofile!")
            audioFiles.add(audioFile)
            duration += clipLength
        }else {
            vidIsFull = true
        }
        return clipLength
    }

    fun addImage(imageFile: String, Duration: Double) {
        images[imageFile] = Duration
    }

    private fun concatAudio(outputFile: String) {
        var cmd = "ffmpeg -y"
        audioFiles.forEach { audio ->
            cmd += "-i $audio "
        }
        cmd += "-filter_complex \" "            //start of complex filter
        for (i in 0 until audioFiles.size ) {
            cmd += "[$i:0]"
        }
        cmd += "concat=n=${audioFiles.size}:v=0:a=1[out]" +
                "\" " +                         // end of complex filter
                "-map '[out]' $outputFile"
        execute(cmd)
    }

    private fun cropPortrait(aspect_ratio: Double): String {
        val videoFileHeight =
            execute("ffprobe -v error -select_streams v:0 -show_entries stream=height -of csv=s=x:p=0 $filename").toDouble()
        height = videoFileHeight.toInt()
        width = (videoFileHeight * aspect_ratio).toInt()
        return "[0:v]crop=${width}:$height [vid2]; "
    }

    //looks hacky because this function has to generate a single FFMPEG command for efficiency
    fun renderClip(gpuAccelerated: Boolean, output: String) {
        var cmd = "ffmpeg -y "
        if (gpuAccelerated) {
            cmd += "-hwaccel cuda "
        }
        var tmpAudioFile = "/tmp/tmpConcatAudio.wav"
        concatAudio(tmpAudioFile)

        cmd += "-i $filename -i $tmpAudioFile "      //sets input files,
        images.forEach { image ->
            cmd += "-i ${image.key} "
        }
        cmd += "-filter_complex \" "                 //Start setting the complex filters
        cmd += cropPortrait(aspect_ratio)            //See cropPortrait

        var imageNr =
            2                                  //ImageNr starts at 2 because the base vid and audio file are 0 and 1
        var startTime = 0.00
        images.forEach { image ->
            val imageEndTime = startTime + image.value
            cmd += " [$imageNr]scale=$width:-1 [pic$imageNr]; " +  //Scales the image to the video width
                    "[vid$imageNr][pic$imageNr] overlay = " +
                    "(W-w)/2:(H-h)/2:enable='between(t,$startTime,${imageEndTime})' " +  //sets image in center of vid
                    "[vid${imageNr + 1}] "
            if (imageEndTime <= duration) {       //check because the last item can't have a ';'
                cmd += ";"
            }
            imageNr += 1
            startTime = imageEndTime
        }

        cmd += "\" " +                                          //end of filter_complex
                "-map \"[vid$imageNr]:v\" -map \"1:a\" " +      //Overlay audio file over video
                "-t $duration " +                               //sets video length
                " $output"
        println("Rendering: ")
        execute(cmd)
    }

    private fun execute(cmd: String): String {
        println("Executing: $cmd")
        val pb = ProcessBuilder("sh", "-c", cmd)
        val process = pb.start()
        var res: String
        val elapsed = measureTimeMillis {
            res = String(process.inputStream.readAllBytes())
        }
        println("${elapsed / 1000} seconds spent")
        return res
    }

}