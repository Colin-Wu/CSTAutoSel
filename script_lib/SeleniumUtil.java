package script_lib;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import config.TestSetting;




public class SeleniumUtil {

	public static int getTableRows (WebElement Tbl) {
		
		int retRow = 0;
		
		List<WebElement> rows = Tbl.findElements(By.tagName("tr"));  
	
		retRow = rows.size();
		return retRow;
		
	}
	
	public static int getTableCols (WebElement Tbl) {
		
		int retCol = 0;
		
		List<WebElement> cols = Tbl.findElements(By.tagName("th"));  
		
		retCol = cols.size();
		return retCol;
		
	}	
	
	public static void waitWebElementProperty (WebElement WebElmt, String Prop, String Val) {
		
		new WebDriverWait(TestSetting.driver, TestSetting.Timeout_Sec).until (new ExpectedCondition<Boolean>() {  
		    @Override  
		    public Boolean apply(WebDriver driver) {  
		        Boolean result = false;  
		        try {  
		        	 
		        	System.out.println(WebElmt.getAttribute(Prop));
		        	if (Val.equals(WebElmt.getAttribute(Prop))) {
		            	result = true; 
		            }
		        	  	 
		        } catch(Exception e){         
		        }  
		        return result;  
		    }  
		}); 
		
	}	
	public static void waitWebElementProperty (WebElement WebElmt, String Prop, int Val) {
		
		new WebDriverWait(TestSetting.driver, 20).until (new ExpectedCondition<Boolean>() {  
		    @Override  
		    public Boolean apply(WebDriver driver) {  
		        Boolean result = false;  
		        try {  
		        	 
		        	System.out.println(WebElmt.getAttribute(Prop));
		        	if (String.valueOf(Val).equals(WebElmt.getAttribute(Prop))) {
		            	result = true; 
		            }
		        	  	 
		        } catch(Exception e){         
		        }  
		        return result;  
		    }  
		}); 
		
	}	
	
	public static boolean isWebElementExist (By locator) {
		
        boolean flag = false;  
        try {  
            WebElement element=TestSetting.driver.findElement(locator);  
            flag=null!=element;  
        } catch (NoSuchElementException e) {  
            System.out.println("Element:" + locator.toString()  
                    + " is not exsit!");  
        }  
        return flag; 
		
	}	
}
