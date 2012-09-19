/*
 * 作成日: 2003/08/01
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
package st.chimera.mips.simulator.test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import st.chimera.mips.simulator.*;
import st.chimera.mips.simulator.gate.*;
import st.chimera.mips.simulator.logic.*;

/**
 * @author chimera
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
public class LogicTest extends JFrame implements Runnable {

	JButton inputButton;
	JTextField inputField;
	JTextField outputField1;
	JTextField outputField2;
	JTextField readField1;
	JTextField readField2;
	JTextField writeField;

	DataPath input;
	DataPath output1;
	DataPath output2;
	DataPath readNumber1;
	DataPath readNumber2;
	DataPath writeNumber;
	DataPath const1;

	Registers reg;

	public LogicTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*
				reg = new Registers();
				input = new DataPath(32);
				readNumber1 = new DataPath(5);
				readNumber2 = new DataPath(5);
				writeNumber = new DataPath(5);
				const1 = new DataPath(1);
		
				reg.setInput(input);
				reg.setReadNumber1(readNumber1);
				reg.setReadNumber2(readNumber2);
				reg.setWriteNumber(writeNumber);
				reg.setWrite(const1);
				output1 = reg.getOutput1();
				output2 = reg.getOutput2();
				const1.set(1);
		
				readField1 = new JTextField(8);
				readField2 = new JTextField(8);
				writeField = new JTextField(8);
				inputField = new JTextField(8);
				outputField1 = new JTextField(8);
				outputField2 = new JTextField(8);
				inputButton = new JButton("設定");
		
				getContentPane().setLayout(new FlowLayout());
				getContentPane().add(inputField);
				getContentPane().add(writeField);
				getContentPane().add(readField1);
				getContentPane().add(readField2);
				getContentPane().add(outputField1);
				getContentPane().add(outputField2);
				getContentPane().add(inputButton);
		
				inputButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						input.set(Integer.parseInt(inputField.getText()));
						writeNumber.set(Integer.parseInt(writeField.getText()));
						readNumber1.set(Integer.parseInt(readField1.getText()));
						readNumber2.set(Integer.parseInt(readField2.getText()));
					}
				});
		*/

		inputButton = new JButton("設定");
		getContentPane().setLayout(new FlowLayout());
		getContentPane().add(inputButton);
		inputButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Architecture.getInstance().startClock();
			}
		});

		pack();

		//(new Thread(this)).start();
	}

	public void run() {
		while (true) {
			outputField1.setText(Integer.toString(output1.get()));
			outputField2.setText(Integer.toString(output2.get()));
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
		}
	}

	public static void main(String[] args) {
		(new LogicTest()).setVisible(true);
	}
}
