/*
 * Server
 *
 * A general (and simple) server class.  Heavily based
 * on the example code in the excellent O'Rielly Nutshell
 * book.
 *
 *
 * 7.1.96 MatK <ripper@roomfullaspies.com>
*/


import java.io.*;
import java.net.*;



abstract public class Server extends Thread {

  public final static int DEFAULT_PORT = 6789;
  protected int port;
  protected ServerSocket listen_socket;
  private final static String PREFIX = ">> ";



  /**
   * Display a message
   * @param s The message
   */
  protected static void message(String s) {
    System.out.println(PREFIX + s);
  }

  /**
   * Display an error message and stop the server.
   * @param e An exception
   * @param msg An explanatory message
   */
  protected static void fail(Exception e, String msg) {
    System.err.println(msg + ":" + e);
    System.err.println("\nServer terminating.");
    System.exit(1);
  }

  /**
   * Create a socket on the specified port.
   * @param port the port.
   */
  public Server(int port) {
    if (port == 0) port = DEFAULT_PORT;
    this.port = port;
    try {
      listen_socket = new ServerSocket(port);
    } catch (IOException e) {
      fail(e, "Exception creating server socket");
    }
    System.out.println("Server: listening on port " + port + "\n");
    this.start();
  }


  /**
   * This run method must be implemented.
   *
   * This is where you simple wait forever listening for client
   * connections.  Something like this:
   *
   * public void run() {
   *  try {
   *   while(true) {
   *    Socket client_socket = listen_socket.accept();
   *    Connection c = new Connection(client_socket);
   *   }
   *  } catch(IOException e) {
   *    fail(e, "Exception while listening for connections");
   *  }
   * }
   */
  abstract public void run();
}





