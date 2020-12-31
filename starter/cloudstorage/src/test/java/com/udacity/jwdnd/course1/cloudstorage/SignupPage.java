package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    @FindBy(id = "inputFirstName")
    private WebElement firstNameField;

    @FindBy(id = "inputLastName")
    private WebElement lastNameField;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "signup")
    private WebElement signupButton;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void setFirstNameField(String firstNameField) {
        this.firstNameField.sendKeys(firstNameField);
    }

    public void setLastNameField(String lastNameField) {
        this.lastNameField.sendKeys(lastNameField);
    }

    public void setUsernameField(String usernameField) {
        this.usernameField.sendKeys(usernameField);
    }

    public void setPasswordField(String passwordField) {
        this.passwordField.sendKeys(passwordField);
    }

    public void signUp(String fname, String lname, String uname, String pword){
        setFirstNameField(fname);
        setLastNameField(lname);
        setUsernameField(uname);
        setPasswordField(pword);
        signupButton.click();
    }
}
