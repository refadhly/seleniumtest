package androidEmulator;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;



public class ProductOutgoing {
	
	private static final String SERVER_URL = "http://d2397b712107.ngrok.io/";
	
	//=======> untuk kebutuhan screenshot
		private String pathOutput = "C:\\Users\\refad\\eclipse-workspace\\SeleniumJavaFramework\\src\\test\\resources\\record\\";
		//Create new directory under C:\nonExistedDirectory\directory
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy_HHmmss"); 
		File pathResult = new File(pathOutput+"\\"+"Single_ProductOut_"+formatter.format(date));
		//Create directory for existed path.
		Boolean isCreated = pathResult.mkdir(); 
		
		//=======> membuat file txt untuk log
		private String fileLogOutput = pathResult+"\\"+"Log.txt";
		File fileLog = new File(fileLogOutput);
	
	

private AndroidDriver<MobileElement> androDriver;
	
	@BeforeTest
	public void setup() throws MalformedURLException, Exception {
		//=======> untuk kebutuhan logging output agar system.out.print tulis ke file txt
				FileOutputStream fis = new FileOutputStream(fileLogOutput, true);
				PrintStream out = new PrintStream(fis);
				System.setOut(out);
	
		try {
			
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("platformName","Android");
			caps.setCapability("platformVersion", "11");
			caps.setCapability("browserName","Chrome");
			caps.setCapability("udid", "RRCR4006R1Y");
			caps.setCapability("chromedriverExecutable", "D:\\CT\\support\\chromedriver.exe");
			caps.setCapability("noReset","true");
	        caps.setCapability("fullReset", "false");
	        			
			URL url = new URL("http://0.0.0.0:4723/wd/hub");
			androDriver = new AndroidDriver<MobileElement>(url,caps);
//			driver = new AndroidDriver<MobileElement>(url,caps);
//			driver = new IOSDriver<MobileElement>(url,caps);
			
			System.out.println("Setup aplikasi berhasil");
		}
	        catch(Exception exp) {
			System.out.println("Cause is : "+exp.getCause());
			System.out.println("Message is : "+exp.getMessage());
			exp.printStackTrace();
		}
	}	
	
	
	@AfterTest
	public void teardown() throws Exception {
		//=======> untuk kebutuhan logging output agar system.out.print tulis ke file txt
				FileOutputStream fis = new FileOutputStream(fileLogOutput, true);
				PrintStream out = new PrintStream(fis);
				System.setOut(out);
				
		System.out.println("Testing aplikasi telah selesai. Terima Kasih");
//		androDriver.quit();
	}
	
	
	@Test(priority=1)
	public void login() throws InterruptedException, Exception {
		//=======> untuk kebutuhan logging output agar system.out.print tulis ke file txt
				FileOutputStream fis = new FileOutputStream(fileLogOutput, true);
				PrintStream out = new PrintStream(fis);
				System.setOut(out);
				
		androDriver.get(SERVER_URL);
		
		System.out.println("===============START STEP LOGIN===============");
		System.out.println("nama method : loginAdmin()\n");
		//=======> memastikan orang yang login saat ini
		String ActualTitle = androDriver.getTitle();
		String ExpectedTitle = "Login Tester QAS";
		System.out.println("Apakah title sama ? " + "[" + ActualTitle + "] == " + "[" + ExpectedTitle + "]" );

		try {
			AssertJUnit.assertEquals(ActualTitle, ExpectedTitle);
			System.out.println("Title Passed >>>>> OK \n");
		}
		catch(Exception exp) {
			System.out.println("Cause is : "+exp.getCause());
			System.out.println("Message is : "+exp.getMessage());
			System.out.println("Title tidak SESUAI, cek code kembali");
			exp.printStackTrace();
		}	
		
		//=======> isi form login
		WebElement inputLogin = androDriver.findElement(By.xpath("//input[contains(@id,'signin-email')]"));
		inputLogin.clear();
		inputLogin.sendKeys("admin@gmail.com");

		WebElement inputPassword = androDriver.findElement(By.xpath("//input[contains(@id,'signin-password')]"));
		inputPassword.clear();
		inputPassword.sendKeys("secret");

		//=======> proses login
		WebElement buttonLogin = androDriver.findElement(By.xpath("//button[@type='submit'][contains(.,'LOGIN')]"));
		buttonLogin.click();

		Thread.sleep(2*1000);
		System.out.println("nama method : loginAdmin()");
		System.out.println("===============FINISH STEP LOGIN===============\n");
		
	}
	
	@Test(priority=2)
	public void outgoing() throws Exception {
		//=======> untuk kebutuhan logging output agar system.out.print tulis ke file txt
				FileOutputStream fis = new FileOutputStream(fileLogOutput, true);
				PrintStream out = new PrintStream(fis);
				System.setOut(out);
				
		//screenshot param
		TakesScreenshot ts=(TakesScreenshot)androDriver;
				
		String produk 	= "Jeans";
		String customer = "PT. Maju";
		String quantity = "2";
		String dateout		= "2021-06-18";
		
		System.out.println("mulai menambahkan produk yang keluar");
		
		Thread.sleep(2*1000);
		androDriver.get(SERVER_URL+"productsOut");
		Thread.sleep(2*1000);
		new WebDriverWait(androDriver, 20).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@onclick='addForm()'][contains(.,'Add Products Out')]"))).click();
		Thread.sleep(2*1000);
		new WebDriverWait(androDriver, 20).until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//h3[@class='modal-title'][contains(.,'Add Products')]"), "Add Products"));
		
		//pengisian data
		Select popupProductOut = new Select(androDriver.findElement(By.xpath("//select[@name='product_id']")));
		popupProductOut.selectByVisibleText(produk);
		
		Select popupCustomerOut = new Select(androDriver.findElement(By.xpath("//select[@name='customer_id']")));
		popupCustomerOut.selectByVisibleText(customer);
		
		WebElement popupQuantityOut = androDriver.findElement(By.xpath("//input[@name='qty']"));
		popupQuantityOut.clear();
		popupQuantityOut.sendKeys(quantity);
		
		WebElement popupDateOut = androDriver.findElement(By.xpath("//input[@name='tanggal']"));
		popupDateOut.clear();
		popupDateOut.sendKeys(dateout);
		
		if(androDriver.isKeyboardShown()) {   androDriver.hideKeyboard(); }
		
		//cara screenshot
				Thread.sleep(2*1000);
				FileHandler.copy(ts.getScreenshotAs(OutputType.FILE), 
						new File(pathResult+"\\"+"Produk_"+produk+"Out_"+quantity+"_Submit.png"));
		
		WebElement submitProdukOut = androDriver.findElement(By.xpath("//button[@type='submit'][contains(.,'Submit')]"));
		submitProdukOut.click();
		
		
		//untuk memastikan
		Thread.sleep(4*1000);
//		new WebDriverWait(androDriver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@type,'search')]"))).sendKeys(produk);
		WebElement inputsearchProductOut = androDriver.findElement(By.xpath("//input[contains(@type,'search')]"));
		inputsearchProductOut.sendKeys(produk);
		
		Thread.sleep(2*1000);
		if(androDriver.isKeyboardShown()) {   androDriver.hideKeyboard(); }
		
		//cara screenshot
		Thread.sleep(2*1000);
		FileHandler.copy(ts.getScreenshotAs(OutputType.FILE), 
				new File(pathResult+"\\"+"Produk_"+produk+"Out_"+quantity+"_Result.png"));
		
		System.out.println("selesai menambahkan produk yang keluar");
		
		Thread.sleep(5*1000);
	}
		
}