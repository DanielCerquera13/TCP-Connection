package client;

import java.io.IOException;
import java.util.Scanner;

public class MainClient {

	public static void main(String[] args) throws IOException {

		TCPConnection connection = TCPConnection.getInstance(0);

		connection.connect("127.0.0.1", 5000);

		Scanner scan = new Scanner(System.in);

		while (true) {

			String line = scan.nextLine();

			connection.sendMessage(line);

		}

	}

}
