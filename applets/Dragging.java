import java.applet.Applet;
import java.awt.Cursor;
import java.awt.*;

public class Dragging extends Applet {

	Image paisagensoff;
	Image paisagenson;
	Image carrosoff;
	Image carroson;
	Image animaisoff;
	Image animaison;
	Image background;

	Image seta1;
	Image seta2;
	Image film;
	Image button1;
	Image button2;
	Image button3;

	Image offImage;
	MediaTracker tracker;
	Dimension offDimension;
	Graphics offGraphics;
	String string;

	boolean begin = false;
	boolean dragging = false;
	boolean already = false;		
	int ax, bx, cx = 0;
	int ay, by, cy;
	int s1x, s1y, s2y, s2x;
	int tempx, tempy;
	int distance;
	int which;
	int name;

	public void init() {
		tracker = new MediaTracker(this);
		string = new String("Hehe.");
		animaisoff = getImage(getCodeBase(), "animais-1.gif");
		animaison = getImage(getCodeBase(), "animais-2.gif");
		paisagensoff = getImage(getCodeBase(), "paisagens-1.gif");
		paisagenson = getImage(getCodeBase(), "paisagens-2.gif");
		carrosoff = getImage(getCodeBase(), "carros-1.gif");
		carroson = getImage(getCodeBase(), "carros-2.gif");
		background = getImage(getCodeBase(), "background.jpg");
		seta1 = getImage(getCodeBase(), "seta-1.jpg");
		seta2 = getImage(getCodeBase(), "seta-2.jpg");
		film = getImage(getCodeBase(), "film.jpg");

		button1 = animaisoff;
		button2 = paisagensoff;
		button3 = carrosoff;

		distance = 30;
		s1y = 222;
		s2y = 222;
		
		ay = 15;
		by = (ay + button1.getHeight(this)) + distance;
		cy = (by + button2.getHeight(this)) + distance;

		tracker.addImage(animaisoff, 0);
		tracker.addImage(animaison, 0);
		tracker.addImage(carrosoff, 0);
		tracker.addImage(carroson, 0);
		tracker.addImage(paisagensoff, 0);
		tracker.addImage(paisagenson, 0);
		tracker.addImage(background, 0);
		
	}

	public void paint(Graphics g) {
		update(g);
	}

	public void update(Graphics g) {
		Dimension d = size();

		if (!tracker.checkAll()) {
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
			repaint(false ? 0 : 300);
		}
		
		else {

			if ( (offGraphics == null) || (d.width != offDimension.width) || (d.height != offDimension.height) ) {
				offDimension = d;
				offImage = createImage(d.width, d.height);
				offGraphics = offImage.getGraphics();
			}
			
			s2x = 0;
			s1x = d.width - seta1.getWidth(this);

			offGraphics.setColor(getBackground());
			offGraphics.fillRect(0, 0, d.width, d.height);
			offGraphics.setColor(Color.red);
	
			offGraphics.drawImage(background, 0, 0, this);
			offGraphics.drawImage(button1, ax, ay, this);
			offGraphics.drawImage(button2, bx, by, this);
			offGraphics.drawImage(button3, cx, cy, this);
			offGraphics.drawImage(film, d.width/2 - film.getWidth(this)/2, 130, this);
			offGraphics.drawImage(seta2, s2x, s2y, this);
			offGraphics.drawImage(seta1, s1x, s1y, this);
			offGraphics.drawString(string, 0, 350);
	
			g.drawImage(offImage, 0, 0, this);
		}
	}
	
	public boolean mouseMove(Event e, int x, int y) {
		if (!dragging) {
		if ( ((x > this.ax) && (x < (ax + button1.getWidth(this))) ) && ((y > this.ay) && (y < (ay + button1.getHeight(this)))) ) {
			if (which != 1) {	
				button2 = paisagensoff;
				button3 = carrosoff;
				button1 = animaison;
				this.string = "Button 1.";
				which = 1;
				repaint();
			}
		}

		else if ( ((x > this.bx) && (x < (bx + button2.getWidth(this))) ) && ((y > this.by) && (y < (by + button2.getHeight(this)))) ) {
			if (which != 2) {	
				button1 = animaisoff;
				button3 = carrosoff;
				button2 = paisagenson;
				this.string = "Button 2.";
				which = 2;
				repaint();
			}
		}

		else if ( ((x > this.cx) && (x < (cx + button3.getWidth(this))) ) && ((y > this.cy) && (y < (cy + button3.getHeight(this)))) ) {
			if (which != 3) {	
				button1 = animaisoff;
				button2 = paisagensoff;
				button3 = carroson;
				this.string = "Button 3.";
				which = 3;
				repaint();
			}
		}

		else {
			if (which != 0) {
				button1 = animaisoff;
				button2 = paisagensoff;
				button3 = carrosoff;
				which = 0;
				repaint();
			}				
		}
	}
		return true;
	}

	public boolean mouseDrag(Event e, int x, int y) {
		if(!dragging) {
			if ( ((x > this.ax) && (x < (ax + button1.getWidth(this))) ) && ((y > this.ay) && (y < (ay + button1.getHeight(this)))) ) {
				name = 1;	
			}
			else if ( ((x > this.bx) && (x < (bx + button2.getWidth(this))) ) && ((y > this.by) && (y < (by + button2.getHeight(this)))) ) {
				name = 2;
			}
			else if ( ((x > this.cx) && (x < (cx + button3.getWidth(this))) ) && ((y > this.cy) && (y < (cy + button3.getHeight(this)))) ) {
				name = 3;
			}
			else {
				name = 0;
			}
		}		

		switch(name) {
			case 1: 
				if (!dragging) {
					if(begin) {
						tempx = x - this.ax;
						tempy = y - this.ay;
						begin = false;
					}
					else {
						this.ax = x - tempx;
						this.ay = y - tempy;
						this.string = this.ax + "";
						dragging = true;
						repaint();
					}
				}
		
				else {
					this.ax = x - tempx;
					this.ay = y - tempy;
					this.string = this.ax + "";
					repaint();
				}
				break;
			case 2:
				if (!dragging) {
					if(begin) {
						tempx = x - this.bx;
						tempy = y - this.by;
						begin = false;
					}
					else {
						this.bx = x - tempx;
						this.by = y - tempy;
						this.string = this.bx + "";
						dragging = true;
						repaint();
					}
				}
		
				else {
					this.bx = x - tempx;
					this.by = y - tempy;
					this.string = this.bx + "";
					repaint();
				}
				break;
			case 3:
				if (!dragging) {
					if(begin) {
						tempx = x - this.cx;
						tempy = y - this.cy;
						begin = false;
					}
					else {
						this.cx = x - tempx;
						this.cy = y - tempy;
						this.string = this.cx + "";
						dragging = true;
						repaint();
					}
				}
		
				else {
					this.cx = x - tempx;
					this.cy = y - tempy;
					this.string = this.cx + "";
					repaint();
				}
				break;
		}
	
		return true;
	}

	public boolean mouseUp(Event e, int x, int y) {
		if(dragging) dragging = false;
		begin = true;
		return true;
	}

}


