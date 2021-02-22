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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotePage {
    @FindBy(id="nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id="add-note-button")
    private WebElement addNoteButton;

    @FindBy(id="edit-note-link")
    private WebElement editNoteLink;

    @FindBy(id="save-changes-note-button")
    private WebElement saveChangesNoteButton;

    @FindBy(id = "delete-note-link")
    private WebElement deleteNoteLink;

    @FindBy(id="note-title")
    private WebElement noteTitle;

    @FindBy(id="note-description")
    private WebElement noteDescription;

    @FindBy(id="note-title-display")
    private WebElement noteTitleDisplay;

    @FindBy(id="note-description-display")
    private WebElement noteDescriptionDisplay;

    private final WebDriver driver;

    public void waitForElement(WebElement element){
        new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(element));
    }

    private void waitForElementClick(WebElement element){
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
        waitForElementClick(navNotesTab);
    }

    public void addNewNote(String noteTitle, String noteDescription){
        waitForElementClick(addNoteButton);
        waitForElementSendKeys(this.noteTitle, noteTitle);
        waitForElementSendKeys(this.noteDescription, noteDescription);
        waitForElementClick(saveChangesNoteButton);
    }

    public List<String> getNoteDetails(){
        return Arrays.asList(getWebElement(noteTitleDisplay).getText(), getWebElement(noteDescriptionDisplay).getText());
    }

    public void editNote(String title, String description){
        waitForElementClick(editNoteLink);
        waitForElementSendKeys(this.noteTitle, title);
        waitForElementSendKeys(this.noteDescription, description);
        waitForElementClick(saveChangesNoteButton);
    }

    public void deleteNote(String title, String description){
        waitForElementClick(deleteNoteLink);
    }
}