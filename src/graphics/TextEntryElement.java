package graphics;

import java.awt.*;

/**
 * @author onContentStop
 */
public class TextEntryElement extends TextElement {
	protected String infoText;
	protected int cursorFlashPeriodMS = 750;
	protected boolean hasFocus = false;
	public TextEntryElement(int x, int y, int width, int height, Graphics g) {
		super(x, y, width, height, g);
		textAlign = Align.LEFT;
	}
	
	//region getters and setters
	public String getInfoText() {
		return infoText;
	}
	
	public void setInfoText(String infoText) {
		this.infoText = infoText;
	}
	//endregion
	
	public void draw() {
		super.draw();
		if(hasFocus) {
			drawCursor();
		} else {
			
		}
	}
	
	private void drawCursor() {
		Graphics2D graphics2D = graphics.getGraphics2D();
		FontMetrics fm = graphics2D.getFontMetrics(graphics2D.getFont());
		int textLength = (int)fm.getStringBounds(text, graphics2D).getWidth();
		int trueX = DrawingTools.getTrueX(x, width, alignX);
		int trueY = DrawingTools.getTrueY(y, height, alignY);
		int widthMinusPad = width - 2 * borderWidth;
		int truePad = (int)(widthMinusPad * 0.25);
		int cursorOffset = 2;
		int fontHeight = graphics2D.getFont().getSize();
		graphics2D.setColor(borderColor);
		switch(textAlign) {
			case LEFT:
				graphics2D.drawLine(trueX + truePad + textLength + cursorOffset,
						trueY + height / 2 - fontHeight / 2,
						trueX + truePad + textLength + cursorOffset,
						trueY + height / 2 + fontHeight / 2);
				return;
			case CENTER_HORIZONTAL:
				graphics2D.drawLine(trueX + width / 2 + textLength / 2 + cursorOffset,
						trueY + height / 2 - fontHeight / 2,
						trueX + width / 2 + textLength / 2 + cursorOffset,
						trueY + height / 2 + fontHeight / 2);
				return;
			case RIGHT:
				graphics2D.drawLine(trueX + width - truePad + cursorOffset,
						trueY + height / 2 - fontHeight / 2,
						trueX + width - truePad + cursorOffset,
						trueY + height / 2 + fontHeight / 2);
				return;
			default:
				throw new IllegalArgumentException("Invalid text align: " + textAlign + " (Must be a horizontal align)");
		}
	}
}
