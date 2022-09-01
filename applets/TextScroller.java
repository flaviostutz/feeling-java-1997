import java.applet.Applet;
import java.awt.*;
import java.util.*;
import java.net.URL;

public class TextScroller extends Applet implements Runnable {

	URL link;
	int begin, end, oldEnd, newA, delayTime, i, line, animY, p, a, numLines, difference, lineSpace;
	int letterSize, maxChars, normalDelay, y;
	String mainText, status;
	boolean changeIt, firstA, stoped;
	Thread thread;
	Graphics offGraphics;
	Image offImage;
	Dimension d;
	Dimension offDimension;
	int[] charsPerLine;
	Color textColor;
	String[] texts;
	String param = null;

	public void init() {
		try {
			link = new URL(getCodeBase(), "Java1.html");
		}
		catch (java.net.MalformedURLException a) {}
		thread = new Thread(this);
		mainText = getParameter("Text");
		if (getParameter("autor") != "Flávio Stutz") status = "Cópia não autorizada pelo autor Flávio Stutz (flaviostutz@hotmail.com)";
		if (mainText == null) mainText = "Applet feito por Flávio Stutz (flaviostutz@hotmail.com).";
		textColor = new Color(100, 46, 97);

		param = getParameter("LetterSize");
		if (param != null) letterSize = Integer.valueOf(param).intValue();
		param = getParameter("MaxChars");
		if (param != null) maxChars = Integer.valueOf(param).intValue();
		param = getParameter("NormalDelay");
		if (param != null) normalDelay = Integer.valueOf(param).intValue();
		param = getParameter("CordY");
		if (param != null) y = Integer.valueOf(param).intValue();

		delayTime = normalDelay;
		setBackground(Color.white);
		numLines = mainText.length() / maxChars;
		texts = new String[numLines + 100];
		setBackground(Color.white);
		charsPerLine = new int[numLines + 100];
		lineSpace = letterSize * 9/10;
		difference = 0;
		newA = 0;
		line = 0;
		animY = 0;
		firstA = true;
		stoped = false;
		firstA = true;
		oldEnd = 0;
		p = 0;

		for (i = 0; i <= numLines; i ++) {
			if (i == 0) {
				begin = 0;
				if (numLines == 0) end = mainText.substring(0, mainText.length()).lastIndexOf('.');
				else end = mainText.substring(0, maxChars).lastIndexOf(' ');
				oldEnd = end;
				if ((end == -1) && (numLines != 0)) {
					end = begin + maxChars;
					oldEnd = end - 1;
				}
				difference = maxChars - end;
			}
			else {
				if (i < numLines) {
					begin = oldEnd + 1;
					end = mainText.substring(0, begin + maxChars).lastIndexOf(' ');
					oldEnd = end;
					if ((end == -1) && (numLines != 0)) {
						end = begin + maxChars;
						oldEnd = end - 1;
					}
					difference = maxChars - end;
				}
				else if (i == numLines) {
					begin = oldEnd + 1;
					end = mainText.substring(0, mainText.length()).lastIndexOf('.');
					if (end == -1) end = begin + maxChars;
					if ((end - begin) > maxChars) {
						end = mainText.substring(0, begin + maxChars).lastIndexOf(' ');
						numLines ++;
					}
					oldEnd = end;
					if ((end == -1) && (numLines != 0)) {
						end = begin + maxChars;
						oldEnd = end - 1;
					}
					difference = maxChars - end;
				}
			}
			if (difference >= maxChars) {
				numLines ++;
				difference = difference - maxChars;
			}
			texts[i] = mainText.substring(begin, end);
			charsPerLine[i] = texts[i].length();
		}
	}

	public void run() {
		while (thread != null) {
			try { 
				thread.sleep(delayTime);
			}
			catch (java.lang.InterruptedException e) {
				thread.stop();
			}
			repaint();
		}
	}

	public void start() {
		if(thread == null) thread = new Thread(this);
		thread.start();
	}
	
	public void stop() {
		if (thread != null) thread.stop();
	}

	public String next(String text, int p) {
		return text.substring(0, p);
	}

	public void paint(Graphics g) {
		update(g);
	}

	public void update(Graphics g) {
		if (status != null) getAppletContext().showStatus(status);
		d = size();
		if ((numLines == line) && (p >= charsPerLine[numLines])) {
			thread = null;
			stoped = true;
		}
		if ((p == charsPerLine[line]) && (numLines != line)) {
			changeIt = true;
			if (!stoped) line ++;
			if (!stoped) p = 0;
		}

// OffGraphics
			if ( (offGraphics == null) || (d.width != offDimension.width) || (d.height != offDimension.height) ) {
				offDimension = d;
				offImage = createImage(d.width, d.height);
				offGraphics = offImage.getGraphics();
			}

			offGraphics.setFont(new Font("Courier", Font.PLAIN, letterSize));
			offGraphics.setColor(getBackground());
			offGraphics.fillRect(0, 0, d.width, d.height);

// Scroll Engine
		if (changeIt) {
			offGraphics.setColor(Color.black);
			delayTime = normalDelay / 7;
			for (a = 0; a <= line - 1; a ++) offGraphics.drawString(next(texts[a], charsPerLine[a]), 0, y + ((line - a - 1) * lineSpace) + animY);
			if (animY == lineSpace) {
				changeIt = false;
				delayTime = normalDelay;
			}
			animY ++;
		}
		else {
			offGraphics.setColor(Color.blue);
			animY = 0;
			offGraphics.drawString(next(texts[line], p), 0, y);
			offGraphics.setColor(Color.black);
			for (a = 0; a < line; a ++) offGraphics.drawString(next(texts[a], charsPerLine[a]), 0, (y + ((line - 1 - a) * lineSpace) + lineSpace));
			if (!stoped) p ++;
			if (!stoped) i ++;
		}
		//offGraphics.drawString("Line: " + line + " numLines: " + numLines + " charsPerLine[line]: " + charsPerLine[line], 0, 220);
		//for (int b = 0; b <= numLines; b ++) offGraphics.drawString(texts[b], 30, 30 + (b * lineSpace));

		g.drawImage(offImage, 0, 0, this);
	}

	public boolean mouseMove(Event e, int x, int y) {
		if ((y > (50)) && (textColor != Color.blue)) {
		}
		else if ((y < (50)) && (textColor != Color.red)) {
		}			
	return true;
	}

	public boolean mouseDown(Event e, int x, int y) {
		if (thread != null) {
			getAppletContext().showDocument(link, "_blank");
			thread = null;
			stoped = true;
		}
		else {
			thread = new Thread(this);
			thread.start();
			stoped = false;
		}
	return true;
	}
}
