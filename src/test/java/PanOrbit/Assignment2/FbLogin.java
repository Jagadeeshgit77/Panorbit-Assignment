package PanOrbit.Assignment2;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FbLogin {
	WebDriver driver;
	public static List<String[]> readCSV(String filePath) throws IOException, CsvException {
	    CSVReader reader = new CSVReader(new FileReader(filePath));
	    List<String[]> records = reader.readAll();
	    reader.close();
	    return records;
	}
	@BeforeTest
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.facebook.com/");
	}
	
	@Test
	public void Login() throws IOException, CsvException {
		String CSV_path = "C:\\Users\\Krishnachaithanya\\eclipse-workspace\\Assignment2\\src\\main\\java\\Book1.csv";
		CSVReader reader = new CSVReader(new FileReader(CSV_path));
		List<String[]> records = reader.readAll();
		reader.close();
		records.remove(0);
		
		CSVWriter writer = new CSVWriter(new FileWriter(CSV_path));
		boolean headerWritten = false;
		
 for (String[] record : records) {
	    	
	    	String email = record[0];
	        String password = record[1];
	      

	        driver.get("https://www.facebook.com/");

	        driver.findElement(By.id("email")).sendKeys(email);

	        driver.findElement(By.id("pass")).sendKeys(password);

	        driver.findElement(By.xpath("//button[@value='1']")).click();

	        String actual = "";
	        if (driver.getCurrentUrl().contains("https://www.facebook.com/home")) {
	            actual = "Pass";
	        } else {
	            actual = "Fail";
	        }
	        record[3] = actual;
	   
	       if (!headerWritten) {
	            
	            headerWritten = true;
	            writer.writeNext(new String[]{"Username", "Password", "Expected Output", "Test Case Result"});
	        }
	        writer.writeNext(record);
	        
       }
 writer.close();
 
	    }
	
	@AfterTest
	public void exit() {
		driver.quit();
	}

}
