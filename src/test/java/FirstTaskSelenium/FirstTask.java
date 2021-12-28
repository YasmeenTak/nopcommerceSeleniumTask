package FirstTaskSelenium;

import java.time.Duration;
import java.time.Instant;
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

	public static void main(String[] args) throws InterruptedException {

		// TODO Auto-generated method stub
		WebDriver driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
		// Assert Title
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, "Your store. Login");
		// Assert Url
		Assert.assertTrue(driver.getCurrentUrl().contains("login"));

		// ------------------------- Login -----------------------------//
		// Verify email and password Inputs not empty
		WebElement emailInput = driver.findElement(By.className("email"));
		Assert.assertEquals(emailInput.getAttribute("value"), "admin@yourstore.com");

		WebElement passwordInput = driver.findElement(By.className("password"));
		Assert.assertEquals(passwordInput.getAttribute("value"), "admin");

		// Login
		WebElement loginBtn = driver.findElement(By.cssSelector("[type=\"submit\"]"));
		loginBtn.click();

		// Assert Url logged in
		Assert.assertTrue(driver.getCurrentUrl().contains("admin"));

		// Verify sidebar appear
		WebElement aside = driver.findElement(By.cssSelector("aside"));
		Assert.assertTrue(aside.isDisplayed(), "Sidebar visibility");

		// --------------------------- Add a new product ---------------------//
		Thread.sleep(2000);
		// Click Catalog

		WebElement catalogBtn = driver.findElement(By.xpath("//nav/ul/li/a/*[contains(text(),'Catalog')]/ancestor::a"));
		catalogBtn.click();

		// Click Product
		WebElement productBtn = driver.findElement(By.cssSelector("a[href=\"/Admin/Product/List\"]"));
		productBtn.click();

		// Verify Url
		Assert.assertTrue(driver.getCurrentUrl().contains("Product/List"));

		// Add new product
		WebElement addNewBtn = driver.findElement(By.cssSelector("a[href=\"/Admin/Product/Create\"]"));
		Assert.assertEquals(addNewBtn.getText(), "Add new");
		addNewBtn.click();

		Thread.sleep(3000);
		WebElement card = driver.findElement(By.id("product-info"));
		WebElement cardSelector = driver.findElement(By.id("product-info"));

		if (card.getAttribute("class").contains("collapsed-card")) {
			WebElement toggle = driver.findElement(By.cssSelector(cardSelector + " .card-title"));
			toggle.click();
			System.out.println("openCardIfClosed: " + cardSelector);
		}
		// Fill product name
		WebElement productName = driver.findElement(By.id("Name"));
		productName.sendKeys("Mouse");
		Assert.assertEquals(productName.getAttribute("value"), "Mouse");

		// Fill Short description
		WebElement shortDescription = driver.findElement(By.id("ShortDescription"));
		shortDescription.sendKeys("New mouse New mouse");
		Assert.assertEquals(shortDescription.getAttribute("value"), "New mouse New mouse");

		// Fill Full description
		WebElement frameElm = driver.findElement(By.id("FullDescription_ifr"));
		driver.switchTo().frame(frameElm);
		WebElement fullDescription = driver.switchTo().activeElement();
		fullDescription.sendKeys("A mouse is a small handheld input device that controls a computer screen's cursor");
		Assert.assertEquals(fullDescription.getText(),
				"A mouse is a small handheld input device that controls a computer screen's cursor");
		driver.switchTo().defaultContent();

		// Fill SKU with unique value
		Instant currentTime = Instant.now();
		String currentTimeValue = String.valueOf(currentTime);

		WebElement skuInput = driver.findElement(By.id("Sku"));
		String sku = "123" + currentTimeValue;
		;
		skuInput.sendKeys(sku);
		Assert.assertEquals(skuInput.getAttribute("value"), sku);

		// Fill Category
		WebElement category = driver.findElement(By.xpath("//select[@id=\"SelectedCategoryIds\"]/parent::div"));
		category.click();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		List<WebElement> selectCategoryList = driver
				.findElements(By.xpath("//ul[@id=\"SelectedCategoryIds_listbox\"]/li[contains(text(),'Computers')]"));
		selectCategoryList.get(0).click();
		Assert.assertEquals(selectCategoryList.get(0).getText(), "Computers");

		WebElement a = driver.findElement(By.className("card-body"));
		a.click();
		// Fill Price
		WebElement priceInput = driver.findElement(By.xpath("//input[@id=\"Price\"]/preceding-sibling::input"));
		new Actions(driver).moveToElement(priceInput).click().perform();

		WebElement price = driver.findElement(By.xpath("//input[@id=\"Price\"]"));
		price.sendKeys("10");

		// Select Inventory
		WebElement inventorySelect = driver.findElement(By.id("ManageInventoryMethodId"));
		Select dropdown = new Select(inventorySelect);
		dropdown.selectByValue("1");

		Assert.assertEquals(dropdown.getFirstSelectedOption().getText(), "Track inventory");

		WebElement saveProductBtn = driver.findElement(By.name("save"));
		saveProductBtn.click();

		Assert.assertTrue(driver.getCurrentUrl().contains("Product/List"));

		// Verify success alert appear
		WebElement successAlert = driver.findElement(By.className("alert-success"));
		// System.out.println(successAlert.getCssValue("background-color"));
		boolean isSuccess = (successAlert.getCssValue("background-color").equals("rgba(23, 183, 109, 1)"));
		Assert.assertTrue(isSuccess, "Verify alert background-color");

		// Verify alert message content
		boolean isAlertContains = successAlert.getText().contains("The new product has been added successfully.");
		Assert.assertTrue(isAlertContains, "Verify alert message content");

		// ------------------ Add Discount to the previously added product  ---------------------//

	}

}

//Scenario
//- Navigate to https://admin-demo.nopcommerce.com/
//- Login with the default admin user
//- Navigate to products page from the left nav.
//- Add a new product in Basic Model (Fill all fields in the Product Info, Price, and Inventory sections).
//- Navigate to the Discount section from the left nav.
//- Add a new discout assinged to the previously added product applied from 31/12/2021 to 28/2/2022.
//- Write all possible assertions for the previous scenario.