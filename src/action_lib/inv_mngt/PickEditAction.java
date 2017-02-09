package action_lib.inv_mngt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import config.TestSetting;
import obj_repository.inv_mngt.PickEditObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class PickEditAction {
	
WebDriver webdriver;
	
	public PickEditAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}

	public HashMap<Object, Object> SavePick (HashMap<String, ?> InputObj) throws NoSuchElementException {
				
		String outRetVal = "-1";
		List<String> Boxidlst = new ArrayList<String>();
		
		HashMap<Object, Object> RstObj = new HashMap<Object, Object>();
		RstObj.put("Ret", outRetVal);
		RstObj.put("BoxIDs", Boxidlst);

		
		String inBoxID = InputObj.get("BoxID").toString();
	    String inQty = InputObj.get("Qty").toString();
	    String inQA = InputObj.get("QA").toString();
	    String caseid = "";
		Object caseidobj = InputObj.get("caseid");	
		if (caseidobj != null) {
			caseid = caseidobj.toString();
		}
 
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
    	    		    return RstObj;
    	    	   }
    	    	   
    	    	   showhideBtn.click();
    	    	   if(!inBoxID.isEmpty())
    	    	   {
    	    		   List<WebElement> BoxIDs = pickEditObj.getTxtBoxIDs();
    	    		   WebElement BoxId = BoxIDs.get(0);
    	    		   BoxId.clear();
    	    		   BoxId.sendKeys(inBoxID);
    	    	   }
    	        
    	    	   if(!inQty.isEmpty())
    	    	   {
    	    		   List<WebElement> Qtys = pickEditObj.getTxtQtys();
    	    		   WebElement Qty =  Qtys.get(0);
    	    		   Qty.clear();
    	    		   Qty.sendKeys(inQty);
    	    	   }
	            }
	        }
    	   WebElement BtnSave = pickEditObj.getBtnSearchLocation();
    	   if(!BtnSave.isEnabled())
    	   {
    		   outRetVal = "1";
    			RstObj.put("Ret", outRetVal);
    			RstObj.put("BoxIDs", Boxidlst);    		   
    		   return RstObj;
    	   }
    	   
    	   List<WebElement> BoxIDs = pickEditObj.getTxtBoxIDs();
    	   for (WebElement TxtBox:BoxIDs) {
        	   // sync resource when multi thread running
        	   if (!caseid.equals("")) {
        		   TestSetting.reslist.addBoxid(caseid, TxtBox.getAttribute("value"));
        	   }
    		   Boxidlst.add(TxtBox.getAttribute("value"));
    	   }
    	   
    	   
    	   BtnSave.click(); 
    	   SeleniumUtil.waitPageRefresh(BtnSave);
    	   
    	   WebElement BtnYes = pickEditObj.getBtnYesLocation();
    	   BtnYes.click();
    	   SeleniumUtil.waitPageRefresh(BtnYes);
    	   
    	   
    	   boolean isLblSuccessMsgexist = SeleniumUtil.isWebElementExist(webdriver, pickEditObj.getLblSuccessLocator(), 5);
		
			if(isLblSuccessMsgexist)
			{
				WebElement lblSuccessMessage = pickEditObj.getLblSuccessLocation();

				CommUtil.logger.info("> Message : "+lblSuccessMessage.getText());
			
				if (CommUtil.isMatchByReg(lblSuccessMessage.getText(), "Saved successfully\\.")) {
					//outRetVal="0";
					//CommUtil.logger.info("> Return Value : " + outRetVal);
					//return outRetVal;
				  }
				else if(lblSuccessMessage.getText().contains("Saved successfully"))
				{
					//outRetVal="0";
					//CommUtil.logger.info("> Return Value : " + outRetVal);
					//return outRetVal;
				}
				else 
				{
					CommUtil.logger.info(" > Success message doesn't match.");
					outRetVal = "-1";
	    			RstObj.put("Ret", outRetVal);
	    			RstObj.put("BoxIDs", Boxidlst);    		   
	    		   return RstObj;
				}
			}
			else
			{
				CommUtil.logger.info(" > Success message doesn't exist.");
				outRetVal = "-1";
    			RstObj.put("Ret", outRetVal);
    			RstObj.put("BoxIDs", Boxidlst);    		   
    		   return RstObj;
			}
			
			if (inQA.equals("1")) {
		    	   BtnYes = pickEditObj.getBtnYesLocation();
		    	   BtnYes.click();
		    	   SeleniumUtil.waitPageRefresh(BtnYes);
			} else if (inQA.equals("0")) {
		    	   WebElement BtnNo = pickEditObj.getBtnNo();
		    	   BtnNo.click();
		    	   SeleniumUtil.waitPageRefresh(BtnNo);
		    	   
		    	   BtnYes = pickEditObj.getBtnYesLocation();
		    	   BtnYes.click();
		    	   SeleniumUtil.waitPageRefresh(BtnYes);
			}
			
			
			
       }
       outRetVal="0";
       RstObj.put("Ret", outRetVal);
       RstObj.put("BoxIDs", Boxidlst);    		   
	   return RstObj;

	}
}
