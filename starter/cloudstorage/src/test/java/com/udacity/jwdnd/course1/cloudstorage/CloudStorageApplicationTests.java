package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.pages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;
	private String baseURL;

	private static WebDriver driver;

	private LoginPage loginPage;
	private SignupPage signupPage;
	private HomePage homePage;
	private NotePage notePage;
	private CredentialPage credentialPage;

	String firstName = "Hello";
	String lastName = "World";
	String username = "UserName";
	String password = "12345";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		driver = new ChromeDriver();
		baseURL = "http://localhost:" + this.port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			this.driver.quit();
		}
	}

	private void signup(){
		driver.get(baseURL + "/signup");
		signupPage = new SignupPage(driver);
		signupPage.signup(firstName, lastName, username, password);
	}

	private void login(){
		driver.get(baseURL + "/login");
		loginPage = new LoginPage(driver);
		loginPage.login(username, password);
	}

	private void createNote(String title, String description){
		driver.get(baseURL + "/home");
		notePage = new NotePage(driver);
		notePage.clickOnNavNotesTab();
		notePage.addNewNote(title, description);
	}

	@Test
	@Order(1)
	public void testGetSignupPage() {
		driver.get(baseURL + "/signup");
		signupPage = new SignupPage(driver);
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	@Order(2)
	public void testGetLoginPage() {
		driver.get(baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(3)
	void testUnauthorizedUserHomeRedirectionToLogin(){
		driver.get(baseURL + "/home");
		homePage = new HomePage(driver);
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(4)
	void testUserSignupLoginHomeLogout() {
		signup();
		login();

		Assertions.assertEquals("Home", driver.getTitle());

		driver.get(baseURL + "/home");
		homePage = new HomePage(driver);
		homePage.logout();

		Assertions.assertEquals("Login", driver.getTitle());
		Assertions.assertEquals(baseURL + "/login?logout", driver.getCurrentUrl());
		Assertions.assertEquals("You have been logged out", driver.findElement(By.id("logout-message")).getText());
	}

	@Test
	@Order(5)
	void testAddNewNote(){
		signup();
		login();
		String title = "Note Title";
		String description = "Note Description";
		driver.get(baseURL + "/home");
		notePage = new NotePage(driver);
		notePage.clickOnNavNotesTab();
		notePage.addNewNote(title, description);
		notePage.clickOnNavNotesTab();
		List<String> details = notePage.getNoteDetails();
		Assertions.assertEquals(title, details.get(0));
		Assertions.assertEquals(description, details.get(1));
	}

	@Test
	@Order(6)
	void testEditExistingNote(){
		signup();
		login();
		String title = "Note Title";
		String description = "Note Description";
		driver.get(baseURL + "/home");
		notePage = new NotePage(driver);
		notePage.clickOnNavNotesTab();
		notePage.addNewNote(title, description);
		notePage.clickOnNavNotesTab();
		List<String> details = notePage.getNoteDetails();
		Assertions.assertEquals(title, details.get(0));
		Assertions.assertEquals(description, details.get(1));

		title="new title";
		description="new description";
		notePage.editNote(title, description);
		notePage.clickOnNavNotesTab();
		details = notePage.getNoteDetails();
		Assertions.assertEquals(title, details.get(0));
		Assertions.assertEquals(description, details.get(1));
	}

	@Test
	@Order(7)
	void testDeleteExistingNote(){
		signup();
		login();
		String title = "Note Title";
		String description = "Note Description";
		driver.get(baseURL + "/home");
		notePage = new NotePage(driver);
		notePage.clickOnNavNotesTab();
		notePage.addNewNote(title, description);
		notePage.clickOnNavNotesTab();

		List<String> details = notePage.getNoteDetails();
		Assertions.assertEquals(title, details.get(0));
		Assertions.assertEquals(description, details.get(1));

		notePage.deleteNote(title, description);
		notePage.clickOnNavNotesTab();
		Assertions.assertEquals(0, driver.findElements(By.id("note-title-display")).size());
		Assertions.assertEquals(0, driver.findElements(By.id("note-description-display")).size());
	}

	@Test
	@Order(8)
	void testCreateCredentials(){
		String url = "www.superduperdrive.com";
		String secondUrl = "www.second-credential.com";
		String secondUsername = "second user";
		String secondPassword = "second password";

		signup();
		login();

		driver.get(baseURL + "/home");
		credentialPage = new CredentialPage(driver);

		credentialPage.clickOnCredentialTab();
		credentialPage.addNewCredential(url, username, password);

		credentialPage.clickOnCredentialTab();
		credentialPage.addNewCredential(secondUrl, secondUsername, secondPassword);

		credentialPage.clickOnCredentialTab();

		// First credential
		List<String> details = credentialPage.getCredentialDetails(1);
		Assertions.assertEquals(url, details.get(0));
		Assertions.assertEquals(username, details.get(1));
		// Password must be encrypted
		Assertions.assertNotEquals(password, details.get(2));

		// Second credential
		details = credentialPage.getCredentialDetails(2);
		Assertions.assertEquals(secondUrl, details.get(0));
		Assertions.assertEquals(secondUsername, details.get(1));
		// Password must be encrypted
		Assertions.assertNotEquals(secondPassword, details.get(2));
	}

	@Test
	@Order(9)
	public void testEditCredentials(){
		String firstUrl = "www.superduperdrive.com";
		String firstUsername = this.username;
		String firstPassword = this.password;
		String secondUrl = "www.second-credential.com";
		String secondUsername = "second user";
		String secondPassword = "second password";
		String suffix = "modified";

		signup();
		login();

		driver.get(baseURL + "/home");
		credentialPage = new CredentialPage(driver);

		credentialPage.clickOnCredentialTab();
		credentialPage.addNewCredential(firstUrl, firstUsername, firstPassword);

		credentialPage.clickOnCredentialTab();
		credentialPage.addNewCredential(secondUrl, secondUsername, secondPassword);

		credentialPage.clickOnCredentialTab();
		// View first credential
		credentialPage.viewCredential(1);
		// Password must be decrypted when viewed
		Assertions.assertEquals(firstPassword, credentialPage.getUncryptedPassword());

		// Edit first credential
		firstUrl += "/" + suffix;
		firstUsername += "-" + suffix;
		firstPassword += "-" + suffix;
		credentialPage.setCredential(firstUrl, firstUsername, firstPassword);

		credentialPage.clickOnCredentialTab();
		List<String> details = credentialPage.getCredentialDetails(1);
		Assertions.assertEquals(firstUrl, details.get(0));
		Assertions.assertEquals(firstUsername, details.get(1));
		// Password must be encrypted
		Assertions.assertNotEquals(firstPassword, details.get(2));

		// View second credential
		credentialPage.viewCredential(2);
		// Password must be decrypted when viewed
		Assertions.assertEquals(secondPassword, credentialPage.getUncryptedPassword());
		// Edit second credential
		secondUrl += "/" + suffix;
		secondUsername += "-" + suffix;
		secondPassword += "-" + suffix;
		credentialPage.setCredential(secondUrl, secondUsername, secondPassword);

		credentialPage.clickOnCredentialTab();
		details = credentialPage.getCredentialDetails(2);
		Assertions.assertEquals(secondUrl, details.get(0));
		Assertions.assertEquals(secondUsername, details.get(1));
		// Password must be encrypted
		Assertions.assertNotEquals(secondPassword, details.get(2));
	}

	@Test
	@Order(10)
	public void testDeleteCredentials() {
		signup();
		login();

		driver.get(baseURL + "/home");
		credentialPage = new CredentialPage(driver);

		credentialPage.clickOnCredentialTab();
		credentialPage.addNewCredential("www.superduperdrive.com", this.username, this.password);

		credentialPage.clickOnCredentialTab();
		credentialPage.addNewCredential("www.second-credential.com", "second user", "second password");

		credentialPage.clickOnCredentialTab();
		credentialPage.deleteCredential(1);
		credentialPage.clickOnCredentialTab();
		credentialPage.deleteCredential(2);

		credentialPage.clickOnCredentialTab();
		Assertions.assertEquals(0, driver.findElements(By.id("credential-list")).size());
	}
}
