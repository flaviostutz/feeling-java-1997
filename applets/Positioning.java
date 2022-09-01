import java.applet.Applet;
import java.awt.*;

public class Positioning extends Applet {

	int count = 0;
	Dimension offDimension;
	Graphics offGraphics;
	Image offImage;
	MediaTracker tracker;
	Image teste;
	Dimension d;
	int i = 0;
	int[] x, y, ox, oy;
	int px, py;
	int numClicks = 0;

	Button undo, clear, next;
	boolean nextIt = false;
	boolean show = false;
	boolean ready = false;
	boolean first = true;
	boolean already = false;

	public void init() {
		x = new int[9999];
		y = new int[9999];
		ox = new int[9999];
		oy = new int[9999];

		tracker = new MediaTracker(this);
		setBackground(Color.white);
		teste = getImage(getCodeBase(), "teste.jpg");
		tracker.addImage(teste, 0);
	}

	public void paint(Graphics g) {
		update(g);
	}

	public void update(Graphics g) {
		d = size();
		
		if (!tracker.checkAll() && !ready) {
			g.clearRect(0, 0, d.width, d.height);
			g.setColor(Color.black);
			g.drawString("Please wait while loading Images...", 0, d.height/2);

			if (already == false) {
				try {
					tracker.waitForAll();
				} 
				catch (InterruptedException e) {}
				already = true;
			}
			repaint(300);
		}
		
		else {
			if ( (offGraphics == null) || (d.width != offDimension.width) || (d.height != offDimension.height) ) {
				offDimension = d;
				offImage = createImage(d.width, d.height);
				offGraphics = offImage.getGraphics();
			}

			offGraphics.setColor(getBackground());
			offGraphics.fillRect(0, 0, d.width, d.height);
			offGraphics.setColor(Color.black);
	
			offGraphics.drawLine(0, 200, 777, 200);
			offGraphics.drawString("Next", 0, 230);
			offGraphics.drawLine(0, 250, 777, 250);
			offGraphics.drawString("Clear", 0, 280);
			offGraphics.drawLine(0, 300, 777, 300);
			offGraphics.drawString("Show points!", 0, 330);
			offGraphics.drawLine(0, 350, 777, 350);
			offGraphics.drawString("Undo", 0, 380);

			if (first) {
				ox[numClicks] = x[numClicks];
				oy[numClicks] = y[numClicks];
				if (!(numClicks == 0)) first = false;
			}
			
			for (i = 1; i <= numClicks; i ++) if (!(numClicks <= 1)) offGraphics.drawLine(ox[i], oy[i], x[i], y[i]);
			show = false;
			offGraphics.drawString(px + ", " + py, 0, 190);

			ox[numClicks + 1] = x[numClicks];
			oy[numClicks + 1] = y[numClicks];
			g.drawImage(offImage, 0, 0, this);
		}
	}

/*	public boolean mouseMove(Event e, int x, int y) {
		this.px = x;
		this.py = y;
		repaint();
		return true;
	}*/
	
	public boolean mouseDown(Event e, int x, int y) {
		this.px = x;
		this.py = y;
		verifyPositions();
		repaint();
		return true;
	}

	public void verifyPositions() {
		if(this.py > 200 & this.py <= 250) {
			numClicks ++;
			this.x[numClicks] = this.x[numClicks - 1];
			this.y[numClicks] = this.y[numClicks - 1];
			first = true;
			nextIt = true;
			repaint();
		}
		else if(this.py > 250 & this.py <= 300) {
			numClicks = 0;
			first = true;
			repaint();
		}
		else if(this.py > 300 & this.py <= 350) {
			for (i = 1; i <= numClicks; i ++) {
				count ++;
				if ((ox[i] == x[i])&&(oy[i] == y[i])) {
					System.out.println("separate[" + count + "] = true;");
					System.out.println("px[" + count + "] = " + x[i] + ";");
					System.out.println("py[" + count + "] = " + y[i] + ";");
				}
				else {
					System.out.println("px[" + count + "] = " + x[i] + ";");
					System.out.println("py[" + count + "] = " + y[i] + ";");
				}
			}
			repaint();
		}
		else if(this.py > 350) {
			if (numClicks != 0) numClicks --;
			repaint();
		}
		else if (nextIt) {
			numClicks ++;
			nextIt = false;
			repaint();
			first = true;
			this.x[numClicks] = this.px;
			this.y[numClicks] = this.py;
		}
		else {
			numClicks ++;
			this.x[numClicks] = this.px;
			this.y[numClicks] = this.py;
		}			
	}


	public boolean mouseDrag(Event e, int x, int y) {
		this.px = x;
		this.py = y;
		verifyPositions();
		repaint();
		return true;
	}
}
