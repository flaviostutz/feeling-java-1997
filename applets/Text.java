import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class Text extends Applet implements Runnable {

	Image offImage;
	Dimension offDimension;
	Graphics offGraphics;
	Dimension d;

	int color;
	int sizedx;
	int sizedy;
	int i;
	Thread t = null;
	char letters[];
	Color colour = new Color(200, 150, 90);
	String text;
	
	public void init() {
		setBackground(Color.white);
		text = "Estou só testando....";
		letters = new char[text.length()];
		setFont(new Font("TimesRoman",Font.BOLD,36));
	}

	public void run() {
		while (t != null) {
			try {
				t.sleep(200);
			}
			catch (InterruptedException e) {}
			repaint();
		}
		t = null;
	}
	
	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}

	public void stop() {
		t = null;
	}

	public void paint(Graphics g) {
		update(g);
	}

	public void update(Graphics g) {
		text.getChars(0, text.length(), letters, 0);

		d = size();
		
		if ( (offGraphics == null) || (d.width != offDimension.width) || (d.height != offDimension.height) ) {
			offDimension = d;
			offImage = createImage(d.width, d.height);
			offGraphics = offImage.getGraphics();
		}

		offGraphics.setColor(getBackground());
		offGraphics.fillRect(0, 0, d.width, d.height);

		offGraphics.setColor(Color.black);
		for (i = 0; i < text.length(); i ++) {
			sizedx = (int) (Math.random() * 6 + 20 * i);
			sizedy = (int) (Math.random() * 6 + 50);
			offGraphics.setColor(randomColor());
			offGraphics.drawChars(letters, i, 1, sizedx, sizedy);					
		}
		g.drawImage(offImage, 0, 0, this);
		
	}

	public Color randomColor() {
		color = (int) (Math.random() * 10);
		switch (color) {
			case 1:
				return Color.yellow;
			case 2:
				return Color.blue;
			case 3:
				return Color.lightGray;
			case 4:
				return Color.orange;
			case 5:
				return Color.pink;
			case 6:
				return Color.green;
			case 7:
				return Color.magenta;
			case 8:
				return Color.cyan;
			case 9:
				return colour;
			case 10:
				return Color.darkGray;
		}
		return Color.red;
	}

}

