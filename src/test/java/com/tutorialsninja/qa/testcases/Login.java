package com.tutorialsninja.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.tutorialsninja.qa.base.Base;
import com.tutorialsninja.qa.pages.AccountPage;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.LoginPage;
import com.tutorialsninja.qa.utils.Utilities;

public class Login extends Base {
	WebDriver driver; 
	LoginPage loginPage;
    
	public Login() {
		super();
	}
	
	@BeforeMethod
	public void setup() {
		
		driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
		HomePage homePage = new HomePage(driver);
		loginPage = homePage.navigateToLoginPage();
	}
	
	@AfterMethod //after every test case this method will get called, this method will get called irrespective of tc is passed or failed
	public void tearDown() { //for closing the browser
		driver.quit();
	}
	
	@Test(priority = 1, dataProvider="validCredentialsSupplier")
	public void verifyLoginWithValidCredentials(String email, String password) {
		//Earlier it was a normal(), after add annotation Testng annotation @Test it become Testng Test method
		AccountPage accountPage = loginPage.login(email, password);
		//verification
		Assert.assertTrue(accountPage.getDisplayStatusOfEditYourAccountInformationOption(), "Edit your Account Information option is not displayed"); //This will return as true or false
	
	}
	/*//Hardcoded data to supply data to verifyLoginWithValidCredentials(String email, String password) 
		//since there is 3 data below, it will run 3 times
	@DataProvider(name="validCredentialsSupplier")
	public Object[][] supplyTestData() {
		Object[][] data = {{"sandesh.test@gmail.com", "Password@1"}, 
				{"sandesh.test2@gmail.com", "Password@1"}, 
				{"sandesh.test3@gmail.com", "Password@1"}};
		return data;
	}
*/	
	@DataProvider(name="validCredentialsSupplier")
	public Object[][] supplyTestData(){
		Object[][] data = Utilities.getTestDataFromExcel("Login");
		return data;
	}
	
	@Test(priority = 2)
	public void verifyLoginWithInvalidCredentials() {
		
		loginPage.login(Utilities.generateEmailWithTimeStamp(), dataProp.getProperty("invalidPassword"));
		Assert.assertTrue(loginPage.retrieveEmailPasswordNotMatchingWarningMessageText().contains(dataProp.getProperty("emailPasswordNoMatchWarning")), "Expected Warning message is not displayed"); //contains is written because actual warning message will be displaying two div messages one is div class and second is i class
	}
	@Test(priority = 3)
	public void verifyLoginWithInvalidEmailAndValidPassword() {
	
        loginPage.enterEmailAddress(Utilities.generateEmailWithTimeStamp());//If you use same invalid email for more than 5 attempts. you will get security message. Hence we will use timestamp() to avoid security message and failure of testcase
        loginPage.enterPassword(prop.getProperty("validPassword"));
        loginPage.clickOnLoginButton();
        String actualWarningMessage = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
        String expectedWarningMessage = dataProp.getProperty("emailPasswordNoMatchWarning");
		Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage), "Expected Warning message is not displayed"); //contains is written because actual warning message will be displaying two div messages one is div class and second is i class
		
	}
	@Test(priority = 4)
	public void verifyLoginWithValidEmailAndInvalidPassword() {
		
		loginPage.login(prop.getProperty("validEmail"), dataProp.getProperty("invalidPassword"));
		
		String actualWarningMessage = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String expectedWarningMessage = dataProp.getProperty("emailPasswordNoMatchWarning");
		Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage), "Expected Warning message is not displayed"); //contains is written because actual warning message will be displaying two div messages one is div class and second is i class
	
	}
	@Test(priority = 5)
	public void verifyLoginWithoutProvidingCredentials() {
		
		loginPage.clickOnLoginButton();
		
		String actualWarningMessage = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String expectedWarningMessage = dataProp.getProperty("emailPasswordNoMatchWarning");
		Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage), "Expected Warning message is not displayed"); //contains is written because actual warning message will be displaying two div messages one is div class and second is i class
				
	}	
}
