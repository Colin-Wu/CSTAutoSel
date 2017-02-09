package action_lib.shipping;

import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import obj_repository.shipping.ShippingListObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class ShippingListAction {
	WebDriver webdriver;

	public ShippingListAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	public String ShipListGoToShipping (HashMap<String, ?> InputObj) throws NoSuchElementException {
		//RetVal:-1:error;0: success
		String ret = "-1";	
		String SearchOrdNum = InputObj.get("OrdNum").toString();
		
		ShippingListObj Obj = new ShippingListObj(webdriver);
	
		WebElement tblResult = Obj.getTblSearchResult();
		
		int colidx = SeleniumUtil.getTableColIdxByName(tblResult, "Order Number");

		int searchrow = SeleniumUtil.getRowByVal(tblResult, colidx, SearchOrdNum);

		WebElement shippinglink = Obj.getLinkShipping(searchrow-1);
		
		if (shippinglink != null) {
			CommUtil.logger.info(" > Shipping link clicked. Order Number:" + SearchOrdNum);
			shippinglink.click();
			
			ret = "0";	
		}
		
		return ret;
		
	}		
	public String SearchShipping (HashMap<String, ?> InputObj) throws NoSuchElementException {

		String isFound = "0";	
	
		String SearchProjectCode = InputObj.get("ProjectCode").toString();
		String SearchOrdNum = InputObj.get("OrdNum").toString();
		String SearchBoxID = InputObj.get("BoxID").toString();
		String SearchStatus = InputObj.get("Status").toString();

		ShippingListObj ShippingListObj = new ShippingListObj(webdriver);
		
		WebElement TxtProjectCode = ShippingListObj.getTxtProjectCode();
		if (!TxtProjectCode.getAttribute("value").equals(SearchProjectCode)) {
			TxtProjectCode.sendKeys(SearchProjectCode);
		}
		
		WebElement TxtOrderNumber = ShippingListObj.getTxtOrderNumber();
		if (!TxtOrderNumber.getAttribute("value").equals(SearchOrdNum)) {
			TxtOrderNumber.sendKeys(SearchOrdNum);
		}	
		
		WebElement TxtBoxID = ShippingListObj.getTxtBoxID();
		if (!TxtBoxID.getAttribute("value").equals(SearchBoxID)) {
			TxtBoxID.sendKeys(SearchBoxID);
		}	
		
		if (!SearchStatus.equals("")) {
			WebElement WebEleSelStatus = ShippingListObj.getSelStatus();
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
	
		WebElement BtnSearch = ShippingListObj.getBtnSearch();

		BtnSearch.click();
		
		SeleniumUtil.waitPageRefresh(BtnSearch);
		//SeleniumUtil.waitPageRefreshByLoadingIcon(webdriver);
		
		WebElement tblResult = ShippingListObj.getTblSearchResult();
		
        int tblRow = SeleniumUtil.getTableRows(tblResult);
		if (tblRow > 1) {
			CommUtil.logger.info(" > Shipping is Found.");
			isFound = "1";
		} else {
			CommUtil.logger.info(" > Shipping is not Found.");
		} 
		
		return isFound;
		
	}		
}
