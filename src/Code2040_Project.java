import java.net.*;
import java.io.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;

public class Code2040_Project {

	public static void main(String[] args) throws Exception {
		String token = register();
		System.out.println(token);
		
	}

	public static String register() throws Exception {
		//Creating JSON Object
		JSONObject dictionary = new JSONObject();
		dictionary.put("email", "fidels@stanford.edu");
		dictionary.put("github", "https://github.com/fidels7/Code2040_Project.git");

		//HTTP Request
		URL url = new URL("http://challenge.code2040.org/api/register");
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

		writer.write(dictionary.toString());
		writer.flush();
		String line;
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		//Parsing
		line = reader.readLine();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(line);
		JSONObject jsonObject = (JSONObject) obj;

		//Closing streams
		writer.close();
		reader.close();

		return (String) jsonObject.get("result");
	}
}