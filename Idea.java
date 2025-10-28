import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Idea {
    public static void main(String[] args)
    {
        Scanner str_input = new Scanner(System.in);
        String choice;
        
        while(true)
        {
            clearScreen();
            System.out.println("\n");
            System.out.println("IBorrow: Book Borrowing Management System");
            System.out.println("[1] Add borrower");
            System.out.println("[2] View borrowers");
            System.out.println("[3] Change borrower info");
            System.out.println("[4] Delete borrower");
            System.out.println("[5] Search a borrower");
            System.out.println("[0] Exit");

            System.out.print("Enter choice: ");
            choice = str_input.nextLine();

            if(choice.equals("1")) 
            {
                try 
                { 
                    clearScreen();
                    System.out.println("\n");
                    
                    AddRecord(); 
                } 
                catch(IOException e) 
                { 
                    e.printStackTrace(); 
                }
            }
            else if(choice.equals("2"))
            {
                try 
                { 
                    clearScreen(); 
                    System.out.println("\n"); 
                    
                    ViewAllRecord(); 
                } 
                catch(IOException e) 
                { 
                    e.printStackTrace(); 
                }
            }
            else if(choice.equals("3"))
            {
                try { SearchRecordbyIDOrName(); } catch(IOException e) { e.printStackTrace(); }
            }
            else if(choice.equals("4"))
            {
                try { UpdateRecordbyID(); } catch(IOException e) { e.printStackTrace(); }
            }
            else if(choice.equals("5"))
            {
                try { DeleteRecordByID(); } catch(IOException e) { e.printStackTrace(); }
            }
            else if(choice.equals("0")) 
            {
                System.out.println("Thank you for using IBorrow");
                break;
            }
            else
            {
                System.out.println("INVALID INPUT TRY AGAIN");
            }

            System.out.print("Press any key. ");
            str_input.nextLine();
        }

        str_input.close();
    }

    public static void AddRecord() throws IOException 
    {
        Scanner strInput = new Scanner(System.in);
        BufferedWriter bw;
        String name, id, section, bookName;
        
        System.out.println("==========ADD RECORD==========");
        try
        {
            bw = new BufferedWriter(new FileWriter("employee_db.txt", true));

            System.out.print("Enter name: ");
            name = strInput.nextLine();
            System.out.print("Enter student ID: ");
            id = strInput.nextLine();
            System.out.print("Enter section: ");
            section = strInput.nextLine();
            System.out.print("Enter book name: ");
            bookName = strInput.nextLine();

            name = name.toUpperCase();
            id = id.toUpperCase();
            section = section.toUpperCase();
            bookName = bookName.toUpperCase();

            bw.write(name + "::::" + id + "::::" + section + "::::" + bookName);
            bw.flush();
            bw.newLine();
            bw.close();
        }
        catch(IOException e)
        {
            System.out.println(e.toString());
        }
    }

    public static void showUnCensoredRecord(String[] data) 
    {
        System.out.printf(
            "%-20s %-15s %-12s %-30s%n",
            data[0].toString(),
            data[1].toString(), 
            data[2].toString(), 
            data[3].toString()
        );
	}
    
    public static void showCensoredRecord(String[] data) 
    {
        System.out.printf(
            "%-20s %-15s %-12s %-30s%n",
            data[0].toString().replaceAll("[AEIOU]", "*"),
            data[1].toString(), 
            data[2].toString(), 
            data[3].toString()
        );
	}
    
    public static void showTableHeader() 
    {
        System.out.printf(
            "%-20s %-15s %-12s %-30s%n",
            "Name", 
            "ID", 
            "Section", 
            "Book Name"
        );
	}

    public static void ViewAllRecord() throws IOException 
    {
        try 
        { 
			Scanner strInput = new Scanner(System.in);
            String[] data;
            String record;
            BufferedReader br;
            String choice, inpPasswd, crrtPasswd;
            boolean isValid;
            
            isValid = false;
            crrtPasswd = "newjeans";
            br = new BufferedReader(new FileReader("employee_db.txt"));
            
            System.out.print("Show uncensored information? (requires password, Y/N): ");
            choice = strInput.nextLine();
            if(choice.equalsIgnoreCase("y")) 
            {
                System.out.print("Enter password: ");
                inpPasswd = strInput.nextLine();
                if(inpPasswd.equals(crrtPasswd))
                {
                    isValid = true;
                }
                else
                {
                    System.out.println("INCORRECT PASSWORD");
                }
            }
            else
            {
                isValid = false;
            }

			showTableHeader();

			while((record = br.readLine()) != null) 
            {
				data = record.split("::::");
				
                if(isValid)
                {
                    showUnCensoredRecord(data);
                }
                else
                {
                    showCensoredRecord(data);
                }
			}

			br.close();    
		} 
        catch (IOException ex) 
        {
			System.out.println(ex.toString());
		}
    }
    
    public static void UpdateRecordbyID() throws IOException 
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'UpdateRecordbyID'");
    }

    public static void DeleteRecordByID() throws IOException 
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'DeleteRecordByID'");
    }
    
    public static void SearchRecordbyIDOrName() throws IOException 
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'SearchRecordbyIDOrName'");
    }

    public static void clearScreen()
    {
        try 
        {
            if(System.getProperty("os.name").contains("Windows"))
            {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else
            {
                // Runtime.getRuntime().exec("clear");
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }   
        } 
        catch(IOException | InterruptedException ex) {}
	}
}