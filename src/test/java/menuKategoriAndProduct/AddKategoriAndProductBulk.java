package menuKategoriAndProduct;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

//import com.github.javafaker.Faker;
import com.opencsv.CSVReader;

public class AddKategoriAndProductBulk {

	//=======> parameter bersama
	private static WebDriver webDriver;
	private static final String SERVER_URL = "http://127.0.0.1:8000/";
//	private Faker faker = new Faker(new Locale("id_ID"));

	//=======> CSV file untuk data driven atau bulk secara automation
	private String pathCSV ="C:\\Users\\refad\\eclipse-workspace\\SeleniumJavaFramework\\src\\test\\resources\\kategoriprodukbulk.csv";

	//=======> untuk kebutuhan screenshot
	private String pathOutput = "C:\\Users\\refad\\eclipse-workspace\\SeleniumJavaFramework\\src\\test\\resources\\record\\";
	//Create new directory under C:\nonExistedDirectory\directory
	Date date = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy_HHmmss"); 
	File pathResult = new File(pathOutput+"\\"+"Bulk_AddKategoriProduk"+formatter.format(date));
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

	@Test(priority=3)
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


	@Test(priority=4)
	public void addKategoriproduct() throws Exception {
		//=======> untuk kebutuhan logging output agar system.out.print tulis ke file txt
		FileOutputStream fis = new FileOutputStream(fileLogOutput, true);
		PrintStream out = new PrintStream(fis);
		System.setOut(out);

		//screenshot param
		TakesScreenshot ts=(TakesScreenshot)webDriver;

		CSVReader reader = new CSVReader(new FileReader(pathCSV));
		String [] csvCell;

		//=======> proses looping jumlah row data di csv, dan juga deklarasi cell datanya    
		while ((csvCell = reader.readNext()) != null) 
		{   
			String CellKategori		= csvCell[0];
			String CellNamaProduk 	= csvCell[1];
			String CellHargaProduk 	= csvCell[2];
			String CellJlmhProduk 	= csvCell[3];
			String CellFotoProduk	= csvCell[4];
			

		WebElement menuKategori = webDriver.findElement(By.xpath("//a[contains(.,'Kategori')]"));
		menuKategori.click();

		// cek apakah kategori tersebut tampil di row data ?
		WebElement searchKategori = webDriver.findElement(By.xpath("//input[contains(@type,'search')]"));
		searchKategori.sendKeys(CellKategori);
		Thread.sleep(3*1000);


		//=====> disini untuk mencoba apakah kategori tersebut sudah ada atau belum, jika belum maka create kategori
		try {
			// cek apa tampil data not found
			if(webDriver.findElement(By.xpath("//td[contains(.,'No matching records found')]")).isDisplayed())
			{
				System.out.println(webDriver.findElement(By.xpath("//td[contains(.,'No matching records found')]")).getText());
				System.out.println("data belum ada. membuat kategori dimulai");
				WebElement buttonAddKategori = webDriver.findElement(By.xpath("//a[@onclick='addForm()'][contains(.,'Add Categories')]"));
				buttonAddKategori.click();
				Thread.sleep(1*1000);
				WebElement popupInputNameNewKategori = webDriver.findElement(By.xpath("//input[contains(@id,'name')]"));
				popupInputNameNewKategori.clear();
				popupInputNameNewKategori.sendKeys(CellKategori);
				//						WebElement buttonCancel = webDriver.findElement(By.xpath("//button[@type='button'][contains(.,'Cancel')]"));
				//						buttonCancel.click();

				//cara screenshot
				Thread.sleep(2*1000);
				FileHandler.copy(ts.getScreenshotAs(OutputType.FILE), 
						new File(pathResult+"\\"+"Kategori_"+CellKategori+"_SubmitNew.png"));

				WebElement popupSubmitNewKategori = webDriver.findElement(By.xpath("//button[@type='submit'][contains(.,'Submit')]"));
				popupSubmitNewKategori.click();
				Thread.sleep(1*1000);
				WebElement inputsearchKategori = webDriver.findElement(By.xpath("//input[contains(@type,'search')]"));
				inputsearchKategori.clear();
				inputsearchKategori.sendKeys(CellKategori);
				System.out.println("add kategori berhasil");

				//cara screenshot
				Thread.sleep(2*1000);
				FileHandler.copy(ts.getScreenshotAs(OutputType.FILE), 
						new File(pathResult+"\\"+"Kategori_"+CellKategori+"_ResultNew.png"));

			}
		}
		catch(NoSuchElementException exp) {
			System.out.println("data sudah ada, langsung create produk saja");

			//cara screenshot
			Thread.sleep(2*1000);
			FileHandler.copy(ts.getScreenshotAs(OutputType.FILE), 
					new File(pathResult+"\\"+"Kategori_"+CellKategori+"_Result.png"));
		}



		//=====> memulai membuat produk
		Thread.sleep(1*1000);
		WebElement menuProduct = webDriver.findElement(By.xpath("(//a[contains(.,'Product')])[1]"));
		menuProduct.click();
		System.out.println("add produk dimulai");

		// cek apakah kategori tersebut tampil di row data ?
		WebElement searchProduct = webDriver.findElement(By.xpath("//input[contains(@type,'search')]"));
		searchProduct.sendKeys(CellNamaProduk);
		Thread.sleep(2*1000);


		//=====> disini untuk mencoba apakah produk tersebut sudah ada atau belum, jika belum maka update produk
		try {
			// cek apa tampil data not found
			if(webDriver.findElement(By.xpath("//td[contains(.,'No matching records found')]")).isDisplayed()) {
				System.out.println(webDriver.findElement(By.xpath("//td[contains(.,'No matching records found')]")).getText());
				System.out.println("data belum ada. membuat produk dimulai");

				WebElement buttonAddProduct = webDriver.findElement(By.xpath("//a[@onclick='addForm()'][contains(.,'Add Products')]"));
				buttonAddProduct.click();
				Thread.sleep(1*1000);

				//pengisian produk
				WebElement popupInputNamaNewProduct = webDriver.findElement(By.xpath("//input[@name='nama']"));
				popupInputNamaNewProduct.clear();
				popupInputNamaNewProduct.sendKeys(CellNamaProduk);

				WebElement popupInputPriceNewProduct = webDriver.findElement(By.xpath("//input[@name='harga']"));
				popupInputPriceNewProduct.clear();
				popupInputPriceNewProduct.sendKeys(CellHargaProduk);

				WebElement popupInputQtyNewProduct = webDriver.findElement(By.xpath("//input[@name='qty']"));
				popupInputQtyNewProduct.clear();
				popupInputQtyNewProduct.sendKeys(CellJlmhProduk);

				WebElement popupInputImageProduct = webDriver.findElement(By.xpath("//input[@name='image']"));
				popupInputImageProduct.clear();
				popupInputImageProduct.sendKeys(CellFotoProduk);

				Select popupSelectCategoryProduct = new Select(webDriver.findElement(By.xpath("//select[@name='category_id']")));
				popupSelectCategoryProduct.selectByVisibleText(CellKategori);

				//cara screenshot
				Thread.sleep(2*1000);
				FileHandler.copy(ts.getScreenshotAs(OutputType.FILE), 
						new File(pathResult+"\\"+"Kategori_"+CellKategori+"Produk_"+CellNamaProduk+"_SubmitNew.png"));

				WebElement popupSubmitNewProduct = webDriver.findElement(By.xpath("//button[@type='submit'][contains(.,'Submit')]"));
				popupSubmitNewProduct.click();

				Thread.sleep(1*1000);
				WebElement inputsearchProductNew = webDriver.findElement(By.xpath("//input[contains(@type,'search')]"));
				inputsearchProductNew.clear();
				inputsearchProductNew.sendKeys(CellNamaProduk);

				System.out.println("add produk berhasil");

				//cara screenshot
				Thread.sleep(2*1000);
				FileHandler.copy(ts.getScreenshotAs(OutputType.FILE), 
						new File(pathResult+"\\"+"Kategori_"+CellKategori+"Produk_"+CellNamaProduk+"_ResultNew.png"));
			}
		}
		catch(NoSuchElementException exp) {
			System.out.println("data sudah ada, proses edit produk");

			WebElement buttoneditProduk = webDriver.findElement(By.xpath("//a[contains(.,'Edit')]"));
			buttoneditProduk.click();
			Thread.sleep(2*1000);

			WebElement popupEditNamaNewProduct = webDriver.findElement(By.xpath("//input[@name='nama']"));
			popupEditNamaNewProduct.clear();
			popupEditNamaNewProduct.sendKeys(CellNamaProduk);

			WebElement popupInputPriceNewProduct = webDriver.findElement(By.xpath("//input[@name='harga']"));
			popupInputPriceNewProduct.clear();
			popupInputPriceNewProduct.sendKeys(CellHargaProduk);

			WebElement popupInputQtyNewProduct = webDriver.findElement(By.xpath("//input[@name='qty']"));
			popupInputQtyNewProduct.clear();
			popupInputQtyNewProduct.sendKeys(CellJlmhProduk);

			WebElement popupInputImageProduct = webDriver.findElement(By.xpath("//input[@name='image']"));
			popupInputImageProduct.clear();
			popupInputImageProduct.sendKeys(CellFotoProduk);

			Select popupSelectCategoryProduct = new Select(webDriver.findElement(By.xpath("//select[@name='category_id']")));
			popupSelectCategoryProduct.selectByVisibleText(CellKategori);

			//cara screenshot
			Thread.sleep(2*1000);
			FileHandler.copy(ts.getScreenshotAs(OutputType.FILE), 
					new File(pathResult+"\\"+"Kategori_"+CellKategori+"Produk_"+CellNamaProduk+"_SubmitUpdate.png"));

			WebElement popupSubmitNewProduct = webDriver.findElement(By.xpath("//button[@type='submit'][contains(.,'Submit')]"));
			popupSubmitNewProduct.click();

			Thread.sleep(1*1000);
			WebElement inputsearchProductNew = webDriver.findElement(By.xpath("//input[contains(@type,'search')]"));
			inputsearchProductNew.clear();
			inputsearchProductNew.sendKeys(CellNamaProduk);

			//cara screenshot
			Thread.sleep(2*1000);
			FileHandler.copy(ts.getScreenshotAs(OutputType.FILE), 
					new File(pathResult+"\\"+"Kategori_"+CellKategori+"Produk_"+CellNamaProduk+"_ResultUpdate.png"));

			System.out.println("update produk berhasil");
		}

		System.out.println("test per row");
		}
		Thread.sleep(2*1000);
		System.out.println("Create/update kategori produk telah berhasil");
	}

}
