import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;



public class BaseTest {
	public WebDriver driver;
	
	@BeforeTest
	public void setUp() {
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
	}
	
	@AfterTest
	public void tearDown() {
		if(driver != null) {
			driver.quit();
		}
	}
}
