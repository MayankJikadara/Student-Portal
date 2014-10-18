import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Window.Type;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;


public class topic extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					topic frame = new topic();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public topic() {
		setTitle("Add Topic");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Create a new Subject :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(53, 35, 244, 42);
		contentPane.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(53, 75, 340, 48);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.out.println("Create File ");
				Main.out.println(textField.getText());
				String s = Main.in.nextLine();
				//				System.out.println();
				if(s.equals("True")){
					Main.btnRefresh.doClick();
					dispose();
				}
				else{
					JOptionPane.showMessageDialog(null, "File already exists or something went wrong");
				}
			}
		});
		btnSubmit.setBounds(166, 168, 89, 23);
		contentPane.add(btnSubmit);
	}

}
