package server;

import java.net.UnknownHostException;
import java.util.Scanner;

public class MainServer {

	public static void main(String[] args) throws UnknownHostException {

		TCPConnection connection = TCPConnection.getInstance(5000);
		connection.waitForConnection();

		Scanner scan = new Scanner(System.in);

		while (true) {

			String mensaje = scan.nextLine();

			connection.sendMessage(mensaje);

		}

	}

}
