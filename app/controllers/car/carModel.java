package controllers.car;

import org.json.JSONArray;

import db.MysqlHelper;

public class carModel {
	private MysqlHelper m_mysqlHelper = new MysqlHelper();
	
	
	public boolean  insertData(String brand,String color,String city,String time) {
		String sql ="INSERT INTO carhourcount(brand,color,city,hour) "
				+ "VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE count=count+1";
		
		JSONArray params = new JSONArray();
		params.put(brand);
		params.put(color);
		params.put(city);
		params.put(time);
		
		
		return m_mysqlHelper.executeNonSelectSql(sql, params);
	}
	
}
