package physics;

/**
 * Created by kyle on 5/15/17.
 */
public class Scalar {
	protected double magnitude;
	public Scalar(double magnitude) {
		this.magnitude = magnitude;
	}

	public Scalar() {
		magnitude = 0;
	}

	public double getMagnitude() {
		return magnitude;
	}
	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}
}
