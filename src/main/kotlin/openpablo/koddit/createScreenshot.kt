package openpablo.koddit

import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import java.io.File


//Requires gecko driver... 'which geckodriver'
class createScreenshot(geckoDriverPath1: String){
    var driver = FirefoxDriver()
    val baseUrl = "https://www.reddit.com"
    init {
        System.setProperty("webdriver.gecko.driver", geckoDriverPath1)
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null")
        driver[baseUrl]
        clickIfExists("//*[text()[contains(., 'Accept all')]]")
    }

    fun snap(id: String, url: String, path: String){
        val dest = File("$path/$id.png" )
        if (!isFileExists(dest)) {
            driver[baseUrl + url]
            val is18Plus = driver.findElements(By.xpath("//*[text()[contains(., 'You must be at least eighteen years old to view this content. Are you over eighteen and willing to see adult content?')]]")).isNotEmpty()
            if(is18Plus) {
                clickIfExists("//*[text()[contains(., 'Yes')]]")
                clickIfExists("//*[text()[contains(., 'Click to see nsfw')]]")
            }
            val htmlElement = driver.findElements(By.id(id))

            val screenshot = (htmlElement[0] as TakesScreenshot).getScreenshotAs(OutputType.FILE)

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
