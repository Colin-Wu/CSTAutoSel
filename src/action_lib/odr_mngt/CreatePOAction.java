package action_lib.odr_mngt;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import obj_repository.odr_mngt.CreatePOObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class CreatePOAction {

	WebDriver webdriver;

	public CreatePOAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public HashMap<String, String> CreateStandardPO (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		//RetVal:-1:error;0: success;1:Order num is disabled for HP project
		String ret = "-1";
		String PONum = "";
		
		HashMap<String, String> RetObj = new HashMap<String, String>();
		RetObj.put("RetVal", ret);
		RetObj.put("PONum", PONum);	
		
		String ProjectCode = InputObj.get("ProjectCode").toString();
		String PartNum = InputObj.get("PartNum").toString();
		String Qty = InputObj.get("Qty").toString();
		String OrdNum = InputObj.get("OrdNum").toString();
		String StockGroup = InputObj.get("StockGroup").toString();
	
		CreatePOObj Obj = new CreatePOObj(webdriver);
		
		WebElement TxtProjectCode = Obj.getTxtProjectCode();
		TxtProjectCode.sendKeys(ProjectCode);
		
		WebElement TxtCustomerPO = Obj.getTxtCustomerPO();
		String oldVal = TxtCustomerPO.getText();

		WebElement TxtVendor = Obj.getTxtVendor();
		TxtVendor.click();	
		SeleniumUtil.waitPageRefresh(TxtVendor);
		WebElement LinkNewPO = Obj.getLinkNewPO();
		LinkNewPO.click();

		SeleniumUtil.waitWebElementProperty(webdriver, Obj.getTxtCustomerPOlocator(), "value", oldVal);
		
		if (ProjectCode.equals("HP") && !OrdNum.equals("")) {
			WebElement TxtOrderNum = Obj.getTxtOrderNum();
			if (TxtOrderNum.isEnabled()) {
				TxtOrderNum.sendKeys(OrdNum);
			} else {
				ret = "1";
			}
		}
		
		TxtCustomerPO = Obj.getTxtCustomerPO();
		PONum = TxtCustomerPO.getAttribute("value");
		
		WebElement tblPartlist = Obj.getTblPartList();
			
		int colidx = SeleniumUtil.getTableColIdxByName(tblPartlist, "Part Number");
		int searchrow = 1;
		By locator = Obj.getTxtPartNumLocator();	
		WebElement txtPartNum = SeleniumUtil.getCellElement(webdriver, tblPartlist, colidx, searchrow, locator);		
		if (txtPartNum != null) {
			txtPartNum.clear();
			txtPartNum.sendKeys(PartNum);
			
			TxtVendor = Obj.getTxtVendor();
			TxtVendor.click();
		}
		
		SeleniumUtil.waitPageRefresh(TxtVendor);
		
		WebElement WebEleSelStockGroup = Obj.getSelStockGroup(0);
		Select SelStockGroup = new Select(WebEleSelStockGroup);
		
		boolean isHasVal = SeleniumUtil.isSelectHasOption(SelStockGroup, StockGroup);
		if (!isHasVal) {
			CommUtil.logger.info(" > StockGroup option not found in UI. StockGroup:"+StockGroup);
			return RetObj;
		}
		
		SelStockGroup.selectByVisibleText(StockGroup);
		
		tblPartlist = Obj.getTblPartList();
		colidx = SeleniumUtil.getTableColIdxByName(tblPartlist, "QTY");
		locator = Obj.getTxtQtyLocator();	
		WebElement txtQty = SeleniumUtil.getCellElement(webdriver, tblPartlist, colidx, searchrow, locator);	
		if (txtQty != null) {
			txtQty.clear();
			txtQty.sendKeys(Qty);
		}
		
		WebElement BtnSave = Obj.getBtnSave();
		BtnSave.click();
		
		SeleniumUtil.waitPageRefresh(BtnSave);


		boolean isConfirmYesexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getBtnConfirmYesLocator(), 0);		
		if (isConfirmYesexist) {
			WebElement BtnConfirmYes = Obj.getBtnConfirmYes();	
			BtnConfirmYes.click();
			
			SeleniumUtil.waitPageRefresh(BtnConfirmYes);
		}	
		

		ret = "0";
		
		RetObj.put("RetVal", ret);
		RetObj.put("PONum", PONum);	
		

		return RetObj;
	}
}
