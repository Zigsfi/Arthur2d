package arthur2d;


import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.ListIterator;

public class Engine extends Canvas {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedList<Drawable> drawables;
	private BufferedImage[] layer;
	private BufferedImage buffImg;
	private int width, height;
	private int resX, resY;
	private int camX, camY, camSX, camSY;
	final EngineHost eng;
	public Engine(EngineHost e, int w, int h, int rx, int ry, int layers) {
		drawables = new LinkedList<Drawable>();
		width = w;
		height = h;
		resX = rx;
		resY = ry;
		camSX = rx;
		camSY = ry;
		layer = new BufferedImage[layers];
		for (int i = 0; i < layers; i++) {
			layer[i] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		}
		buffImg = new BufferedImage(rx, ry, BufferedImage.TYPE_INT_ARGB);
		eng = e;
		new Thread() {
			public void run() {
				while(true) {
					eng.doStuff();
					repaint();
					try {
						sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	public void update(Graphics g) {
		paint(g);
	}

	public void paint(Graphics g) {
		Graphics off = buffImg.getGraphics();
		off.setColor(Color.white);
		off.fillRect(0, 0, resX, resY);
		for (int i = 0; i < layer.length; i++) {
			Graphics2D t = (Graphics2D) layer[i].getGraphics();
			t.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
			t.setColor(new Color(0, 0, 0, 0));
			t.fillRect(0, 0, width, height);
		}
		ListIterator<Drawable> drawIt = drawables.listIterator();
		Drawable d;
		while (drawIt.hasNext()) {
			d = drawIt.next();
			d.draw(layer);
		}
		for (int i = 0; i < layer.length; i++) {
			off.drawImage(layer[i], 0, 0, resX, resY, camX, camY, camX + camSX, camY + camSY, this);
		}
		g.drawImage(buffImg, 0, 0, resX, resY, this);
	}
	
	public void setCamera(int x, int y, int sx, int sy) {
		camX = x;
		camY = y;
		camSX = sx;
		camSY = sy;
	}
	
	public void addDrawable(Drawable d) {
		drawables.add(d);
	}

	public BufferedImage getScene() {
		return buffImg;
	}

}
