package action_lib.odr_mngt;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import obj_repository.odr_mngt.OrderIndexObj;

public class OrderIndexAction {

	WebDriver webdriver;
	
	public OrderIndexAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public void GotoPOMngt () throws NoSuchElementException {
		
		OrderIndexObj ordIdxObj = new OrderIndexObj(webdriver);
	
		WebElement cmdPOManagement = ordIdxObj.getCmdPOManagement();

		cmdPOManagement.click();
		
	}
	
      public void GotoOrdAdmin () throws NoSuchElementException {
		
		OrderIndexObj ordIdxObj = new OrderIndexObj(webdriver);
	
		WebElement cmdOrderAdmin = ordIdxObj.getCmdOrderAdmin();

		cmdOrderAdmin.click();
		
	  }

      public void GotoOrdUpdate () throws NoSuchElementException {
	
	  OrderIndexObj ordIdxObj = new OrderIndexObj(webdriver);

	  WebElement cmdOrderUpdate = ordIdxObj.getCmdOrderUpdate();

	  cmdOrderUpdate.click();
	
    }

     public void GotoTmpltAdmin () throws NoSuchElementException {
	
	 OrderIndexObj ordIdxObj = new OrderIndexObj(webdriver);

	 WebElement cmdTemplateAdmin = ordIdxObj.getCmdTemplateAdmin();

	 cmdTemplateAdmin.click();
	
    }
}
