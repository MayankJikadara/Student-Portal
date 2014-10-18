import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;


public class viewFile extends JFrame {

	private static final Integer BUFFER_SIZE = 1000;
	private static String subject;
	private JPanel contentPane;
	private static JTextArea textArea;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		subject = args[0];
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viewFile frame = new viewFile();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public viewFile() throws IOException, URISyntaxException, ClassNotFoundException {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		textArea = new JTextArea();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
						.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
						.addGap(4))
				);
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(textArea, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
				);
		contentPane.setLayout(gl_contentPane);

		ObjectOutputStream oos = new ObjectOutputStream(Main.clientSocket.getOutputStream());  
		ObjectInputStream ois = new ObjectInputStream(Main.clientSocket.getInputStream());  
		byte [] buffer = new byte[BUFFER_SIZE];
		Object o = ois.readObject();
		Integer bytesRead = 0;  

		do {  
			o = ois.readObject();  

			if (!(o instanceof Integer)) {  
				JOptionPane.showMessageDialog(null, "Something is wrong");  
			}  

			bytesRead = (Integer)o;  
			o = ois.readObject();  

			if (!(o instanceof byte[])) {  
				JOptionPane.showMessageDialog(null, "Something is wrong");  
			}  

			buffer = (byte[])o;  
			// 3. Write data to output file.  
			textArea.setText(new String(buffer));  
		} while (bytesRead == BUFFER_SIZE); 

	}
}