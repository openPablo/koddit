package openpablo.koddit

import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.File
import java.time.Duration
import org.openqa.selenium.OutputType

//Requires gecko driver... 'which geckodriver'
class RedditScreenshot(geckoDriverPath1: String){
    var driver = FirefoxDriver()
    val baseUrl = "https://www.reddit.com"
    init {
        System.setProperty("webdriver.gecko.driver", geckoDriverPath1)
        driver[baseUrl]
        clickIfExists("//*[text()[contains(., 'Accept all')]]")
    }

    fun snap(id: String, url: String, path: String){
        val dest = File("$path/$id.png" )
        if (!isFileExists(dest)) {
            driver[baseUrl + url]
            val is18Plus = !driver.findElements(By.xpath("//*[text()[contains(., 'You must be at least eighteen years old to view this content. Are you over eighteen and willing to see adult content?')]]")).isEmpty()
            if(is18Plus) {
                clickIfExists("//*[text()[contains(., 'Yes')]]")
                clickIfExists("//*[text()[contains(., 'Click to see nsfw')]]")
            }
            val htmlElement = driver.findElements(By.id(id))

            var screenshot = (htmlElement[0] as TakesScreenshot).getScreenshotAs(OutputType.FILE)

            screenshot.copyTo(dest)
            println("Saved pic to $path/$id.png")
        } else {
            println("Screenshot already taken for $id")
        }
    }
    fun clickIfExists(selector: String){
        val acceptCookies = driver.findElements(By.xpath(selector)).isNotEmpty()
        if(acceptCookies){
            driver.findElement(By.xpath(selector)).click()
        }
    }
    fun close(){
        driver.close()
    }
}
