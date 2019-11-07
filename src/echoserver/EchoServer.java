package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServer server = new EchoServer();
		server.start();
	}

	private void start() throws IOException, InterruptedException {
		ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
		while (true) {
			Socket socket = serverSocket.accept();
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();

			// Put your code here.
			Thread t = new Thread(new ServerThreader(inputStream, outputStream, socket));
			t.start();
		}
	}

}

class ServerThreader implements Runnable {
	private InputStream input;
	private OutputStream output;
	private Socket socket;


	public ServerThreader(InputStream input, OutputStream output, Socket socket) {
		this.input = input;
		this.output = output;
		this.socket = socket;

	}
	public void run() {
		try {
			int i;
			while ((i = input.read()) != -1) {
				output.write(i);
			}
			socket.shutdownOutput();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}