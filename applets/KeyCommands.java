import java.applet.Applet;
import java.awt.*;

public class KeyCommands extends Applet {

	Dimension d;
	Dimension offDimension;
	Graphics offGraphics;
	MediaTracker tracker;
	Image offImage;

	Image background;
	String text;
	
	boolean ready = false;
	boolean already = false;

	public void init() {
		tracker = new MediaTracker(this);
		setBackground(Color.white);
		background = getImage(getCodeBase(), "background.jpg");
		text = "Vamo vê!";

		tracker.addImage(background, 0);
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
				resize(background.getWidth(this), background.getHeight(this));
				offDimension = d;
				offImage = createImage(d.width, d.height);
				offGraphics = offImage.getGraphics();
			}

			offGraphics.setColor(getBackground());
			offGraphics.fillRect(0, 0, d.width, d.height);
			offGraphics.setColor(Color.black);

			offGraphics.drawImage(background, 0, 0, this);
			offGraphics.drawString(text, 0, 40);

			g.drawImage(offImage, 0, 0, this);
		}
	}

    public boolean handleEvent(Event e) {
		switch(e.key) {
			case Event.UP:
				break;
			case Event.DOWN:
				break;
			case Event.LEFT:
				break;
			case Event.RIGHT:
				break;
			case 32:
				break;
		}
		text = "e = " + e.key;// + " " + px + ", " + py;
		repaint();
	return true;
	}
}
