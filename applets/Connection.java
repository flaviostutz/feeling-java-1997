

  /**
   * class Connection.  This handles all the communication between
   * the client and the server.  You need to override the run
   * method to make this do something.
   *
   * Shamelessly based on example code in the O'Reilly Nutshell book.
   *
   * 7.1.96 MatK <ripper@roomfullaspies.com>
   */

import java.io.*;
import java.net.*;


abstract public class Connection extends Thread {

  // Socket for this connection
  protected Socket client;

  // Input Stream
  protected DataInputStream in;

  // Output Stream
  protected PrintStream out;


  /**
   * Construct a connection.  Input and output streams are
   * connected to the given socket.
   * @param client_socket a socket to connect to
   */
  public Connection(Socket client_socket) {
    client = client_socket;
    try {
      in = new DataInputStream(client.getInputStream());
      out = new PrintStream(client.getOutputStream());
    }
    catch (IOException e) {
      try {
	      client.close();
      } catch (IOException e2) { }
        System.err.println("Exception while getting socket streams: " + e);
        return;
      }
    this.start();
  }

  /**
   * Provide the server service.  If the connection has been
   * set up, then you can use "in" and "out" to read and write
   * to the connection.
   */
  abstract public void run();
}


