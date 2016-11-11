package action_lib.inv_mngt;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import obj_repository.inv_mngt.PickAdminObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class PickAdminAction {
WebDriver webdriver;
	
	public PickAdminAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
public String SearchPickAdmin (HashMap<String, ?> InputObj) throws NoSuchElementException {

		String inProjCode = InputObj.get("ProjectCode").toString();
		String inOrdNum = InputObj.get("OrdNum").toString();
		String inUserName = InputObj.get("UserName").toString();
	 
		String outIsFound = "-1";
		PickAdminObj pickAdminObj= new PickAdminObj(webdriver);

		WebElement TxtProject = pickAdminObj.getTxtProjectCodeLocation();
		TxtProject.sendKeys(inProjCode);
		WebElement TxtUserName = pickAdminObj.getTxtUserNameLocation();
		TxtUserName.sendKeys(inUserName);
		WebElement TxtOrderNum = pickAdminObj.getTxtOrderNumLocation();
		TxtOrderNum.sendKeys(inOrdNum);
				
		WebElement BtnSearch = pickAdminObj.getBtnSearchLocation();
		BtnSearch.click();
		SeleniumUtil.waitPageRefresh(BtnSearch);
		
        WebElement tblResult = pickAdminObj.getTblSearchResultLocation();
		
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

public String SavePickAdmin(HashMap<String, ?> InputObj) throws NoSuchElementException {
	
	String inOrdNum = InputObj.get("OrdNum").toString();
	String inUserName = InputObj.get("UserName").toString();
	
    String vOrdNumColname = "Order #";
   //  String vUserNameColname = "User Name";
	
	String OutRetVal="-1";
	PickAdminObj pickAdminObj= new PickAdminObj(webdriver);
	WebElement tblResult = pickAdminObj.getTblSearchResultLocation();
	
    int tblRow = SeleniumUtil.getTableRows(tblResult);
    
	if(tblRow > 1)
	{
		int colidx = SeleniumUtil.getTableColIdxByName(tblResult, vOrdNumColname);
		CommUtil.logger.info(" > Col Index: "+colidx);
		int searchrow = SeleniumUtil.getRowByVal(tblResult, colidx, inOrdNum);
		CommUtil.logger.info(" > Row Index :" +searchrow);
		
		if(searchrow < 0)
		{
			CommUtil.logger.info(" > Rec Not found.");
			OutRetVal="3";
			return OutRetVal;
		}
		
		
		By UserNamelocator = pickAdminObj.getCmbUserNameLocator();
		WebElement EleUsername = SeleniumUtil.getCellElement(webdriver, tblResult, 11, searchrow, UserNamelocator);
		
		Select CmbUsername= new Select(EleUsername);
		
		/*WebElement assignUserList = pickAdminObj.getAssignUserLocation();
		Select cmbAssignUserList = new Select(assignUserList);*/
		
		boolean isHasVal = false;
		isHasVal = SeleniumUtil.isSelectHasOption(CmbUsername, inUserName);
		CommUtil.logger.info(" > isHasVal."+isHasVal);
		if(!isHasVal)
		{
			CommUtil.logger.info(" > UserName Not found.");
			OutRetVal="3";
			return OutRetVal;
		}
		
		CmbUsername.selectByVisibleText(inUserName);
		
		// Assign the UserName to the UserName in combobox in grid -start
	
		// Assign the UserName to the UserName in combobox in grid - end
		
		//cmbAssignUserList.selectByVisibleText(inUserName);
		tblResult = pickAdminObj.getTblSearchResultLocation();
		By locator = pickAdminObj.getSaveLinkLocator();
		WebElement editSavelink = SeleniumUtil.getCellElement(webdriver, tblResult, 1, searchrow, locator);
		
		if (editSavelink != null) {
			CommUtil.logger.info(" > Save Link Clicked..");
			editSavelink.click();
			boolean isBtnYesexist = SeleniumUtil.isWebElementExist(webdriver, pickAdminObj.getYesBtnLocator(), 0);
			
			if(isBtnYesexist)
			{
				WebElement btnYes = pickAdminObj.getYesBtnLocation();
				btnYes.click();
				
				boolean isLblSuccessMsgexist = SeleniumUtil.isWebElementExist(webdriver, pickAdminObj.getLblSuccessLocator(), 0);
				
				if(isLblSuccessMsgexist)
				{
					WebElement lblSuccessMessage = pickAdminObj.getLblSuccessLocation();
	
					CommUtil.logger.info("> Message : "+lblSuccessMessage.getText());
				
					if (CommUtil.isMatchByReg(lblSuccessMessage.getText(), "Saved successfully\\.")) {
						OutRetVal="0";
						CommUtil.logger.info("> Return Value : " + OutRetVal);
						return OutRetVal;
					  }
					else
					{
						CommUtil.logger.info(" > Success message doesn't match.");
						OutRetVal = "-1";
					}
				}
				else
				{
					CommUtil.logger.info(" > Success message doesn't exist.");
					OutRetVal = "-1";
				}
			}
			else
			{
				OutRetVal = "2";
				return  OutRetVal;	
			}
		}
	}
	return OutRetVal;
  }
}
