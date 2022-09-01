import java.util.*;
import java.text.DateFormat;
import java.applet.Applet;
import java.awt.*;

public class Clock extends Applet implements Runnable {

	Dimension offDimension;
	Graphics offGraphics;
	Image offImage;
	Dimension d;
	Thread thread;
	
	int i, hour, min, sec, centreX, centreY, width, height, x, y;
	int adjustX, adjustY;
	Math math;
	Date date;
	Calendar calendar;

	public void init() {
		x = Integer.valueOf(getParameter("x")).intValue();
		y = Integer.valueOf(getParameter("y")).intValue();
		width = Integer.valueOf(getParameter("widthX")).intValue();
		height = Integer.valueOf(getParameter("heightY")).intValue();
		adjustX = Integer.valueOf(getParameter("adjustX")).intValue();
		adjustY = Integer.valueOf(getParameter("adjustY")).intValue();
		
		centreX = width/2 + x;
		centreY = height/2 + y;
		thread = new Thread(this);
		setBackground(Color.white);
	}

	public void start() {
		if (thread != null) {
			thread.start();
		}
		else thread = new Thread(this);
	}

	public void run() {
		while(thread != null) {
			try { thread.sleep(1000);} catch (InterruptedException e) {}
			repaint();
		}
	}
	
	public void stop() {
		if (thread != null) {
			thread.stop();
			thread = null;
		}
	}

	public void paint(Graphics g) {
		update(g);
	}

	public void update(Graphics g) {
		date = new Date();

		d = size();

		if ( (offGraphics == null) || (d.width != offDimension.width) || (d.height != offDimension.height) ) {
			offDimension = d;
			offImage = createImage(d.width, d.height);
			offGraphics = offImage.getGraphics();
		}
		offGraphics.setColor(getBackground());
		offGraphics.fillRect(0, 0, d.width, d.height);
		offGraphics.setColor(new Color(0, 0, 0));
		drawClock(offGraphics);
		g.drawImage(offImage, 0, 0, this);
	}

	public void drawClock(Graphics g) {
		hour = date.getHours();
		min = date.getMinutes();
		sec = date.getSeconds();
		
		g.drawOval(x, y, width, height);
		g.drawOval(x + 1, y, width, height);
		g.drawOval(x + 1, y + 1, width, height);
		g.drawOval(x, y + 1, width, height);
		g.drawLine(centreX, centreY, centreX, centreY);

		g.setFont(new Font("verdana", Font.BOLD, (width + height)/21));
		for (i = 1; i <= 12; i ++) {
			g.drawString(Integer.toString(i), centreX + adjustX + cos(i * 30 - 90), centreY + adjustY + sen(i * 30 - 90));
		}
		
		for (i = 0; i < 2; i ++) g.drawLine(centreX + i, centreY + i, centreX + cos(sec * 6 - 90), centreY + sen(sec * 6 - 90));
		for (i = 0; i < 5; i ++) {
			g.drawLine(centreX + i, centreY + i, centreX + cos(hour * 30 - 90 + 5 * min / 12)*2/3, centreY + sen(hour * 30 - 90 + 5 * min / 12)*2/3);
			g.drawLine(centreX + i, centreY + i, centreX + cos(min * 6 - 90 + sec * 6 / 60)*7/8, centreY + sen(min * 6 - 90 + sec * 6 / 60)*7/8);
		}
		
		g.drawString(hour + ":" + min + ":" + sec, 20, 20);		
	}

	public int sen(int angle) {
		return (int)(math.sin(math.PI * angle/180)*width*8/18);
	}

	public int cos(int angle) {
		return (int)(math.cos(math.PI * angle/180)*height*8/18);
	}
	
}

