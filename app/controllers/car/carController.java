package controllers.car;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import enums.ErrorCode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import util.ResponseHelper;

public class carController extends Controller {
	
	private carModel m_model;
	
	@Inject
	public carController(carModel model) {
		m_model=model;
	}
	
	public Result insertData() {
		JsonNode json = request().body().asJson();
		try {
			String brand = json.get("brand").asText();
			String color = json.get("color").asText();
			String city = json.get("city").asText();
			String time = getTime();
			
			if(!m_model.insertData(brand, color, city,time))
				return ok(ResponseHelper.genErrorResult(ErrorCode.PARAM_ERR));
			
			return ok(ResponseHelper.genOKResult());
		}
		catch(Exception e) {
			System.out.println(e);
			return ok(ResponseHelper.genErrorResult(ErrorCode.SYS_ERR));
		}
	}
	
	
	private String getTime() {
		SimpleDateFormat m_fullsdf = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		return m_fullsdf.format(new Date());
	}
}	
