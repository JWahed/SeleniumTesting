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
    }

    @Test
    void findDressTest() {

        this.driver.get("http://automationpractice.com/index.php");
        WebElement search = this.driver.findElement(By.cssSelector("#search_query_top"));
        search.sendKeys("Dress");
        search.sendKeys(Keys.ENTER);
        WebElement result = this.driver.findElement(By.cssSelector("#center_column > ul > li.ajax_block_product.col-xs-12.col-sm-6.col-md-4.first-in-line.last-item-of-tablet-line.first-item-of-mobile-line > div > div.right-block > h5 > a"));

        assertEquals("Printed Chiffon Dress", result.getText());
    }

    @Test
    void checkoutDressTest() {

        this.driver.get("http://automationpractice.com/index.php");
        WebElement search = this.driver.findElement(By.cssSelector("#search_query_top"));
        search.sendKeys("Dress");
        search.sendKeys(Keys.ENTER);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement addToCartBtn = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#center_column > ul > li:nth-child(1) > div > div.right-block > div.button-container > " +
                        "a.button.ajax_add_to_cart_button.btn.btn-default")));
        if (addToCartBtn.isDisplayed()) {
            System.out.println("Got IT!");
            addToCartBtn.click();
        }

//        this.driver.findElement(By.cssSelector("#center_column > ul > li.ajax_block_product.col-xs-12.col-sm-6.col-md-4.last-in-line.last-item-of-tablet-line.last-item-of-mobile-line > div > div.right-block > div.button-container > a.button.ajax_add_to_cart_button.btn.btn-default > span"));
//        this.driver.findElement(By.cssSelector("#layer_cart > div.clearfix > div.layer_cart_cart.col-xs-12.col-md-6 > div.button-container > a > span"));
//        this.driver.findElement(By.cssSelector("#center_column > ul > li.ajax_block_product.col-xs-12.col-sm-6.col-md-4.last-in-line.last-item-of-tablet-line.last-item-of-mobile-line > div > div.right-block > h5 > a"));
//        this.driver.findElement(By.cssSelector("#center_column > p.cart_navigation.clearfix > a.button.btn.btn-default.standard-checkout.button-medium > span"));
//        this.driver.findElement(By.cssSelector("#email")).sendKeys(testWebsiteUsername);
//        this.driver.findElement(By.cssSelector("#passwd")).sendKeys(testWebsitePassword, Keys.ENTER);
//        this.driver.findElement(By.cssSelector("#center_column > form > p > button > span"));
//        this.driver.findElement(By.cssSelector("#cgv")).click();
//        this.driver.findElement(By.cssSelector("#center_column > form > p > button > span"));
//        this.driver.findElement(By.cssSelector("#HOOK_PAYMENT > div:nth-child(1) > div > p > a"));
//        this.driver.findElement(By.cssSelector("#cart_navigation > button > span"));
        WebElement result = this.driver.findElement(By.cssSelector("#center_column > div > p > strong"));

        assertEquals("Your order on My Store is complete", result.getText());

    }


    @AfterEach
    void tearDown() {
        this.driver.close();
    }
}
