package prg.training.addressbook.base;

import org.openqa.selenium.*;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import prg.training.addressbook.utils.appManager.AppManager;

import java.io.File;

/**
 * Created by QA Lady on 3/29/2017.
 */
public class HelperBase {

    protected AppManager appManager;

    public HelperBase(AppManager appManager) {
        this.appManager = appManager;
    }


    //common methods
    public void clickOn(By locator) {
        WebElement element = getElement(locator);
        element.click();
    }

    public void clickOn(WebElement element) {
        element.click();
    }

    public void enterText(By locator, String text) {
        WebElement element = getElement(locator);
        //will enter text only if text not equals null if the field doesn't already have the same text entered
        if (text == null || element.getAttribute("value").equals(text)) {
            return;
        } else {
            clickOn(element);
            element.clear();
            element.sendKeys(text);
        }
    }

    public void attach(By locator, File file) {
        if (file == null) {
            WebElement element = getElement(locator);
            //selenium doesn't like "/" had to replace to "\\"
            element.sendKeys(file.getAbsolutePath().replace("/", "\\"));
        }
    }

    public WebElement getElement(By locator) {
        WebDriverWait wait = new WebDriverWait(TestBase.getDriver(), 3);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((Locatable) element).getCoordinates().inViewPort();
        return element;
    }

    public boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (InvalidSelectorException ex) {
            throw ex;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public void submit() {
        clickOn(By.name("submit"));
    }

    public void update() {
        clickOn(By.name("update"));
    }

    public void checkSuccessMessage(By locator, String expectedText) {
        Assert.assertEquals(getElement(locator).getText(), expectedText);
    }
}
