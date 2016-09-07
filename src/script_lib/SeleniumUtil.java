package script_lib;


import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import config.TestSetting;




public class SeleniumUtil {

	public static int getTableColIdxByName (WebElement Tbl, String ColName) {
		
		int retColIdx = -1;
		
		List<WebElement> rows = Tbl.findElements(By.tagName("tr"));  
		
		WebElement tblhead = rows.get(0);
		
		List<WebElement> cols = tblhead.findElements(By.tagName("th")); 
		
		for (int i = 0; i < cols.size(); i++) {
			if (CommUtil.isMatchByReg(cols.get(i).getText(), ColName)) {
				retColIdx = i;
				break;
			}
		}
		
		return retColIdx;
		
	}
	public static int getRowByVal (WebElement Tbl, int ColIdx, String Val) {
		
		int retRow = -1;
		
		List<WebElement> rows = Tbl.findElements(By.tagName("tr"));  

		for (int i = 1; i < rows.size(); i++) {
			WebElement tblrow = rows.get(i);
			List<WebElement> cols = tblrow.findElements(By.tagName("td")); 

			if (CommUtil.isMatchByReg(cols.get(ColIdx).getText(), Val)) {
				retRow = i;
				break;
			}			
		}		
		
		return retRow;
		
	}
	
	public static WebElement getCellElement (WebDriver webdriver, WebElement Tbl, int ColIdx, int row, By locator) {
		
		WebElement retEle = null;
		
		List<WebElement> rows = Tbl.findElements(By.tagName("tr"));  
		WebElement tblrow = rows.get(row);
		
		List<WebElement> cols = tblrow.findElements(By.tagName("td")); 

		WebElement cell = cols.get(ColIdx);
		
		if (isWebElementExist(webdriver, cell, locator, 0)) {
			retEle = cell.findElement(locator);
		}
	
		
		return retEle;
		
	}

	public static int getTableRows (WebElement Tbl) {
		
		int retRow = 0;
		
		List<WebElement> rows = Tbl.findElements(By.tagName("./tr"));  
	
		retRow = rows.size();
		return retRow;
		
	}
	
	public static int getTableCols (WebElement Tbl) {
		
		int retCol = 0;
		
		List<WebElement> cols = Tbl.findElements(By.tagName("th"));  
		
		retCol = cols.size();
		return retCol;
		
	}	

	public static boolean waitPageRefresh(WebElement trigger) {

		boolean isRefresh = false;
		try {
			for (int i = 1; i < TestSetting.Timeout_Sec; i++) {

				trigger.getTagName();
				Thread.sleep(1000);

			}
		} catch (StaleElementReferenceException e) {
			isRefresh = true;

			return isRefresh;
		} catch (WebDriverException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return isRefresh;
	}
	
	public static void waitWebElementProperty (WebDriver webdriver, By locator, String Prop, String Val) {
 //       webdriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

		new WebDriverWait(webdriver, TestSetting.Timeout_Sec).until (new ExpectedCondition<Boolean>() {  
		    @Override  
		    public Boolean apply(WebDriver driver) {  
		        Boolean result = false;  
		        try {  
		        	WebElement WebElmt= webdriver.findElement(locator);

		        	if (!Val.equals(WebElmt.getAttribute(Prop))) {

		            	result = true; 
		            }
		        	  	 
		        } catch(Exception e){  

		        }  
		        return result;  
		    }  
		}); 
	//	webdriver.manage().timeouts().implicitlyWait(TestSetting.Timeout_Sec, TimeUnit.SECONDS);		
	}	
	public static void waitWebElementProperty (WebDriver webdriver, WebElement WebElmt, String Prop, int Val, int waitTime) {
        webdriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);			
		new WebDriverWait(webdriver, waitTime).until (new ExpectedCondition<Boolean>() {  
		    @Override  
		    public Boolean apply(WebDriver driver) {  
		        Boolean result = false;  
		        try {  
		        	 
		        	System.out.println("attr:"+WebElmt.getAttribute(Prop));
		        	if (!String.valueOf(Val).equals(WebElmt.getAttribute(Prop))) {
		            	result = true; 
		            }
		        	  	 
		        } catch(Exception e){         
		        }  
		        return result;  
		    }  
		}); 
		webdriver.manage().timeouts().implicitlyWait(TestSetting.Timeout_Sec, TimeUnit.SECONDS);
		
	}	
	
	public static boolean isWebElementExist (WebDriver webdriver, By locator, int waitTime) {
		
        boolean flag = false; 
        webdriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        WebDriverWait wait = new WebDriverWait(webdriver, waitTime);
		
        try {
		flag = wait.until (new ExpectedCondition<Boolean>() {  
		    @Override  
		    public Boolean apply(WebDriver driver) {  
		        Boolean result = false;  
		        try {  
		            WebElement element=webdriver.findElement(locator);  
		            result=null!=element;  
		        } catch (NoSuchElementException e) {  
		            
		        }  
		        return result;  
		    }  
		}); 
        }catch (Exception e) {  
        	flag = false; 
        }  
		
		webdriver.manage().timeouts().implicitlyWait(TestSetting.Timeout_Sec, TimeUnit.SECONDS);
		 
        return flag; 
		
	}	
	public static boolean isWebElementExist (WebDriver webdriver, WebElement parent, By locator, int waitTime) {
		
        boolean flag = false; 
        webdriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        
		WebDriverWait wait = new WebDriverWait(webdriver, waitTime);
		try {
		flag = wait.until (new ExpectedCondition<Boolean>() {  
		    @Override  
		    public Boolean apply(WebDriver driver) {  
		        Boolean result = false;  
		        try {  
		            WebElement element=parent.findElement(locator);  
		            result=null!=element;  
		        } catch (NoSuchElementException e) {  
		            System.out.println("Element:" + locator.toString()  
		                    + " is not exsit!");  
		        }  
		        return result;  
		    }  
		}); 
		} catch (Exception e) {  
        }  
		
		webdriver.manage().timeouts().implicitlyWait(TestSetting.Timeout_Sec, TimeUnit.SECONDS);
        return flag; 
		
	}
	public static boolean isSelectHasOption (Select selectList, String Val) {
		
        boolean flag = false; 
		List<WebElement> options = selectList.getOptions();
		for (WebElement option : options) {
			if (CommUtil.isMatchByReg(option.getText(), Val)) {
				flag = true;
				break;
			}
		}
        return flag; 
		
	}
}
