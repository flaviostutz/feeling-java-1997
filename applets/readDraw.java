import java.awt.*;

public class readDraw extends java.applet.Applet {
	int[] px, py;
	int numPoints;
	int i;
	boolean[] separate;

	public void init() {
		numPoints = 0;
		px = new int[numPoints];
		py = new int[numPoints];
		separate = new boolean[numPoints];
		resize(777,200);
// Put the output result here!

// that's all!
	}

	public void paint(Graphics g) {
		for (i = 1; i <= numPoints; i ++) {
			if (!separate[i])g.drawLine(px[i - 1], py[i - 1], px[i], py[i]);
			else g.drawLine(px[i], py[i], px[i], py[i]);
		}
	}
}


