package graphics;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author onContentStop
 */
public class TextElement extends Element {
	protected String text;
	protected Color highlightColor = Color.white;
	protected Color textColor = Color.black;
	protected Color borderColor = Color.black;
	protected Align textAlign = Align.CENTER_HORIZONTAL;
	
	
	protected int borderWidth = 3;
	
	public TextElement(int x, int y, int width, int height, Graphics g) {
		super(g);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	//region getters and setters
	public Color getHighlightColor() {
		return highlightColor;
	}
	
	public void setHighlightColor(Color highlightColor) {
		this.highlightColor = highlightColor;
	}
	
	public Color getTextColor() {
		return textColor;
	}
	
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}
	
	public Color getBorderColor() {
		return borderColor;
	}
	
	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}
	
	public int getBorderWidth() {
		return borderWidth;
	}
	
	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
	}
	
	public Align getTextAlign() {
		return textAlign;
	}
	
	public void setTextAlign(Align textAlign) {
		this.textAlign = textAlign;
	}
	//endregion
	
	@Override
	public void draw() {
		Graphics2D graphics2D = graphics.getGraphics2D();
		Font f = graphics2D.getFont();
		f = f.deriveFont(getBestFontSize(graphics2D, f));
		
		int trueX = DrawingTools.getTrueX(x, width, alignX);
		int trueY = DrawingTools.getTrueY(y, height, alignY);
		//draw borders
		graphics2D.setColor(borderColor);
		graphics2D.fillRect(trueX, trueY, width, height);
		//draw background
		graphics2D.setColor(highlightColor);
		graphics2D.fillRect(trueX + borderWidth, trueY + borderWidth, width - 2 * borderWidth, height - 2 * borderWidth);
		//draw text
		graphics2D.setColor(textColor);
		DrawingTools.drawText(text, trueX + width / 2, trueY + height / 2, Align.CENTER_HORIZONTAL, Align.CENTER_VERTICAL, graphics2D);
	}
	
	private float getBestFontSize(Graphics2D g2d, Font f) {
		int realW = width - borderWidth * 2;
		int realH = height - borderWidth * 2;
		
		double fillProportion = 0.75;
		
		int bestW = (int)(fillProportion * realW);
		int bestH = (int)(fillProportion * realH);
		FontMetrics fm = g2d.getFontMetrics(f);
		Rectangle2D bounds = fm.getStringBounds(text, g2d);
		float size = 1;
		
		for(; bounds.getHeight() < bestH && bounds.getWidth() < bestW; size++) {
			Font font = f.deriveFont(size);
			fm = g2d.getFontMetrics(font);
			bounds = fm.getStringBounds(text, g2d);
		}
		return size;
	}
	
	private void drawText(Graphics2D graphics2D) {
		int trueX = DrawingTools.getTrueX(x, width, alignX);
		int trueY = DrawingTools.getTrueY(y, height, alignY);
		switch(textAlign) {
			case LEFT:
				int widthMinusPad = width - 2 * borderWidth;
				int truePad = (int)(widthMinusPad * 0.25);
				DrawingTools.drawText(text, trueX + truePad, trueY + height / 2, Align.LEFT, Align.CENTER_VERTICAL, graphics2D);
				return;
			case CENTER_HORIZONTAL:
				DrawingTools.drawText(text, trueX + width / 2, trueY + height / 2, Align.CENTER_HORIZONTAL, Align.CENTER_VERTICAL, graphics2D);
				return;
			case RIGHT:
				widthMinusPad = width - 2 * borderWidth;
				truePad = (int)(widthMinusPad * 0.25);
				DrawingTools.drawText(text, trueX + width - truePad, trueY + height / 2, Align.RIGHT, Align.CENTER_VERTICAL, graphics2D);
				return;
			default:
				throw new IllegalArgumentException("Invalid text align: " + textAlign + " (Must be a horizontal align)");
		}
	}
}
