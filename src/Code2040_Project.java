import java.net.*;
import java.io.*;

public class Code2040_Project {

	public static void main(String[] args) throws Exception {
		URL url = new URL("http://challenge.code2040.org/api/register");
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

		writer.write("value=1&anotherValue=1");
		writer.flush();
		String line;
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		writer.close();
		reader.close();

	}
}