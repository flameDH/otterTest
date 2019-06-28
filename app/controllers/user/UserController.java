package controllers.user;

import java.sql.Connection;

import javax.inject.Inject;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import enums.ErrorCode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import util.JSONHelper;
import util.ResponseHelper;

public class UserController extends Controller {

	private UserModel m_model;
	private UserHandler m_handler;

	/***   
	 *說明 
	 *用正規表示式拉出與註冊username相同的pattern  例如 mike5 會拉出mike51 mike52 mike53 mike 5566
	 *將結果用userName長度及userName做排序
	 *在handler中依序處理 如果照順序排到沒填的狀況即為ID 比如 mike6後面為mike8  輸入mike會得到mike7
	 *如都跑完就是結果再加1
	 *最後Insert完在來select結果retrun填值
	 *
	 *此外有用transaction lock table 同時只能有一個人處理 確保無誤
	 */
	 
	
	@Inject
	public UserController(UserModel model, UserHandler handler) {
		m_model = model;
		m_handler = handler;
	}

	public Result register() {
		JsonNode json = request().body().asJson();
		Connection conn = null;
		ObjectNode rtnNode = Json.newObject();
		try {
			
			String userName = json.get("username").asText();

			try {
				// 拿取連線
				conn = m_model.getConnection();
				m_model.startTransaction(conn);
				
				//取得
				JSONArray data = m_model.checkUser(conn, userName);
				// 計算處理ID
				if (data.length() > 0) {
					userName = m_handler.processId(userName, data);
				}

				// 新增資料並取得;
				JSONObject result = new JSONObject();
				if (m_model.insertUser(conn, userName)) {
					result = m_model.getUserData(conn, userName);
					m_model.commitTransaction(conn);
				} else {
					m_model.rollbackTransaction(conn);
				}
				//填回傳值
				rtnNode.put("userId", JSONHelper.getInt(result, "USER_ID", 0));
				rtnNode.put("userName", JSONHelper.getString(result, "USER_NAME", "FAILED"));

				return ok(ResponseHelper.genOKResult(rtnNode));
			} catch (Exception e) {
				m_model.rollbackTransaction(conn);
			}

			return ok(ResponseHelper.genErrorResult(ErrorCode.SYS_ERR));
		} catch (Exception e) {
			System.out.println(e);
			return ok(ResponseHelper.genErrorResult(ErrorCode.SYS_ERR));
		}

	}

}
