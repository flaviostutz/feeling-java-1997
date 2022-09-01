import java.awt.*;
import java.applet.Applet;

public class FlyTogether extends Applet implements Runnable {

	Image offImage;
	Dimension offDimension;
	Graphics offGraphics;
	Dimension d;

	boolean suspended, parx, pary;
	boolean fly = true;
	boolean first = true;
	int equivx;
	int equivy;
	int difx, dify, olddifx, olddify;
	int distance;
	int newx, newy, oldx, oldy = 15;
	int i, x, y;
	Thread t = null;
	int numberx[];
	int numbery[];
	char letters[];
	String text;
	
	public void init() {
		suspended = false;
		distance = 18;
		text = "This is JAVA!";
		letters = new char[text.length()];
		numberx = new int[text.length()];
		numbery = new int[text.length()];
		setBackground(Color.white);
		setFont(new Font("Arial",Font.BOLD,distance * 2));
	}

	public void run() {
	while (t != null) {
			try {
				t.sleep(100);
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
		t.stop();
		t = null;
	}

	public void paint(Graphics g) {
		update(g);
	}

	public void update(Graphics g) {
		if(first) {
			for (i = 0; i < text.length(); i ++) {
				numberx[i] = (int) (i * distance);
				numbery[i] = (int) (distance);
				first = false;
			}
			text.getChars(0, text.length(), letters, 0);
		}			

		d = size();
		
		if ( (offGraphics == null) || (d.width != offDimension.width) || (d.height != offDimension.height) ) {
			offDimension = d;
			offImage = createImage(d.width, d.height);
			offGraphics = offImage.getGraphics();
		}

		offGraphics.setColor(getBackground());
		offGraphics.fillRect(0, 0, d.width, d.height);
		offGraphics.setFont(new Font("Arial",Font.BOLD,distance * 2));
		offGraphics.setColor(Color.black);

		//if ((numberx[0] - numberx[1] >= 10) || (numbery[0] - numbery[1] >= 10)) fly = false;

		if(!fly) {
			for (i = 0; i < text.length(); i ++) {
				if(i != 0) {
					if(numberx[i] != (oldx + (i * distance))) {
						if(numberx[i] > (oldx + (i * distance))) numberx[i] = (int) (numberx[i] - equivx); 
						else if(numberx[i] < (oldx + (i * distance))) numberx[i] = (int) (numberx[i] + equivx + 1); 
					}
					if(numbery[i] != oldy) {
						if(numbery[i]  > oldy) numbery[i] = (int) (numbery[i] - equivy);
						else if(numbery[i] < oldy) numbery[i] = (int) (numbery[i] + equivy);
					}
				}
				offGraphics.drawChars(letters, i, 1, numberx[i], numbery[i]);
			}
		}

		else {
			for (i = 0; i < text.length(); i ++) {
				if(i == 0) {
					numberx[0] = (int) (oldx + ((newx - oldx)/10));
					numbery[0] = (int) (oldy + ((newy - oldy)/10));
				}
				else {
					if ((((numberx[i] - numberx[i - 1])/5) == 0) && (((numbery[i] - numbery[i - 1])/5) == 0)) {
						if ((numberx[i] - numberx[i - 1]) > 0) numberx[i] --;
						else if ((numberx[i] - numberx[i - 1]) < 0) numberx[i] ++;
						if ((numbery[i] - numbery[i - 1]) > 0) numbery[i] --;
						else if ((numbery[i] - numbery[i - 1]) < 0) numbery[i] ++;
					}
					else {
						numberx[i] = (int) (numberx[i] - ((numberx[i] - numberx[i - 1])/5));
						numbery[i] = (int) (numbery[i] - ((numbery[i] - numbery[i - 1])/5));
					}
				}
				offGraphics.drawChars(letters, i, 1, numberx[i], numbery[i]);
			}
		}
		/*if((numberx[text.length() - 1] == text.length() * distance) && (oldy == numbery[text.length() - 1])) {
		if (numberx[text.length() -1] - numberx[text.length() - 2] == 1) {
			t.suspend();
			suspended = true;
		}
		for (i = 0; i < text.length(); i ++) {
			offGraphics.drawString(numberx[i] + ", ", i * 26, 60);
			offGraphics.drawString(numbery[i] + ", ", i * 26, 75);
		}*/
		g.drawImage(offImage, 0, 0, this);
		oldx = numberx[0];
		oldy = numbery[0];
	}

	public boolean mouseDown(Event e, int x, int y) {
		if (suspended) {
			t.resume();
			suspended = false;
		}
		fly = true;
		this.newx = x;
		this.newy = y;
		return true;
	}
}

