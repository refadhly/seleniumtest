package menuKategoriAndProduct;

import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import org.testng.AssertJUnit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
//import java.util.Locale;
import java.util.Locale;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

//import com.github.javafaker.Faker;

public class AddKategoriAndProductSingle {

	//=======> parameter bersama
		private static WebDriver webDriver;
		private static final String SERVER_URL = "http://127.0.0.1:8000/";
		private Faker faker = new Faker(new Locale("id_ID"));

		//=======> untuk kebutuhan screenshot
		private String pathOutput = "C:\\Users\\refad\\eclipse-workspace\\SeleniumJavaFramework\\src\\test\\resources\\record\\";
		//Create new directory under C:\nonExistedDirectory\directory
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy_HHmmss"); 
		File pathResult = new File(pathOutput+"\\"+"Single_AddKategoriProduk"+formatter.format(date));
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
			Thread.sleep(3*1000);
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

			Thread.sleep(1*1000);
			System.out.println("nama method : loginAdmin()");
			System.out.println("===============FINISH STEP LOGIN===============\n");
		}
		
		
		@Test(priority=2)
		public void addKategoriproduct() throws Exception {
			//=======> untuk kebutuhan logging output agar system.out.print tulis ke file txt
			FileOutputStream fis = new FileOutputStream(fileLogOutput, true);
			PrintStream out = new PrintStream(fis);
			System.setOut(out);
			
			//screenshot param
		    TakesScreenshot ts=(TakesScreenshot)webDriver;
			
			//variable nama kategori yang akan di input
//			String categ = "sepokat";
			String categfaker = faker.commerce().material();
			//variable nama produk yang akan di input
//			String produk = "kets2";
			String produkfaker = faker.commerce().productName();
			String hargaproduk = "190000";
			String qtyproduk = "100";
			String imageproduk = "C:\\Users\\refad\\Downloads\\download.jpg";

				
			WebElement menuKategori = webDriver.findElement(By.xpath("//a[contains(.,'Kategori')]"));
			menuKategori.click();
			
			// cek apakah kategori tersebut tampil di row data ?
			WebElement searchKategori = webDriver.findElement(By.xpath("//input[contains(@type,'search')]"));
			searchKategori.sendKeys(categfaker);
			Thread.sleep(1*1000);
			
			
			//=====> disini untuk mencoba apakah kategori tersebut sudah ada atau belum, jika belum maka create kategori
			try {
				// cek apa tampil data not found
				if(webDriver.findElement(By.xpath("//td[contains(.,'No matching records found')]")).isEnabled())
				{
					System.out.println(webDriver.findElement(By.xpath("//td[contains(.,'No matching records found')]")).getText());
					System.out.println("data belum ada. membuat kategori dimulai");
					WebElement buttonAddKategori = webDriver.findElement(By.xpath("//a[@onclick='addForm()'][contains(.,'Add Categories')]"));
					buttonAddKategori.click();
					Thread.sleep(1*1000);
					WebElement popupInputNameNewKategori = webDriver.findElement(By.xpath("//input[contains(@id,'name')]"));
					popupInputNameNewKategori.clear();
					popupInputNameNewKategori.sendKeys(categfaker);
//					WebElement buttonCancel = webDriver.findElement(By.xpath("//button[@type='button'][contains(.,'Cancel')]"));
//					buttonCancel.click();
					
					//cara screenshot
					Thread.sleep(2*1000);
					FileHandler.copy(ts.getScreenshotAs(OutputType.FILE), 
							new File(pathResult+"\\"+"Kategori_"+categfaker+"_SubmitNew.png"));
					
					WebElement popupSubmitNewKategori = webDriver.findElement(By.xpath("//button[@type='submit'][contains(.,'Submit')]"));
					popupSubmitNewKategori.click();
					Thread.sleep(1*1000);
					WebElement inputsearchKategori = webDriver.findElement(By.xpath("//input[contains(@type,'search')]"));
					inputsearchKategori.clear();
					inputsearchKategori.sendKeys(categfaker);
					System.out.println("add kategori berhasil");
					
					//cara screenshot
					Thread.sleep(2*1000);
					FileHandler.copy(ts.getScreenshotAs(OutputType.FILE), 
							new File(pathResult+"\\"+"Kategori_"+categfaker+"_ResultNew.png"));
					
				}
			}
			catch(NoSuchElementException  exp) {
				System.out.println("data sudah ada, langsung create produk saja");
				
				//cara screenshot
				Thread.sleep(2*1000);
				FileHandler.copy(ts.getScreenshotAs(OutputType.FILE), 
						new File(pathResult+"\\"+"Kategori_"+categfaker+"_Result.png"));
			}
			
			
		
			//=====> memulai membuat produk
			Thread.sleep(1*1000);
			WebElement menuProduct = webDriver.findElement(By.xpath("(//a[contains(.,'Product')])[1]"));
			menuProduct.click();
			System.out.println("add produk dimulai");
			
			// cek apakah kategori tersebut tampil di row data ?
			WebElement searchProduct = webDriver.findElement(By.xpath("//input[contains(@type,'search')]"));
			searchProduct.sendKeys(produkfaker);
			Thread.sleep(2*1000);
		
			
			//=====> disini untuk mencoba apakah produk tersebut sudah ada atau belum, jika belum maka update produk
			try {
				// cek apa tampil data not found
				if(webDriver.findElement(By.xpath("//td[contains(.,'No matching records found')]")).isDisplayed() ) {
					System.out.println(webDriver.findElement(By.xpath("//td[contains(.,'No matching records found')]")).getText());
					System.out.println("data belum ada. membuat produk dimulai");

					WebElement buttonAddProduct = webDriver.findElement(By.xpath("//a[@onclick='addForm()'][contains(.,'Add Products')]"));
					buttonAddProduct.click();
					Thread.sleep(1*1000);

					//pengisian produk
					WebElement popupInputNamaNewProduct = webDriver.findElement(By.xpath("//input[@name='nama']"));
					popupInputNamaNewProduct.clear();
					popupInputNamaNewProduct.sendKeys(produkfaker);

					WebElement popupInputPriceNewProduct = webDriver.findElement(By.xpath("//input[@name='harga']"));
					popupInputPriceNewProduct.clear();
					popupInputPriceNewProduct.sendKeys(hargaproduk);

					WebElement popupInputQtyNewProduct = webDriver.findElement(By.xpath("//input[@name='qty']"));
					popupInputQtyNewProduct.clear();
					popupInputQtyNewProduct.sendKeys(qtyproduk);

					WebElement popupInputImageProduct = webDriver.findElement(By.xpath("//input[@name='image']"));
					popupInputImageProduct.clear();
					popupInputImageProduct.sendKeys(imageproduk);

					Select popupSelectCategoryProduct = new Select(webDriver.findElement(By.xpath("//select[@name='category_id']")));
					popupSelectCategoryProduct.selectByVisibleText(categfaker);
					
					//cara screenshot
					Thread.sleep(2*1000);
					FileHandler.copy(ts.getScreenshotAs(OutputType.FILE), 
							new File(pathResult+"\\"+"Kategori_"+categfaker+"Produk_"+produkfaker+"_SubmitNew.png"));

					WebElement popupSubmitNewProduct = webDriver.findElement(By.xpath("//button[@type='submit'][contains(.,'Submit')]"));
					popupSubmitNewProduct.click();

					Thread.sleep(2*1000);
					WebElement inputsearchProductNew = webDriver.findElement(By.xpath("//input[contains(@type,'search')]"));
					inputsearchProductNew.clear();
					inputsearchProductNew.sendKeys(produkfaker);

					System.out.println("add produk berhasil");
					
					//cara screenshot
					Thread.sleep(2*1000);
					FileHandler.copy(ts.getScreenshotAs(OutputType.FILE), 
							new File(pathResult+"\\"+"Kategori_"+categfaker+"Produk_"+produkfaker+"_ResultNew.png"));
				}
			}
			catch(NoSuchElementException exp) {
				System.out.println("data sudah ada, proses edit produk");

				WebElement buttoneditProduk = webDriver.findElement(By.xpath("//a[contains(.,'Edit')]"));
				buttoneditProduk.click();
				Thread.sleep(2*1000);

				WebElement popupEditNamaNewProduct = webDriver.findElement(By.xpath("//input[@name='nama']"));
				popupEditNamaNewProduct.clear();
				popupEditNamaNewProduct.sendKeys(produkfaker);

				WebElement popupInputPriceNewProduct = webDriver.findElement(By.xpath("//input[@name='harga']"));
				popupInputPriceNewProduct.clear();
				popupInputPriceNewProduct.sendKeys(hargaproduk);

				WebElement popupInputQtyNewProduct = webDriver.findElement(By.xpath("//input[@name='qty']"));
				popupInputQtyNewProduct.clear();
				popupInputQtyNewProduct.sendKeys(qtyproduk);

				WebElement popupInputImageProduct = webDriver.findElement(By.xpath("//input[@name='image']"));
				popupInputImageProduct.clear();
				popupInputImageProduct.sendKeys(imageproduk);

				Select popupSelectCategoryProduct = new Select(webDriver.findElement(By.xpath("//select[@name='category_id']")));
				popupSelectCategoryProduct.selectByVisibleText(categfaker);			
				
				//cara screenshot
				Thread.sleep(2*1000);
				FileHandler.copy(ts.getScreenshotAs(OutputType.FILE), 
						new File(pathResult+"\\"+"Kategori_"+categfaker+"Produk_"+produkfaker+"_SubmitUpdate.png"));

				WebElement popupSubmitNewProduct = webDriver.findElement(By.xpath("//button[@type='submit'][contains(.,'Submit')]"));
				popupSubmitNewProduct.click();
				
				//=======> search untuk bukti data sudah bertambah
				new WebDriverWait(webDriver, 20).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[contains(@type,'search')]")));
//				Thread.sleep(3*1000);
				WebElement inputSearchDataNew = webDriver.findElement(By.xpath("//input[contains(@type,'search')]"));
				inputSearchDataNew.clear();
				inputSearchDataNew.sendKeys(produkfaker);
//				new WebDriverWait(webDriver, 20).until(ExpectedConditions.elementToBeClickable(By.xpath("//th[contains(.,'ID')]"))).click();
				Thread.sleep(2*1000);
				WebElement sortbyid = webDriver.findElement(By.xpath("//th[contains(.,'ID')]"));
				sortbyid.click();
				
				
				
				
				
				//cara screenshot
				Thread.sleep(2*1000);
				FileHandler.copy(ts.getScreenshotAs(OutputType.FILE), 
						new File(pathResult+"\\"+"Kategori_"+categfaker+"Produk_"+produkfaker+"_ResultUpdate.png"));

				System.out.println("update produk berhasil");
			}
			
			Thread.sleep(2*1000);
			System.out.println("Create/update kategori produk telah berhasil");
						
		}
		
	
}
