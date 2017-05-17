package graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author onContentStop
 */
public class ThreadManager {
	private HashMap<Runnable, Thread> threads;
	private List<Thread> threadList;
	public ThreadManager() {
		threads = new HashMap<>();
		threadList = new ArrayList<>();
	}
	public void addThread(Runnable runnable) {
		threads.put(runnable, new Thread(runnable));
		threadList.add(threads.get(runnable));
		System.out.println("[ThreadManager] Adding thread: " + runnable.getClass().getName() + " (" + threads.get(runnable).getName() + ')');
	}
	public Thread findThread(Runnable runnable) {
		if(threads.containsKey(runnable)) {
			return threads.get(runnable);
		}
		return null;
	}
	public void startThread(Runnable runnable) {
		if(threads.containsKey(runnable)) {
			threads.get(runnable).start();
			System.out.println("[ThreadManager] Starting thread: " + runnable.getClass().getName() + " (" + threads.get(runnable).getName() + ')');
		} else {
			System.out.println("[ERROR] [ThreadManager] Could not find a thread for " + runnable.getClass().getName());
		}
	}
	public boolean isThreadAlive(Runnable runnable) {
		return threads.containsKey(runnable) && threads.get(runnable).isAlive();
	}
	public void killThread(Runnable runnable) {
		if(threads.containsKey(runnable)) {
			threads.get(runnable).interrupt();
			threadList.remove(threads.get(runnable));
			System.out.println("[ThreadManager] Killed thread: " + runnable.getClass().getName() + " (" + threads.get(runnable).getName() + ')');
			threads.remove(runnable);
		}
	}
	public int getNumActiveThreads() {
		int numActiveThreads = 0;
		for(Thread t : threadList) {
			if(t.isAlive()) {
				numActiveThreads++;
			}
		}
		return numActiveThreads;
	}
	public Set<Runnable> getAllRunnables() {
		return threads.keySet();
	}
}
