package controllers.user;

import db.MysqlBatchHelper;
import java.sql.Connection;
import org.json.JSONArray;
import org.json.JSONObject;

public class UserModel {
	
	private MysqlBatchHelper m_mysqlHelper = new MysqlBatchHelper();
	
	public Connection getConnection() throws Exception {
		return m_mysqlHelper.getConnection(false);
	}

	public boolean startTransaction(Connection conn) throws Exception {
		return m_mysqlHelper.startTransaction(conn);
	}

	public boolean commitTransaction(Connection conn) throws Exception {
		return m_mysqlHelper.commitTransaction(conn);
	}

	public boolean rollbackTransaction(Connection conn) throws Exception {
		return m_mysqlHelper.rollbackTransaction(conn);
	}

	public boolean closeTransaction(Connection conn) throws Exception {
		return m_mysqlHelper.closeConnection(conn);
	}
	

	 /**
	  * 拉出符合 pattern的資料開始找蘿蔔塞坑
	  * @param conn
	  * @param userName
	  * @return
	  * @throws Exception
	  */
	public JSONArray checkUser(Connection conn,String userName) throws Exception {
		String sql="SELECT USER_NAME FROM USER WHERE USER_NAME REGEXP ? ORDER BY LENGTH(USER_NAME),USER_NAME";
		JSONArray params = new JSONArray();
		params.put("^"+userName+"[0-9]*$");
		return m_mysqlHelper.executeSql(conn, sql, params);
	}

	/**
	 * 新增使用者名稱
	 * @param conn 連線
	 * @param userName 使用者名稱
	 * @return
	 * @throws Exception
	 */
	public boolean insertUser(Connection conn,String userName) throws Exception {
		String sql="INSERT INTO USER(USER_NAME) VALUES(?)";
		JSONArray params = new JSONArray();
		params.put(userName);
		return m_mysqlHelper.executeNonSelectSql(conn, sql,params);
	}

	/**
	 * 拿取最後要輸出的資料 trasaction結束在做即可
	 * @param userName
	 * @return
	 * @throws Exception 
	 */
	public JSONObject getUserData(Connection conn,String userName) throws Exception {
		String sql="SELECT USER_ID,USER_NAME FROM USER WHERE USER_NAME=? limit 1";
		JSONArray params = new JSONArray();
		params.put(userName);
		
		return m_mysqlHelper.executeSql(conn,sql, params).getJSONObject(0);
	} 

}
