package script_lib;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import config.TestSetting;

public class CommUtil {
	
	public static Logger logger = Logger.getLogger(CommUtil.class.getName()) ; 
	
	public static int getCaselistIdxByCasename (String SearchingCaseName) {
		
		int retRow = 0;
		
        for (int i = 0; i < TestSetting.caselist.size(); i++) {
        	HashMap<?, ?> map = (HashMap<?, ?>) TestSetting.caselist.get(i);

        	String caseName = (String) map.get("casename");
        	if (caseName.equals(SearchingCaseName)) {
        		retRow = i;
        		break;
        	}

        }
		return retRow;
		
	}
	public static void sendMailNotification () {
		String EmailAccount = TestSetting.MailAccount;
		String EmailPassword = "";
		String EmailSMTPHost = TestSetting.MailServer;
		String receiveMailAccount = TestSetting.MailReceiver;
	    
        Properties props = new Properties();  
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", EmailSMTPHost);
        props.setProperty("mail.smtp.auth", "false");

        Session session = Session.getDefaultInstance(props);
        session.setDebug(false); 

        MimeMessage message;
		try {
			message = createMimeMessage(session, EmailAccount, receiveMailAccount);
	        Transport transport = session.getTransport();
	        transport.connect(EmailAccount, EmailPassword);
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    		
	}
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail) throws Exception {
        
    	long costTime = (TestSetting.endtime - TestSetting.begintime) - TimeZone.getDefault().getRawOffset();
    	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    	String hms = formatter.format(costTime);
    	
    	MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMail, "", "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "", "UTF-8"));
        message.setSubject("CST Selenium test result :" + TestSetting.TestResult + ". Exe Time:"+hms, "UTF-8");
        message.setContent(TestSetting.MailResult, "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();

        return message;
    }	
	public static void setResultToCaselist (int idx, String RunRslt) {

        	HashMap<String, String>  retmap = (HashMap<String, String> ) TestSetting.caselist.get(idx);

        	retmap.put("result", RunRslt);
     		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    		String exeDate = df.format(new Date());
        	retmap.put("exeDate", exeDate);
       	
        	TestSetting.caselist.set(idx, retmap);

		
	}
	public static ArrayList<ArrayList<HashMap<String, String>>> getCaselistForMulti (int Thrdsize) {

		ArrayList<ArrayList<HashMap<String, String>>> retlist = new ArrayList<ArrayList<HashMap<String, String>>>();
		int SingleSize = TestSetting.caselist.size()/Thrdsize;
		int listIdx = 0;
		for (int i = 1; i <= Thrdsize; i++) {
			ArrayList<HashMap<String, String>> sublist = new ArrayList<HashMap<String, String>>();
			
			if (TestSetting.caselist.size()%Thrdsize != 0 && i == Thrdsize) {
				for (int j = 0; j < TestSetting.caselist.size() - SingleSize*(Thrdsize-1); j++) {
					listIdx = (i-1)*SingleSize+j;
					sublist.add(j,TestSetting.caselist.get(listIdx));
				}					
			} else {
				for (int j = 0; j < SingleSize; j++) {
					listIdx = (i-1)*SingleSize+j;
					sublist.add(j,TestSetting.caselist.get(listIdx));
				}					
			}
			
			retlist.add(sublist);
		}
		
		return retlist;

	}
	public static String excpt2Str(Exception ex) {
		String retStr = "";
		StringBuffer tempStr = new StringBuffer();
		tempStr.append(ex.getMessage()).append("\n");
		//err += ex.getMessage() + "\n";
		for (int i = 0; i < ex.getStackTrace().length;i++) {
			tempStr.append(ex.getStackTrace()[i]).append("\n");
			//err += ex.getStackTrace()[i]+"\n";
		}
		retStr = tempStr.toString();
		return retStr;
	}
	
	public static boolean isMatchByReg(String txt, String patten) {
		boolean retb = false;
		Pattern pattern = Pattern.compile(patten);
		Matcher matcher = pattern.matcher(txt);
		retb= matcher.matches();
		return retb;
	}	
	
	public static String genDateTimeStamp() {
		String ret = "";
		
		Date currentTime = new Date();
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		ret = dateTimeFormat.format(currentTime);
		
		return ret;
	}
		
	 public static void screenshot(TakesScreenshot drivername, String caseid) {
		 String imageFormat = "png";
		 String timestamp = genDateTimeStamp();
		 String fullfilename = System.getProperty("user.dir")+"\\screenshot\\"+caseid + "_"+timestamp+"."+imageFormat;

	    File scrFile = drivername.getScreenshotAs(OutputType.FILE);
        // Now you can do whatever you need to do with it, for example copy somewhere
        try {
			CommUtil.logger.info("Saving Screenshot: "+fullfilename);	
			FileUtils.copyFile(scrFile, new File(fullfilename));
			CommUtil.logger.info("Screenshot saved.");	
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("Can't save screenshot");
            e.printStackTrace();
        } 
	}
}
