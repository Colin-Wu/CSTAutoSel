package script_lib;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import autoMain.AutoTest;
import config.TestSetting;

public class CommUtil {
	
	public static Logger logger = Logger.getLogger(AutoTest.class.getName()) ; 
	
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
	public static void setResultToCaselist (int idx, String RunRslt) {

        	HashMap<String, String>  retmap = (HashMap<String, String> ) TestSetting.caselist.get(idx);

        	retmap.put("result", RunRslt);
        	
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
}
