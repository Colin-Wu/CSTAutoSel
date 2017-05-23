package action_lib.odr_mngt;

import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import obj_repository.odr_mngt.OrderUpdateObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class OrderUpdateAction {
	WebDriver webdriver;

	public OrderUpdateAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	
	public String EditOrderUpdate (HashMap<String, ?> InputObj) throws NoSuchElementException {
		//RetVal:-1:error;0: success;1:Message not found.
		String ret = "-1";	
		String SearchOrdNum = InputObj.get("OrdNum").toString();
		String updateStatus = InputObj.get("Status").toString();
		String updatePONum = InputObj.get("PONum").toString();
		
		
		OrderUpdateObj Obj = new OrderUpdateObj(webdriver);
	
		WebElement tblResult = Obj.getTblSearchResult();
		
		int colidx = SeleniumUtil.getTableColIdxByName(tblResult, "Order Number");

		int searchrow = SeleniumUtil.getRowByVal(tblResult, colidx, SearchOrdNum);
		
		int rowIdx = searchrow-1;
		WebElement editlink = Obj.getLinkEdit(rowIdx);
		editlink.click();
		
		SeleniumUtil.waitPageRefreshByLoadingIcon(webdriver);
		
		if (!updateStatus.equals("")) {
			WebElement WebEleSelStatus = Obj.getSelStatus(rowIdx);
			Select SelStatus = new Select(WebEleSelStatus);
			
	        if (!SelStatus.getFirstSelectedOption().getAttribute("value").equals(updateStatus)) {

				boolean isHasVal = SeleniumUtil.isSelectHasOption(SelStatus, updateStatus);
				if (!isHasVal) {
					CommUtil.logger.info(" > Status option not found in UI. SearchStatus:"+updateStatus);
					return ret;
				}
				
				SelStatus.selectByVisibleText(updateStatus);
	        }
		}
		
		if (!updatePONum.equals("")) {
			WebElement TxtPO = Obj.getTxtPO(rowIdx);
			TxtPO.clear();
			TxtPO.sendKeys(updatePONum);
		}
		
		WebElement savelink = Obj.getLinkSave(rowIdx);
		savelink.click();
		
		SeleniumUtil.waitPageRefreshByLoadingIcon(webdriver);
		
		boolean isLblMsgexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblSuccessMessageLocator(), 0);
		
		if (isLblMsgexist) {
			WebElement lblMessage = Obj.getLblSuccessMessage();	
			CommUtil.logger.info(" > Msg: "+lblMessage.getText());
			if (CommUtil.isMatchByReg(lblMessage.getText(), "Update complete\\.")) {
				ret = "0";
			}
		} else {
			CommUtil.logger.info(" > Message not found. ");
			ret = "1";
		}
	
	
		return ret;
		
	}	
	
	public String SearchOrderUpdate (HashMap<String, ?> InputObj) throws NoSuchElementException {

		String isFound = "0";	
		
		String SearchProjectCode = InputObj.get("ProjectCode").toString();
		String SearchOrdNum = InputObj.get("OrdNum").toString();
		
		OrderUpdateObj ordupdateObj = new OrderUpdateObj(webdriver);
		
		WebElement TxtProjectCode = ordupdateObj.getTxtProjectCode();
		if (!TxtProjectCode.getAttribute("value").equals(SearchProjectCode)) {
			TxtProjectCode.sendKeys(SearchProjectCode);
		}
		
		WebElement TxtOrderNumber = ordupdateObj.getTxtOrderNumber();
		if (!TxtOrderNumber.getAttribute("value").equals(SearchOrdNum)) {
			TxtOrderNumber.sendKeys(SearchOrdNum);
		}	
		
	
		WebElement BtnSearch = ordupdateObj.getBtnSearch();

		BtnSearch.click();
		
		//SeleniumUtil.waitPageRefresh(BtnSearch);
		SeleniumUtil.waitPageRefreshByLoadingIcon(webdriver);
		WebElement tblResult = ordupdateObj.getTblSearchResult();
		
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
