package test;


import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;



public class AmazonSearch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String Category;
		String SearchVal;
		WebDriver driver = new ChromeDriver();
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
		driver.get("https://www.amazon.in/");
		
		//WebElement search = driver.findElement(By.xpath("//select[@id = 'searchDropdownBox']"));
		//search.click();
		
		
		try {
			    Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root", "root");
				
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from amazon");
				
				while(rs.next()) {
					
					System.out.println(rs.getString(2)+ " " + rs.getString(3));
					
					Category = rs.getString(2);
					WebElement search = driver.findElement(By.xpath("//select[@id = 'searchDropdownBox']"));
					search.sendKeys(Category);
					SearchVal = rs.getString(3);
					WebElement searchBar = driver.findElement(By.xpath("//input[@id = 'twotabsearchtextbox']"));
					searchBar.sendKeys(SearchVal);
					WebElement searchButton = driver.findElement(By.xpath("//input[@type = 'submit']"));
					searchButton.click();
					List<WebElement> ShownResults = driver.findElements(By.xpath("//*[@data-component-type='s-search-result']"));
					System.out.println("Total Number of Shown Results: " + ShownResults.size());
					
					List<WebElement> SponserResults = driver.findElements(By.xpath("//*[@class = 'aok-inline-block s-sponsored-label-info-icon']"));
					System.out.println("Total Number of Sponsers: " + SponserResults.size());
					
					int Results = ShownResults.size() - SponserResults.size();
					System.out.println("Total NUmber of Mobiles: " + Results);
					
					TakesScreenshot tsObj = (TakesScreenshot)driver;
					
					File myFile = tsObj.getScreenshotAs(OutputType.FILE);
					
					
				
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.close();
		
		
	}

}
