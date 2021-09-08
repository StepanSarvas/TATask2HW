import javafx.scene.layout.Priority;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.xml.sax.helpers.AttributesImpl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AvicTestHW2 {
    private WebDriver driver;

    @BeforeTest
    public void chromeDriverSetUp(){
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
    }

    @BeforeMethod
    public void browserSetUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://avic.ua/");
    }

    @Test(priority = 1)
    public void checkBrandNameInURL(){
        driver.findElement(By.xpath("//input[contains(@class, 'search-query')]")).sendKeys("Philips");
        driver.findElement(By.xpath("//button[contains(@class, 'button-reset search-btn')]")).click();
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@class, 'btn-see-more js_show_more')]")));
        assertTrue (driver.getCurrentUrl().contains("query=Philips"));
    }

    @Test (priority = 2)
    public void checkGoodsAmountAfterShowMore(){
        driver.findElement(By.xpath("//input[contains(@class, 'search-query')]")).sendKeys("Philips");
        driver.findElement(By.xpath("//button[contains(@class, 'button-reset search-btn')]")).click();
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@class, 'btn-see-more js_show_more')]")));
        driver.findElement(By.xpath("//a[contains(@class, 'btn-see-more js_show_more')]")).click();
        WebDriverWait wait2 = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@data-product, '246562')]")));
        List<WebElement> elementList = driver.findElements(By.xpath("//div[contains(@class, 'item-prod')]"));
        int goodsAmount = elementList.size();
        assertEquals(goodsAmount, 24);
    }

    @Test(priority = 3)
        public void checkThatGoodsContainBrandName(){
        driver.findElement(By.xpath("//input[contains(@class, 'search-query')]")).sendKeys("Philips");
        driver.findElement(By.xpath("//button[contains(@class, 'button-reset search-btn')]")).click();
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@class, 'btn-see-more js_show_more')]")));
        driver.findElement(By.xpath("//a[contains(@class, 'btn-see-more js_show_more')]")).click();
        WebDriverWait wait2 = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@data-product, '246562')]")));
        List<WebElement> elementList = driver.findElements(By.xpath("//div[contains(@class, 'item-prod')]"));
        for(WebElement webElement : elementList){
            assertTrue(webElement.getText().contains("Philips") || webElement.getText().contains("PHILIPS"));
        }
        }

    @Test (priority = 4)
    public void checkPriceInBucket(){
        driver.findElement(By.xpath("//span[text() = 'Каталог товарів']")).click();
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text() = 'Аудіо техніка']")));
        driver.findElement(By.xpath("//span[text() = 'Аудіо техніка']")).click();
        WebDriverWait wait3 = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[contains(@alt, 'Навушники')]")));
        driver.findElement(By.xpath("//img[contains(@alt, 'Навушники')]")).click();
        WebDriverWait wait4 = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@class, 'filter-link') and contains(@href, 'naushniki/proizvoditel--marshall')]")));
        driver.findElement(By.xpath("//a[contains(@class, 'filter-link') and contains(@href, 'naushniki/proizvoditel--marshall')]")).click();
        WebDriverWait wait5 = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@data-ecomm-cart, 'Marshall Major IV Black')]")));
        driver.findElement(By.xpath("//a[contains(@data-ecomm-cart, 'Marshall Major IV Black')]")).click();
        WebDriverWait wait6 = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, 'count--plus ')]")));
        driver.findElement(By.xpath("//span[contains(@class, 'count--plus ')]")).click();
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String webElement = driver.findElement(By.xpath("//div[contains(@class, 'item-total')]/span [contains(@class , 'prise')]")).getText();
        assertEquals(webElement, "7640 грн");

    }


    @AfterMethod
    public void tearDown(){
        driver.close();
    }
}
