package Exercise2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class Ftse100Pom {

    private RemoteWebDriver driver;
    private String url = "https://www.hl.co.uk/shares/stock-market-summary/ftse-100/risers";

    @BeforeEach
    void setup() {
        this.driver = new ChromeDriver();
        this.driver.manage().window().maximize();
    }

    @Test
    void testRisers() {

        // ignore for now
        this.driver.get(url);
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        WebElement cookiesbtn = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#acceptCookieButton")));
        cookiesbtn.click();

        RisersPage risersPage = PageFactory.initElements(driver, RisersPage.class);

        Float previous = Float.MAX_VALUE;

        for (Float f : risersPage.getRiserPercents()) {
            if (f > previous) {
                fail();
            }
        }
    }

    @Test
    void testFallers() {

        this.driver.get(url);
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        WebElement cookiesbtn = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#acceptCookieButton")));
        cookiesbtn.click();

        FallersPage fallersPage = PageFactory.initElements(driver, FallersPage.class);

        Float previous = Float.MIN_VALUE;

        for (Float f : fallersPage.getFallerPercents()) {
            if (f < previous) {
                fail();
            }
        }
    }

    @AfterEach
    void tearDown() {
        this.driver.close();
    }
}
