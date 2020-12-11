package BrowserMobPOC.BrowserMobPOC.pages;

import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;


public class CaptureNetworkLogs {
			
		String harFileName = "/home/ramit/Documents/fareye-qatest.har";
		
		public WebDriver driver;
		public BrowserMobProxy proxy;
		
		@BeforeTest
		public void setUp() {
			
		    proxy = new BrowserMobProxyServer();
		    proxy.setTrustAllServers(true);
		    proxy.start();

		    Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
		   
		    DesiredCapabilities capabilities = new DesiredCapabilities();
		    ChromeOptions options = new ChromeOptions();
		    options.addArguments("--ignore-certificate-errors");
		    capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
		    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,true);
		    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(capabilities);
			
		    proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

		    proxy.newHar("BrowserMobPOC.Sample"); // HAR Label
	        
		}
		
		
	
		@AfterTest
		public void tearDown() throws IOException {

			Har har = proxy.getHar();
			
			java.io.StringWriter writer = new java.io.StringWriter();
			
			try {
				har.writeTo(writer);
			} catch (IOException ex) {
				 System.out.println (ex.toString());
			}
		
			String harAsString = writer.toString();
			
			 JSONObject Jobject = new JSONObject(harAsString);
		     JSONObject log = Jobject.getJSONObject("log");
		     JSONArray entries = log.getJSONArray("entries");
		     
		     for(int i=0;i<entries.length();i++)
		     {
		    	 JSONObject entry = entries.getJSONObject(i);
		    	 JSONObject request = entry.getJSONObject("request");
		    	 Object method = request.get("method");
		    	 Object url = request.get("url");
		    	 JSONObject response = entry.getJSONObject("response");
		    	 Object status = response.get("status");
		    	 Object statusText = response.get("statusText");
		    	
		    	 
		    	 System.out.println("Method: " +method.toString());
		    	 System.out.println("URL: " +url.toString());
		    	 System.out.println("Status: " +status.toString()); // need soft assertion here
		    	 System.out.println("StatusText: " +statusText.toString());
		    	 
		    	 System.out.println(" ");
		     }	 
		     
		     
			com.google.gson.JsonElement harAsJson = new com.google.gson.Gson().toJsonTree(writer.toString());
						
			  try (FileWriter file = new FileWriter("qatest_fareye_network_logs_json.json")) {
				  
		            file.write(harAsJson.getAsString());
		            file.flush();
		 
		        } catch (IOException e) {
		            e.printStackTrace();
		        }	
			
			if (driver != null) {
				proxy.stop();
				driver.quit();
			}
		}
		
		
	}



