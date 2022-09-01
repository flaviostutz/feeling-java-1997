import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class eachConnection extends Thread {
	PrintWriter out;
	BufferedReader in;
	Socket socket;
	
	public eachConnection(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
				System.out.println("Erro ao abrir o stream do socket");
		}
	}
}

