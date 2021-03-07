package client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPConnection {

	private static TCPConnection instance = null;

	private TCPConnection(int puerto) {
		try {
			server = new ServerSocket(puerto);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public synchronized static TCPConnection getInstance(int port) {
		if (instance == null) {
			instance = new TCPConnection(port);
		}
		return instance;
	}

	private Socket socket;
	private ServerSocket server;
	private Receiver receiver;
	private Sender sender;

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	public Sender getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	// Metodo del servidor
	public void waitForConnection(int puerto) {
		try {
			System.out.println("Esperando cliente");
			socket = server.accept();
			System.out.println("Cliente conectado!");

			receiver = new Receiver(socket.getInputStream());
			receiver.start();

			sender = new Sender(socket.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void connect(String ip, int port) {
		try {
			socket = new Socket(ip, port);

			receiver = new Receiver(socket.getInputStream());
			receiver.start();

			sender = new Sender(socket.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String msg) {

		sender.sendMessage(msg);
		;

	}

	public void closeConnection() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
