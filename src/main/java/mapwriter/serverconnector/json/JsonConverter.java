package mapwriter.serverconnector.json;

import java.util.ArrayList;
import java.util.List;

import mapwriter.map.Marker;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonConverter {
	// Singleton
	private static JsonConverter instance = null;
	public static JsonConverter getInstance() {
		if(instance == null) {
			instance = new JsonConverter();
		}
		
		return instance;
	}
	
	// Class
	Gson gson = new Gson();
	
	public Object getAsObject(JsonElement element, Class<?> expectedType) {
		Object output = null;
		
		if(element.isJsonArray()) {
			output = this.getAsList(element, expectedType);
		}
		
		else if(element.isJsonObject()) {
			output = this.getAsSingle(element.getAsJsonObject(), expectedType);
		}
		
		// should never happen in this case
		else if(element.isJsonPrimitive()) {
			output = element.getAsJsonPrimitive().getAsString();
		}
		
		return output;
	}
	
	private List<Marker> getAsList(JsonElement source, Class<?> expectedType) {
		JsonArray array = source.getAsJsonArray();
		List<Marker> result = new ArrayList<Marker>();
		
		for(JsonElement element : array) {
			result.add( getAsSingle(element.getAsJsonObject(), expectedType) );
		}
		
		return result;
	}
	
	private Marker getAsSingle(JsonObject object, Class<?> expectedType) {
		if (expectedType == Marker.class) {
			int id = object.get("id").getAsInt();
			String name = object.get("name").getAsString();
			String groupName = object.get("group").getAsString();
			int x = object.get("x").getAsInt();
			int y = object.get("y").getAsInt();
			int z = object.get("z").getAsInt();
			int dimension = object.get("dimension").getAsInt();
			int colour = object.get("color").getAsInt();
			
			Marker marker = new Marker(id, name, groupName, x, y, z, dimension, colour);
			return marker;
		}
		
		return null;
	}
}
