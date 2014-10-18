import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.*;

public class Login {

	Connection client;
	static Statement st;
	static ResultSet rs;

	// The server socket.
	static ServerSocket serverSocket = null;
	// The client socket.
	static Socket clientSocket = null;

	// This chat server can accept up to maxClientsCount clients' connections.
	private static final int maxClientsCount = 10;
	private static final clientThread[] threads = new clientThread[maxClientsCount];



	public Login() throws SQLException
	{
		connect();

	}
	public static ArrayList<ArrayList<String>> gettopiclist() throws SQLException
	{
		String sql="select TopicName,Path from Topics";
		ArrayList<ArrayList<String>> feedback = new ArrayList<ArrayList<String>>();
		ArrayList<String> feed = null;

		try {
			ResultSet rs = st.executeQuery(sql);

			ResultSetMetaData rsm = rs.getMetaData();
			feed = new ArrayList<String>();

			for(int y = 1;y<rsm.getColumnCount();y++){

				feed.add(rsm.getColumnName(y));
			}
			feedback.add(feed);

			while(rs.next()){
				feed = new ArrayList<String>();
				for(int i=1;i<=rsm.getColumnCount();i++){

					feed.add(rs.getString(i));
				}
				feedback.add(feed);
			}



		} catch (SQLException e) {
			//handler
		}
		return feedback;


	}
	public static ArrayList<ArrayList<String>> getuserlist(PrintStream os) throws SQLException
	{
		String sql="select FName,LName,GPA,UserID,Password from Table2";
		String sql2="select UserID from Table2"; 
		ArrayList<ArrayList<String>> feedback = new ArrayList<ArrayList<String>>();
		ArrayList<String> feed = null;
		ResultSet rs2=st.executeQuery(sql2);
		int count=0;
		while(rs2.next())
		{
			count++;
		}
		System.out.println("Sending the count of number of users "+count);
//		System.out.println("HERE");
		try {
			ResultSet rs = st.executeQuery(sql);
						
			
			os.println(count);

			ResultSetMetaData rsm = rs.getMetaData();
			feed = new ArrayList<String>();

			for(int y = 1;y<rsm.getColumnCount();y++){

				feed.add(rsm.getColumnName(y));
			}
			feedback.add(feed);
			

			while(rs.next()){
				feed = new ArrayList<String>();
				for(int i=1;i<=rsm.getColumnCount();i++){

					feed.add(rs.getString(i));
				}
				feedback.add(feed);
			}



		} catch (SQLException e) {
			//handler
			System.out.println(e);
		}
		return feedback;


	}
	public static void senduserlist(PrintStream os) throws SQLException
	{
		ArrayList<ArrayList<String>> reply=	getuserlist(os);
		
		int i=1;
		System.out.println("Sending the user list row-wise");
		for(ArrayList<String> tmp : reply)
		{
			if(i!=1)
			{
				os.println(tmp.toString());
				System.out.println("Sending User data to Admins  "+tmp.toString());
			}
			i++;
		}

		os.println("ExitR");
	}
	public static void sendtopiclist(PrintStream os) throws SQLException
	{
		ArrayList<ArrayList<String>> reply=	gettopiclist();
		int i=1;
		System.out.println("Sending Topic List to Client");
		for(ArrayList<String> tmp : reply)
		{
			if(i!=1)
			{
				os.println(tmp.get(0));
				System.out.println(tmp.get(0));
			}
			i++;
		}
		//Just an exit statement for client to know that the list has ended
		os.println("ExitR");
	}
	public static void addtopic(String topic) throws SQLException
	{
		String path="D:\\DSpRACTICE\\Trials\\Files";

		String sql="INSERT INTO Topics(TopicName,Path) VALUES('"+topic+"','"+path+"');";
		st.execute(sql);



	}
	public static boolean searchuser(String user,String pass) throws SQLException 
	{

		String sql="select UserID,Password from Table2 where UserID="+user+"and Password='"+pass+"'";
		rs=st.executeQuery(sql);

		int count=0;	//stores number of rows

		while(rs.next())
		{
			count++;	
			System.out.println("There is a login from the Client Side UserID:"+user);
		}

		if(count==1)//if only 1 exists
		{
			return true;
		}
		else
		{
			return false;
		}


	}
	public static boolean adduser(String fname,String lname,String gpa,String user,String pass)
	{
		String path="D:\\DSpRACTICE\\Trials\\Files";

		String sql="INSERT INTO Table2(FName,LName,GPA,UserID,Password) VALUES('"+fname+"','"+lname+"','"+gpa+"',"+user+",'"+pass+"')";
		try {
			st.execute(sql);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}

	}
	public void connect() throws SQLException 
	{
		// TODO Auto-generated method stub
		String driver="sun.jdbc.odbc.JdbcOdbcDriver";
		try 
		{
			Class.forName(driver);
			String db="jdbc:odbc:Credentials";
			client=DriverManager.getConnection(db);
			st=client.createStatement();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void main(String[] args) throws SQLException 
	{
		// TODO Auto-generated method stub
		Login user=new Login();

		// The default port number.
		int portNumber = 12324;
		/*
		 * Open a server socket on the portNumber (default 2222). Note that we can
		 * not choose a port less than 1023 if we are not privileged users (root).
		 */
		try {
			serverSocket = new ServerSocket(portNumber,0,InetAddress.getByName(Inet4Address.getLocalHost().getHostAddress()));
		} catch (IOException e) {
			System.out.println(e);
		}


		/*
		 * Create a client socket for each connection and pass it to a new client
		 * thread.
		 */
		while (true) 
		{
			try 
			{
				clientSocket = serverSocket.accept();
				int i = 0;
				for (i = 0; i < maxClientsCount; i++) 
				{
					if (threads[i] == null) 
					{
						(threads[i] = new clientThread(clientSocket, threads)).start();

						break;
					}
				}
				if (i == maxClientsCount) 
				{
					PrintStream os = new PrintStream(clientSocket.getOutputStream());
					os.println("Server too busy. Try later.");
					os.close();
					clientSocket.close();
				}
			}catch (IOException e) 
			{
				System.out.println(e);
			}
		}
	}
}

class clientThread extends Thread 
{

	private String clientName = null;
	private  DataInputStream is;
	private PrintStream os ;
	private Socket clientSocket = null;
	private final clientThread[] threads;
	private int maxClientsCount;

	public clientThread(Socket clientSocket, clientThread[] threads) 
	{
		this.clientSocket = clientSocket;
		this.threads = threads;
		maxClientsCount = threads.length;
	}

	public void run() 
	{
		int maxClientsCount = this.maxClientsCount;
		clientThread[] threads = this.threads;

		try {
			/*
			 * Create input and output streams for this client.
			 */
			is = new DataInputStream(clientSocket.getInputStream());
			os = new PrintStream(clientSocket.getOutputStream());

			String feed=null;
			//	      os.print(true);
			try 
			{
				os.flush();
				Login.sendtopiclist(os);
				System.out.println("Sending data.....");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while (true) 
			{
				os.flush();
				feed = is.readLine().trim();
			
				if(feed!=null)
				{
					//	        	os.println("Rec");
					//	        	System.out.println("REc");
					System.out.println("Got the following query from Client:"+feed);

					if(feed.startsWith("Create File"))
					{

						File file = null;
						String name=is.readLine().trim();
						System.out.println(name+"dasda");
						if(name != null){
							file = new File("D:\\DSpRACTICE\\Trials\\Files\\"+name);

						}
						try {
							if(!file.createNewFile())
							{
								os.println("False");
								System.out.println(false);
							}
							else
							{
								FileWriter fw = new FileWriter(file,true); //the true will append the new data
								fw.write("\nSubject : "+name);//appends the string to the file
								fw.close();
								try {
									Login.addtopic(name);
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								os.println("True");
							}

						} catch (HeadlessException x) {
							// TODO Auto-generated catch block
							x.printStackTrace();
						} catch (IOException y) {
							// TODO Auto-generated catch block
							y.printStackTrace();
						}
					}
					else if(feed.startsWith("Refresh"))
					{
						try {
							Login.sendtopiclist(os);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if(feed.startsWith("Read Only"))
					{
						String sub=is.readLine().trim();
						String path="D:\\DSpRACTICE\\Trials\\Files\\";
						File file = new File(path+""+sub);


						System.out.println(path+""+sub);
						ObjectInputStream ois = new ObjectInputStream(Login.clientSocket.getInputStream());  
						ObjectOutputStream oos = new ObjectOutputStream(Login.clientSocket.getOutputStream());  

						oos.writeObject(file.getName());  

						FileInputStream fis = new FileInputStream(file);  
						byte [] buffer = new byte[1000];  
						Integer bytesRead = 0;  
						boolean flag=false;
						while ((bytesRead = fis.read(buffer)) > 0) 
						{  
							oos.writeObject(bytesRead);
							System.out.println(bytesRead);
							oos.writeObject(Arrays.copyOf(buffer, buffer.length));  
							flag=true;
						}
						if(!flag){
							os.println("False");
							System.out.println("The TopicList is completely Empty.Sending this Information to client");
						}
					}
					else if(feed.startsWith("Write"))
					{
						String userid=is.readLine().trim();
						System.out.println(userid);
						String pass=is.readLine().trim();
						String subject=is.readLine().trim();
						System.out.println(pass);
						System.out.println(userid+" "+pass);
						boolean flag=false;
						try {
							flag = Login.searchuser(userid, pass);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						if(flag)
						{	


							os.println("True");
							System.out.println("User has got full authentication to Write File :"+subject);
							String path="D:\\DSpRACTICE\\Trials\\Files\\";
							File file = new File(path+""+subject);  
							ObjectInputStream ois = new ObjectInputStream(Login.clientSocket.getInputStream());  
							ObjectOutputStream oos = new ObjectOutputStream(Login.clientSocket.getOutputStream());  

							oos.writeObject(file.getName());  

							FileInputStream fis = new FileInputStream(file);  
							byte [] buffer = new byte[1000];  
							Integer bytesRead = 0; 
							while ((bytesRead = fis.read(buffer)) > 0) 
							{  
								oos.writeObject(bytesRead);
								System.out.println(bytesRead);
								oos.writeObject(Arrays.copyOf(buffer, buffer.length));  
							}  
							String toadd=is.readLine().trim();
							System.out.println("Got the following statement"+toadd+" to write in subject: "+subject);
							while(!toadd.contains("\\quit"))
							{
								
								try
								{
									String filename= path+""+subject;
									FileWriter fw = new FileWriter(filename,true); //the true will append the new data
									fw.write("\n"+toadd);//appends the string to the file
									fw.close();


									oos.writeObject(file.getName());  


									buffer = new byte[1000];  
									bytesRead = 0; 
									while ((bytesRead = fis.read(buffer)) > 0) 
									{  
										oos.writeObject(bytesRead);
										System.out.println(bytesRead);
										oos.writeObject(Arrays.copyOf(buffer, buffer.length));  
									}
									toadd=is.readLine().trim();
								}
								catch(IOException ioe)
								{
									System.err.println("IOException: " + ioe.getMessage());
								}
								System.out.println("Got the following statement"+toadd+" to write in subject: "+subject);

							}

						}
						else
						{
							os.println("False");
						}
					}
					else if(feed.startsWith("Signup"))
					{
						String fname=is.readLine().trim();
						String lname=is.readLine().trim();
						String gpa=is.readLine().trim();
						String user=is.readLine().trim();
						String pass=is.readLine().trim();
						System.out.println("User wants to signup :)");
						boolean flag=false;
						flag=Login.adduser(fname, lname, gpa, user, pass);
						if(flag)
							os.println("True");
						else
							os.println("False");
					}
					else if(feed.startsWith("Admin"))
					{
						String user=is.readLine().trim();
						String pass=is.readLine().trim();
						if(user.equals("admin") && pass.equals("IAmAdmin") )
						{
							os.println("True");
							System.out.println("True");
							try {
								Login.senduserlist(os);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else
							os.println("False");
					}
					else if(feed.startsWith("Exit"))
					{
						break;
					}
				}
			}
			is.close();
			os.close();
			clientSocket.close();
		} catch (IOException e) {

		}
	}
}
