package action_lib.inv_mngt;

import java.util.HashMap;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import obj_repository.inv_mngt.PickQueueObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class PickQueueAction {
	
WebDriver webdriver;
	
	public PickQueueAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}

	public String SearchPickQueue (HashMap<String, ?> InputObj) throws NoSuchElementException {
	
		String inProjCode = InputObj.get("ProjectCode").toString();
		String inOrdNum = InputObj.get("OrdNum").toString();
		String inUserName = InputObj.get("UserName").toString();
	 
		String outIsFound = "-1";
		
		PickQueueObj PickqueObj = new PickQueueObj(webdriver);
		
		WebElement TxtProject = PickqueObj.getTxtProjectCodeLocation();
		TxtProject.sendKeys(inProjCode);
		WebElement TxtUserName = PickqueObj.getTxtUserNameLocation();
		
		if(!inUserName.isEmpty()){
			TxtUserName.clear();
		}
		
		TxtUserName.sendKeys(inUserName);
		WebElement TxtOrderNum = PickqueObj.getTxtOrderNumLocation();
		TxtOrderNum.sendKeys(inOrdNum);
				
		WebElement BtnSearch = PickqueObj.getBtnSearchLocation();
		BtnSearch.click();
		SeleniumUtil.waitPageRefresh(BtnSearch);
		
        WebElement tblResult = PickqueObj.getTblSearchResultLocation();
		
        int tblRow = SeleniumUtil.getTableRows(tblResult);
		
        if (tblRow > 1) {
			CommUtil.logger.info(" > Rec is found.");
			outIsFound = "1";
		} else {
			CommUtil.logger.info(" > Rec is not found.");
			outIsFound = "0";
		}
		
		return outIsFound;
	}	
	
	public String PickQueueGoToPick (String OrderNum) throws NoSuchElementException {
		
		String outRetVal = "-1";
		
		String vOrdNumColname = "Order #";
		PickQueueObj PickqueObj = new PickQueueObj(webdriver);
		WebElement tblResult = PickqueObj.getTblSearchResultLocation();
			
	    int tblRow = SeleniumUtil.getTableRows(tblResult);
	    
	    if(tblRow > 1)
	    {
	    	int colidx = SeleniumUtil.getTableColIdxByName(tblResult, vOrdNumColname);
	    	CommUtil.logger.info(" > Col Index: "+colidx);
			int searchrow = SeleniumUtil.getRowByVal(tblResult, colidx, OrderNum);
			
			CommUtil.logger.info(" > Row Index: "+searchrow);
			By locator = PickqueObj.getLinkPickDetailLocator();
			
			WebElement editPickDetaillink = SeleniumUtil.getCellElement(webdriver, tblResult,0, searchrow, locator);
			
			if (editPickDetaillink != null) {
				CommUtil.logger.info(" > Pick link is clicked:" + OrderNum);
				CommUtil.logger.info(" > Pick:" + editPickDetaillink.toString());
				editPickDetaillink.click();
				SeleniumUtil.waitPageRefresh(editPickDetaillink);
				outRetVal="0";
			}
	    }
		return outRetVal;	
	}
}
