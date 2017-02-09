package action_lib.production;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import obj_repository.production.RepairTechQueueObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class RepairTechQueueAction {
	WebDriver webdriver;

	public RepairTechQueueAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public String GoToEvalQuote (HashMap<String, ?> InputObj) throws NoSuchElementException {
		//RetVal:-1:error;0: success
		String ret = "-1";	
		String SearchOrdNum = InputObj.get("OrdNum").toString();
		
		int GoCol = 0;
		
		RepairTechQueueObj Obj = new RepairTechQueueObj(webdriver);
	
		WebElement tblResult = Obj.getTblSearchResult();
		
		int colidx = SeleniumUtil.getTableColIdxByName(tblResult, "Order #");

		int searchrow = SeleniumUtil.getRowByVal(tblResult, colidx, SearchOrdNum);

		By locator = Obj.getLinkGoLocator();
		
		WebElement golink = SeleniumUtil.getCellElement(webdriver, tblResult, GoCol, searchrow, locator);
		
		if (golink != null) {
			CommUtil.logger.info(" > Go link clicked. Order Number:" + SearchOrdNum);
			golink.click();
			
			ret = "0";	
		}
		
		return ret;
		
	}
	public String SearchRepairTechQueue (HashMap<String, ?> InputObj) throws NoSuchElementException {

		String isFound = "0";	
		
		String SearchRepairDispName = InputObj.get("RepairDispName").toString();
		String SearchProjectCode = InputObj.get("ProjectCode").toString();
		String SearchOrdNum = InputObj.get("OrdNum").toString();
		String SearchBoxID = InputObj.get("BoxID").toString();
		String SearchSN = InputObj.get("SN").toString();


		RepairTechQueueObj repairTechQObj = new RepairTechQueueObj(webdriver);
		
        WebElement tblResult = repairTechQObj.getTblSearchResult();
       // String oldTbl = tblResult.getText();
        
		WebElement TxtDisplayName = repairTechQObj.getTxtDisplayName();
		if (!TxtDisplayName.getAttribute("value").equals(SearchRepairDispName)) {
			TxtDisplayName.clear();
			TxtDisplayName.sendKeys(SearchRepairDispName);
		}	
		
		WebElement TxtProjectCode = repairTechQObj.getTxtProjectCode();
		if (!TxtProjectCode.getAttribute("value").equals(SearchProjectCode)) {
			TxtProjectCode.sendKeys(SearchProjectCode);
		}
		
		WebElement TxtOrderNumber = repairTechQObj.getTxtOrderNumber();
		if (!TxtOrderNumber.getAttribute("value").equals(SearchOrdNum)) {
			TxtOrderNumber.sendKeys(SearchOrdNum);
		}	
		
		WebElement TxtBoxID = repairTechQObj.getTxtBoxID();
		if (!TxtBoxID.getAttribute("value").equals(SearchBoxID)) {
			TxtBoxID.sendKeys(SearchBoxID);
		}	
		
		WebElement TxtSN = repairTechQObj.getTxtSN();
		if (!TxtSN.getAttribute("value").equals(SearchSN)) {
			TxtSN.sendKeys(SearchSN);
		}	
	
		WebElement BtnSearch = repairTechQObj.getBtnSearch();

		BtnSearch.click();
		
		SeleniumUtil.waitWebElementContain(webdriver, repairTechQObj.getTblSearchResultLocator(), SearchOrdNum);
		
		tblResult = repairTechQObj.getTblSearchResult();
		
        int tblRow = SeleniumUtil.getTableRows(tblResult);
		if (tblRow > 1) {
			CommUtil.logger.info(" > Repair is Found.");
			isFound = "1";
		} else {
			CommUtil.logger.info(" > Repair is not Found.");
		} 
		
		return isFound;
		
	}	
}
