import java.applet.Applet;
import java.awt.*;

public class TextFly extends Applet implements Runnable {

	Image offImage;
	Dimension offDimension;
	Graphics offGraphics;
	Dimension d;

	boolean suspended, parx, pary;
	boolean fly = true;
	boolean first = true;
	long equivx;
	long equivy;
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
		text = "Flávio Stutz...";
		letters = new char[text.length()];
		numberx = new int[text.length()];
		numbery = new int[text.length()];
		setBackground(Color.white);
		setFont(new Font("ArialBlack",Font.BOLD,distance * 2));
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

		offGraphics.setColor(Color.black);

		difx = oldx - newx;
		dify = oldy - newy;

		if(difx < 0) difx *= (-1);
		if(dify < 0) dify *= (-1);

		if((difx != 0) && (dify != 0)) {
			equivx = difx / dify + 1;
			equivy = dify / difx + 1;
		}
		else {
			equivx = 2;
			equivy = 1;
		}
				
		if ((difx <= 11) || (dify <= 11)) fly = false;

		if(!fly) {
			for (i = 0; i < text.length(); i ++) {
				if(i != 0) {
					if(numberx[i] != (oldx + (i * distance))) {
						if(numberx[i] > (oldx + (i * distance))) numberx[i] = (int) (numberx[i] - equivx); 
						else if(numberx[i] < (oldx + (i * distance))) numberx[i] = (int) (numberx[i] + equivx + 1); 
					}
					if(numbery[i] != oldy) {
						if(numbery[i]  > oldy) numbery[i] = (int) (numbery[i] - equivy);
						else if(numbery[i] < oldy) numbery[i] = (int) (numbery[i] + equivy + 1);
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
					numberx[i] = (int) (numberx[i] - ((numberx[i] - numberx[i - 1])/5));
					numbery[i] = (int) (numbery[i] - ((numbery[i] - numbery[i - 1])/5));
				}
				offGraphics.drawChars(letters, i, 1, numberx[i], numbery[i]);
			}
		}
		if((numberx[text.length() - 1] == text.length() * distance) && (oldy == numbery[text.length() - 1])) {
			stop();
			suspended = true;
		}
		
		g.drawImage(offImage, 0, 0, this);
		oldx = numberx[0];
		oldy = numbery[0];
	}

	public boolean mouseDown(Event e, int x, int y) {
		if (suspended) {
		start();
		suspended = false;
		}
		fly = true;
		this.newx = x;
		this.newy = y;
		return true;
	}
}

