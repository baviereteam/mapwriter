package mapwriter.serverconnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mapwriter.Mw;
import mapwriter.map.Marker;
import mapwriter.serverconnector.json.OperationResult;

public class MarkerInterface {
	private String lastGetError = "";
	
	public List<Marker> list() throws Exception {
		if (Mw.instance.serverId == 0) {
			throw new Exception ("No server selected !");
		}

		// Only parameter required is the master key
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("userKey", Mw.instance.serverKey);

		try {
			// Execute the server command
			OperationResult result = WebConnector.getInstance().execute(
					"markers/" + Mw.instance.serverId + "/list", parameters);

			StringBuilder sb = new StringBuilder();

			// Operation success
			if (result.getResult()) {
				sb.append("Execution success.\n");

				List<Marker> markers = (List<Marker>) result.getResultingObject(Marker.class);
				return markers;
			}

			// Operation failed on server
			else {
				sb.append("Execution failed.\n");
				sb.append("Server answered: ");
				sb.append(result.getErrorMessage());
			}

			throw new Exception(sb.toString());
		}

		catch (Exception e) {
			throw e;
		}
	}
	
	private Marker get(int markerId) {
		this.lastGetError = "";
		
		// Only parameter required is the master key
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("userKey", Mw.instance.serverKey);
		
		Marker marker = null;
		try {
			// Execute the server command
			OperationResult result = WebConnector.getInstance().execute(
				"markers/get/" + markerId, parameters);
		
			// Operation success
			if(result.getResult()) {
				marker = (Marker) result.getResultingObject(Marker.class);
			}
		}
		
		catch(Exception e)  {
			this.lastGetError = e.getMessage();
		}
		
		return marker;
	}
	
	public int add(int color, int dimension, String group, String name, int x, int y, int z) throws Exception {
		if (Mw.instance.serverId == 0) {
			throw new Exception ("No server selected !");
		}
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("userKey", Mw.instance.serverKey);
		parameters.put("color", ((Integer)color).toString() );
		parameters.put("dimension", ((Integer)dimension).toString() );
		parameters.put("group", group);											
		parameters.put("name", name);
		parameters.put("x", ((Integer)x).toString() );
		parameters.put("y", ((Integer)y).toString() );
		parameters.put("z", ((Integer)z).toString() );
		
		try {
			// Execute the server command
			OperationResult result = WebConnector.getInstance().execute(
					"markers/" + Mw.instance.serverId + "/add", parameters);
		
			StringBuilder sb = new StringBuilder();
			
			// Operation success
			if(result.getResult()) {
				// All right ! return the ID
				Marker m = (Marker) result.getResultingObject(Marker.class);
				return m.getId();
			}
			
			// Operation failed on server
			else {
				sb.append("Execution failed.\n");
				sb.append("Server answered: ");
				sb.append(result.getErrorMessage());
				
				throw new Exception(sb.toString());
			}
			
		}
		
		catch(Exception e)  {
			throw e;
		}
	}
	
	public void edit(int id, Integer color, Integer dimension, String group, String name, Integer x, Integer y, Integer z) throws Exception {
		if (Mw.instance.serverId == 0) {
			throw new Exception ("No server selected !");
		}
		
		// Extract the previous marker
		Marker marker = this.get(id);
		if(marker == null) {
			throw new Exception ("Could not extract the selected marker: " + this.lastGetError);
		}
		
		// Map properties. Null parameters will keep the previous values. All others will be replaced.
		if(color != null) {
			marker.colour = color;
		}
		
		if(dimension != null) {
			marker.dimension = dimension;
		}
		
		if(group != null) {
			marker.groupName = group;
		}
		
		if(name != null) {
			marker.name = name;
		}

		if(x != null) {
			marker.x = x;
		}
		
		if(y != null) {
			marker.y = y;
		}
		
		if(z != null) {
			marker.z = z;
		}
		
		// Business starts now
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("userKey", Mw.instance.serverKey);
		parameters.put("color", ((Integer) marker.colour).toString() );
		parameters.put("dimension", ((Integer) marker.dimension).toString() );
		parameters.put("group", marker.groupName);											
		parameters.put("name", marker.name);
		parameters.put("x", ((Integer) marker.x).toString() );
		parameters.put("y", ((Integer) marker.y).toString() );
		parameters.put("z", ((Integer) marker.z).toString() );
		
		try {
			// Execute the server command
			OperationResult result = WebConnector.getInstance().execute(
					"markers/edit/" + id, parameters);
		
			StringBuilder sb = new StringBuilder();
			
			// Operation success
			if(result.getResult()) {
				// All right !
			}
			
			// Operation failed on server
			else {
				sb.append("Execution failed.\n");
				sb.append("Server answered: ");
				sb.append(result.getErrorMessage());
				
				throw new Exception(sb.toString());
			}
		}
		
		catch(Exception e)  {
			throw e;
		}
	}
}
