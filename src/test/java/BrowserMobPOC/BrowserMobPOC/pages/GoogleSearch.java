package BrowserMobPOC.BrowserMobPOC.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class GoogleSearch extends CaptureNetworkLogs {
	
	@Test
	public void search() throws InterruptedException {
		
		driver.get("https://www.google.com/");
		WebElement searchField = driver.findElement(By.name("q"));
		searchField.sendKeys("FarEye");
		
		searchField.sendKeys(Keys.ENTER);
		
		Thread.sleep(5000);
	}
		
		

}
