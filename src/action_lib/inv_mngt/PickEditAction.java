package action_lib.inv_mngt;
import java.util.HashMap;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import obj_repository.inv_mngt.PickEditObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class PickEditAction {
	
WebDriver webdriver;
	
	public PickEditAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}

	public String SavePick (HashMap<String, ?> InputObj) throws NoSuchElementException {
		String outRetVal = "-1";
		String inBoxID = InputObj.get("BoxID").toString();
	    String inQty = InputObj.get("Qty").toString();
	    
	    // Check the Table row count
	    
	   PickEditObj pickEditObj = new PickEditObj(webdriver);
       WebElement tblResult = pickEditObj.getTblPickingAreaSearchResultLocation();
		
       int tblRow = SeleniumUtil.getTableRows(tblResult);
       
       if(tblRow > 1)
       {
    	   if(!inBoxID.isEmpty() || !inQty.isEmpty())
    	   {
    		   
    		   WebElement tblInnerGridResult = pickEditObj.getTblSearchResultLocation();
    			
    	       int tblRowCnt = SeleniumUtil.getTableRows(tblInnerGridResult);
    	       
    	       if(tblRowCnt>1)
    	       {
    	    	   WebElement showhideBtn = pickEditObj.getImageShowhideLocation();
    	    	   if(tblInnerGridResult == null)
    	    	   {
    	    			CommUtil.logger.info("> Show hide button do not exist : ");
    	    		    return "-1";
    	    	   }
    	    	   
    	    	   showhideBtn.click();
    	    	   if(!inBoxID.isEmpty())
    	    	   {
    	    		   WebElement BoxId = pickEditObj.getTxtBoxIDLocation();
    	    		   BoxId.clear();
    	    		   BoxId.sendKeys(inBoxID);
    	    	   }
    	        
    	    	   if(!inQty.isEmpty())
    	    	   {
    	    		   WebElement Qty = pickEditObj.getTxtQtyLocation();
    	    		   Qty.clear();
    	    		   Qty.sendKeys(inQty);
    	    	   }
	            }
	        }
    	    	   WebElement BtnSave = pickEditObj.getBtnSearchLocation();
    	    	   if(!BtnSave.isEnabled())
    	    	   {
    	    		   return "1";
    	    	   }
    	    	   BtnSave.click(); 
    	    	   SeleniumUtil.waitPageRefresh(BtnSave);
    	    	   
    	    	   WebElement BtnYes = pickEditObj.getBtnYesLocation();
    	    	   BtnYes.click();
    	    	   
    	    	   
    	    	   boolean isLblSuccessMsgexist = SeleniumUtil.isWebElementExist(webdriver, pickEditObj.getLblSuccessLocator(), 0);
   				
	   				if(isLblSuccessMsgexist)
	   				{
	   					WebElement lblSuccessMessage = pickEditObj.getLblSuccessLocation();
	   	
	   					CommUtil.logger.info("> Message : "+lblSuccessMessage.getText());
	   				
	   					if (CommUtil.isMatchByReg(lblSuccessMessage.getText(), "Saved successfully\\.")) {
	   						outRetVal="0";
	   						CommUtil.logger.info("> Return Value : " + outRetVal);
	   						return outRetVal;
	   					  }
	   					else if(lblSuccessMessage.getText().contains("Saved successfully"))
	   					{
	   						outRetVal="0";
	   						CommUtil.logger.info("> Return Value : " + outRetVal);
	   						return outRetVal;
	   					}
	   					else 
	   					{
	   						CommUtil.logger.info(" > Success message doesn't match.");
	   						outRetVal = "-1";
	   						return outRetVal;
	   					}
	   				}
	   				else
	   				{
	   					CommUtil.logger.info(" > Success message doesn't exist.");
	   					outRetVal = "-1";
	   					return outRetVal;
	   				}
       }
		return outRetVal ;
	}
}
