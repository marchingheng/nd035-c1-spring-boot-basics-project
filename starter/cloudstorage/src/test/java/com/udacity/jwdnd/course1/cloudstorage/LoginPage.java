package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "login")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void setUsernameField(String usernameField) {
        this.usernameField.sendKeys(usernameField);
    }

    public void setPasswordField(String passwordField) {
        this.passwordField.sendKeys(passwordField);
    }

    public void login(String uname, String pword){
        setUsernameField(uname);
        setPasswordField(pword);
        loginButton.click();
    }
}

