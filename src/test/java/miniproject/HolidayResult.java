package miniproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HolidayResult {
	
	public static WebDriver driver;
	public static WebDriverWait wait;
	
	public void createDriver() {
		System.out.print("Enter the required browser(Chrome or Edge): ");
		Scanner sc=new Scanner(System.in);
		String browser=sc.nextLine();
		if(browser.equalsIgnoreCase("chrome")) {
			driver=new ChromeDriver();
			driver.get("https://www.royalcaribbean.com/alaska-cruises");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			driver.manage().window().maximize();
		}
		else if(browser.equalsIgnoreCase("edge")) {
			driver=new EdgeDriver();
			driver.get("https://www.royalcaribbean.com/alaska-cruises");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			driver.manage().window().maximize();
		}
	}
	public void closeAd() {
		wait=new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[1]"))).click();
	}
	public void scrollNdClick() {
		JavascriptExecutor js=(JavascriptExecutor) driver;
		WebElement hc=driver.findElement(By.xpath("//*[@id=\"rciFooterGroup-1-4\"]/a"));
		js.executeScript("arguments[0].scrollIntoView(true);",hc);
		hc.click();
	}
	public String excelData() throws IOException {
		driver.findElement(By.xpath("//*[@id=\"rciSearchHeaderIcon\"]/div")).click();
		FileInputStream file=new FileInputStream(System.getProperty("user.dir")+"\\DataSource\\InputData.xlsx");
		XSSFWorkbook book=new XSSFWorkbook(file);
		XSSFSheet sheet=book.getSheet("Sheet1");
		XSSFRow row=sheet.getRow(0);
		String data=row.getCell(0).toString();
		book.close();
		file.close();
		return data;
	}
	public void firstResult(String data) {
		wait=new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rciSearchInput"))).sendKeys(data);
		driver.findElement(By.xpath("//*[@id=\"rciSearchInputIcon\"]")).click();
		String []res=driver.findElement(By.xpath("//*[@id=\"siteSearchApp\"]/div[1]/div/div[2]/div")).getText().split(" ");
		System.out.println("Result before filter applied: "+res[1]);
	}
	public void find() {
		WebElement find=driver.findElement(By.xpath("//*[@id=\"rciHeaderMenuItem-1\"]"));
		find.click();
	}
	public void filtering() {
		//date 
		WebElement date=driver.findElement(By.xpath("//*[@id=\"filters-content\"]/button[1]"));
		date.click();
		driver.findElement(By.xpath("//*[@id=\"custom-filter-label-SPRING_CRUISES_Dates\"]")).click();
		//port
		WebElement port=driver.findElement(By.xpath("/html/body/div[1]/div[3]/div/div[2]/section/div/section[1]/div/div/button[2]"));
		port.click();
		driver.findElement(By.xpath("//*[@id=\"departure-port-label-BYE\"]")).click();
		//destination
		WebElement des=driver.findElement(By.xpath("/html/body/div[1]/div[3]/div/div[2]/section/div/section[1]/div/div/button[3]"));
		des.click();
		driver.findElement(By.xpath("//*[@id=\"custom-filter-label-PCC\"]")).click();
		//No. of nights
		WebElement num=driver.findElement(By.xpath("/html/body/div[1]/div[3]/div/div[2]/section/div/section[1]/div/div/button[4]"));
		num.click();
		driver.findElement(By.xpath("//*[@id=\"nights-filter-wrap\"]/section/section/div[2]/div[1]/button")).click();
		//ships
		WebElement ship=driver.findElement(By.xpath("/html/body/div[1]/div[3]/div/div[2]/section/div/section[1]/div/div/button[5]"));
		ship.click();
		driver.findElement(By.xpath("/html/body/div[1]/div[3]/div/div[2]/section/div/section/div[2]/div[2]/div[2]/button")).click();
		//res2
		driver.findElement(By.xpath("/html/body/div[1]/div[3]/div/div[2]/section/section/button")).click();
	    String res2=driver.findElement(By.xpath("//span[normalize-space()='(5)']")).getText();
	    System.out.println("Result after filter applied: "+res2);		
	}
	public void screenshot() throws IOException {
		WebElement ss=driver.findElement(By.xpath("//div[@class='MuiTypography-root MuiTypography-title2 styles__SectionTitle-sc-mayrxo-5 hBpQU css-13jcdqr']"));
		File src=ss.getScreenshotAs(OutputType.FILE);
		File trg=new File("C:\\Users\\2303846\\OneDrive - Cognizant\\Desktop\\royalcaribbean\\Screenshots\\result.png");
		FileUtils.copyFile(src, trg);
		
	}
	public void closeBrowser() {
		driver.quit();
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		HolidayResult hr=new HolidayResult();
		hr.createDriver();
		hr.closeAd();
		hr.scrollNdClick();
		String data=hr.excelData();
		hr.firstResult(data);
		hr.find();
		hr.filtering();
		hr.screenshot();
		hr.closeBrowser();		
	}

}


