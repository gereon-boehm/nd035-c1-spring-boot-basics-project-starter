package com.udacity.jwdnd.course1.cloudstorage.pages;

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

public class NotePage {
    @FindBy(id="link-to-home")
    WebElement linkToHome;

    @FindBy(id="nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id="add-note-button")
    private WebElement addNoteButton;

    @FindBy(id="save-changes-note-button")
    private WebElement saveChangesNoteButton;

    @FindBy(id="note-title")
    private WebElement noteTitle;

    @FindBy(id="note-description")
    private WebElement noteDescription;

    private final WebDriver driver;

    public void waitForElement(WebElement element){
        new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(element));
    }

    private void clickElement(WebElement element){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    private void waitForElementSendKeys(WebElement element, String key){
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='"+ key +"';", element);
    }

    private WebElement getWebElement(WebElement element){
        waitForElement(element);
        return element;
    }

    public NotePage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void clickOnNavNotesTab(){
        clickElement(navNotesTab);
    }

    public void addNewNote(String noteTitle, String noteDescription){
        clickElement(addNoteButton);
        waitForElementSendKeys(this.noteTitle, noteTitle);
        waitForElementSendKeys(this.noteDescription, noteDescription);
        clickElement(saveChangesNoteButton);
    }

    public List<String> getNoteDetails(int id){
        WebElement title = getWebElement(driver.findElement(By.id("note-title-display-" + id)));
        WebElement description = getWebElement(driver.findElement(By.id("note-description-display-" + id)));
        return Arrays.asList(title.getText(), description.getText());
    }

    public void editNote(int id, String title, String description){
        clickElement(driver.findElement(By.id("note-edit-link-" + id)));
        waitForElementSendKeys(this.noteTitle, title);
        waitForElementSendKeys(this.noteDescription, description);
        clickElement(saveChangesNoteButton);
    }

    public void deleteNote(int id){
        clickElement(driver.findElement(By.id("note-delete-link-" + id)));
    }

    public void goToHome(){
        clickElement(linkToHome);
    }
}