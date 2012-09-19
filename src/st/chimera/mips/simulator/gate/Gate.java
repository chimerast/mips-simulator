/*
 * 作成日: 2003/08/01
 */
package st.chimera.mips.simulator.gate;

import java.util.*;

/**
 * ゲート抽象クラス。全てのゲートはここから派生される。
 * 
 * @author chimera
 */
abstract public class Gate implements Runnable {
	private static ThreadGroup gateThreadGroup = new ThreadGroup("ExecuteUnitThread");

	/**
	 * ゲートのスリープ時間。他のスレッドに制御を渡すために
	 * スリープを掛ける。
	 */
	private static int UNIT_DELAY_TIME = 1;

	/**
	 * 生成されたゲートのリスト。現在存在しているゲートに
	 * アクセスするために使用する。停止させられると
	 * リストから外れる。
	 */
	private static List gates = Collections.synchronizedList(new LinkedList());

	/**
	 * ゲートスレッドの属するスレッドグループを返す。これを使用して、
	 * 全てのゲートのスレッドを一気に待機状態に持っていくことができる。
	 * 
	 * @return ゲートスレッドのスレッドグループ
	 */
	public static ThreadGroup getExecuteUnitThreadGroup() {
		return gateThreadGroup;
	}

	/**
	 * 全てのゲートのスレッドを開始する。
	 */
	public static void startAllUnit() {
		Iterator itr = gates.iterator();
		while (itr.hasNext())
			 ((Gate)itr.next()).start();
	}

	/**
	 * 全てのゲートのスレッドを止める。再始動できないので
	 * 終了時のみ実行。
	 */
	public static void terminateAllUnit() {
		Iterator itr = gates.iterator();
		while (itr.hasNext()) {
			try {
				((Gate)itr.next()).terminate();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 全てのゲートを安定するまで更新する。
	 */
	public static void updateAllUnit() {
		while (true) {
			boolean updated = false;
			Iterator itr = gates.iterator();
			while (itr.hasNext()) {
				Gate gate = (Gate)itr.next();
				if (gate.updateflag == true) {
					gate.update();
					gate.updateflag = false;
					updated = true;
				}
			}

			if (!updated)
				break;
		}
	}

	private boolean terminated = false;
	private boolean updateflag = true;
	private Thread thread;
	private String name;

	/**
	 * コンストラクタ。
	 * 
	 * @param name スレッド名
	 */
	public Gate(String name) {
		this.name = name;
		gates.add(this);
	}

	/**
	 * 実行スレッド。
	 */
	public final void run() {
		try {
			while (!terminated) {
				update();
				Thread.sleep(UNIT_DELAY_TIME);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			terminated = false;
			thread = null;
		}
	}

	/**
	 * 実行スレッドの開始。
	 *
	 */
	synchronized public void start() {
		if (thread == null) {
			thread = new Thread(gateThreadGroup, this, name);
			thread.start();
		}
	}

	/**
	 * 実行スレッドの停止。
	 * 
	 * @throws InterruptedException
	 */
	synchronized public void terminate() throws InterruptedException {
		if (thread != null) {
			terminated = true;
			if (Thread.currentThread() != thread)
				thread.join();
		}
	}

	/**
	 * 更新フラグを立てる
	 */
	public void setUpdate() {
		updateflag = true;
	}

	/**
	 * ゲートの更新。実行スレッドから呼び出される。
	 */
	abstract protected void update();
}

