/*
 * 作成日: 2003/08/01
 */
package st.chimera.mips.simulator.gate;

import java.util.*;

import st.chimera.mips.simulator.*;

/**
 * クロック生成器
 * 
 * @author chimera
 */
public class Clock implements Runnable {
	public static interface Callback {
		void call();
	}

	private static List callbefore = new LinkedList();
	private static int clockDelayTime = 500;
	private static boolean terminated = false;
	private static Thread thread;

	public static void setClockDelayTime(int time) {
		if (time < 10)
			time = 10;
		if (time > 5000)
			time = 5000;
		clockDelayTime = time;
	}

	public static void addCallBeforeClockEdge(Callback callback) {
		callbefore.add(callback);
	}

	public static void callBackAll() {
		// 登録された関数の呼び出し
		Iterator itr = callbefore.iterator();
		while (itr.hasNext())
			 ((Callback)itr.next()).call();		
	}

	public static void start() {
		synchronized (Clock.class) {
			if (thread == null) {
				thread = new Thread(new Clock(), "Clock");
				thread.start();
			}
		}
	}

	public static void terminate() throws InterruptedException {
		synchronized (Clock.class) {
			if (thread != null) {
				terminated = true;
				if (thread != Thread.currentThread())
					thread.join();
			}
		}
	}

	public static boolean isRunning() {
		return thread != null;
	}

	private Clock() {
	}

	public void run() {
		try {
			System.out.println("Clock Start");
			while (!terminated) {
				// レジスタの更新に影響しないよう全てのユニットの
				// 実行をとめる
				Gate.getExecuteUnitThreadGroup().suspend();

				if (!Architecture.getInstance().isRealMode())
					Gate.updateAllUnit();
		
				callBackAll();

				// クロックエッジ
				Register.clockEdge();

				// 全てのユニットの実行を再開
				Gate.getExecuteUnitThreadGroup().resume();

				Thread.sleep(clockDelayTime);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			terminated = false;
			thread = null;
			System.out.println("Clock Stop");
		}
	}
}
