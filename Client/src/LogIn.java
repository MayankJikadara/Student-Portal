import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class LogIn extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	static int index;
	static String userId;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		index = Integer.parseInt(args[0]);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn frame = new LogIn();
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
	public LogIn() {
		setTitle("Log In");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setBounds(162, 68, 165, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(162, 113, 165, 20);
		contentPane.add(passwordField);

		JLabel lblUserId = new JLabel("User Id :");
		lblUserId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUserId.setBounds(49, 69, 103, 17);
		contentPane.add(lblUserId);

		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPassword.setBounds(49, 114, 103, 17);
		contentPane.add(lblPassword);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\mayank\\Desktop\\img\\EasyLogin_Icon (45x48).jpg"));
		lblNewLabel.setBounds(351, 66, 73, 65);
		contentPane.add(lblNewLabel);

		JButton btnViewSubject = new JButton("View Subject");
		btnViewSubject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String subject = Main.getTitle().get(index);
				//				System.out.println(index+": index");
				Main.out.println("Read Only");
				Main.out.println(subject);
				String[] args = {subject};
				viewFile.main(args);
			}
		});
		btnViewSubject.setBounds(33, 170, 130, 23);
		contentPane.add(btnViewSubject);

		JButton btnLogInTo = new JButton("Log In to Discuss");
		btnLogInTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userId = textField.getText();
				String pswd = passwordField.getText();
				//				System.out.println(pswd+": pswd");
				String subject = Main.getTitle().get(index);
				if(userId == null || pswd == null){
					JOptionPane.showMessageDialog(null, "Invalid UserId or Password");
				}
				else{
					Main.out.println("Write Permission");
					Main.out.println(userId);
					Main.out.println(pswd);
					Main.out.println(subject);
					if(Main.in.nextLine().equals("True"))
					{
						//						System.out.println("true sent");
						String[] args = {subject};
						Subject.main(args);
						dispose();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Invalid UserId or Password");
					}
				}
			}
		});
		btnLogInTo.setBounds(199, 170, 158, 23);
		contentPane.add(btnLogInTo);
	}
}
