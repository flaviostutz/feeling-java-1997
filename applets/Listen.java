/*
 * Listen
 *
 * Sit on a port and echo any text that comes in.
 *
 *
 * 7.1.96 MatK <ripper@roomfullaspies.com>
 */

import java.io.*;

public class Listen extends java.lang.Object {

  public final static String USAGE = "usage: java Listen <port>";


  public static void main(String[] args) {
    int port;

    if (args.length == 1) {
        try {
            port = Integer.parseInt(args[0]);
            Lserver s = new Lserver(port);
        } catch (NumberFormatException e) {
            System.out.println("Port number format error.");
        }
    } else {
        System.out.println(USAGE);
    }
  }


}
