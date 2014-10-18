import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;


public class Database extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JLabel lblLastName;
	private JLabel lblCgpa;
	private JLabel lblUserId;
	private JLabel lblPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Database frame = new Database();
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
	public Database() {
		int count = Integer.parseInt(Main.in.nextLine());
		//		int count = 3;
				System.out.println(count+": count");
		setTitle("DataBase");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		//		ArrayList<String> user_list = new ArrayList<>();

		String users;
		Object [][]data = new String[count][];
		Object[] header = {"First Name","Last Name","CGPA","ID","Password"};
		int index = 0;
		while(!(users = Main.in.nextLine()).equals("ExitR"))
			//		while(index<3)
		{
			data[index++] = users.substring(1,users.length()-1).split(", ");
			//			user_list.add(users);
		}

		//		TableModel 
		DefaultTableModel model = new DefaultTableModel(data, header);
		table = new JTable(model);

		JLabel lblFirstName = new JLabel("First Name");

		lblLastName = new JLabel("Last Name");

		lblCgpa = new JLabel("CGPA");

		lblUserId = new JLabel("User Id");

		lblPassword = new JLabel("Password");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(table, GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
										.addGap(9))
										.addGroup(gl_contentPane.createSequentialGroup()
												.addGap(9)
												.addComponent(lblFirstName, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
												.addGap(18)
												.addComponent(lblLastName, GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(lblCgpa, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(lblUserId, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
												.addContainerGap())))
				);
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblFirstName)
								.addComponent(lblLastName)
								.addComponent(lblCgpa, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblUserId)
								.addComponent(lblPassword))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(table, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
				);
		contentPane.setLayout(gl_contentPane);



	}
}
