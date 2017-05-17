package graphics;

import java.awt.*;
import java.util.List;

/**
 * @author onContentStop
 */
public class RandomColorGenerator {
	private List<Color> colorList;
	private Color lastColor;
	
	public RandomColorGenerator(List<Color> colors) {
		if(colors.size() <= 0) {
			throw new IllegalArgumentException("Color list is too small! (size of " + colors.size() + ")");
		}
		colorList = colors;
		lastColor = colors.get(0);
	}
	
	public static Color getOpaqueColor() {
		int r, g, b;
		r = (int)(Math.random() * 256);
		g = (int)(Math.random() * 256);
		b = (int)(Math.random() * 256);
		return new Color(r, g, b);
	}
	
	public Color getNextColor() {//Returns a color from the list not including lastColor, or the only entry in the list.
		if(colorList.size() > 1) {
			int lastColorIndex = colorList.indexOf(lastColor);
			int index = (int)(Math.random() * colorList.size() - 1);
			if(index >= lastColorIndex)
				index++;
			Color c = colorList.get(index);
			lastColor = c;
			return c;//Note that a list of size 2 will just return each color, alternating forever.
		} else {
			return colorList.get(0); //there's no randomness here, why would you do this???
		}
	}
	
	public void addColor(Color c) {
		colorList.add(c);
	}
	
	/**
	 * @param c the color to remove.
	 * @return whether the operation was successful.
	 */
	public boolean removeColor(Color c) {
		return colorList.remove(c);
	}
}
