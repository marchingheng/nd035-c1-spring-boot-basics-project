package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;
	private static WebDriverWait wait;

	private SignupPage signupPage;
	private LoginPage loginPage;
	private HomePage homePage;

	String baseUrl;

	String signupPath = "/signup";
	String loginPath = "/login";
	String homePath = "/home";
	String logoutPath = loginPath+"?logout";

	String fname = "Rahul";
	String lname = "Gupta";
	String uname = "ra18";
	String pword = "1234";

	/*Notes*/
	String title = "My Note 34";
	String description = "Description 34";
	String updateDescription = "Updated Description 1";

	/* Credential */
	String url = "http://localhost:8080/home";
	String cred_user = "rahul12";
	String cred_pwd = "3456";
	String update_cred_user = "updated-rahul12";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 20);
	}

	@BeforeEach
	public void beforeEach(){
		baseUrl = "http://localhost:" + this.port;
		signupPage = new SignupPage(driver);
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
	}

	@AfterAll
	static void afterAll() {
		driver.quit();
	}

	@Test
	@Order(1)
	public void testUnAuthAccess() {
		testHomePageNoAccessWithoutLogin();
	}

	@Test
	@Order(2)
	public void testValidUserSignupAndLogin() throws InterruptedException {
		testSignup();
		testLogin();
		testLogout();
		testHomePageNoAccessWithoutLogin();
	}

	@Test
	@Order(3)
	public void testSaveNotes() throws InterruptedException {
		testLogin();
		wait.until(ExpectedConditions.visibilityOf(homePage.getNotesTab()));
		homePage.gotoNotesTab();
		wait.until(ExpectedConditions.visibilityOf(homePage.getAddNoteButton()));
		homePage.saveNote(title,description);
		homePage.goBackToNotes();
		List<WebElement> rows = homePage.getNoteRow();
		WebElement rowAdded = rows.get(rows.size()-1).findElement(By.id("row-note-description"));
		Assertions.assertEquals(description, rowAdded.getText());
		testLogout();
	}

	@Test
	@Order(4)
	public void testEditNotes() throws InterruptedException {
		testLogin();
		wait.until(ExpectedConditions.visibilityOf(homePage.getNotesTab()));
		homePage.gotoNotesTab();
		wait.until(ExpectedConditions.visibilityOf(homePage.getAddNoteButton()));
		List<WebElement> rows = homePage.getNoteRow();
		WebElement editButton = rows.get(rows.size()-1).findElement(By.id("edit-note"));
		homePage.clickEditNote(editButton);
		homePage.editNote(updateDescription);
		homePage.goBackToNotes();
		WebElement rowAdded = rows.get(rows.size()-1).findElement(By.id("row-note-description"));
		Assertions.assertEquals(updateDescription, rowAdded.getText());
		testLogout();
	}

	@Test
	@Order(5)
	public void testDeleteNotes() throws InterruptedException {
		testLogin();
		wait.until(ExpectedConditions.visibilityOf(homePage.getNotesTab()));
		homePage.gotoNotesTab();
		wait.until(ExpectedConditions.visibilityOf(homePage.getAddNoteButton()));
		List<WebElement> rows = homePage.getNoteRow();
		WebElement deleteButton = rows.get(rows.size()-1).findElement(By.id("delete-note-button"));
		String removedDescription = rows.get(rows.size()-1).findElement(By.id("row-note-description")).getText();
		homePage.clickDeleteNote(deleteButton);
		homePage.goBackToNotes();
		if(rows.size() != 0 ) {
			WebElement lastRow = rows.get(rows.size() - 1).findElement(By.id("row-note-description"));
			Assertions.assertNotEquals(removedDescription, lastRow.getText());
		}else{
			Assertions.assertEquals(rows.size(), 0);
		}
		testLogout();
	}

	@Test
	@Order(6)
	public void testSaveCred() throws InterruptedException {
		testLogin();
		wait.until(ExpectedConditions.visibilityOf(homePage.getCredTab()));
		homePage.gotoCredTab();
		wait.until(ExpectedConditions.visibilityOf(homePage.getAddCredButton()));
		homePage.saveCred(url, cred_user, cred_pwd);
		homePage.goBackToCred();
		List<WebElement> rows = homePage.getCredRow();
		WebElement rowAdded = rows.get(rows.size()-1).findElement(By.className("row-cred-uname"));
		Assertions.assertEquals(cred_user, rowAdded.getText());
		testLogout();
	}

	@Test
	@Order(7)
	public void testEditCred() throws InterruptedException {
		testLogin();
		wait.until(ExpectedConditions.visibilityOf(homePage.getCredTab()));
		homePage.gotoCredTab();
		wait.until(ExpectedConditions.visibilityOf(homePage.getAddCredButton()));
		List<WebElement> rows = homePage.getCredRow();
		WebElement editButton = rows.get(rows.size()-1).findElement(By.className("edit-cred"));
		homePage.clickEditCred(editButton);
		homePage.editCred(update_cred_user);
		homePage.goBackToCred();
		WebElement rowAdded = rows.get(rows.size()-1).findElement(By.className("row-cred-uname"));
		Assertions.assertEquals(update_cred_user, rowAdded.getText());
		testLogout();
	}

	@Test
	@Order(8)
	public void testDeleteCred() throws InterruptedException {
		testLogin();
		wait.until(ExpectedConditions.visibilityOf(homePage.getCredTab()));
		homePage.gotoCredTab();
		wait.until(ExpectedConditions.visibilityOf(homePage.getAddCredButton()));

		List<WebElement> rows = homePage.getCredRow();
		WebElement deleteButton = rows.get(rows.size()-1).findElement(By.id("delete-credential-button"));
		String removedUsername = rows.get(rows.size()-1).findElement(By.className("row-cred-uname")).getText();

		homePage.clickDeleteCred(deleteButton);
		homePage.goBackToCred();
		if( rows.size() != 0){
			WebElement lastRow = rows.get(rows.size()-1).findElement(By.className("row-cred-uname"));
			Assertions.assertEquals(removedUsername, lastRow.getText());
		}else{
			Assertions.assertEquals(rows.size(), 0);
		}
		testLogout();
	}



	public void testSignup() {
		driver.get(baseUrl + signupPath);
		Assertions.assertEquals("Sign Up", driver.getTitle());
		signupPage.signUp(fname, lname, uname, pword);
		wait.until(ExpectedConditions.urlToBe(baseUrl + loginPath));
	}

	public void testHomePageNoAccessWithoutLogin(){
		driver.get(baseUrl + homePath);
		wait.until(ExpectedConditions.urlToBe(baseUrl + loginPath));
		Assertions.assertEquals("Login", driver.getTitle());
	}

	public void testLogin(){
		driver.get(baseUrl + loginPath);
		Assertions.assertEquals("Login", driver.getTitle());
		loginPage.login(uname, pword);
		wait.until(ExpectedConditions.urlToBe(baseUrl + homePath));
		Assertions.assertEquals("Home", driver.getTitle());
	}

	public void testLogout() {
		driver.get(baseUrl + homePath);
		wait.until(ExpectedConditions.visibilityOf(homePage.getLogoutButton()));
		homePage.logout();
		wait.until(ExpectedConditions.urlToBe(baseUrl + logoutPath));
		Assertions.assertEquals("Login", driver.getTitle());
	}

}