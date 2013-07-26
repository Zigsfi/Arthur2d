package arthur2d;

import java.awt.Color;
import java.awt.RadialGradientPaint;

public class Light {
	public int xPos, yPos;
	public int range;
	RadialGradientPaint gradient;
	public Light(int x, int y, int r) {
		xPos = x;
		yPos = y;
		range = r;
		float f[] = {0.6f, 1f};
		Color c[] = {new Color(0, 0, 0, 0), new Color(0, 0, 0)};
		gradient = new RadialGradientPaint(xPos, yPos, range, f, c);
	}
}
