package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SignupPage {
    @FindBy(id = "inputFirstName")
    private WebElement firstname;

    @FindBy(id = "inputLastName")
    private WebElement lastname;

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "signup-button")
    private WebElement signupButton;

    @FindBy(id = "login-link")
    private WebElement loginLink;

    private final WebDriver driver;

    private void waitForElement(WebElement element){
        new WebDriverWait(driver, 100).until(ExpectedConditions.visibilityOf(element));
    }

    private void waitForTitle(String title){
        new WebDriverWait(driver, 100).until(ExpectedConditions.titleContains(title));
    }

    private void waitForButtonClick(WebElement element){
        new WebDriverWait(driver, 100).until(ExpectedConditions.elementToBeClickable(element));
        System.out.println(element.getText() + " is clickable");
        element.click();
    }

    private void waitForElementSendKeys(WebElement element, String key){
        waitForElement(element);
        element.sendKeys(key);
    }

    public SignupPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void signup(String  inputFirstname, String inputLastname, String inputUsername, String inputPassword) {
        waitForElementSendKeys(firstname, inputFirstname);
        waitForElementSendKeys(lastname,  inputLastname);
        waitForElementSendKeys(username,  inputUsername);
        waitForElementSendKeys(password, inputPassword);
        waitForButtonClick(signupButton);
        waitForTitle("Login");
    }
}
