/*
 * An Lconnection is just like a Connection, except
 * it's tailored to the needs of the LISTEN app.
 *
 *
 * 7.1.96 MatK <ripper@roomfullaspies.com>
 */

import java.io.*;
import java.net.*;

public class Lconnection extends Connection {

  // Keep a handle on my server
  private Server myServer;


  /**
   * Construct a new connection.
   * @param client_socket the Client socket.
   * @param s a reference to my spawning server
   */
  public Lconnection(Socket client_socket, Server s) {
    super(client_socket);
    myServer = s;
  }


  /**
   * Simply print out everything that comes down
   * the input stream.
   */
  public void run() {
    String line;

    try {
      while((line = in.readLine()) != null) {
        System.out.println(line);
      }
    } catch(IOException e) {
      myServer.message("Connection closed (" + e.toString() + ")");
    } finally {
      try {
        client.close(); // Just in case...
      } catch(IOException e2) { }
    }
  }

} //end class



