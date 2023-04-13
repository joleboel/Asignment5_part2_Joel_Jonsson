package stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class MyStepdefs {

    private WebDriver driver;
    private WebDriverWait wait;
    //denna kod är ful och jag är inte stolt över den >:(
    //får massa socketexceptions pga någon version mismatch men adam sa att det var ok
    @Given("i navigate to the website")
    public void iNavigateToTheWebsite() {
        System.setProperty("webdriver.chrome.driver", "C:\\selenium\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.get("https://login.mailchimp.com/signup/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));//kan hinna göra eventuell captcha under denna tid kan höjas om det behövs
        driver.manage().window().fullscreen();
        WebElement cookie = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("onetrust-reject-all-handler")));
        if(cookie.isDisplayed()) {
        cookie.click();
    }

}

    @And("i enter an email and password")
    public void iEnterAnEmailAndPassword() {
        WebElement pw = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("new_password")));
        WebElement mail = driver.findElement(By.id("email"));
        mail.click();
        //ändra addressen nedan mellan testruns far att första testet ska bli godkänt
        mail.sendKeys("haloodoodofoojedoeoooo@chimpmail.com");
        pw.click();
        pw.sendKeys("NNmmm99@@");

    }

    @When("i click sign up")
    public void iClickSignUp() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
        WebElement cookie = driver.findElement(By.id("onetrust-reject-all-handler"));
        if(cookie.isDisplayed()) {
            cookie.click();
        }
        WebElement signup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-account-enabled")));
        signup.click();
        driver.manage().window().fullscreen();
    }

    @Then("my account will be created")
    public void myAccountWillBeCreated() {
        WebElement success = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div/div/div[3]/main/div/div/div/div/div/div/div[1]/section/div/h1")));
        Boolean expected = true;
        Boolean actual = success.isDisplayed();
        assertEquals(expected, actual);
    }

    @And("i enter a very long username")
    public void iEnterAVeryLongUsername() {
        WebElement mail = driver.findElement(By.id("email"));
        WebElement nameText = driver.findElement(By.id("new_username"));
        WebElement pw = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("new_password")));
        nameText.click();
        nameText.sendKeys("oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
        mail.click();
        mail.sendKeys("rwvcec@chimpmail.com");
        pw.click();
        pw.sendKeys("Jjjj9@jj");
    }

    @Then("it will tell me to use a shorter username")
    public void itWillTellMeToUseAShorterUsername() {
        WebElement longName = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("span.invalid-error")));

        String expected = "Enter a value less than 100 characters long";
        String actual = longName.getText();
        assertEquals(expected, actual);
    }

    @And("i enter a name that is already in use")
    public void iEnterANameThatIsAlreadyInUse() {
        WebElement mail = driver.findElement(By.id("email"));
        WebElement pw = driver.findElement(By.id("new_password"));
        mail.click();
        mail.sendKeys("jolebole@mailchimp.com");
        pw.click();
        pw.sendKeys("Hejje@99");
    }

    @Then("it will tell me that it is in use")
    public void itWillTellMeThatItIsInUse() {
        WebElement usedname = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span") ));
        Boolean expected = true;
        Boolean actual = usedname.isDisplayed();
        assertEquals(expected, actual);

    }

    @And("i enter a username and password")
    public void iEnterAUsernameAndPassword() {
        WebElement pw = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("new_password")));
        WebElement name = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("new_username")));
        pw.click();
        pw.sendKeys("Hy99@lll");
        name.click();
        name.sendKeys("aaaaaaaaaaaaaaaaaaaah");

    }

    @Then("it will tell me to add @ to my address")
    public void itWillTellMeToAddToMyAddress() {
        WebElement mailtip = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("span.invalid-error")));
        String expected = "An email address must contain a single @.";
        String actual = mailtip.getText();
        assertEquals(expected, actual);
    }

    @After
    public void shutdown(){
        driver.close();
        driver.quit();
    }
}
