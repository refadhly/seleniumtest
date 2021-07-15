package menuUsers;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;

public class AddUsers {
	//=======> parameter bersama
	private static WebDriver webDriver;
	private static final String SERVER_URL = "http://127.0.0.1:8000/";
	private Faker faker = new Faker(new Locale("id_ID"));

	//=======> untuk kebutuhan screenshot
	private String pathOutput = "C:\\Users\\refad\\eclipse-workspace\\SeleniumJavaFramework\\src\\test\\resources\\record\\";
	//Create new directory under C:\nonExistedDirectory\directory
	Date date = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy_HHmmss"); 
	File pathResult = new File(pathOutput+"\\"+"Single_AddUser_"+formatter.format(date));
	//Create directory for existed path.
	Boolean isCreated = pathResult.mkdir(); 
	
	//=======> membuat file txt untuk log
	private String fileLogOutput = pathResult+"\\"+"Log.txt";
	File fileLog = new File(fileLogOutput);
	
	
	@BeforeTest
	public void setupstart() throws InterruptedException, Exception {
		//=======> untuk kebutuhan logging output agar system.out.print tulis ke file txt
		FileOutputStream fis = new FileOutputStream(fileLogOutput, true);
		PrintStream out = new PrintStream(fis);
		System.setOut(out);

		System.out.println("Setup aplikasi dimulai");
		System.setProperty("webdriver.chrome.driver","D:\\CT\\support\\chromedriver.exe");
		webDriver = new ChromeDriver();
		webDriver.manage().window().maximize();
		System.out.println("Buka browser");
		System.out.println("Deleting Cookies and Cache");
		webDriver.manage().deleteAllCookies();
		Thread.sleep(5*1000);
		System.out.println("Delete Cookies and Cache DONE");

		System.out.println("Setup aplikasi berhasil");
	}

	@AfterTest
	public void finishing() throws Exception {
		//=======> untuk kebutuhan logging output agar system.out.print tulis ke file txt
		FileOutputStream fis = new FileOutputStream(fileLogOutput, true);
		PrintStream out = new PrintStream(fis);
		System.setOut(out);

		System.out.println("Testing aplikasi telah selesai. Terima Kasih");
		webDriver.close();
	}

	@Test(priority=1)
	public void loginAdmin() throws InterruptedException, Exception {
		//=======> untuk kebutuhan logging output agar system.out.print tulis ke file txt
		FileOutputStream fis = new FileOutputStream(fileLogOutput, true);
		PrintStream out = new PrintStream(fis);
		System.setOut(out);
		
		webDriver.get(SERVER_URL);
		System.out.println("===============START STEP LOGIN===============");
		System.out.println("nama method : loginAdmin()\n");
		//=======> memastikan orang yang login saat ini
		String ActualTitle = webDriver.getTitle();
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
		WebElement inputLogin = webDriver.findElement(By.xpath("//input[contains(@id,'signin-email')]"));
		inputLogin.clear();
		inputLogin.sendKeys("admin@gmail.com");

		WebElement inputPassword = webDriver.findElement(By.xpath("//input[contains(@id,'signin-password')]"));
		inputPassword.clear();
		inputPassword.sendKeys("secret");

		//=======> proses login
		WebElement buttonLogin = webDriver.findElement(By.xpath("//button[@type='submit'][contains(.,'LOGIN')]"));
		buttonLogin.click();

		//=======> memastikan orang yang login saat ini
		WebElement namaLogin = webDriver.findElement(By.xpath("//span[@class='hidden-xs']"));
		String LoginAs = namaLogin.getText();
		//System.out.println("Siapa yang login ? " + LoginAs);
		String ExpNamaLogin = "admin";
		System.out.println("Apakah user login sama ? " + "[" + LoginAs + "] == " + "[" + ExpNamaLogin + "]" );

		try {
			AssertJUnit.assertEquals(LoginAs, ExpNamaLogin);
			System.out.println("User Passed >>>>> OK \n");
		}
		catch(Exception exp) {
			System.out.println("Cause is : "+exp.getCause());
			System.out.println("Message is : "+exp.getMessage());
			System.out.println("User tidak SESUAI, cek code kembali");
			exp.printStackTrace();
		}

		Thread.sleep(2*1000);
		System.out.println("nama method : loginAdmin()");
		System.out.println("===============FINISH STEP LOGIN===============\n");
	}

	@Test(priority=2)
	public void createUser() throws Exception {
		//=======> untuk kebutuhan logging output agar system.out.print tulis ke file txt
		FileOutputStream fis = new FileOutputStream(fileLogOutput, true);
		PrintStream out = new PrintStream(fis);
		System.setOut(out);
		
		//screenshot param
	    TakesScreenshot ts=(TakesScreenshot)webDriver;

		System.out.println("===============START STEP CREATE USER===============");
		System.out.println("nama method : createUser()\n");
		WebElement buttonMenuUser = webDriver.findElement(By.xpath("//a[contains(.,'Users')]"));
		buttonMenuUser.click();

		//=======> memastikan page/url saat ini
		//WebElement pageUser = webDriver.findElement(By.xpath("//span[@class='hidden-xs']"));
		String urlUser = webDriver.getCurrentUrl();
		//System.out.println("URL sekarang ? " + urlUser);
		String ExpUrlUser = "http://127.0.0.1:8000/user";
		System.out.println("Apakah url sama ? " + "[" + urlUser + "] == " + "[" + ExpUrlUser + "]" );

		try {
			AssertJUnit.assertEquals(urlUser, ExpUrlUser);
			System.out.println("URL Passed >>>>> OK \n");
		}
		catch(Exception exp) {
			System.out.println("Cause is : "+exp.getCause());
			System.out.println("Message is : "+exp.getMessage());
			System.out.println("URL tidak SESUAI, cek code kembali");
			exp.printStackTrace();
		}

		//=======> mulai masuk ke form penambahan user
		WebElement buttonAddUser = webDriver.findElement(By.xpath("//a[@href='/register'][contains(.,'Add User')]"));
		buttonAddUser.click();

		System.out.println("add user dimulai");

		//=======> isi form login
		WebElement inputNamaUserNew = webDriver.findElement(By.xpath("//input[contains(@name,'name')]"));
		inputNamaUserNew.clear();
		String NamaUserNew = faker.name().firstName();
		inputNamaUserNew.sendKeys(NamaUserNew);

		WebElement inputEmailUserNew = webDriver.findElement(By.xpath("//input[contains(@name,'email')]"));
		inputEmailUserNew.clear();
		String EmailUserNew = NamaUserNew + "@gmail.com";
		inputEmailUserNew.sendKeys(EmailUserNew);

		WebElement inputPwdUserNew = webDriver.findElement(By.xpath("//input[@name='password']"));
		inputPwdUserNew.clear();
		inputPwdUserNew.sendKeys("123456");

		WebElement inputrePwdUserNew = webDriver.findElement(By.xpath("//input[contains(@name,'password_confirmation')]"));
		inputrePwdUserNew.clear();
		inputrePwdUserNew.sendKeys("123456");
		
		//cara screenshot
		Thread.sleep(2*1000);
		FileHandler.copy(ts.getScreenshotAs(OutputType.FILE), 
				new File(pathResult+"\\"+NamaUserNew+"_Submit.png"));
		

		//=======> mulai masuk ke form penambahan user
		WebElement buttonSubmitUserNew = webDriver.findElement(By.xpath("//button[@type='submit'][contains(.,'REGISTER')]"));
		buttonSubmitUserNew.click();

		//=======> search untuk bukti data sudah bertambah
		new WebDriverWait(webDriver, 20).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[contains(@type,'search')]")));
//		Thread.sleep(3*1000);
		WebElement inputSearchDataNew = webDriver.findElement(By.xpath("//input[contains(@type,'search')]"));
		inputSearchDataNew.clear();
		inputSearchDataNew.sendKeys(NamaUserNew);
//		new WebDriverWait(webDriver, 20).until(ExpectedConditions.elementToBeClickable(By.xpath("//th[contains(.,'ID')]"))).click();
		Thread.sleep(2*1000);
		WebElement sortbyid = webDriver.findElement(By.xpath("//th[contains(.,'ID')]"));
		sortbyid.click();
		
		//cara screenshot
		Thread.sleep(2*1000);
		FileHandler.copy(ts.getScreenshotAs(OutputType.FILE), 
				new File(pathResult+"\\"+NamaUserNew+"_Result.png"));
		
		System.out.println("add user selesai");

		Thread.sleep(2*1000);
		System.out.println("nama method : createUser()");
		System.out.println("===============FINISH STEP CREATE USER===============\n");
	}




}
