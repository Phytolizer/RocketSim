package graphics;

import java.awt.*;

import static graphics.DrawingTools.getTrueX;
import static graphics.DrawingTools.getTrueY;
import static java.awt.Color.white;

/**
 * @author onContentStop
 */
public class ButtonElement extends Element {
	private final Color CLEAR = new Color(0, 0, 0, 0);
	private final Color HOVERING = new Color(128, 128, 128, 64);
	private final Color CLICKING = new Color(128, 128, 128, 128);
	private boolean clicking, mouseOver;
	private Color color, coverColor, borderColor = Color.black;
	private String text;
	private ButtonListener clickListener;
	
	public ButtonElement(Graphics graphics) {
		super(graphics);
		color = white;
		coverColor = CLEAR;
		text = "";
		x = 0;
		y = 0;
		width = 100;
		height = 100;
	}
	
	public ButtonElement(int x, int y, int width, int height, Graphics graphics) {
		super(graphics);
		color = white;
		coverColor = CLEAR;
		text = "";
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public ButtonElement(int x, int y, int width, int height, Color backgroundColor, Graphics graphics) {
		super(graphics);
		color = backgroundColor;
		coverColor = CLEAR;
		text = "";
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void setHovering(boolean hovering) {
		mouseOver = hovering;
		coverColor = hovering ? HOVERING : CLEAR;
	}
	
	public void setClicking(boolean clicking) {
		this.clicking = clicking;
		coverColor = clicking ? CLICKING : (mouseOver ? HOVERING : CLEAR);
	}
	
	private int getBestFontSize(Font f, Graphics2D context) {
		int width = 0;
		double fillProportion = 2d / 3d;
		int i;
		for(i = 1; width < fillProportion * this.width; i++) {
			f = f.deriveFont(f.getStyle(), i);
			FontMetrics fm = context.getFontMetrics(f);
			width = fm.stringWidth(text);
			if(width == 0)
				return 0;
		}
		if(f.getSize() > height) {
			return (int)(fillProportion * height);
		}
		return i;
	}
	
	private boolean isInBounds(int x1, int y1, int x2, int y2) {
		return x1 > x2 && y1 > y2 && x1 < (x2 + width) && y1 < (y2 + height);
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}
	
	private void onClick() {
		if(clickListener != null) {
			clickListener.onClick();
		}
	}
	
	public void setClickListener(ButtonListener buttonListener) {
		clickListener = buttonListener;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void draw() {
		Graphics2D graphics2D = graphics.getGraphics2D();
		int mouseX = graphics.getFrame().mouseX;
		int mouseY = graphics.getFrame().mouseY;
		Font f = graphics.getFont().deriveFont(
				graphics.getFont().getStyle(),
				getBestFontSize(graphics.getFont(),
						graphics2D));
		graphics2D.setFont(f);
		
		//check bounds
		setHovering(isInBounds(mouseX, mouseY, getTrueX(x, width, alignX), getTrueY(y, height, alignY)));
		if(! graphics.getFrame().clicking() && clicking) {
			onClick();
		}
		setClicking(mouseOver && graphics.getFrame().clicking());
		
		//actual drawing
		graphics2D.setColor(color);
		DrawingTools.fillRect(x, y, width, height, alignX, alignY, graphics2D);
		graphics2D.setColor(coverColor);
		DrawingTools.fillRect(x, y, width, height, alignX, alignY, graphics2D);
		graphics2D.setColor(borderColor);
		DrawingTools.drawRect(x, y, width, height, alignX, alignY, graphics2D);
		int centerX = getTrueX(x, width, alignX) + width / 2;
		int centerY = getTrueY(y, height, alignY) + height / 2;
		DrawingTools.drawText(text, centerX, centerY, Align.CENTER_HORIZONTAL, Align.CENTER_VERTICAL, graphics2D);
	}
}
