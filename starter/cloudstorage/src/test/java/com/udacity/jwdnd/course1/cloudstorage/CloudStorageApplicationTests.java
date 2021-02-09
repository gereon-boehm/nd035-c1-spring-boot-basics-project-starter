package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.text.SimpleDateFormat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;
	private String baseURL;

	private static WebDriver driver;
	private LoginPage loginPage;
	private SignupPage signupPage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@BeforeEach
	public void beforeEach() {
		baseURL = "http://localhost:" + this.port;
	}

	@AfterEach
	public void afterEach() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getSignupPage() {
		driver.get(baseURL + "/signup");
		signupPage = new SignupPage(driver);
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void getLoginPage() {
		driver.get(baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	void testUserSignupLoginHomeRedirection() {
		String firstName = "Hello";
		String lastName = "World";
		String username = "testName";
		String password = "12345";

		driver.get(baseURL + "/signup");
		signupPage = new SignupPage(driver);

		signupPage.signup(firstName, lastName, username, password);

		driver.get(baseURL + "/login");
		loginPage = new LoginPage(driver);

		loginPage.login(username, password);

		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Assertions.assertEquals("Sign Up", driver.getTitle());
	}
}
