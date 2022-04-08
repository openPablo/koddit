package openpablo.koddit

import com.google.cloud.texttospeech.v1.*
import com.google.protobuf.ByteString
import java.io.File
import java.io.FileOutputStream

fun textToSpeech(text: String, filename: String) {
    val file = File(filename)
    if (!isFileExists(file)) {
        TextToSpeechClient.create().use { textToSpeechClient ->
            val input = SynthesisInput.newBuilder().setText(text).build()

            val voice =
                VoiceSelectionParams.newBuilder()
                    .setLanguageCode("en-US")
                    .setSsmlGender(SsmlVoiceGender.MALE)
                    .build()

            val audioConfig =
                AudioConfig.newBuilder()
                    .setAudioEncoding(AudioEncoding.LINEAR16).build() //wav file

            val response =
                textToSpeechClient.synthesizeSpeech(input, voice, audioConfig)

            // Get the audio contents from the response
            val audioContents: ByteString = response.audioContent
            FileOutputStream(filename).use { out ->
                out.write(audioContents.toByteArray())
                println("Audio content written to file \"$filename\"")
            }
        }
    } else {
        println("Speech file was already generated!")
    }

}

fun isFileExists(file: File): Boolean {
    return file.exists() && !file.isDirectory
}