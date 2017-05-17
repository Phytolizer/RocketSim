package graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author onContentStop
 */
public class DrawingTools {
	
	public static void drawText(String str, int x_, int y_, Graphics2D g2d) {
		drawText(str, x_, y_, Align.LEFT, Align.TOP, g2d);
	}
	
	public static void drawText(String str, int x_, int y_, Align alignX, Align alignY, Graphics2D g2d) {
		Font f = g2d.getFont();
		FontMetrics metrics = g2d.getFontMetrics(f);
		Rectangle2D rect = metrics.getStringBounds(str, g2d);
		int x = getTrueX(x_, (int)rect.getWidth(), alignX);
		int y = getTrueY(y_, (int)rect.getHeight(), alignY) - (alignY == Align.CENTER_VERTICAL ? metrics.getDescent() * 3 / 4 : 0);
		g2d.drawString(str, x, (int)(y + rect.getHeight()));
	}
	
	public static void drawRect(int x, int y, int sizeX, int sizeY, Graphics2D g2d) {
		drawRect(x, y, sizeX, sizeY, Align.LEFT, Align.TOP, g2d);
	}
	
	public static void drawRect(int x, int y, int sizeX, int sizeY, Align alignX, Align alignY, Graphics2D g2d) {
		int realX = getTrueX(x, sizeX, alignX);
		int realY = getTrueY(y, sizeY, alignY);
		g2d.drawRect(realX, realY, sizeX, sizeY);
	}
	
	public static void fillRect(int x, int y, int sizeX, int sizeY, Graphics2D g2d) {
		fillRect(x, y, sizeX, sizeY, Align.LEFT, Align.TOP, g2d);
	}
	
	public static void fillRect(int x, int y, int sizeX, int sizeY, Align alignX, Align alignY, Graphics2D g2d) {
		int realX = getTrueX(x, sizeX, alignX);
		int realY = getTrueY(y, sizeY, alignY);
		g2d.fillRect(realX, realY, sizeX, sizeY);
	}
	
	public static void drawImage(String filePath, int x, int y, int width, int height, Graphics2D g2d) {
		drawImage(filePath, x, y, width, height, Align.LEFT, Align.TOP, g2d);
	}
	
	public static void drawImage(String filePath, int x, int y, Graphics2D g2d) {
		drawImage(filePath, x, y, Align.LEFT, Align.TOP, g2d);
	}
	
	public static void drawImage(String filePath, int x, int y, double scale, Graphics2D g2d) {
		drawImage(filePath, x, y, scale, Align.LEFT, Align.TOP, g2d);
	}
	
	public static void drawImage(String filePath, int x, int y, double scale, Align alignX, Align alignY, Graphics2D g2d) {
		BufferedImage image;
		File imageFile = new File(filePath);
		image = getImageFromFile(imageFile);
		if(image != null) {
			int width = image.getWidth();
			int height = image.getHeight();
			int newWidth = (int)(scale * width);
			int newHeight = (int)(scale * height);
			drawImage(image, x, y, newWidth, newHeight, alignX, alignY, g2d);
		}
	}
	
	public static void drawImage(String filePath, int x, int y, int width, int height, Align alignX, Align alignY, Graphics2D g2d) {
		BufferedImage image;
		File imageFile = new File(filePath);
		image = getImageFromFile(imageFile);
		if(image != null) {
			drawImage(image, x, y, width, height, alignX, alignY, g2d);
		}
	}
	
	public static void drawImage(String filePath, int x, int y, Align alignX, Align alignY, Graphics2D g2d) {
		BufferedImage image;
		File imageFile = new File(filePath);
		image = getImageFromFile(imageFile);
		if(image != null) {
			int width = image.getWidth();
			int height = image.getHeight();
			drawImage(image, x, y, width, height, alignX, alignY, g2d);
		}
	}
	
	public static void drawImage(BufferedImage image, int x, int y, int width, int height, Align alignX, Align alignY, Graphics2D g2d) {
		int newX = getTrueX(x, width, alignX);
		int newY = getTrueY(y, height, alignY);
		g2d.drawImage(image, newX, newY, width, height, (img, infoflags, x_, y_, width_, height_) -> false);
	}
	
	public static Dimension getImageSize(String filePath) {
		BufferedImage image;
		File imageFile = new File(filePath);
		image = getImageFromFile(imageFile);
		if(image != null) {
			return new Dimension(image.getWidth(), image.getHeight());
		}
		return null;
	}
	
	public static BufferedImage getImageFromFile(File imageFile) {
		try {
			if(! imageFile.exists()) {
				System.out.println("Image file not found: " + imageFile.getPath());
			} else {
				return ImageIO.read(imageFile);
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}
	
	public static int getTrueX(int x, int width, Align align) {
		int newX;
		switch(align) {
			case LEFT:
				newX = x;
				break;
			case CENTER_HORIZONTAL:
				newX = x - width / 2;
				break;
			case RIGHT:
				newX = x - width;
				break;
			default:
				throw new IllegalArgumentException("Cannot use Align." + align.toString() + " for horizontal position");
		}
		return newX;
	}
	
	public static int getTrueY(int y, int height, Align align) {
		int newY;
		switch(align) {
			case TOP:
				newY = y;
				break;
			case CENTER_VERTICAL:
				newY = y - height / 2;
				break;
			case BOTTOM:
				newY = y - height;
				break;
			default:
				throw new IllegalArgumentException("Cannot use Align." + align.toString() + " for vertical position");
		}
		return newY;
	}
}
