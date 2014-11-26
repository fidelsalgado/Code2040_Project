import java.net.*;
import java.io.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;

public class Code2040_Project {

	public static void main(String[] args) throws Exception {
		//registering
		String token = register();
		System.out.println("Token unique identifier: " + token + ".");
		System.out.println();
		
		//Stage 1 Reverse a String
		String str = getStringToReverse(token);
		System.out.println("------Stage 1 Reverse a String------");
		System.out.println("String to reverse: " + str);
		String reverseStr = reverseString(str);
		System.out.println("String reversed: " + reverseStr);
		System.out.println("Submitting result...");
		submitStringReversed(token, reverseStr);
		System.out.println("Result submitted.");
		System.out.println("-------Stage 1 DONE-------");
		System.out.println();
		
		//GRADES
		String grades = getGrades(token);
		System.out.println(grades);
	}
	
	public static String getGrades(String token) throws Exception {
		JSONObject dictionary = new JSONObject();
		dictionary.put("token", token);
		
		URL url = new URL("http://challenge.code2040.org/api/status");
		JSONObject jsonObject = getData(dictionary, url);	
		return (String) jsonObject.get("result").toString();
	}
	
	public static void submitStringReversed(String token, String revStr) throws Exception{
		JSONObject dictionary = new JSONObject();
		dictionary.put("token", token);
		dictionary.put("string", revStr);
		
		URL url = new URL("http://challenge.code2040.org/api/validatestring");
		//submitData(dictionary, url);
		JSONObject jsonObject = getData(dictionary, url);	
		String str = (String) jsonObject.get("result");
		System.out.println(str);
	}
	
	public static String reverseString(String str) {
		String revStr = "";
		for(int i = str.length() - 1; i >= 0; i--) {
			revStr += str.charAt(i);
		}		
		return revStr;
	}
	
	public static String getStringToReverse(String token) throws Exception {
		JSONObject dictionary = new JSONObject();
		dictionary.put("token", token);
		
		URL url = new URL("http://challenge.code2040.org/api/getstring");
		JSONObject jsonObject = getData(dictionary, url);	
		return (String) jsonObject.get("result");
	}
	
	public static String register() throws Exception {
		//Creating JSON Object
		JSONObject dictionary = new JSONObject();
		dictionary.put("email", "fidels@stanford.edu");
		dictionary.put("github", "https://github.com/fidels7/Code2040_Project.git");

		//HTTP Request
		URL url = new URL("http://challenge.code2040.org/api/register");
		JSONObject jsonObject = getData(dictionary, url);	
		return (String) jsonObject.get("result");
	}
	
	public static JSONObject getData(JSONObject dictionary, URL url) throws Exception {
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
		return jsonObject;
	}
	
	public static void submitData(JSONObject dictionary, URL url) throws Exception {
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

		System.out.println(dictionary);
		writer.write(dictionary.toString());
		writer.flush();

		//Closing streams
		writer.close();
	}
}