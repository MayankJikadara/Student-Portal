import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ListModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.awt.Component;
import java.awt.Color;
import java.awt.Canvas;
import javax.swing.Box;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class Main {

	private JFrame frmStudentPortal;
	private JTextField textField;
	private JPasswordField passwordField;
	static JButton btnRefresh;
	static String host;
	private static ArrayList<String> title = new ArrayList<>();
	public  static ArrayList<String> getTitle() {
		return title;
	}
	public static void addTitle(String t) {
		title.add(t);
	}

	static Socket clientSocket = null;
	static Scanner in = null;
	static PrintStream out = null;

	public JList list;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		int portNumber = 12324;
		host = "10.100.98.80";

		try {
			clientSocket = new Socket(host, portNumber);
			//			System.out.println("server Connected");
			in = new Scanner(clientSocket.getInputStream());
			out = new PrintStream(clientSocket.getOutputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmStudentPortal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmStudentPortal = new JFrame();
		frmStudentPortal.setBackground(new Color(240, 240, 240));
		frmStudentPortal.setTitle("Student Portal");
		frmStudentPortal.setBounds(100, 100, 450, 300);

		frmStudentPortal.getContentPane().setLayout(null);
		frmStudentPortal.setSize(640, 480);

		JLabel lblTopicList = new JLabel("Topic List");
		lblTopicList.setIcon(new ImageIcon("C:\\Users\\mayank\\workspace\\StudentPortal\\img\\agt_forum.png"));
		lblTopicList.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTopicList.setBounds(384, 11, 95, 26);
		frmStudentPortal.getContentPane().add(lblTopicList);
		//		System.out.println(Main.in.nextLine());
		title = getFileListFromServer();		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(269, 48, 345, 319);
		frmStudentPortal.getContentPane().add(scrollPane);
		//		System.out.println(title.toString()+"]]]]]]]]]]]]]]]]]");
		String []arr = {"No Discussion"};
		if(title.isEmpty())list = new JList(arr);
		else
			list = new JList(title.toArray());
		scrollPane.setViewportView(list);

		btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				out.println("Refresh");
				title = getFileListFromServer();
				list.setListData(title.toArray());
			}
		});
		btnRefresh.setToolTipText("Refresh the forum topic list to see if anyone has added a new topic");
		btnRefresh.setBounds(298, 395, 81, 23);
		frmStudentPortal.getContentPane().add(btnRefresh);

		JButton btnAddNewTopic = new JButton("Add New Topic");
		btnAddNewTopic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				topic.main(null);
			}
		});
		btnAddNewTopic.setToolTipText("Add a new topic that is not in the list.");
		btnAddNewTopic.setBounds(422, 395, 142, 23);
		frmStudentPortal.getContentPane().add(btnAddNewTopic);

		JButton btnAdminLogIn = new JButton("Admin Log In");
		btnAdminLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AdminLogIn.main(null);
			}
		});
		btnAdminLogIn.setIcon(new ImageIcon("C:\\Users\\mayank\\workspace\\StudentPortal\\img\\admin (16x16).jpg"));
		btnAdminLogIn.setBounds(30, 46, 167, 23);
		frmStudentPortal.getContentPane().add(btnAdminLogIn);

		JLabel lblUserId = new JLabel("User ID :");
		lblUserId.setBounds(31, 233, 58, 14);
		frmStudentPortal.getContentPane().add(lblUserId);

		textField = new JTextField();
		textField.setBounds(119, 230, 111, 20);
		frmStudentPortal.getContentPane().add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(119, 261, 111, 20);
		frmStudentPortal.getContentPane().add(passwordField);

		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setBounds(32, 264, 77, 14);
		frmStudentPortal.getContentPane().add(lblPassword);


		textField_1 = new JTextField();
		textField_1.setBounds(119, 198, 111, 21);
		frmStudentPortal.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(119, 167, 111, 20);
		frmStudentPortal.getContentPane().add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setBounds(119, 133, 111, 20);
		frmStudentPortal.getContentPane().add(textField_3);
		textField_3.setColumns(10);

		JButton btnLogIn = new JButton("Sign Up");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String Fname = textField_3.getText().toString();
				String Lname = textField_2.getText().toString();
				String CGPA = textField_1.getText().toString();
				String userId = textField.getText().toString();
				String pswd = passwordField.getText().toString();
				if(Fname.isEmpty()||Lname.isEmpty()||CGPA.isEmpty()||userId.isEmpty()||pswd.isEmpty())
					JOptionPane.showMessageDialog(null, "All Fields are necessary");

				Main.out.println("Signup");
				Main.out.println(Fname+"\n"+Lname+"\n"+CGPA+"\n"+userId+"\n"+pswd);

				if(Main.in.nextLine().equals("True"))
				{
					JOptionPane.showMessageDialog(null, "Successfully signed up for Student portal");
				}
				else
					JOptionPane.showMessageDialog(null, "User ID should be unique");

			}
		});
		btnLogIn.setBounds(86, 292, 89, 23);
		frmStudentPortal.getContentPane().add(btnLogIn);

		JLabel lblFirstName = new JLabel("First Name :");
		lblFirstName.setBounds(30, 136, 79, 14);
		frmStudentPortal.getContentPane().add(lblFirstName);

		JLabel lblLastName = new JLabel("Last Name :");
		lblLastName.setBounds(30, 170, 79, 14);
		frmStudentPortal.getContentPane().add(lblLastName);

		JLabel lblCgpa = new JLabel("CGPA :");
		lblCgpa.setBounds(30, 201, 46, 14);
		frmStudentPortal.getContentPane().add(lblCgpa);
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList)evt.getSource();
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					String [] args = {String.valueOf(index)};
					LogIn.main(args);
				} 
			}
		});

		frmStudentPortal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmStudentPortal.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				Main.out.println("Exit");
				System.exit(0);
			}
		});
	}
	private ArrayList<String> getFileListFromServer() {
		// TODO Auto-generated method stub
		ArrayList<String> tmp = new ArrayList<>();
		while(Main.in.hasNextLine()){
			String kj = Main.in.nextLine();
			if(kj.equals("ExitR"))
				break;
			tmp.add(kj);
		}
		//		System.out.println(tmp.toString());
		return tmp;
	}
}
