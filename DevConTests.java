import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DevConTests extends BaseTest{

		//Test Scenario 1 : Page Load and Initial Setup
		@Test (priority = 1)
		public void pageLoadAndPopupTests() {
			driver.get("baseURL");
			
			//Verify the title
			Assert.assertEquals(driver.getTitle(),"DevCon 2025 Registration");
			
			//wait for popup and close it
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
			WebElement popupClose = wait.until(ExpectedConditions.elementToBeClickable(By.id("popup-close")));
			popupClose.click();
		}
			
			//Test scenario 2: Form Filling and Basic Interactions
			@Test(priority = 2)
			public void formFillingTest() {
				//personal info
				driver.findElement(By.id("firstname")).sendKeys("Sagar");
				driver.findElement(By.id("lastname")).sendKeys("Vishwakarma");
				driver.findElement(By.id("email")).sendKeys("sagar.619.ss@gmail.com");
				
				//Dietary Preference
				driver.findElement(By.id("veg")).click();
				
				//Areas of Interest
				driver.findElement(By.id("interest-testing")).click();
				driver.findElement(By.id("interest-frontend")).click();
				
				//verification
				Assert.assertTrue(driver.findElement(By.id("veg")).isSelected());
				Assert.assertTrue(driver.findElement(By.id("interest-testing")).isSelected());
			}
			
			//Test scenario 3 : Handling Dynamic Elements
			@Test(priority = 3)
			public void dynamicElements() {
				Select Dropdown = new Select(driver.findElement(By.id("workshop-track")));
				Dropdown.selectByVisibleText("Data Science");
				
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				WebElement specificWorkshop = wait.until(ExpectedConditions.elementToBeClickable(By.id("specific-workshop")));
				new Select(specificWorkshop).selectByVisibleText("Introduction to Database");
			
				//Calendar interaction
				driver.findElement(By.id("date-picker")).sendKeys("16/08/2025");
				
			}
			
			//Test sceanrio 4 : Advanced UI and Window Handling
			@Test(priority = 4)
			public void windowAndUi() {
				WebElement sponsor = driver.findElement(By.id("sponsors-logo"));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",sponsor);
				
				//Click sponsor & switch window
				sponsor.click();
				String mainWindow = driver.getWindowHandle();
				for(String winHandle : driver.getWindowHandles()) {
					if(!winHandle.equals(mainWindow)) {
						driver.switchTo().window(winHandle);
						Assert.assertTrue(driver.getTitle().contains("Sponsor"));
						driver.close();
					}
				}
				driver.switchTo().window(mainWindow);
				
				//Scroll to terms and conditions
				driver.switchTo().frame("iframe");
				driver.findElement(By.id("agree-check")).click();
				driver.switchTo().defaultContent();
			}

			//Test Scenario 5 : Submission and Final Verification
			@Test (priority = 5)
			public void submissionTest() throws IOException {
				driver.findElement(By.id("submit")).click();
				
				//success alert
				WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
				Alert alert = wait.until(ExpectedConditions.alertIsPresent());
				Assert.assertEquals(alert.getText(),"Registration Successfull!");
				alert.accept();
				
				//Wait for confirmation
				wait.until(ExpectedConditions.titleContains("confirmation"));
				
				//Screenshot
				File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(src, new File("D:\\MASAI\\Selenium\\DevCon2025-Automation\\screenshots"));
			}
	}


