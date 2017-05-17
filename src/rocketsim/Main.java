package rocketsim;

import graphics.ThreadManager;

/**
 * Created by kyle on 5/11/17.
 */
public class Main {
	public static void main(String[] args) {
		ThreadManager tm = new ThreadManager();
		RocketSim rocketSim = new RocketSim(tm);
		rocketSim.setVisible(true);
		tm.addThread(rocketSim);
		tm.startThread(rocketSim);
		while(tm.getNumActiveThreads() > 0) {

		}
		System.exit(0);
	}
}
