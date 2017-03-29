package prg.training.addressbook.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import prg.training.addressbook.utils.appManager.AppManager;
import prg.training.addressbook.utils.appManager.WebDriverProvider;

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
        WebElement element = WebDriverProvider.getDriver().findElement(locator);
        element.click();
    }

    public void enterText(By locator, String text) {
        WebDriverProvider.getDriver().findElement(locator).click();
        WebDriverProvider.getDriver().findElement(locator).clear();
        WebDriverProvider.getDriver().findElement(locator).sendKeys(text);
    }

    public void submit() {
        clickOn(By.name("submit"));
    }
}
