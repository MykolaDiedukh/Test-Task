package Code;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.PreparedStatement;

public class conn {
	public static Connection conn;
	public static Statement statmt;
	public static ResultSet resSet;	  
	private static int id;
	private static String author;
	private static String book;
	public static ArrayList<Integer> colums = new ArrayList<Integer>();	 
	// --------Connect to Data Base--------
	public static void Conn() throws ClassNotFoundException, SQLException {
		   conn = null;
		   Class.forName("org.sqlite.JDBC");
		   conn = DriverManager.getConnection("jdbc:sqlite:LibraryManaging.s3db");
		   System.out.println("Data Base Connected!");
	   }	
	// --------Create table--------
	public static void CreateDB() throws ClassNotFoundException, SQLException{
		statmt = conn.createStatement();
		statmt.execute("CREATE TABLE if not exists 'books' ('id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
				+ "'author' TEXT NOT NULL, 'book' TEXT NOT NULL);");		
		System.out.println("Table is exists or already made.");
	   }
	// --------Delete book by name from table---------
	public static void DeleteBook(String name) throws ClassNotFoundException, SQLException{
		resSet = statmt.executeQuery("SELECT * FROM books WHERE book like '"+name+"'");
		while (resSet.next()){			
			int id = resSet.getInt("id");
			colums.add(id);
			author = resSet.getString("author");
			book = resSet.getString("book");
			System.out.println("Number: "+id +" Author: "+author +" \""+ book+"\"");
		}
		if (colums.size()==1){
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM books WHERE id = ?");
			pstmt.setInt(1, colums.get(0));
			pstmt.executeUpdate();
			System.out.println("P: Book was delete");
		} if (colums.size()>1){
			System.out.println("We have few books with such name please choose one by typing a number of book");
			 int row = new Scanner(System.in).nextInt();
			 PreparedStatement pstmt = conn.prepareStatement("DELETE FROM books WHERE id = ?");
				pstmt.setInt(1, row);
				pstmt.executeUpdate();
				System.out.println("P: Book was delete");
		} if (colums.size()==0){System.out.println("P: Book not found");}
		colums.clear();
	}
	//---------Update name of book----------
	public static void UpdateName(String name, String newName) throws ClassNotFoundException, SQLException{
		resSet = statmt.executeQuery("SELECT * FROM books WHERE book like '"+name+"'");
		while (resSet.next()){			
			int id = resSet.getInt("id");
			colums.add(id);
			author = resSet.getString("author");
			book = resSet.getString("book");
			System.out.println("Number: "+id +" Author: "+author +" \""+ book+"\"");
		}
		if (colums.size()==1){
			PreparedStatement pstmt = conn.prepareStatement("UPDATE books SET book = ? WHERE id = ?");
			pstmt.setString(1, newName);
			pstmt.setInt(2, colums.get(0));
			pstmt.executeUpdate();
			System.out.println("P: Name was change");
		} if (colums.size()>1){
			System.out.println("We have few books with such name please choose one by typing a number of book");
			 int row = new Scanner(System.in).nextInt();
			 PreparedStatement pstmt = conn.prepareStatement("UPDATE books SET book = ? WHERE id = ?");
				pstmt.setString(1, newName);
				pstmt.setInt(2, row);
				pstmt.executeUpdate();
				System.out.println("P: Name was change");
		} if (colums.size()==0) {System.out.println("P: Book not found");}
		colums.clear();
	}
	// --------Add new book into table--------
	public static void WriteDB(String author, String book) throws SQLException{
		PreparedStatement st = conn.prepareStatement("INSERT INTO 'books' ('author', 'book') VALUES (?, ?)");
		st.setString(1, author);
		st.setString(2, book);
		st.execute();		  
		System.out.println("P: Author"+author +" \""+ book+"\" was added");
	}
	
	// -------- Show all books from table--------
	public static void ReadDB() throws ClassNotFoundException, SQLException{
		resSet = statmt.executeQuery("SELECT * FROM books ORDER BY book ASC");
		while(resSet.next()){
			id = resSet.getInt("id");
			author = resSet.getString("author");
			book = resSet.getString("book");
	         System.out.println("\nAuthor: " + author+ ", Name: \"" + book +"\".");
		}	
		System.out.println("P: Done");
	    }
		// --------CLose Date Base--------
	public static void CloseDB() throws ClassNotFoundException, SQLException{
		conn.close();
		statmt.close();
		if (resSet != null) resSet.close();			
		System.out.println("P: Program is closed");
	}

}