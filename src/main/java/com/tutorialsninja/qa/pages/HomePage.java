package com.tutorialsninja.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	
	WebDriver driver;
	//Objects
	@FindBy(xpath="//span[text() = 'My Account']")                        //This annotation is provided by selenium. PF design pattern
	private WebElement myAccountDropMenu;    //WebElement is an interface in selenium
	
	@FindBy(linkText="Login")
	private WebElement loginOption; //private because no one should use and update.
	
	@FindBy(linkText="Register")
	private WebElement registerOption;
	
	@FindBy(name="search")
	private WebElement searchBoxField;
	
	@FindBy(xpath="//div[@id = 'search' ]/descendant::button")
	private WebElement searchButton;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this); //we are telling PF to automatically initialize the web elements. this = classname.java
		
	}
	//Actions
	public void clickOnMyAccount() {
		myAccountDropMenu.click();
	}
	public LoginPage selectLoginOption() {
		loginOption.click();
		return new LoginPage(driver);
	}	
	public LoginPage navigateToLoginPage() { //Clubbed above two methods for optimisation of code. If you want you can do else you can keep same.
		myAccountDropMenu.click();
		loginOption.click();
		return new LoginPage(driver);
	}
	
	public RegisterPage selectRegisterOption() {
		registerOption.click();
		return new RegisterPage(driver);
	}
	public void enterProductIntoSearchBoxField(String productText) {
		searchBoxField.sendKeys(productText);
	}
	public SearchPage clickOnSearchButton() {
		searchButton.click();
		return new SearchPage(driver);
	}
	public RegisterPage navigateToRegisterPage() {
		myAccountDropMenu.click();
		registerOption.click();
		return new RegisterPage(driver);
		
	}
}
