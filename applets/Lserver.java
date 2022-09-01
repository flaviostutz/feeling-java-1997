/* Lserver
 *
 * Just like a Server, but customized for my particular
 * need.
 *
 * 7.1.96 MatK <ripper@roomfullaspies.com>
*/


import java.net.*;
import java.io.IOException;


public class Lserver extends Server implements Runnable {

  /**
   * Construct a new server.
   * @param port The port number
   */
  public Lserver(int port) {
    super(port);
  }

  /**
   * Start the server (which is a Thread).  Simple cycle forever
   * waiting for clients to connect.  Fails when it recieves
   * an IOException (generally ^C).
   */
  public void run() {
    try {
      while(true) {
        Socket client_socket = listen_socket.accept();
        message("New connection: " + client_socket.toString());
        Lconnection c = new Lconnection(client_socket, this);
      }
    } catch(IOException e) {
      fail(e, "Exception while waiting for connection.\n" + e.toString());
    }
  }

} //end class
