package rocketsim;

import graphics.Background;
import graphics.Graphics;
import graphics.ThreadManager;
import graphics.Timer;
import physics.Vector;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by kyle on 5/11/17.
 */
public class RocketSim extends Graphics {
	private Background background;
	private Vector thrustVector;
	public RocketSim(ThreadManager threadManager) {
		super(threadManager);
		init();
	}

	private void init() {
		List<Color> colors = new ArrayList<>();
		//region colors
		colors.add(Color.BLUE);
		//endregion
		Timer colorTimer = new Timer();
		new Thread(colorTimer).start();
		colorTimer.start();
		background = new Background(colors, colorTimer, 10000);
		thrustVector = new Vector(0, 100);//Newtons
	}

	protected void runActions() {
		background.update();
		draw();
	}

	protected void drawActions() {
		graphics2D.setColor(background.getCurrentColor());
		graphics2D.fillRect(0, 0, width, height);
		//drawing happens here!
	}
}
