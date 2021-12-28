package FirstTaskSelenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class FirstTask {

	final static String url = "https://admin-demo.nopcommerce.com/";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriver driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
		//Assert Title
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, "Your store. Login");
		
		//Input not empty
		WebElement emailInput = driver.findElement(By.className("email"));
		emailInput.getAttribute("value").equals("admin@yourstore.com");
		WebElement passwordInput = driver.findElement(By.className("password"));
		passwordInput.getAttribute("value").equals("admin");
		
		//Login
		WebElement loginBtn = driver.findElement(By.cssSelector("[type=\"submit\"]"));
		loginBtn.click();
		
		//Click Catalog
		//List<WebElement> dashboardList = driver.findElements(By.cssSelector("//ul[@class='nav']/li")).get(0);
		WebElement catalogBtn = driver.findElement(By.cssSelector(".nav-pills>:nth-child(2)"));
		catalogBtn.click();
		
		//Click Product
		WebElement productBtn = driver.findElement(By.cssSelector("a[href=\"/Admin/Product/List\"]"));
		productBtn.click();
		
		//Add new product
		WebElement addNewBtn = driver.findElement(By.cssSelector("a[href=\"/Admin/Product/Create\"]"));
		addNewBtn.click();
		
		//Fill product name
		WebElement productName = driver.findElement(By.id("Name"));
		productName.sendKeys("Mouse");
		
		//Fill Short description
		WebElement descriptionInput = driver.findElement(By.id("ShortDescription"));
		descriptionInput.sendKeys("New mouse");
		
		//Fill Full description
		driver.switchTo().frame("FullDescription_ifr");
		WebElement editable = driver.switchTo().activeElement();
		editable.sendKeys("Your text here");		
	    driver.switchTo().defaultContent();
	    
	    //Fill SKU
		WebElement skuInput = driver.findElement(By.id("Sku"));
		skuInput.sendKeys("212");
	    
		//Fill Category
		WebElement category = driver.findElement(By.xpath("//select[@id=\"SelectedCategoryIds\"]/parent::div"));
		category.click();
		
		List<WebElement> selectCategoryList = driver.findElements(By.xpath("//ul[@id=\"SelectedCategoryIds_listbox\"]/li"));
		selectCategoryList.get(0).click();
		
		driver.findElement(By.xpath("//ul[@id='SelectedCategoryIds_taglist']//*[contains(text(), 'Computers')]"));
		Assert.assertEquals(selectCategoryList.get(0).getText(), "Computers");
		
		//Fill Price
		WebElement priceInput = driver.findElement(By.xpath("//input[@id=\"Price\"]/preceding-sibling::input"));
		new Actions(driver).moveToElement(priceInput).click().perform();
		
		WebElement price = driver.findElement(By.xpath("//input[@id=\"Price\"]"));
		price.sendKeys("10");
		
		//Select Inventory
		WebElement inventorySelect = driver.findElement(By.id("ManageInventoryMethodId"));
		Select dropdown = new Select(inventorySelect);
		dropdown.selectByValue("1");
		
		Assert.assertEquals(dropdown.getFirstSelectedOption().getText(), "Track inventory");
		
		WebElement saveProductBtn = driver.findElement(By.name("save"));
		saveProductBtn.click();

	}

}

//Scenario
//- Navigate to https://admin-demo.nopcommerce.com/
//- Login with the default admin user
//- Navigate to products page from the left nav.
//- Add a new product in Basic Mode (Fill all fields in the Product Info, Price, and Inventory sections).
//- Navigate to the Discount section from the left nav.
//- Add a new discout assinged to the previously added product applied from 31/12/2021 to 28/2/2022.
//- Write all possible assertions for the previous scenario.