package physics;

import java.util.HashMap;

/**
 * Created by kyle on 5/15/17.
 */
public class Kinematics {
	public static HashMap<String, Vector> stepMotion(Vector sumForces, Vector velocity, double mass, Vector position, double timeStep) {
		HashMap<String, Vector> out = new HashMap<>();
		double prevX = position.getXComponent();
		double prevY = position.getYComponent();
		//F=ma
		double a_x = sumForces.getXComponent() / mass;
		double a_y = sumForces.getYComponent() / mass;
		double v_x = velocity.getXComponent();
		double v_y = velocity.getYComponent();
		//v = v + at
		out.put("velocity", new Vector(v_x + a_x * timeStep, v_y + a_y * timeStep));
		//x = vt + .5at**2
		double changeX = v_x * timeStep + 0.5 * a_x * Math.pow(timeStep, 2);
		double changeY = v_y * timeStep + 0.5 * a_y * Math.pow(timeStep, 2);
		out.put("position", new Vector(prevX + changeX, prevY + changeY));
		return out;
	}
}
