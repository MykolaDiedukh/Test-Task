package Code;
import java.util.Scanner;
import java.sql.SQLException;

public class Main {
	
	static Boolean running = true;
	static Scanner in = new Scanner(System.in);
	public static void main(String [] args)throws ClassNotFoundException, SQLException {
		conn.Conn();
		conn.CreateDB();
		System.out.println("\nEnter 1 for add book."+
				"\nEnter 2 for remove book"+
				"\nEnter 3 for edit book"+
				"\nEnter 4 show all books from library"+
				"\nEnter 5 to close program");
		
		while (running) {
			System.out.print("\nU: ");
			String answer = in.next();
			switch (answer) {
			case "1":
				addBook();
				break;
			case "2":
				removeBook();
				break;
			case "3":
				ChangeBook();
				break;
			case "4":
				System.out.println("P: all books");
				conn.ReadDB();
				break;
			case "5":
				closeProgram();
				break;
			default: System.out.println("P: Command not found");
				break;
			}
		}
	}
	//--------Method of remove book-----------------
	private static void removeBook()throws ClassNotFoundException, SQLException {
		String name;
		System.out.println("\nP: Enter name of book what you like to delete");
		in.nextLine();//throw away the \n 
		name = in.nextLine();
		//System.out.println("\nP: Enter new name of book what you like to change");
		//newName = in.nextLine();
		conn.DeleteBook(name);	
	}
	//--------Method of change name of book----------
	private static void ChangeBook() throws ClassNotFoundException, SQLException {
		String name, newName;
		System.out.println("\nP: Enter name of book what you like to change");
		in.nextLine();//throw away the \n 
		name = in.nextLine();
		System.out.println("\nP: Enter new name of book what you like to change");
		newName = in.nextLine();
		conn.UpdateName(name, newName);		
	}
	//--------Method of close program------------
	private static void closeProgram() throws ClassNotFoundException, SQLException {
		conn.CloseDB();
		System.exit(0);		
	}
	//--------Method of add book----------------
	private static void addBook() throws ClassNotFoundException, SQLException {
		String author, book;
		System.out.println("\nP: Enter Author of book: ");
		in.nextLine();//throw away the \n 
		author = in.nextLine();		
		System.out.println("\nP: Enter name of book: ");
		book = in.nextLine();
		conn.WriteDB(author, book);				
	}
}
