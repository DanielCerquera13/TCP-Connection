package client;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Sender {

	private OutputStream os;
	private long s;
	private String rtt = "RTT";
	private String speed = "speed";

	public long getS() {
		return s;
	}

	public void setS(long s) {
		this.s = s;
	}

	public Sender(OutputStream os) {

		this.os = os;

		for (int i = 0; i < 1021; i++) {

			rtt += ".";

		}

		for (int i = 0; i < 8187; i++) {

			speed += ".";

		}

	}

	public void sendMessage(String msg) {

		new Thread() {

			@Override
			public void run() {

				BufferedWriter bwriter = new BufferedWriter(new OutputStreamWriter(os));

				if (msg.equals("RTT")) {

					s = System.currentTimeMillis();

					try {
						bwriter.write(rtt + "\n");
						bwriter.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if (msg.equals("speed")) {

					s = System.currentTimeMillis();

					try {
						bwriter.write(speed + "\n");
						bwriter.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				else {

					try {
						bwriter.write(msg + "\n");
						bwriter.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

		}.start();

	}

}
