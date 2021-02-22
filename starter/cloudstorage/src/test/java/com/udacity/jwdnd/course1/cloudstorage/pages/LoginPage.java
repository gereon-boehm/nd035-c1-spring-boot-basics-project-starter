package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    private final WebDriver driver;
    private void waitForElement(WebElement element){
        new WebDriverWait(driver, 100).until(ExpectedConditions.visibilityOf(element));
    }

    private void waitForElementSendKeys(WebElement element, String key){
        waitForElement(element);
        element.sendKeys(key);
    }

    private void waitForElementClick(WebElement element){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void login(String username, String password){
        waitForElementSendKeys(this.username, username);
        waitForElementSendKeys(this.password, password);
        waitForElementClick(loginButton);
    }
}
