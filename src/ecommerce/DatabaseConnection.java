package ecommerce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DatabaseConnection {
	static String root;
	public static void makeDatabase()throws IOException
	{
		
		
		int x;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			int flag=1;
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			System.out.println("OPENING VELOMART ECOMMERCE ");
			System.out.println("     LOADING ......");
			do
			{
				System.out.print("ENTER PASSWORD TO PROCEED = ");
				root=br.readLine();
			    try
			    {
			    	Connection check=DriverManager.getConnection("jdbc:mysql://localhost:3306/?autoReconnect=true&useSSL=false","root",root);
			    	flag=1;
			    }
			    catch(Exception e)
			    {
			    	flag=0;
			    	System.out.println("WRONG PASSWORD ENTERED ! ENTER AGAIN !");
			    }
			}while(flag==0);
			PreparedStatement query;
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/?autoReconnect=true&useSSL=false","root",root);
			//creating database ecommerce
		    query=con.prepareStatement("create database ecommerce");
		    x=query.executeUpdate();
		 
			
			
			query=con.prepareStatement("use ecommerce");
		    x=query.executeUpdate();
		    
		    
			
			
			query=con.prepareStatement("create table admininfo ( AdminId int not null, Name varchar(20) not null, Age int not null, Email varchar(30) not null, Address varchar(30) not null, ContactNumber varchar(11) not null, primary key(AdminID) )");
		    x=query.executeUpdate();
		   
			
			
			query=con.prepareStatement("create table custinfo ( CustId int not null, Name varchar(20) not null, Age int not null, Email varchar(30) not null, Address varchar(30) not null, ContactNumber varchar(11) not null, primary key(CustID) )");
		    x=query.executeUpdate();
		   
			
			
			query=con.prepareStatement("create table logininfo ( userID int not null, password varchar(20) not null, userType char not null, primary key(userID) )");
		    x=query.executeUpdate();
		 
			
		
			query=con.prepareStatement("create table products (  productID int not null, Name varchar(20) not null, Type varchar(20) not null, Quantity int not null, Price float not null, primary key(productID) )");
		    x=query.executeUpdate();
		   
		
		    
			query=con.prepareStatement("create table bills ( bill_id int not null, cust_name varchar(20) not null, bill_addr varchar(30) not null, total_amt float not null, primary key(bill_id) )");
		    x=query.executeUpdate();
		    
		    query=con.prepareStatement("INSERT INTO `ecommerce`.`products` (`productID`, `Name`, `Type`, `Quantity`, `Price`) VALUES ('1006', 'apple', 'laptop', '30', '145000'");
		    x=query.executeUpdate();
		    
			
		}
		catch(Exception e)
		{
			System.out.println("");
		}
		System.out.println("DATABASE CONNECTED SUCCESSFULLY.....\n");
	}
	
}
