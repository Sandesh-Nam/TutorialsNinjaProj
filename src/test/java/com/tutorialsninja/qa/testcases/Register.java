package com.tutorialsninja.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.tutorialsninja.qa.base.Base;
import com.tutorialsninja.qa.pages.AccountSuccessPage;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.RegisterPage;
import com.tutorialsninja.qa.utils.Utilities;

public class Register extends Base {
	WebDriver driver;
	RegisterPage registerPage;
	AccountSuccessPage accountSuccessPage;
	
	public Register() {
		super();
	}
	@BeforeMethod
	public void setup() {
		driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
		HomePage homePage = new HomePage(driver);
		registerPage = homePage.navigateToRegisterPage();
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	@Test(priority = 1)
	public void verifyRegisteringAnAccountWithMandatoryFields() {
		accountSuccessPage = registerPage.registerWithMandatoryFields(dataProp.getProperty("firstName"), dataProp.getProperty("lastName"), 
				Utilities.generateEmailWithTimeStamp(), dataProp.getProperty("telephoneNumber"), 
				prop.getProperty("validPassword"));		
		Assert.assertEquals(accountSuccessPage.retrieveAccountSuccessPageHeading(), dataProp.getProperty("accountSuccessfullyCreatedHeading"), "Account Success page is not displayed");
		
	}
	@Test(priority = 2)
	public void verifyRegisteringAccountByProvidingAllFields() {
		
		accountSuccessPage = registerPage.registerWithAllFields(dataProp.getProperty("firstName"),
				dataProp.getProperty("lastName"), Utilities.generateEmailWithTimeStamp(), 
				dataProp.getProperty("telephoneNumber"), prop.getProperty("validPassword"));

		accountSuccessPage = registerPage.clickOnContinueButton();	
		Assert.assertEquals(accountSuccessPage.retrieveAccountSuccessPageHeading(), dataProp.getProperty("accountSuccessfullyCreatedHeading"), "Account Success page is not displayed");	
	}
	@Test(priority = 3)
	public void verifyRegisteringAccountWithExistingEmailAddress() {
		
		registerPage.registerWithAllFields(dataProp.getProperty("firstName"),
				dataProp.getProperty("lastName"), prop.getProperty("validEmail"), 
				dataProp.getProperty("telephoneNumber"), prop.getProperty("validPassword"));

		Assert.assertTrue(registerPage.retrieveDuplicateEmailAddressWarning().contains(dataProp.getProperty("duplicateEmailWarning")));
}
	@Test(priority = 4)
	public void verifyRegisteringAccountWithoutFillingAnyDetails()
	{
		registerPage.clickOnContinueButton();
		
		String actualPrivacyPolicyWarning = registerPage.retrievePrivacyPolicyWarning();
		Assert.assertTrue(actualPrivacyPolicyWarning.contains(dataProp.getProperty("privacyPolicyWarning")), "Privacy Policy Warning message is not displayed");
			
		Assert.assertEquals(registerPage.retrieveFirstNameWarning(), dataProp.getProperty("firstNameWarning") , "First Name Warning message is not displayed");
	    
		Assert.assertEquals(registerPage.retrieveLastNameWarning(), dataProp.getProperty("lastNameWarning"), "Last Name Warning message is not displayed");
		
		Assert.assertEquals(registerPage.retrieveEmailWarning(), dataProp.getProperty("emailWarning"), "Email Warning message is not displayed");
		
		Assert.assertEquals(registerPage.retrieveTelephoneWarning(), dataProp.getProperty("telephoneWarning"), "Telephone Warning message is not displayed");
		
		Assert.assertEquals(registerPage.retrievePasswordWarning(), dataProp.getProperty("passwordWarning"), "Password Warning message is not displayed");
			
	}

}
