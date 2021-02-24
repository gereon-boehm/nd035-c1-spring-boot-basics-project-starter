package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
	private ResultPage resultPage;

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
		login();
		String title = "Note Title";
		String description = "Note Description";
		driver.get(baseURL + "/home");
		notePage = new NotePage(driver);
		notePage.clickOnNavNotesTab();
		notePage.addNewNote(title, description);
		notePage.goToHome();
		notePage.clickOnNavNotesTab();
		List<String> details = notePage.getNoteDetails(1);
		Assertions.assertEquals(title, details.get(0));
		Assertions.assertEquals(description, details.get(1));
	}

	@Test
	@Order(6)
	void testEditExistingNote(){
		login();
		driver.get(baseURL + "/home");
		notePage = new NotePage(driver);
		notePage.clickOnNavNotesTab();
		notePage.editNote(1, "New Title", "New Description");
		notePage.goToHome();
		notePage.clickOnNavNotesTab();
		List<String> details = notePage.getNoteDetails(1);
		Assertions.assertEquals("New Title", details.get(0));
		Assertions.assertEquals("New Description", details.get(1));
	}

	@Test
	@Order(7)
	void testDeleteExistingNote(){
		login();
		driver.get(baseURL + "/home");
		notePage = new NotePage(driver);
		notePage.clickOnNavNotesTab();
		notePage.deleteNote(1);
		notePage.goToHome();
		notePage.clickOnNavNotesTab();
		Assertions.assertEquals(0, driver.findElements(By.id("note-list")).size());
	}

	@Test
	@Order(8)
	void testCreateCredentials(){
		login();

		driver.get(baseURL + "/home");
		credentialPage = new CredentialPage(driver);

		credentialPage.clickOnCredentialTab();
		credentialPage.addNewCredential("www.first-url.com", "firstUser", "first-password");
		credentialPage.goToHome();
		credentialPage.clickOnCredentialTab();
		credentialPage.addNewCredential("www.second-url.com", "secondUser", "second-password");
		credentialPage.goToHome();
		credentialPage.clickOnCredentialTab();

		// First credential
		List<String> details = credentialPage.getCredentialDetails(1);
		Assertions.assertEquals("www.first-url.com", details.get(0));
		Assertions.assertEquals("firstUser", details.get(1));
		// Password must be encrypted
		Assertions.assertNotEquals("first-password", details.get(2));

		// Second credential
		details = credentialPage.getCredentialDetails(2);
		Assertions.assertEquals("www.second-url.com", details.get(0));
		Assertions.assertEquals("secondUser", details.get(1));
		// Password must be encrypted
		Assertions.assertNotEquals("second-password", details.get(2));
	}

	@Test
	@Order(9)
	public void testEditCredentials(){
		login();

		driver.get(baseURL + "/home");
		credentialPage = new CredentialPage(driver);

		credentialPage.clickOnCredentialTab();
		// View first credential
		credentialPage.viewCredential(1);
		// Password must be decrypted when viewed
		Assertions.assertEquals("first-password", credentialPage.getUncryptedPassword());

		// Edit first credential
		credentialPage.setCredential("www.first-url-edited.com", "firstUsernameEdited", "first-password-edited");
		credentialPage.goToHome();
		credentialPage.clickOnCredentialTab();
		List<String> details = credentialPage.getCredentialDetails(1);
		Assertions.assertEquals("www.first-url-edited.com", details.get(0));
		Assertions.assertEquals("firstUsernameEdited", details.get(1));
		// Password must be encrypted
		Assertions.assertNotEquals("first-password-edited", details.get(2));

		// View second credential
		credentialPage.viewCredential(2);
		// Password must be decrypted when viewed
		Assertions.assertEquals("second-password", credentialPage.getUncryptedPassword());
		// Edit second credential
		credentialPage.setCredential("www.second-url-edited.com", "secondUsernameEdited", "second-password-edited");
		credentialPage.goToHome();
		credentialPage.clickOnCredentialTab();
		details = credentialPage.getCredentialDetails(2);
		Assertions.assertEquals("www.second-url-edited.com", details.get(0));
		Assertions.assertEquals("secondUsernameEdited", details.get(1));
		// Password must be encrypted
		Assertions.assertNotEquals("second-password-edited", details.get(2));
	}

	@Test
	@Order(10)
	public void testDeleteCredentials() {
		login();

		driver.get(baseURL + "/home");
		credentialPage = new CredentialPage(driver);

		credentialPage.clickOnCredentialTab();
		credentialPage.deleteCredential(1);
		credentialPage.goToHome();
		credentialPage.clickOnCredentialTab();
		credentialPage.deleteCredential(2);
		credentialPage.goToHome();
		credentialPage.clickOnCredentialTab();
		Assertions.assertEquals(0, driver.findElements(By.id("credential-list")).size());
	}
}
