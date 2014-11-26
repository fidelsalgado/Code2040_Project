import java.net.*;
import java.io.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import java.util.*;


public class Code2040_Project {

	public static void main(String[] args) throws Exception {
		//registering
		String token = register();
		System.out.println("Token unique identifier: " + token + ".");
		System.out.println();
		
//		//Stage 1 Reverse a String
//		String str = getStringToReverse(token);
//		System.out.println("------Stage 1 Reverse a String------");
//		System.out.println("String to reverse: " + str);
//		String reverseStr = reverseString(str);
//		System.out.println("String reversed: " + reverseStr);
//		System.out.println("Submitting result...");
//		String result = submitStringReversed(token, reverseStr);
//		System.out.println("Result submitted.");
//		System.out.println("RESULT -->  " + result);
//		System.out.println("-------Stage 1 DONE-------");
//		System.out.println();
//		
//		//Stage 2 Needle in a haystack
//		System.out.println("------Stage 2 Needle in a Haystack------");
//		JSONObject dataForNeedleInHaystack = getNeedleInHaystackData(token);
//		int index = locateNeedleInHaystack(dataForNeedleInHaystack);
//		System.out.println("Needle is located in index: " + index);
//		System.out.println("Submitting result...");
//		result = submitNeedleIndex(token, index);
//		System.out.println("Result submitted.");
//		System.out.println("RESULT -->  " + result);
//		System.out.println("-------Stage 2 DONE-------");
//		System.out.println();
		
//		//Stage 3 
//		System.out.println("------Stage 3 Prefix------");
//		JSONObject dataForPrefix = getPrefixData(token);
//		ArrayList<String> list = new ArrayList<String>()
		
//		//GRADES
//		System.out.println("----------------GRADES-------------------");
//		String grades = getGrades(token);
//		System.out.println(grades);
	}
	
	/*
	 * ---------------------------STAGE 3 METHODS----------------------------------
	 */
//	public static String[] getArrayWithNoPrefix(JSONObject data) { 
//		
//	}
	
	public static JSONObject getPrefixData(String token) throws Exception {
		JSONObject dictionary = new JSONObject();
		dictionary.put("token", token);
		
		URL url = new URL("http://challenge.code2040.org/api/prefix");
		JSONObject jsonObject = getData(dictionary, url);	
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(jsonObject.get("result").toString());
		JSONObject result = (JSONObject) obj;
		System.out.println(result);
		
		return result;
	}
	
	/*
	 * ---------------------------STAGE 2 METHODS----------------------------------
	 */
	public static String submitNeedleIndex(String token, int index) throws Exception {
		JSONObject dictionary = new JSONObject();
		dictionary.put("token", token);
		dictionary.put("needle", index);
		
		URL url = new URL("http://challenge.code2040.org/api/validateneedle");
		JSONObject jsonObject = getData(dictionary, url);	
		String str = jsonObject.get("result").toString();
		return str;
	}
	
	public static int locateNeedleInHaystack(JSONObject data) {
		String [] haystack = data.get("haystack").toString().replace("[", "").replace("]", "").replace("\"", "").split(",");
		String needle = data.get("needle").toString();
		
		for(int i=0; i < haystack.length; i++) {
			if(haystack[i].equals(needle)) return i;
		}
		
		return 0;
	}
	
	public static JSONObject getNeedleInHaystackData(String token) throws Exception {
		JSONObject dictionary = new JSONObject();
		dictionary.put("token", token);
		
		URL url = new URL("http://challenge.code2040.org/api/haystack");
		JSONObject jsonObject = getData(dictionary, url);	
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(jsonObject.get("result").toString());
		JSONObject result = (JSONObject) obj;
		
		return result;
	}
	
	/*
	 * ---------------------------STAGE 1 METHODS----------------------------------
	 */
	public static String submitStringReversed(String token, String revStr) throws Exception{
		JSONObject dictionary = new JSONObject();
		dictionary.put("token", token);
		dictionary.put("string", revStr);
		
		URL url = new URL("http://challenge.code2040.org/api/validatestring");
		JSONObject jsonObject = getData(dictionary, url);	
		String str = jsonObject.get("result").toString();
		return str;
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
	
	/*
	 * ------------------GETTING UNIQUE IDENTIFIER-----------------------
	 */
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
	
	/*
	 * ---------------------GRADES--------------------------------
	 */
	public static String getGrades(String token) throws Exception {
		JSONObject dictionary = new JSONObject();
		dictionary.put("token", token);
		
		URL url = new URL("http://challenge.code2040.org/api/status");
		JSONObject jsonObject = getData(dictionary, url);	
		return (String) jsonObject.get("result").toString();
	}
	
	/*
	 * -------------------METHODS FOR REQUESTING/SUBMITTING DATA-------------------
	 */
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
}