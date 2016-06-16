package com.mikolaj.nalecz.studia.aal;

import java.awt.EventQueue;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.naming.ldap.Rdn;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.mikolaj.nalecz.studia.aal.logika.LineSegmentsSolver;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Gui {

	private JFrame frame;
	private JTextField textField;
	private LineSegmentsSolver solver = new LineSegmentsSolver();
	private ScrollPane scrollPane;
	private TextArea textArea_1;
	private JTextField textField_1;
	private JRadioButton rdbtnRandom;
	private JRadioButton rdbtnRandomV;
	private JRadioButton rdbtnRandomV_1;
	private JCheckBox chckbxSortedset;
	private JCheckBox chckbxPomijaj;
	private JLabel lblOpcje;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 859, 556);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		Lines panel = new Lines(solver);
		panel.setBounds(104, 10, 500, 500);
		frame.getContentPane().add(panel);

		JCheckBox chckbxRepaint = new JCheckBox("graphics off");
		chckbxRepaint.setBounds(1, 71, 97, 23);
		frame.getContentPane().add(chckbxRepaint);

		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				long startTime = System.nanoTime();
				if (rdbtnRandom.isSelected())
					solver.dodajLosowe(Integer.valueOf(textField.getText()), 500);
				if (rdbtnRandomV.isSelected())
					solver.dodajLosowe2(Integer.valueOf(textField.getText()), 500);
				if (rdbtnRandomV_1.isSelected())
					solver.dodajLosowe3(Integer.valueOf(textField.getText()), 500);
				textField_1.setText(String.valueOf((System.nanoTime() - startTime) / 1000));
				textArea_1.setText(solver.toString());
				if (chckbxRepaint.isSelected() == false)
					panel.repaint();
			}
		});
		btnGenerate.setBounds(1, 10, 89, 23);
		frame.getContentPane().add(btnGenerate);

		textField = new JTextField();
		textField.setText("10");
		textField.setBounds(4, 44, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		textArea_1 = new TextArea();
		textArea_1.setEditable(false);
		textArea_1.setBounds(606, 10, 237, 500);
		frame.getContentPane().add(textArea_1);

		scrollPane = new ScrollPane();
		scrollPane.setBounds(606, 10, 237, 500);
		frame.getContentPane().add(scrollPane);

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(0, 101, 86, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		rdbtnRandom = new JRadioButton("Random v1");
		rdbtnRandom.setSelected(true);
		rdbtnRandom.setBounds(1, 129, 109, 23);
		frame.getContentPane().add(rdbtnRandom);

		rdbtnRandomV = new JRadioButton("Random v2");
		rdbtnRandomV.setBounds(1, 155, 109, 23);
		frame.getContentPane().add(rdbtnRandomV);

		rdbtnRandomV_1 = new JRadioButton("Random v3");
		rdbtnRandomV_1.setBounds(1, 181, 109, 23);
		frame.getContentPane().add(rdbtnRandomV_1);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnRandomV);
		buttonGroup.add(rdbtnRandomV_1);
		buttonGroup.add(rdbtnRandom);
		
		chckbxSortedset = new JCheckBox("TreeSet");
		chckbxSortedset.setToolTipText("Po zaznaczeniu tej opcji program bedzie uzywal TreeSet'u zamiast LinkedListy.\r\nEfektem tego bedzie spowolnienie laczenia grup odcinkow z O(1) do O(n).\r\nJezeli zaznaczona jest opcja \"Pomijaj\", pomijanie bedzie dzialalo duzo bardziej \r\nefektywnie, gdyz zamiast jednego odcinka, beda pomijane\r\nwszystkie odcinki po pierwszym pominietym. Jest to mozliwe poniewaz sa one posortowane od lewego do prawego.");
		chckbxSortedset.setBounds(1, 232, 97, 23);
		frame.getContentPane().add(chckbxSortedset);
		chckbxSortedset.addItemListener(new ItemListener() {
		      public void itemStateChanged(ItemEvent e) {
		    	  solver.setTreeSet(chckbxSortedset.isSelected());
		        }
		      });
		
		chckbxPomijaj = new JCheckBox("Pomijaj");
		chckbxPomijaj.setBounds(1, 258, 97, 23);
		frame.getContentPane().add(chckbxPomijaj);
		chckbxPomijaj.addItemListener(new ItemListener() {
		      public void itemStateChanged(ItemEvent e) {
		    	  solver.setPomijaj(chckbxPomijaj.isSelected());
		        }
		      });
		
		lblOpcje = new JLabel("Opcje");
		lblOpcje.setHorizontalAlignment(SwingConstants.CENTER);
		lblOpcje.setBounds(11, 211, 46, 14);
		frame.getContentPane().add(lblOpcje);

	}
}
