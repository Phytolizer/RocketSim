package physics;

import java.util.List;

import static math.Constants.*;

/**
 * Created by kyle on 5/11/17.
 */
public class Vector extends Scalar {
	private double direction;//r, theta

	public Vector(double x, double y) {
		double[] polarCoords = toPolar(x, y);
		magnitude = polarCoords[0];
		direction = polarCoords[1];
		keepDirectionInBounds();
	}

	private double[] toRectangular(double r, double theta) {
		double[] out = new double[2];
		double x = r * Math.cos(theta);//x
		double y = r * Math.sin(theta);//y
		return new double[] {x, y};
	}
	private double[] toPolar(double x, double y) {
		double r = Math.sqrt(x * x + y * y);
		double theta = Math.atan(y / x);
		return new double[] {r, theta};
	}

	//region getters and setters

	public double getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

	public double getXComponent() {
		return toRectangular(magnitude, direction)[0];
	}

	public void setXComponent(double x) {
		double y = toRectangular(magnitude, direction)[1];
		double[] polar = toPolar(x, y);
		magnitude = polar[0];
		direction = polar[1];
	}

	public double getYComponent() {
		return toRectangular(magnitude, direction)[1];
	}

	public void setYComponent(double y) {
		double x = toRectangular(magnitude, direction)[0];
		double[] polar = toPolar(x, y);
		magnitude = polar[0];
		direction = polar[1];
	}

	//endregion

	public static Vector add(Vector v1, Vector v2) {
		double x1 = v1.getXComponent();
		double y1 = v1.getYComponent();
		double x2 = v2.getXComponent();
		double y2 = v2.getYComponent();
		return new Vector(x1 + x2, y1 + y2);
	}

	public static Vector sumVectors(List<Vector> vectors) {
		double x = 0, y = 0;
		for(Vector v : vectors) {
			double xc = v.getXComponent();
			double yc = v.getYComponent();
			x += xc;
			y += yc;
		}
		return new Vector(x, y);
	}

	public void turn(double angleChange) {
		direction += angleChange;
		keepDirectionInBounds();
	}

	/**
	 * Keeps the value of <code>direction</code> between 0 and 2(pi).
	 */
	private void keepDirectionInBounds() {
		while(direction > 2 * pi) {
			direction -= 2 * pi;
		}
		while(direction < 0) {
			direction += 2 * pi;
		}
	}
}
