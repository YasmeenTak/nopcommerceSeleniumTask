package FirstTaskSelenium;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class FirstTask {

	final static String url = "https://admin-demo.nopcommerce.com/";

	public static void main(String[] args) throws InterruptedException {

		// TODO Auto-generated method stub
		WebDriver driver = new ChromeDriver();
		driver.get(url);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

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

		// Verify sidebar clicking
		WebElement catalogSidebarItem = driver
				.findElement(By.xpath("//ul/li/a/p[contains(text(),'Catalog')]/parent::a/parent::li"));
		wait.until(ExpectedConditions.attributeContains(catalogSidebarItem, "class", "menu-open"));
		Assert.assertTrue(catalogSidebarItem.getAttribute("class").contains("menu-open"));

		// Click Product
		WebElement productBtn = driver.findElement(By.cssSelector("a[href=\"/Admin/Product/List\"]"));
		productBtn.click();

		// Verify Url
		Assert.assertTrue(driver.getCurrentUrl().contains("Product/List"));

		// Verify Heading title
		String pageHeadingTiltePro = driver.findElement(By.cssSelector("form[action='/Admin/Product/List'] h1"))
				.getText();
		Assert.assertTrue(pageHeadingTiltePro.contains("Products"));

		// Add new product
		WebElement addNewBtn = driver.findElement(By.cssSelector("a[href=\"/Admin/Product/Create\"]"));
		Assert.assertEquals(addNewBtn.getText(), "Add new");
		addNewBtn.click();

		// Verify Heading title Add a new product
		Assert.assertTrue(driver.getCurrentUrl().contains("/Admin/Product/Create"));
		String pageHeadingTitleAdd = driver.findElement(By.cssSelector("form#product-form h1")).getText();
		Assert.assertTrue(pageHeadingTitleAdd.contains("Add a new product"));
		Thread.sleep(3000);
		// -------------------------------- Card -----------------------------------//
		WebElement cardEle1 = driver.findElement(By.cssSelector("#product-cards #product-info .card-body"));
		WebElement productIn1 = driver.findElement(By.id("product-info"));
		if (cardEle1.getCssValue("display").equals("none")) {
			productIn1.click();
		}
		// Fill product name
		Instant currentTime = Instant.now();
		String currentTimeValue = String.valueOf(currentTime);		

		WebElement productNameInput = driver.findElement(By.id("Name"));
		String productName = "Mouse" + currentTimeValue;
		productNameInput.sendKeys(productName);
		Assert.assertEquals(productNameInput.getAttribute("value"), productName);
		
		//Verify tool tip
		WebElement productNameTitleEle = driver.findElement(By.cssSelector("[data-original-title=\"The name of the product.\"]"));
		String expectedResultToolTipTitle = "The name of the product.";
		String actualResultToolTipTitle = productNameTitleEle.getAttribute("title");
		if (expectedResultToolTipTitle.equals(actualResultToolTipTitle)) {
			System.out.println("pass");

		} else {
			System.out.println("fail");
		}

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
		WebElement skuInput = driver.findElement(By.id("Sku"));
		String sku = "123" + currentTimeValue;
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

		driver.findElement(By.className("card-body")).click();

		// Fill Price
		WebElement priceInput = driver.findElement(By.xpath("//input[@id=\"Price\"]/preceding-sibling::input"));
		new Actions(driver).moveToElement(priceInput).click().perform();
		String priceNum = "10";
		WebElement price = driver.findElement(By.xpath("//input[@id=\"Price\"]"));
		price.sendKeys(priceNum);
		String priceValue = price.getAttribute("value");
		Assert.assertTrue(priceValue.contains(priceNum));

		// -------------------------------- Card -----------------------------------//
		cardEle1 = driver.findElement(By.cssSelector("#product-cards #product-inventory .card-body"));
		WebElement productInventory = driver.findElement(By.id("product-inventory"));
		if (cardEle1.getCssValue("display").equals("none")) {
			productInventory.click();
		}
		// Select Inventory
		WebElement inventorySelect = driver.findElement(By.id("ManageInventoryMethodId"));
		Select dropdown = new Select(inventorySelect);
		dropdown.selectByValue("1");

		Assert.assertEquals(dropdown.getFirstSelectedOption().getText(), "Track inventory");

		WebElement saveProductBtn = driver.findElement(By.name("save"));
		
		//Verify Hovering
//		Actions actions = new Actions (driver);
//		actions.moveToElement(saveProductBtn).build().perform();
//		String hexColor = Color.fromString(saveProductBtn.getCssValue("background-color")).asHex();
//		System.out.println("addNewProductBtn: " + hexColor);
//		Assert.assertEquals(hexColor, "#4580a2");
		
		saveProductBtn.click();


		// Verify Url
		Assert.assertTrue(driver.getCurrentUrl().contains("Product/List"));

		// Product list location
		WebElement productsList = driver.findElement(By.id("products-grid_info"));
		wait.until(ExpectedConditions.textToBePresentInElement(productsList, "of"));
		List<WebElement> paginationList = driver
				.findElements(By.cssSelector("#products-grid_paginate ul.pagination li"));
		paginationList.get(paginationList.size() - 2).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ajaxBusy")));

		// Verify product/sku/price added to list
		WebElement productNameInList = driver
				.findElement(By.cssSelector("table#products-grid tbody tr:last-child td:nth-child(3)"));
		WebElement skuInList = driver
				.findElement(By.cssSelector("table#products-grid tbody tr:last-child td:nth-child(4)"));
		WebElement priceInList = driver
				.findElement(By.cssSelector("table#products-grid tbody tr:last-child td:nth-child(5)"));

		Assert.assertEquals(productNameInList.getText(), productName);
		Assert.assertEquals(skuInList.getText(), sku);
		Assert.assertEquals(priceInList.getText(), priceNum);
		System.out.println(priceNum);

		// Verify success alert appear
		WebElement successAlert = driver.findElement(By.className("alert-success"));
		// System.out.println(successAlert.getCssValue("background-color"));
		boolean isSuccess = (successAlert.getCssValue("background-color").equals("rgba(23, 183, 109, 1)"));
		Assert.assertTrue(isSuccess, "Verify alert background-color");

		// Verify alert message content
		boolean isAlertContains = successAlert.getText().contains("The new product has been added successfully.");
		Assert.assertTrue(isAlertContains, "Verify alert message content");

		
		// ------------------ Add New Discount ----------//
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		// Clicking on promotion
		WebElement promotionLink = driver
				.findElement(By.xpath("//nav/ul/li/a/*[contains(text(),'Promotions')]/ancestor::a"));
		promotionLink.click();

		// Verify sidebar
		WebElement promotionSidebarItem = driver
				.findElement(By.xpath("//ul/li/a/p[contains(text(),'Promotions')]/parent::a/parent::li"));
		wait.until(ExpectedConditions.attributeContains(promotionSidebarItem, "class", "menu-open"));
		Assert.assertTrue(promotionSidebarItem.getAttribute("class").contains("menu-open"));

		// Click on Discount
		WebElement discountLink = driver.findElement(By.cssSelector("a[href=\"/Admin/Discount/List\"]"));
		discountLink.click();

		// Verify url
		Assert.assertTrue(driver.getCurrentUrl().contains("Discount/List"));

		// Verify Heading
		String pageHeadingTitle = driver.findElement(By.cssSelector(".content-header h1")).getText();
		Assert.assertTrue(pageHeadingTitle.contains("Discounts"));

		// Click Add new
		WebElement addNewDiscountBtn = driver.findElement(By.cssSelector("a[href=\"/Admin/Discount/Create\"]"));
		Assert.assertEquals(addNewDiscountBtn.getText(), "Add new");
		addNewDiscountBtn.click();

		// Verify url Add new
		Assert.assertTrue(driver.getCurrentUrl().contains("Discount/Create"));

		// Verify Heading
		pageHeadingTitle = driver.findElement(By.cssSelector("form#discount-form h1")).getText();
		Assert.assertTrue(pageHeadingTitle.contains("Add a new discount"));

		// -------------------------------------
		// Card---------------------------------------//
		WebElement cardElemet = driver.findElement(By.cssSelector("#discount-cards .card-body"));
		WebElement discountInfo2 = driver.findElement(By.id("discount-info"));
		if (cardElemet.getCssValue("display").equals("none")) {
			discountInfo2.click();
		}

		// Filling Discount name
		WebElement DiscountNameEle = driver.findElement(By.id("Name"));
		String DiscountName = "Yasmeen Discount";
		DiscountNameEle.sendKeys(DiscountName);
		Assert.assertEquals(DiscountNameEle.getAttribute("value"), DiscountName);

		// Select Discount type
		WebElement discountType = driver.findElement(By.id("DiscountTypeId"));
		Select dropdownSelect = new Select(discountType);
		// dropdownSelect.selectByVisibleText("Assigned to products");
		dropdownSelect.selectByValue("2");
		Assert.assertEquals(dropdownSelect.getFirstSelectedOption().getText(), "Assigned to products");

		// Check box Use percentage
		WebElement discountUsePercentage = driver.findElement(By.id("UsePercentage"));
		discountUsePercentage.click();
		WebElement maxDiscount = driver.findElement(By.id("pnlMaximumDiscountAmount"));
		Assert.assertTrue(maxDiscount.isDisplayed(), "Maximum discount amount visibility");

		// Filling Discount percentage
		WebElement discountPercentage = driver
				.findElement(By.xpath("//*[@id=\"pnlDiscountPercentage\"]/div[2]/span/span/input[1]"));
		discountPercentage.sendKeys("5" + Keys.TAB);

		// Filling Discount percentage
		WebElement maximumDiscountAmount = driver.findElement(By.id("MaximumDiscountAmount"));
		// new Actions(driver).moveToElement(maximumDiscountAmount).click().perform();
		// maximumDiscountAmount.sendKeys("5");

		// Filling Start date
		Thread.sleep(3000);
		WebElement startDate = driver.findElement(By.cssSelector("[aria-controls=\"StartDateUtc_dateview\"]"));
		startDate.click();
		WebElement startDateSelect = driver.findElement(By.id("StartDateUtc"));
		startDateSelect.sendKeys("12/31/2021 12:00:00 AM");

		// Filling End date
		WebElement endDate = driver.findElement(By.cssSelector("[aria-controls=\"EndDateUtc_dateview\"]"));
		endDate.click();
		WebElement endDateInput = driver.findElement(By.id("EndDateUtc"));
		endDateInput.sendKeys("2/28/2022 12:00 AM");

		// Clicking save button
		WebElement saveBtn = driver.findElement(By.name("save"));
		saveBtn.click();

		// Verify success alert appear
		WebElement successAlert2 = driver.findElement(By.className("alert-success"));
		// System.out.println(successAlert.getCssValue("background-color"));
		boolean isSuccess2 = (successAlert2.getCssValue("background-color").equals("rgba(23, 183, 109, 1)"));
		Assert.assertTrue(isSuccess2, "Verify alert background-color");

		// Verify alert message content
		boolean isAlertContains2 = successAlert2.getText().contains("The new discount has been added successfully.");
		Assert.assertTrue(isAlertContains, "Verify alert message content");

		// Verify url
		Assert.assertTrue(driver.getCurrentUrl().contains("Discount/List"));
		// Verify Heading
		pageHeadingTitle = driver.findElement(By.cssSelector(".content-header h1")).getText();
		Assert.assertTrue(pageHeadingTitle.contains("Discounts"));

		// ----------- Add Discount to the previously added product ----------------//
		WebElement searchDiscountName = driver.findElement(By.id("SearchDiscountName"));
		searchDiscountName.sendKeys(DiscountName);

		WebElement searchDiscounts = driver.findElement(By.id("search-discounts"));
		searchDiscounts.click();
		// Verify discountTable appears
		WebElement discountTable = driver.findElement(By.className("dataTables_scroll"));
		Assert.assertTrue(discountTable.isDisplayed(), "discount Table visibility");

		// Click edit
		WebElement editBtn = driver.findElement(By.xpath("//tbody/tr/td[7]"));
		editBtn.click();

		// Verify Url
		Assert.assertTrue(driver.getCurrentUrl().contains("Discount/Edit"));

		// Verify heading
		pageHeadingTitle = driver.findElement(By.cssSelector("form#discount-form h1")).getText();
		Assert.assertTrue(pageHeadingTitle.contains("Edit discount details"));
		// + DiscountName
		// Click Add new product in discount
		WebElement addNewProductBtn = driver.findElement(By.id("btnAddNewProduct"));
		String parentWindowID = driver.getWindowHandle();
		addNewProductBtn.click();

		// Get all opened browser windows
		for (String windowID : driver.getWindowHandles()) {
			String title = driver.switchTo().window(windowID).getTitle();
			if (title.contains("Add a new product")) {				
				// Verify Url
				Assert.assertTrue(driver.getCurrentUrl().contains("ProductAddPopup"));
				// Clicking and search
				WebElement sarchProductName = driver.findElement(By.id("SearchProductName"));
				sarchProductName.sendKeys(productName);
				WebElement searchProductsBtn = driver.findElement(By.id("search-products"));
				searchProductsBtn.click();
				WebElement selectedProduct = driver.findElement(By.name("SelectedProductIds"));
				selectedProduct.click();
				Assert.assertTrue(selectedProduct.isSelected());
				WebElement saveBtn2 = driver.findElement(By.name("save"));
				saveBtn2.click();
//				driver.close();
				break;
			}
		}
		// Return to Edit page
		driver.switchTo().window(parentWindowID);

		WebElement saveDiscountProductBtn = driver.findElement(By.name("save"));
		saveDiscountProductBtn.click();
		// Verify Url
		Assert.assertTrue(driver.getCurrentUrl().contains("Discount/List"));
		// Verify success alert appear
		WebElement successAlertLast = driver.findElement(By.className("alert-success"));
		// System.out.println(successAlert.getCssValue("background-color"));
		boolean isSuccess3 = (successAlertLast.getCssValue("background-color").equals("rgba(23, 183, 109, 1)"));
		Assert.assertTrue(isSuccess3, "Verify alert background-color");

		// Verify alert message content
		boolean isAlertContains3 = successAlertLast.getText().contains("The discount has been updated successfully.");
		Assert.assertTrue(isAlertContains3, "Verify alert message content");
		
		//Verify table updated
		WebElement dataTables_scroll = driver.findElement(By.className("dataTables_scroll"));


//		driver.close();
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