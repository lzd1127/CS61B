import java.io.*;

class Nuke2 {
	public static void main(String[] arg) throws Exception {
		BufferedReader keyboard;
		String inputLine;

		keyboard = new BufferedReader(new InputStreamReader(System.in));
		inputLine = keyboard.readLine();
		String output = inputLine.substring(0,1) + inputLine.substring(2);

		System.out.println(output);
	}
}