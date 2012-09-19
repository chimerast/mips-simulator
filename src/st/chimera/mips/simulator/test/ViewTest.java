/*
 * 作成日: 2003/08/01
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
package st.chimera.mips.simulator.test;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.plaf.metal.*;

import st.chimera.mips.assembler.*;
import st.chimera.mips.simulator.*;
import st.chimera.mips.simulator.view.*;

/**
 * @author chimera
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
public class ViewTest extends JFrame {

	JButton startButton;
	JButton stopButton;
	JButton resetButton;
	JButton clockButton;

	public ViewTest(int[] data, String[] code) {
		Architecture.getInstance().setData(data);
		Architecture.getInstance().setSourceCode(code);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.WHITE);

		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		startButton = new JButton("開始");
		stopButton = new JButton("停止");
		resetButton = new JButton("リセット");
		clockButton = new JButton("方式変更");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Architecture.getInstance().startClock();
			}
		});
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Architecture.getInstance().stopClock();
			}
		});
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Architecture.getInstance().resetPC();
				repaint();
			}
		});
		clockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Architecture.getInstance().changeMode();
			}
		});

		getContentPane().add(new ControlOutputView());
		getContentPane().add(new RegistersView());
		getContentPane().add(new MemoryView());
		getContentPane().add(new SourceView());

		getContentPane().add(startButton);
		getContentPane().add(stopButton);
		getContentPane().add(resetButton);
		getContentPane().add(clockButton);

		setSize(800, 600);
		validate();
	}

	public static void main(String[] args) {
		try {
			Assembler asm = new Assembler();
			new MainParser(asm, new FileReader("d:/compsys4.asm"));
			asm.nextStage();
			new MainParser(asm, new FileReader("d:/compsys4.asm"));

			MetalLookAndFeel.setCurrentTheme(new st.chimera.theme.ChimeraMetalTheme());
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

			(new ViewTest(asm.getData(), asm.getSourceCode())).setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
