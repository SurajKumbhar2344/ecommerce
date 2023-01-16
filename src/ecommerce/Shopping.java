package ecommerce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Shopping {
	
	static int UID;
	static int CUID;
	public static void main(String args[]) throws IOException
	{
		DatabaseConnection.makeDatabase();  //Here we call makeDatabase Method present DatabaseConnection
		
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  // we use Buffered reader (Not Scanner class)
		                                                                           //becouse it is faster than scanner and no need to close resource(no leak)
		System.out.println("WELCOME TO ECOMMERCE SHOPPING \n");
		
		int ch;
		do
		{									//This is main page of project
			System.out.println("*************************WELCOME TO VELOMART************************\n");
			System.out.println("                    1 - ADMIN REGISTRATION");
			System.out.println("                    2 - CUSTOMER REGISTRATION");
			System.out.println("                    3 - LOGIN (ALREADY HAVE ACCOUNT)");
			System.out.println("                    4 - EXIT");
			System.out.println("\n********************************************************************\n");
			
			System.out.print("         Enter choice : ");  //u have to enter choice
			
			ch=Integer.parseInt(br.readLine());  //choice stored in ch
			
			if(ch==1)			//conditions
				registerAdmin();
			else if(ch==2)
				registerCustomer();
			else if(ch==3)
				loginSystem();
			else if(ch==4)
				System.out.println("Thank you");
			else
				System.out.println("Wrong choice");
		}while(ch!=4);
		
	}
	
	static void loginSystem()throws IOException         // method for login window
	{
		String chc;
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("*******************************************************");
		System.out.println("\n             WELCOME TO LOGIN PAGE\n");
		System.out.println("*******************************************************\n");
		
									//login info fetch data
		ArrayList<Integer> id=new ArrayList<Integer>();
		ArrayList<String> pass=new ArrayList<String>();
		ArrayList<Character> type=new ArrayList<Character>();
		
		try
		{
			int uid;
			String passw;  
			char tp=' ';
			Class.forName("com.mysql.jdbc.Driver");			// creating Connection to database
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce?autoReconnect=true&useSSL=false","root",DatabaseConnection.root);
			PreparedStatement ps=con.prepareStatement("select * from logininfo",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs=ps.executeQuery();                // Used result set
			
			while(rs.next())
			{
				id.add(Integer.parseInt(rs.getString("userID")));
				pass.add(rs.getString("password"));
				type.add((rs.getString("userType")).charAt(0));
			}
			
			
			
			int flag1=0,flag2=0;			// checking login info
			int f1,f2;
			do
			{
				System.out.print("          Enter USER ID : ");
				uid=Integer.parseInt(br.readLine());
				System.out.print("          Enter PASSWORD : ");
				passw=br.readLine();
				f1=id.indexOf(uid);
				f2=pass.indexOf(passw);
				if(f1==f2 && (f1!=-1 && f2!=-1))
				{
					flag1=1;
					flag2=1;
				}
				if(flag1==0 || flag2==0)
				{
					System.out.println("     INVALID CREDENTIALS , ENTER AGAIN !");
					System.out.print("     Do you want to continue ( Y for yes, N for No)");
					chc=br.readLine();
					if(chc.equalsIgnoreCase("N"))
						break;
				}
					
			}while(flag1==0 || flag2==0);
			
			if(flag1==1 && flag2==1)
			{
				tp=type.get(id.indexOf(uid));
			}
			
			if(tp=='A')           // Here A means Admin
			{
				Admin ob=new Admin(uid,passw);
				ob.AdminPage();          //calling AdminpPage()
			} 
			else if(tp== 'C')     // Here C means Customer
			{
				Customer obb=new Customer(uid,passw);
				obb.CustomerPage();              //calling CustomerPage()
			}
		
		}
		catch(Exception e)
		{
			
			e.printStackTrace();      //Exception
		}
		
	}
	
	static void registerAdmin()throws IOException     //Registering Admin
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String pass,name,num,addr,email;
		
		int age;
		System.out.println("***************************************************************");
		System.out.println("\n        WELCOME TO ADMIN REGISTRATION PAGE\n");
		System.out.println("***************************************************************\n");
		setUID();
		System.out.println("       ADMIN ID = "+UID);
		System.out.print("         Enter password = ");
		pass=br.readLine();
		System.out.print("         Enter Name = ");
		name=br.readLine();
		System.out.print("         Enter age = ");
		age=Integer.parseInt(br.readLine());
		System.out.print("         Enter contact number = ");
		num=br.readLine();
		System.out.print("         Enter address = ");
		addr=br.readLine();
		System.out.print("         Enter email = ");
		email=br.readLine();
		
		//inserting data 
		try
		{
		Class.forName("com.mysql.jdbc.Driver");     // creating database and throwing sql query
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce?autoReconnect=true&useSSL=false","root",DatabaseConnection.root);
		PreparedStatement ps=con.prepareStatement("insert into adminInfo(AdminID,Name,Age,Email,Address,ContactNumber) values(?,?,?,?,?,?)");
		PreparedStatement ps1=con.prepareStatement("insert into loginInfo(userID,password,userType) values(?,?,?)");
		ps.setString(1, Integer.toString(UID));
		ps.setString(2, name);
		ps.setString(3,Integer.toString(age));
		ps.setString(4, email);
		ps.setString(5, addr);
		ps.setString(6, num);
		ps1.setString(1, Integer.toString(UID));
		ps1.setString(2,pass);
		ps1.setString(3, Character.toString('A'));
		int x=ps.executeUpdate();
		int y=ps1.executeUpdate();
		if(x>0 && y>0)
			System.out.println("    -----REGISTRATION DONE SUCCESSFULLY !-----\n");
		else
			System.out.println("  ******REGISTRATION FAILED !******\n");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
	
	
	static void registerCustomer()throws IOException     //Registering Customer
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String pass,name,num,addr,email;
		
		int age;
		System.out.println("**************************************************************");
		System.out.println("        WELCOME TO CUSTOMER REGISTRATION PAGE\n");
		System.out.println("**************************************************************\n");
		setCUID();
		System.out.println("       CUSTOMER ID = "+CUID);
		System.out.print("         Enter password = ");
		pass=br.readLine();
		System.out.print("         Enter Name = ");
		name=br.readLine();
		System.out.print("         Enter age = ");
		age=Integer.parseInt(br.readLine());
		System.out.print("         Enter contact number = ");
		num=br.readLine();
		System.out.print("         Enter address = ");
		addr=br.readLine();
		System.out.print("         Enter email = ");
		email=br.readLine();
		
		//inserting data 
		try
		{
		Class.forName("com.mysql.jdbc.Driver");               //database Connection  and inserting data
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce?autoReconnect=true&useSSL=false","root",DatabaseConnection.root);
		PreparedStatement ps=con.prepareStatement("insert into custInfo(CustID,Name,Age,Email,Address,ContactNumber) values(?,?,?,?,?,?)",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		PreparedStatement ps1=con.prepareStatement("insert into loginInfo(userID,password,userType) values(?,?,?)",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ps.setString(1, Integer.toString(CUID));
		ps.setString(2, name);
		ps.setString(3,Integer.toString(age));
		ps.setString(4, email);
		ps.setString(5, addr);
		ps.setString(6, num);
		ps1.setString(1, Integer.toString(CUID));
		ps1.setString(2,pass);
		ps1.setString(3, Character.toString('C'));
		int x=ps.executeUpdate();
		int y=ps1.executeUpdate();
		if(x>0 && y>0)
			System.out.println("   -------REGISTRATION DONE SUCCESSFULLY !---------\n");
		else
			System.out.println(" ********REGISTRATION FAILED ! *********\n");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
	static void setCUID()      //we used setCUID() to set customer user id and it will increment by 1 by each call
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");    // database
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce?autoReconnect=true&useSSL=false","root",DatabaseConnection.root);
			PreparedStatement ps=con.prepareStatement("select CustID from custinfo");
			ResultSet rs=ps.executeQuery();
			int x=1;
			while(rs.next()) {
				x=Integer.parseInt(rs.getString("CustID"));
			}
			CUID=x+1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//System.out.println(e);
		}
	}
	static void setUID()  // it will set user id ,and incremented by 1
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");   // database
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce?autoReconnect=true&useSSL=false","root",DatabaseConnection.root);
			PreparedStatement ps=con.prepareStatement("select AdminID from admininfo");
			ResultSet rs=ps.executeQuery();
			int x=1000;           //starting from 1001
			while(rs.next()) {
				x=Integer.parseInt(rs.getString("AdminID"));
			}
			UID=x+1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
	}
}



