package action_lib.odr_mngt;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import obj_repository.odr_mngt.OrderAdminObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;


public class OrderAdminAction {
	WebDriver webdriver;

	public OrderAdminAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public void NewOrder () throws NoSuchElementException {
		
		OrderAdminObj ordAdminObj = new OrderAdminObj(webdriver);
	
		WebElement BtnNewOrder = ordAdminObj.getBtnNewOrder();

		BtnNewOrder.click();
		
	}	
	
	public String EditOrder (HashMap<String, ?> InputObj) throws NoSuchElementException {
		//RetVal:-1:error;0: success
		String ret = "-1";	
		String SearchOrdNum = InputObj.get("OrdNum").toString();
		
		int editCol = 0;
		
		OrderAdminObj Obj = new OrderAdminObj(webdriver);
	
		WebElement tblResult = Obj.getTblSearchResult();
		
		int colidx = SeleniumUtil.getTableColIdxByName(tblResult, "Order Number");

		int searchrow = SeleniumUtil.getRowByVal(tblResult, colidx, SearchOrdNum);

		By locator = Obj.getLinkEditLocator();
		
		WebElement editlink = SeleniumUtil.getCellElement(webdriver, tblResult, editCol, searchrow, locator);
		
		if (editlink != null) {
			CommUtil.logger.info(" > Edit link clicked. Order Number:" + SearchOrdNum);
			editlink.click();
			
			WebElement EditOrderConfirmYes = Obj.getBtnEditOrderConfirmYes();
			EditOrderConfirmYes.click();
			
			ret = "0";	
		}
		
		return ret;
		
	}	
	public String ViewOrder (HashMap<String, ?> InputObj) throws NoSuchElementException {
		//RetVal:-1:error;0: success
		String ret = "-1";	
		String SearchOrdNum = InputObj.get("OrdNum").toString();
		
		int editCol = 0;
		
		OrderAdminObj Obj = new OrderAdminObj(webdriver);
	
		WebElement tblResult = Obj.getTblSearchResult();
		
		int colidx = SeleniumUtil.getTableColIdxByName(tblResult, "Order Number");

		int searchrow = SeleniumUtil.getRowByVal(tblResult, colidx, SearchOrdNum);

		By locator = Obj.getLinkViewLocator();
		
		WebElement viewlink = SeleniumUtil.getCellElement(webdriver, tblResult, editCol, searchrow, locator);
		
		if (viewlink != null) {
			CommUtil.logger.info(" > View link clicked. Order Number:" + SearchOrdNum);
			viewlink.click();
			
			ret = "0";	
		}
		
		return ret;
		
	}	
	
	public HashMap<String, String> GetOrdInfoFromResult (HashMap<String, ?> InputObj) throws NoSuchElementException {
		//RetVal:-1:error;0: success
		String ret = "-1";	
		int OrdStatusColSeq = 11;
		int OrdAttrColSeq = 13;
		
		String SearchOrdNum = InputObj.get("OrdNum").toString();
		
		HashMap<String, String> RetObj = new HashMap<String, String>();
		RetObj.put("RetVal", ret);
		RetObj.put("OrdStatus","");
		RetObj.put("OrdAttr", "");	
		
		OrderAdminObj Obj = new OrderAdminObj(webdriver);
	
		WebElement tblResult = Obj.getTblSearchResult();
		
		int colidx = SeleniumUtil.getTableColIdxByName(tblResult, "Order Number");

		int searchrow = SeleniumUtil.getRowByVal(tblResult, colidx, SearchOrdNum);

    	List<WebElement> tableRows = tblResult.findElements(By.xpath("./tr"));
    	
    	List<WebElement> Columns = tableRows.get(searchrow).findElements(By.xpath("./td"));

 	   if(Columns.size() > 0) {
	 		//CommUtil.logger.info("> BoxID : " + Columns.get(boxidColSeq).getText());
	 		String OrdStatus = Columns.get(OrdStatusColSeq).getText();
	 		//CommUtil.logger.info("> PartStatus : " + Columns.get(partStatusColSeq).getText());
	 		String OrdAttr = Columns.get(OrdAttrColSeq).getText();

    	    ret = "0";
	   		RetObj.put("RetVal", ret);
	   		RetObj.put("OrdStatus",OrdStatus);
	   		RetObj.put("OrdAttr", OrdAttr);	


	   } else {
			CommUtil.logger.info(" > Error while retrieving the Order Columns..");
   			return RetObj;
	   }		
		
		
		return RetObj;
		
	}	
	
	public String SearchOrder (HashMap<String, ?> InputObj) throws NoSuchElementException {

		String isFound = "0";	
		
		String SearchProjectCode = InputObj.get("ProjectCode").toString();
		String SearchOrdNum = InputObj.get("OrdNum").toString();
		String SearchPONum = InputObj.get("PONum").toString();
		String SearchStatus = InputObj.get("Status").toString();
		
		OrderAdminObj ordAdminObj = new OrderAdminObj(webdriver);
		
		WebElement TxtProjectCode = ordAdminObj.getTxtProjectCode();
		if (!TxtProjectCode.getAttribute("value").equals(SearchProjectCode)) {
			TxtProjectCode.clear();
			TxtProjectCode.sendKeys(SearchProjectCode);
		}
		
		WebElement TxtOrderNumber = ordAdminObj.getTxtOrderNumber();
		if (!TxtOrderNumber.getAttribute("value").equals(SearchOrdNum)) {
			TxtOrderNumber.clear();
			TxtOrderNumber.sendKeys(SearchOrdNum);
		}	
		
		WebElement TxtPONumber = ordAdminObj.getTxtPONumber();
		if (!TxtPONumber.getAttribute("value").equals(SearchPONum)) {
			TxtPONumber.clear();
			TxtPONumber.sendKeys(SearchPONum);
		}	
		
		if (!SearchStatus.equals("")) {
			WebElement WebEleSelStatus = ordAdminObj.getSelStatus();
			Select SelStatus = new Select(WebEleSelStatus);
			
	        if (!SelStatus.getFirstSelectedOption().getAttribute("value").equals(SearchStatus)) {

				boolean isHasVal = SeleniumUtil.isSelectHasOption(SelStatus, SearchStatus);
				if (!isHasVal) {
					CommUtil.logger.info(" > Status option not found in UI. SearchStatus:"+SearchStatus);
					isFound = "-1";
					return isFound;
				}
				
				SelStatus.selectByVisibleText(SearchStatus);
	        }
		}
	
		WebElement BtnSearch = ordAdminObj.getBtnSearch();

		BtnSearch.click();
		
		//SeleniumUtil.waitPageRefresh(BtnSearch);
		SeleniumUtil.waitPageRefreshByLoadingIcon(webdriver);
		WebElement tblResult = ordAdminObj.getTblSearchResult();
		
        int tblRow = SeleniumUtil.getTableRows(tblResult);
		if (tblRow > 1) {
			CommUtil.logger.info(" > Order is Found.");
			isFound = "1";
		} else {
			CommUtil.logger.info(" > Order is not Found.");
		} 
		
		return isFound;
		
	}	
}
