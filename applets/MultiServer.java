import java.io.*;
import java.net.*;

public class MultiServer {
	
	public static void main(String[] arg) throws IOException {
		ServerSocket listenSocket = null;
		try {
			listenSocket = new ServerSocket(Integer.parseInt(arg[0]));
		} catch (IOException e) {
				System.out.println("Erro ao criar o socket para a porta " + arg[0]);
			}

		while (true)
			new eachConnection(listenSocket.accept()).start();
	}
}

