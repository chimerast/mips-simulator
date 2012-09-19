/*
 * 作成日: 2003/08/14
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.plaf.metal.*;

import st.chimera.mips.assembler.*;
import st.chimera.mips.simulator.*;
import st.chimera.mips.simulator.view.*;

public class MipsSimulator extends JFrame {
	SourceView sourceView = new SourceView();

	private class ControlPanel extends JPanel implements Runnable {
		JButton openButton;
		JButton startButton;
		JButton stopButton;
		JButton resetButton;
		JCheckBox clockButton;
		JTextArea console;
		JScrollPane consolePane;
		JSlider clockSlider;
		BufferedReader in;

		int[] clock = { 10, 20, 50, 100, 500, 1000, 2000, 3000, 4000, 5000 };

		public void run() {
			String buffer;
			try {
				while ((buffer = in.readLine()) != null) {
					console.append(buffer + "\n");
					consolePane.getVerticalScrollBar().setAutoscrolls(true);
				}
			} catch (IOException e) {
				createConsolePipe();
				new Thread(this).start();
			}
		}

		public ControlPanel() {
			createConsolePipe();

			setPreferredSize(new Dimension(356, 320));
			setBorder(new TitledBorder("Control"));
			setLayout(new BorderLayout());

			openButton = new JButton("読み込み");
			startButton = new JButton("開始");
			stopButton = new JButton("停止");
			resetButton = new JButton("リセット");
			clockButton = new JCheckBox("リアルクロックモード");
			clockSlider = new JSlider(0, 9);
			clockSlider.setPaintTicks(true);
			clockSlider.setMajorTickSpacing(1);
			clockSlider.setPaintLabels(true);
			clockSlider.setSnapToTicks(true);

			Hashtable table = new Hashtable();
			for (int i = 0; i < clock.length; ++i) {
				table.put(
					new Integer(i),
					new JLabel(new Integer(clock[i]).toString(), JLabel.CENTER));
			}
			clockSlider.setLabelTable(table);
			clockSlider.setPreferredSize(new Dimension(300, 50));

			console = new JTextArea();
			console.setAutoscrolls(true);
			console.setEditable(false);
			consolePane = new JScrollPane(console);
			consolePane.setPreferredSize(new Dimension(300, 150));

			openButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						String file = JOptionPane.showInputDialog(null, "ファイル名を入力してください");
						if (file != null) {						
							Assembler asm = new Assembler();
							new MainParser(asm, new FileReader(file));
							asm.nextStage();
							new MainParser(asm, new FileReader(file));
							Architecture.getInstance().setData(asm.getData());
							Architecture.getInstance().setSourceCode(asm.getSourceCode());
							sourceView.updateTable();
						}
					} catch (ParseException ex) {
						ex.printStackTrace();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});
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
					if (!Architecture.getInstance().resetPC()) {
						JOptionPane.showMessageDialog(
							null,
							"クロック動作中はリセットできません。",
							"エラー",
							JOptionPane.ERROR_MESSAGE);
					}
					repaint();
				}
			});
			clockButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					clockButton.setSelected(Architecture.getInstance().changeMode());
				}
			});
			clockSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					Architecture.getInstance().setClockDelayTime(clock[clockSlider.getValue()]);
				}
			});

			setLayout(new FlowLayout());
			add(openButton);
			add(startButton);
			add(stopButton);
			add(resetButton);
			add(clockButton);
			add(clockSlider);
			add(consolePane);

			validate();

			new Thread(this).start();
		}

		public void createConsolePipe() {
			try {
				PipedInputStream pipein = new PipedInputStream();
				PrintStream print = new PrintStream(new PipedOutputStream(pipein));
				System.setOut(print);
				in = new BufferedReader(new InputStreamReader(pipein));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public MipsSimulator() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBackground(Color.WHITE);

		getContentPane().setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();
		JPanel centerPanel = new JPanel();

		topPanel.add(new ControlOutputView());
		topPanel.add(new RegistersView());
		topPanel.add(new MemoryView());
		centerPanel.add(sourceView);
		centerPanel.add(new ControlPanel());
		getContentPane().add(topPanel, BorderLayout.NORTH);
		getContentPane().add(centerPanel, BorderLayout.CENTER);

		pack();
	}

	public static void main(String[] args) {
		try {
			JDialog.setDefaultLookAndFeelDecorated(true);
			JFrame.setDefaultLookAndFeelDecorated(true);
			java.awt.Toolkit.getDefaultToolkit().setDynamicLayout(true);
			System.setProperty("sun.awt.noerasebackground", "true");

			MetalLookAndFeel.setCurrentTheme(new st.chimera.theme.ChimeraMetalTheme());
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

			(new MipsSimulator()).setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
