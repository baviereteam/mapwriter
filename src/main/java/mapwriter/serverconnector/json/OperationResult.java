package mapwriter.serverconnector.json;

import com.google.gson.JsonObject;

public class OperationResult {
	private boolean result = false;
	private String errorMessage = "";
	private Object resultingObject = null;
	
	private JsonObject jsonObject = null;
	
	public boolean getResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public Object getResultingObject(Class<?> expectedClass) {
		if(resultingObject == null) {
			resultingObject = JsonConverter.getInstance()
					.getAsObject(jsonObject.get("resultingObject"), expectedClass);
		}
		
		return resultingObject;
	}
	public void setResultingObject(Object resultingObject) {
		this.resultingObject = resultingObject;
	}
	
	public OperationResult(JsonObject jsonObject) {
		this.jsonObject = jsonObject;
		
		this.result = jsonObject.get("result").getAsBoolean();
		this.errorMessage = jsonObject.get("errorMessage").getAsString();
	}
}
