package prg.training.addressbook.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import prg.training.addressbook.utils.appManager.AppManager;

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
        clickOn(element);
        element.clear();
        element.sendKeys(text);
    }

    public WebElement getElement(By locator) {
        WebDriverWait wait = new WebDriverWait(TestBase.getDriver(), 3);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((Locatable) element).getCoordinates().inViewPort();
        return element;
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
