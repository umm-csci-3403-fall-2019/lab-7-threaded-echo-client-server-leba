package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException {
		EchoClient client = new EchoClient();
		client.start();
	}

	private void start() throws IOException {
		Socket socket = new Socket("localhost", PORT_NUMBER);
		InputStream socketInputStream = socket.getInputStream();
		OutputStream socketOutputStream = socket.getOutputStream();

		// Put your code here.

		Thread inputThread = new Thread(){
			public void run(){
				try{
					int readByte;
					while ((readByte = System.in.read()) != -1) {
						// reading each byte
						socketOutputStream.write(readByte);
					}
					socketOutputStream.flush();
					socket.shutdownOutput();
					// Shuting down the output once we are done reading
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		};

		inputThread.start();
		Thread outputThread = new Thread(){
			public void run(){
				int sockByte;
				try {
					while ((sockByte = socketInputStream.read()) != -1) {
						System.out.write(sockByte);
					}
					System.out.flush();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		outputThread.start();
	}
}