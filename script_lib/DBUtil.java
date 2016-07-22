package script_lib;

import java.sql.*;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

import config.TestSetting;

/*
 * Select example:
 * 

			dbutil.getConnection();

			String sqlstr = "select * from  [CSTTest].[dbo].[ORD_Order]  where FK_MDM_Project = 51 and IsDelete = 0 and PK_ORD_Order in (?,94726)";
			String[] param = new String[1];
			param[0] = "94725";
			Collection retCol = dbutil.doQuery(sqlstr,param);
			
			Iterator it = retCol.iterator();
			System.out.println(retCol.size());
			while (it.hasNext()) {
				Hashtable ht = (Hashtable)it.next();
				String strCnt = (String) ht.get("pk_ord_order");
				System.out.println(strCnt);
			}

			dbutil.doRelease();

*
* update example:
			try {
			String sqlstr1 = "update  [CSTTest].[dbo].[ORD_Order] set IsDelete = 1 where FK_MDM_Project = 51 and IsDelete = 0 and PK_ORD_Order in (?)";
			String[] param1 = new String[1];
			param1[0] = "94725";

				int ret = dbutil.doUpdate(sqlstr1, param1);
				if (ret >= 0) {
					dbutil.doCommit();
				} else {
					dbutil.doRollback();
				}
			} catch (SQLException e) {
				dbutil.doRollback();
				throw e;
			} finally {
				try {
					dbutil.doRelease();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					throw e;
				}
			}
*
*
*/

public class DBUtil {
	Connection conn;
    PreparedStatement pStmt = null;
    ResultSet rs = null;

	String dburl = "";
	String dbuser = "";
	String dbpass = "";
	String driverName = "";
	public DBUtil() {
		super();
		this.dburl = TestSetting.Dburl;
		this.dbuser = TestSetting.Dbuser;
		this.dbpass = TestSetting.Dbpass;
		this.driverName = TestSetting.DriverName;
	}
	
    public void getConnection() throws Exception {

		Class.forName(driverName).newInstance();
		conn = DriverManager.getConnection(dburl, dbuser, dbpass);

    	conn.setAutoCommit(false);
    }

    public Collection<Hashtable<String, String>> doQuery(String strSql, String[] params) throws SQLException {
        Vector<Hashtable<String, String>> retVector = new Vector<Hashtable<String, String>>();

        try {
			pStmt = conn.prepareStatement(strSql);
			if (params != null) {
		        for (int ir = 0; ir < params.length; ir++) {
		            pStmt.setString(ir+1, params[ir]);
		        }
			}
	        rs = pStmt.executeQuery();

	        while (rs.next()) { 
	            //初始化Hash表 
	            Hashtable<String, String> ht = new Hashtable<String, String>(); 
	            //获得数据 
	            ResultSetMetaData   md   =   rs.getMetaData();
	            //将ResultSet转化成Hash数据 
	            for(int i=1; i <= md.getColumnCount(); i++){
	                //数据类型转化 
	                String fieldValueStr = " ";
	                String ColumnName = md.getColumnName(i); 
	                switch (md.getColumnType(i)) { 
	                    case Types.VARCHAR://字符串类型 
	                         fieldValueStr = rs.getString(i); 
	                         break; 
	                    case Types.DATE://日期类型 
	                         java.sql.Date dtmp = rs.getDate(i); 
	                         fieldValueStr = dtmp.toString(); 
	                         break; 
	                    case Types.NUMERIC://浮点数类型 
	                         fieldValueStr = rs.getString(i); 
	                         break; 
	                    default : 
	                         fieldValueStr = rs.getString(i); 
	                         break; 
	                } 
	                if (fieldValueStr == null) {
	                	fieldValueStr = " ";
	                }
	                //生成Hash数据 
	                ht.put(((String)ColumnName).toLowerCase(),(String)fieldValueStr); 
	            } 
	            //添加数组 
	            retVector.add(ht); 
	        } 
		
	        return   retVector;
		} catch (SQLException e) {
			conn.rollback();
			throw e;
		} finally {
			rs.close();
			pStmt.close();
		}
    }
    public int doUpdate(String strSql, String[] params) throws SQLException {
        
        try {
            int ret = 0;

            pStmt = conn.prepareStatement(strSql);
			if (params != null) {
		        for (int ir = 0; ir < params.length; ir++) {
		            pStmt.setString(ir+1, params[ir]);
		        }
			}
			ret = pStmt.executeUpdate();

	        return   ret;
		} catch (SQLException e) {
			conn.rollback();
			throw e;
		} finally {
			pStmt.close();
		}
    }

    public void doCommit() throws SQLException {
    	conn.commit();
    }

    public void doRollback() throws SQLException {
    	conn.rollback();
    }
    
    public void doRelease() throws SQLException {
    	conn.close();
    }
    
 
}
