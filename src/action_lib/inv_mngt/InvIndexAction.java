package action_lib.inv_mngt;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import obj_repository.inv_mngt.InvIndexObj;


public class InvIndexAction {
	WebDriver webdriver;
	
	public InvIndexAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public void GotoDockReceiving () throws NoSuchElementException {
		
		InvIndexObj invIdxObj = new InvIndexObj(webdriver);
	
		WebElement CmdDocReceiving = invIdxObj.getCmdDocReceivingLocator();

		CmdDocReceiving.click();
		
	}
	
    public void GotoReceivingPutaway () throws NoSuchElementException {
		
    	InvIndexObj invIdxObj = new InvIndexObj(webdriver);
    	
		WebElement CmdReceivingPutaway = invIdxObj.getCmdReceivingPutawayLocator();

		CmdReceivingPutaway.click();
		
	}

    public void GotoInventoryLookup () throws NoSuchElementException {
	
    	InvIndexObj invIdxObj = new InvIndexObj(webdriver);
    	
		WebElement CmdInventoryLookup = invIdxObj.getCmdInventoryLookupLocator();

		CmdInventoryLookup.click();
	
     }
	 
    public void GotoPickAdmin () throws NoSuchElementException {
    	
    	InvIndexObj invIdxObj = new InvIndexObj(webdriver);
    	
		WebElement CmdPickAdmin = invIdxObj.getCmdPickAdminLocator();

		CmdPickAdmin.click();
    	
    }
    
    public void GotoPickQueue () throws NoSuchElementException {
    	
    	InvIndexObj invIdxObj = new InvIndexObj(webdriver);
    	
		WebElement CmdPickQueue = invIdxObj.getCmdPickQueueLocator();

		CmdPickQueue.click();
    	
    }
    
    public void GotoTransferRequest () throws NoSuchElementException {
    	
    	InvIndexObj invIdxObj = new InvIndexObj(webdriver);
    	
		WebElement CmdTransferRequest = invIdxObj.getCmdTransferRequestLocator();

		CmdTransferRequest.click();
    	
    }

    public void GotoTransferAdmin () throws NoSuchElementException {
    	
    	InvIndexObj invIdxObj = new InvIndexObj(webdriver);
    	
		WebElement CmdTransferAdmin = invIdxObj.getCmdTransferAdminLocator();

		CmdTransferAdmin.click();
    	
    }
    
    public void GotoTransferAction () throws NoSuchElementException {
    	
    	InvIndexObj invIdxObj = new InvIndexObj(webdriver);
    	
		WebElement CmdTransferAction = invIdxObj.getCmdDocReceivingLocator();

		CmdTransferAction.click();
    }
      
}
