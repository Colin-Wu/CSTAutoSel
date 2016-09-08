package obj_repository.odr_mngt;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrderSummaryObj {
	WebDriver webdriver;
	int vOrdNumSeq = 0;
	int vStatusSeq = 1;
	int vPOSeq = 2;
	
	By TblSummaryLocator = By.xpath(".//div[@id='ContentPlaceHolder1_pnlReceivingView']/div/table/tbody");	
	
	By ParentCellLocator = By.xpath(".//span[1]");
	By ChildCellLocator = By.xpath(".//fieldset[1]");					
	By ChildCellLegendLocator = By.xpath(".//fieldset[1]/legend[1]");	
	
	public OrderSummaryObj(WebDriver driver) {
		super();
		this.webdriver = driver;		
	}
	
	public WebElement getTblSummary() throws NoSuchElementException  {
		WebElement retEle = null;

		retEle = webdriver.findElement(TblSummaryLocator);

		return retEle;
	}

	public int getvOrdNumSeq() {
		return vOrdNumSeq;
	}

	public int getvStatusSeq() {
		return vStatusSeq;
	}

	public int getvPOSeq() {
		return vPOSeq;
	}

	public By getTblSummaryLocator() {
		return TblSummaryLocator;
	}

	public By getParentCellLocator() {
		return ParentCellLocator;
	}

	public By getChildCellLocator() {
		return ChildCellLocator;
	}

	public By getChildCellLegendLocator() {
		return ChildCellLegendLocator;
	}	
}
