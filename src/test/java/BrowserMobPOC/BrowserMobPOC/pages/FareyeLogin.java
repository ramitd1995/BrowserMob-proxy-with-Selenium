package BrowserMobPOC.BrowserMobPOC.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class FareyeLogin extends CaptureNetworkLogs {
	
	@Test
	public void login() throws InterruptedException {
		
		driver.get("https://qatest.fareye.co/v2/login");
		
		WebElement username = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/div/form/div[1]/span/input"));
		username.sendKeys("007_admin");
		
		WebElement nextButton = driver.findElement(By.xpath("//button[@class='ant-btn button fe-btn login-submit ant-btn-primary']"));
		nextButton.click();
		Thread.sleep(2000);
		
		WebElement password = driver.findElement(By.xpath("//input[@placeholder='password']"));
		password.sendKeys("admin");
		
		WebElement signinButton = driver.findElement(By.xpath("//button[@class='ant-btn button fe-btn login-submit ant-btn-primary']"));
		signinButton.click();
		
		Thread.sleep(10000);
	}

}
