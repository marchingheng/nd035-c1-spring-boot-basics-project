package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {
    @FindBy(id = "logout")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credTab;

    @FindBy(id = "add-new-note")
    private WebElement addNoteButton;

    @FindBy(id = "add-new-cred")
    private WebElement addCredButton;

    @FindBy(id = "noteModal")
    private WebElement noteModal;

    @FindBy(id = "credentialModal")
    private WebElement credModal;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "credential-url")
    private WebElement credURL;

    @FindBy(id = "credential-username")
    private WebElement credUname;

    @FindBy(id = "credential-password")
    private WebElement credPword;

    @FindBy(id = "save-note")
    private WebElement saveNoteButton;

    @FindBy(id = "save-cred")
    private WebElement saveCredButton;

    @FindBy(id = "goto-home")
    private WebElement gotoHome;

    @FindBy(id = "note-table")
    private WebElement noteTable;

    @FindBy(className = "note-row")
    private List<WebElement> noteRow;

    @FindBy(id = "row-note-description")
    private WebElement rowNoteDescription;

    @FindBy(className = "cred-row")
    private List<WebElement> credRow;

    @FindBy(className = "row-cred-uname")
    private WebElement rowCredUname;

    @FindBy(id = "back-to-home")
    private WebElement backToHome;

    @FindBy(id = "edit-note")
    private WebElement editNoteButton;

    @FindBy(id = "delete-note-button")
    private WebElement deleteNoteButton;

    @FindBy(className = "edit-cred")
    private WebElement editCredButton;

    @FindBy(className = "delete-credential-button")
    private WebElement deleteCredButton;

    WebDriverWait wait;
    JavascriptExecutor jse;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 20);
        jse = (JavascriptExecutor) driver;
    }

    public WebElement getEditNoteButton() {
        return editNoteButton;
    }

    public List<WebElement> getNoteRow() {
        return noteRow;
    }

    public List<WebElement> getCredRow() {
        return credRow;
    }

    public WebElement getNoteTable() {
        return noteTable;
    }

    public WebElement getAddNoteButton() {
        return addNoteButton;
    }

    public WebElement getAddCredButton() {
        return addCredButton;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle.sendKeys(noteTitle);
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription.sendKeys(noteDescription);
    }

    public WebElement getNotesTab() {
        return notesTab;
    }

    public WebElement getCredTab() {
        return credTab;
    }

    public WebElement getLogoutButton() {
        return logoutButton;
    }

    public void setCredURL(String credURL) {
        this.credURL.sendKeys(credURL);
    }

    public void setCredUname(String credUname) {
        this.credUname.sendKeys(credUname);
    }

    public void setCredPword(String credPword) {
        this.credPword.sendKeys(credPword);
    }

    public void logout(){
        logoutButton.click();
    }

    public void gotoNotesTab() throws InterruptedException {
        jse.executeScript("arguments[0].click()", notesTab);
    }

    public void gotoCredTab() throws InterruptedException {
        jse.executeScript("arguments[0].click()", credTab);
    }

    public void clickAddNote(){
        addNoteButton.click();
        wait.until(ExpectedConditions.visibilityOf(noteModal));
    }

    public void clickEditNote(WebElement editNoteButton){
        editNoteButton.click();
        wait.until(ExpectedConditions.visibilityOf(noteModal));
    }

    public void clickAddCred(){
        addCredButton.click();
        wait.until(ExpectedConditions.visibilityOf(credModal));
    }

    public void clickEditCred(WebElement editCredButton){
        editCredButton.click();
        wait.until(ExpectedConditions.visibilityOf(credModal));
    }

    public void clickDeleteNote(WebElement deleteNoteButton){
        jse.executeScript("arguments[0].click()", deleteNoteButton);
        wait.until(ExpectedConditions.visibilityOf(backToHome));
    }

    public void clickDeleteCred(WebElement deleteCredButton){
        jse.executeScript("arguments[0].click()", deleteCredButton);
        wait.until(ExpectedConditions.visibilityOf(backToHome));
    }

    public void saveNote(String title, String description){
        clickAddNote();
        setNoteTitle(title);
        setNoteDescription(description);
        saveNoteButton.click();
    }

    public void goBackToNotes() throws InterruptedException {
        jse.executeScript("arguments[0].click()", backToHome);
        wait.until(ExpectedConditions.visibilityOf(notesTab));
        gotoNotesTab();
        wait.until(ExpectedConditions.visibilityOf(addNoteButton));
    }

    public void goBackToCred() throws InterruptedException {
        jse.executeScript("arguments[0].click()", backToHome);
        wait.until(ExpectedConditions.visibilityOf(credTab));
        gotoCredTab();
        wait.until(ExpectedConditions.visibilityOf(addCredButton));
    }

    public void editNote(String description){
        noteDescription.clear();
        setNoteDescription(description);
        saveNoteButton.click();
    }

    public void editCred(String username){
        credUname.clear();
        setCredUname(username);
        saveCredButton.click();
    }

    public void saveCred(String url, String uname, String pwd){
        clickAddCred();
        setCredURL(url);
        setCredUname(uname);
        setCredPword(pwd);
        saveCredButton.click();
    }
}

