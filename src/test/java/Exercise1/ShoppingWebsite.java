package Exercise1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingWebsite {

    private RemoteWebDriver driver;

    // Saved as string on this class for simplicity
    String testWebsiteUsername = "shrek@scroompa.co.uk";
    String testWebsitePassword = "abc.123";

    @BeforeEach
    void setup() {
        this.driver = new ChromeDriver();
        this.driver.manage().window().maximize();
    }

    @Test
    void findDressTest() {

        this.driver.get("http://automationpractice.com/index.php");
        WebElement search = this.driver.findElement(By.cssSelector("#search_query_top"));
        search.sendKeys("Dress");
        search.sendKeys(Keys.ENTER);
        WebElement result = this.driver.findElement(By.cssSelector("#center_column > ul > " +
                "li.ajax_block_product.col-xs-12.col-sm-6.col-md-4.first-in-line.last-item-of-tablet-line.first-item-of-mobile-line > " +
                "div > div.right-block > h5 > a"));

        assertEquals("Printed Chiffon Dress", result.getText());
    }

    @Test
    void checkoutDressTest() {

        // search
        this.driver.get("http://automationpractice.com/index.php");
        WebElement searchBox = this.driver.findElement(By.id("search_query_top"));
        searchBox.sendKeys("dress");
        searchBox.sendKeys(Keys.ENTER);

        // reveal add to cart button
        WebElement visibleMenu = driver.findElement(By.cssSelector("#center_column > ul " +
                "> li.ajax_block_product.col-xs-12.col-sm-6.col-md-4.last-in-line.last-item-of-tablet-line" +
                ".last-item-of-mobile-line " +
                "> div"));
        Actions actions = new Actions(driver);
        actions.moveToElement(visibleMenu);
        WebElement revealedMenu = driver.findElement(By.cssSelector("#center_column > ul " +
                "> li.ajax_block_product.col-xs-12.col-sm-6.col-md-4.last-in-line.last-item-of-tablet-line" +
                ".last-item-of-mobile-line > div > div.right-block > div.button-container " +
                "> a.button.ajax_add_to_cart_button.btn.btn-default"));
        actions.moveToElement(revealedMenu);
        actions.click().build().perform();

        // click button after waiting for it to appear
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(20));
        WebElement addToCartBtn = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#center_column > ul " +
                        "> li.ajax_block_product.col-xs-12.col-sm-6.col-md-4.last-in-line.last-item-of-tablet-line" +
                        ".last-item-of-mobile-line > div > div.right-block > div.button-container " +
                        "> a.button.ajax_add_to_cart_button.btn.btn-default")));
        addToCartBtn.click();

        // proceed pt1
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                "#layer_cart > div.clearfix > div.layer_cart_cart.col-xs-12.col-md-6 > div.button-container > a")))
                .click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                "#center_column > p.cart_navigation.clearfix > a.button.btn.btn-default.standard-checkout.button-medium")))
                .click();

        // sign in
        WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#email")));
        WebElement pass = this.driver.findElement(By.cssSelector("#passwd"));

        email.sendKeys(testWebsiteUsername);
        pass.sendKeys(testWebsitePassword);
        pass.sendKeys(Keys.ENTER);

        // proceed pt2
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#center_column > form > p > button"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#uniform-cgv"))).click();

        this.driver.findElement(By.cssSelector("#form > p > button")).click();

        // payment
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                "#HOOK_PAYMENT > div:nth-child(1) > div > p > a")))
                .click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                "#cart_navigation > button"))).click();

        // assertion
        WebElement result = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#center_column > div > p > strong")));

        assertEquals("Your order on My Store is complete.", result.getText());
    }


    @AfterEach
    void tearDown() {
        this.driver.close();
    }
}
