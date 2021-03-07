package server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

public class Receiver extends Thread {

	private InputStream is;
	private boolean isAlive = true;
	private String[] c = { "remoteIpconfig", "interface", "whatTimeIsIt", "RTT", "speed" };
	TCPConnection tcp = TCPConnection.getInstance(0);

	public Receiver(InputStream is) {

		this.is = is;

	}

	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));

			while (isAlive) {

				String line = reader.readLine();

				if (line == null) {

					is.close();

				}

				System.out.println(line);

				if (line.equals(c[0])) {

					getIp();

				} else if (line.equals(c[1])) {

					getInterface();

				} else if (line.equals(c[2])) {

					whatTimeIsIt();

				} else if (line.length() == 1024 && line.charAt(0) == 'R' && line.charAt(1) == 'T'
						&& line.charAt(2) == 'T' && line.getBytes().length == 1024) {

					long fin = System.currentTimeMillis();

					double tiempo = (fin - tcp.getSender().getS());

					System.out.println("EL TIEMPO DE IDA Y VUELTA DEL MENSAJE ES DE: " + tiempo + " MILISEGUNDOS");

				} else if (line.length() == 8192 && line.charAt(0) == 's' && line.charAt(1) == 'p'
						&& line.charAt(2) == 'e' && line.charAt(3) == 'e' && line.charAt(4) == 'd'
						&& line.getBytes().length == 8192) {

					long fin = System.currentTimeMillis();

					double tiempo = fin - tcp.getSender().getS();

					double velocidad = 8192 / tiempo;

					System.out.println("LA VELOCIDAD DE TRANSMISION ES DE: " + velocidad + " KB/s");

				}
			}

		} catch (

		Exception e) {

			e.printStackTrace();

		}
	}

	public void getIp() throws UnknownHostException {

		tcp.sendMessage(InetAddress.getLocalHost().getHostAddress());

	}

	public void getInterface() throws SocketException {

		Enumeration<NetworkInterface> network = NetworkInterface.getNetworkInterfaces();

		NetworkInterface net = null;
		String inter = "";

		while (network.hasMoreElements()) {

			net = network.nextElement();

			if (net.isUp()) {

				inter += net.getName() + "\n";

			}

		}

		tcp.sendMessage(inter);

	}

	public void whatTimeIsIt() {

		Calendar calendario = Calendar.getInstance();

		Date fecha = calendario.getTime();

		tcp.sendMessage(fecha.toString());

	}

	public void RTT(String line) {

		tcp.sendMessage(line);

	}

	public void speed(String line) {

		tcp.sendMessage(line);

	}

}
