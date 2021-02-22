package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultPage {
    private final WebDriver driver;

    private void waitForElement(WebElement element) {
        new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(element));
    }

    private void waitForElementClick(WebElement element) {
        waitForElement(element);
        element.click();
    }

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
}