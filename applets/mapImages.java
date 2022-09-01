import java.net.URL;
import java.applet.*;
import java.applet.Applet;
import java.awt.*;
import java.util.*;

public class mapImages extends Applet {

	String string;
	int x, y;
	Image background, pic;

	public void init() {
		x = 0;
		y = 0;
		background = getImage(getCodeBase(), getParameter("image"));
		pic = getImage(getCodeBase(), getParameter("pic"));
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.red);
		g.drawImage(background, 0, 0, this);
		g.drawImage(pic, this.x, this.y, this);
		g.drawString(string, 0, 100);
	}
	
	public boolean mouseDrag(Event e, int x, int y) {
		this.x = x;
		this.y = y;
		this.string = "("+x+", "+y+")";
		repaint();
		return true;
	}
}


