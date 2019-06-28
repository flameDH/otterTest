package controllers.user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserHandler {

	/** 
	 * 將所有相符的pattern照順序檢驗 找到坑就塞下去
	 * @param 
	 * @return
	 * @throws JSONException
	 */
	
	public String processId(String userName,JSONArray data) throws JSONException {
		int count=0;
		
		JSONObject first=data.getJSONObject(count++);
		if (!userName.equals(first.getString("USER_NAME")))
			return userName;
		
		for(;count<data.length();count++) {
			JSONObject temp=data.getJSONObject(count);
			String tempName = userName+String.valueOf(count);
			if(!(tempName.equals(temp.getString("USER_NAME"))))
				return tempName;
		}
		
		return userName+String.valueOf(count);
	}
}
