package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.TestHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;

public class CredentialPage {
    @FindBy(id="link-to-home")
    WebElement linkToHome;

    @FindBy(id="nav-credential-tab")
    private WebElement navCredentialTab;

    @FindBy(id="add-credential-button")
    private WebElement addCredentialButton;

    @FindBy(id = "credential-edit-link")
    private WebElement credentialEditLink;

    @FindBy(id = "credential-delete-link")
    private WebElement credentialDeleteLink;

    @FindBy(id="save-changes-credential-button")
    private WebElement saveChangesCredentialButton;

    @FindBy(id="credential-url")
    private WebElement credentialUrl;

    @FindBy(id="credential-username")
    private WebElement credentialUsername;

    @FindBy(id="credential-password")
    private WebElement credentialPassword;

    private final WebDriver driver;

    public void waitForElement(WebElement element){
        new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(element));
    }

    private void waitForElementClick(WebElement element){
        waitForElement(element);
        element.click();
    }

    private void waitForElementSendKeys(WebElement element, String key){
        waitForElement(element);
        element.clear();
        element.sendKeys(key);
    }

    private void clickElement(WebElement element){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    private void sendKeys(WebElement element, String key){
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='"+ key +"';", element);
    }

    private WebElement getWebElement(WebElement element){
        waitForElement(element);
        return element;
    }

    public CredentialPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void clickOnCredentialTab(){
        clickElement(navCredentialTab);
    }

    public void addNewCredential(String url, String username, String password){
        waitForElementClick(addCredentialButton);
        waitForElementSendKeys(this.credentialUrl, url);
        waitForElementSendKeys(this.credentialUsername, username);
        waitForElementSendKeys(this.credentialPassword, password);
        waitForElementClick(saveChangesCredentialButton);
    }

    public List<String> getCredentialDetails(int row){
        String url = getWebElement(driver.findElement(By.id("credential-url-display-" + row))).getText();
        String username = getWebElement(driver.findElement(By.id("credential-username-display-" + row))).getText();
        String password = getWebElement(driver.findElement(By.id("credential-password-display-" + row))).getText();
        return Arrays.asList(url, username, password);
    }

    public void viewCredential(int row){
        WebElement element = driver.findElement(By.id("credential-edit-link-" + row));
        clickElement(element);
    }

    public String getUncryptedPassword(){
        return this.credentialPassword.getAttribute("value");
    }

    public void setCredential(String url, String username, String password){
        waitForElementSendKeys(this.credentialUrl, url);
        waitForElementSendKeys(this.credentialUsername, username);
        waitForElementSendKeys(this.credentialPassword, password);
        waitForElementClick(saveChangesCredentialButton);
    }

    public void deleteCredential(int row){
        waitForElementClick(driver.findElement(By.id("credential-delete-link-" + row)));
    }

    public void goToHome(){
        clickElement(linkToHome);
    }
}
