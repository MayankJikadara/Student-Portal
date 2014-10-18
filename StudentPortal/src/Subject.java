import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.BoxLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;
import java.awt.FlowLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JScrollPane;


public class Subject extends JFrame {

	private static final int BUFFER_SIZE = 0;
	static JTextArea textArea;
	private JPanel contentPane;
	private static String subject;
//	static Subject frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		subject = args[0];
//		System.out.println("Subject :"+subject);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Subject frame = new Subject();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	
	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Subject() throws ClassNotFoundException, IOException {
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setSize(getMaximumSize());
		
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		
		final JTextArea textArea_1 = new JTextArea();
		////////////////////////////////////////////////////
		final ObjectOutputStream oos = new ObjectOutputStream(Main.clientSocket.getOutputStream());  
        final ObjectInputStream ois = new ObjectInputStream(Main.clientSocket.getInputStream());  
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
            if(buffer.toString() == "False")
            	textArea.setText("Be the first to comment :");
            else
            	textArea.setText(new String(buffer));  
            
        } while (bytesRead == BUFFER_SIZE);

        JButton Submit = new JButton("Submit");
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
       
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 401, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(textArea_1, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(Submit, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
						.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(Submit, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
						.addComponent(textArea_1, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)))
		);
		
		Submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.out.println("< "+LogIn.userId+" > :"+textArea_1.getText());
				
//				try {
//					ObjectOutputStream oos = new ObjectOutputStream(Main.clientSocket.getOutputStream());
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}  
//				
//		        ObjectInputStream ois = null;
//				try {
//					ois = new ObjectInputStream(Main.clientSocket.getInputStream());
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}  
		        byte [] buffer = new byte[BUFFER_SIZE];
		        Object o = null;
				try {
					o = ois.readObject();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        	Integer bytesRead = 0;  

		        	do {  
		        	 try {
						o = ois.readObject();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}  

		        		if (!(o instanceof Integer)) {  
		        			JOptionPane.showMessageDialog(null, "Something is wrong");  
		        		}  

		        		bytesRead = (Integer)o;  
//		        		if(bytesRead == 0)
//		        			textArea.setText("Be the first to comment");
//		        		else{
		        		try {
							o = ois.readObject();
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}  

		        		if (!(o instanceof byte[])) {  
		        			JOptionPane.showMessageDialog(null, "Something is wrong");  
		        		}  

		        		buffer = (byte[])o;  
		        		// 3. Write data to output file.  
		        		textArea.setText(textArea.getText()+new String(buffer));  
		        	} while (bytesRead == BUFFER_SIZE);

			}
		});
		contentPane.setLayout(gl_contentPane);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent winEvt) {
                Main.out.println("\\quit");
                dispose();
            }
        });
        
	}
}