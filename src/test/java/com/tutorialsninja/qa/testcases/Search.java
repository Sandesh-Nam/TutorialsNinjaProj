package com.tutorialsninja.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.tutorialsninja.qa.base.Base;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.SearchPage;

public class Search extends Base {
	WebDriver driver;
	SearchPage searchPage;
	
	public Search() {
		super();
	}
	@BeforeMethod
	public void setup() {
		driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
	}
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	@Test(priority = 1)
	public void verifySearchWithValidProduct() {
		
		HomePage homePage = new HomePage(driver);
		homePage.enterProductIntoSearchBoxField(dataProp.getProperty("validProduct"));
		searchPage = homePage.clickOnSearchButton();
		Assert.assertTrue(searchPage.displayStatusOfHPValidProduct(),"Valid product HP is not displayed in the search results");
	}
	@Test(priority = 2)
	public void verifySearchWithInvalidProduct() {
		
		HomePage homePage = new HomePage(driver);
		homePage.enterProductIntoSearchBoxField(dataProp.getProperty("invalidProduct"));
		searchPage = homePage.clickOnSearchButton();
		String actualSearchMessage = searchPage.retrieveNoProductMessageText();
		Assert.assertEquals(actualSearchMessage, dataProp.getProperty("NoProductTextInSearchResults"), "No product message in search results is not displayed");
	
	}
	@Test(priority = 3)
	public void verifySearchWithoutAnyProduct() {
		HomePage homePage = new HomePage(driver);
		searchPage = homePage.clickOnSearchButton();
		String actualSearchMessage = searchPage.retrieveNoProductMessageText();
		Assert.assertEquals(actualSearchMessage, dataProp.getProperty("NoProductTextInSearchResults"), "No product message in search results is not displayed");
	}

}
