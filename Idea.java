import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Idea {
    public static void main(String[] args)
    {
        Scanner str_input = new Scanner(System.in);
        String choice;
        String inpPasswd, crrtPasswd;
        boolean isValid;
        
        System.out.print("Enter password: ");
        crrtPasswd = str_input.nextLine();
        
        isValid = false;
        while(true)
        {
            clearScreen();System.out.println("\n");
            System.out.println("+ ────────────────────────────────────────────── +");
            System.out.println("│    IBorrow: Book Borrowing Management System   │");
            System.out.println("│ Main                                           │");
            System.out.println("│ [1] Add borrower                               │");
            System.out.println("│ [2] View borrowers                             │");
            System.out.println("│ [3] Change borrower info                       │");
            System.out.println("│ [4] Delete borrower                            │");
            System.out.println("│ [5] Search a borrower                          │");
            System.out.println("│ [0] Exit                                       │");
            System.out.println("│                                                │");
            System.out.println("│ Add Ons                                        │");
            System.out.println("│ [6] Gain priveledge access                     │");
            System.out.println("│ [7] Revoke priveledge access                   │");
            System.out.println("+ ────────────────────────────────────────────── +");

            System.out.print("Enter choice: ");
            choice = str_input.nextLine();

            clearScreen();
            System.out.println("\n");

            if(choice.equals("1")) 
            {
                try { AddRecord(); } catch(IOException e) { e.printStackTrace(); }
            }
            else if(choice.equals("2"))
            {
                try { ViewAllRecord(isValid); } catch(IOException e) { e.printStackTrace(); }
            }
            else if(choice.equals("3"))
            {
                try { UpdateRecordbyID(isValid); } catch(IOException e) { e.printStackTrace(); }
            }
            else if(choice.equals("4"))
            {
                try { DeleteRecordByID(); } catch(IOException e) { e.printStackTrace(); }
            }
            else if(choice.equals("5"))
            {
                try { SearchRecordbyIDOrName(); } catch(IOException e) { e.printStackTrace(); }
            }
            else if(choice.equals("6"))
            {
                System.out.print("Enter password: ");
                inpPasswd = str_input.nextLine();
                if(inpPasswd.equals(crrtPasswd))
                {
                    isValid = true;
                    System.out.println("You now have access to uncensored information");
                }
                else
                {
                    isValid = false;
                    System.out.println("INCORRECT PASSWORD");
                }  
            }
            else if(choice.equals("7"))
            {
                isValid = false;
                System.out.println("Priviledge access revoked successfully");
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

            System.out.print("\nPress any key. ");
            str_input.nextLine();
        }

        str_input.close();
    }

    public static void AddRecord() throws IOException 
    {
        try
        {
            Scanner str_input = new Scanner(System.in);
            BufferedWriter bw;
            String name, id, section, contactInfo, bookName;
            bw = new BufferedWriter(new FileWriter("employee_db.txt", true));

            System.out.println("+ ──────────────────────────────────── +");
            System.out.println("│            ADD RECORD                |");
            System.out.print("Enter name: ");
            name = str_input.nextLine();
            System.out.print("Enter student ID: ");
            id = str_input.nextLine();
            System.out.print("Enter section: ");
            section = str_input.nextLine();
            System.out.print("Enter contact info: ");
            contactInfo = str_input.nextLine();
            System.out.print("Enter book name: ");
            bookName = str_input.nextLine();

            bw.write(name.toUpperCase() + "::::" + id.toUpperCase() + "::::" + section.toUpperCase() + "::::" + contactInfo.toUpperCase() + "::::" + bookName.toUpperCase());
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
            "%-2s %-20s %-15s %-12s %-15s %-30s %-2s%n",
            "│ ",
            data[0].toString(),
            data[1].toString(), 
            data[2].toString(), 
            data[3].toString(),
            data[4].toString(),
            "│ "
        );
	}
    
    public static void showCensoredRecord(String[] data) 
    {
        System.out.printf(
            "%-2s %-20s %-15s %-12s %-15s %-30s %-2s %n",
            "│ ",
            data[0].toString().replaceAll("[AEIOU]", "*"),
            data[1].toString(), 
            data[2].toString(), 
            data[3].toString(),
            data[4].toString(),
            "│ "
        );
	}
    
    public static void showTableHeader() 
    {
        System.out.printf("%-100s%n", "+ ───────────────────────────────────────────────────────────────────────────────────────────────── +");
        System.out.printf(
            "%-2s %-20s %-15s %-12s %-15s %-30s %-2s %n",
            "│ ",
            "Name", 
            "ID", 
            "Section", 
            "Contact Info",
            "Book Name",
            "│ "
        );
        System.out.printf("%-100s%n", "+ ───────────────────────────────────────────────────────────────────────────────────────────────── +");
	}

    public static void ViewAllRecord(boolean isValid) throws IOException 
    {
        try 
        { 
            String[] data;
            String record;
            BufferedReader br;

            br = new BufferedReader(new FileReader("employee_db.txt"));
          
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

            System.out.printf("%-100s%n", "+ ───────────────────────────────────────────────────────────────────────────────────────────────── +");

			br.close();    
		} 
        catch (IOException e) 
        {
			System.out.println(e.toString());
		}
    }
    
    public static void UpdateRecordbyID(boolean isValid) throws IOException 
    {
        try 
        {
			Scanner str_input = new Scanner(System.in);
    		String[] data;
            String newName, newSection, newId, newContactInfo, newBookName, record, id,record2;	
    		File db;
    		File tempDB;
    		BufferedReader br;
    		BufferedWriter bw;
            BufferedReader br2;
            boolean isFound, success;
    		    		
            isFound = false;
            db = new File("employee_db.txt");
            tempDB = new File("employee_db_temp.txt");
            br = new BufferedReader(new FileReader(db));
            br2 = new BufferedReader(new FileReader(db));
            bw = new BufferedWriter(new FileWriter(tempDB));
    		
    		ViewAllRecord(false);
    		System.out.println("Update borrower's record");   	
			System.out.print("Enter the borrower's ID: ");
	    	id = str_input.nextLine();	    
            
            id = id.toUpperCase();
            
	    	showTableHeader();
			while((record = br.readLine()) != null) 
            {
				data = record.split("::::");
    			if(data[1].toString().equals(id)) 
                {
    				isFound = true;
                    if(isValid)
                    {
                        showUnCensoredRecord(data);
                    }
                    else 
                    {
                        showCensoredRecord(data);
                    }
				}
				
			}	 
	    	br.close(); 

			if(isFound) 
            {
				System.out.print("Enter the new name: ");
				newName = str_input.nextLine();   
                System.out.print("Enter the new ID: ");
				newId = str_input.nextLine(); 
                System.out.print("Enter the new section: ");
				newSection = str_input.nextLine();    	
                System.out.print("Enter the new contact info: ");
				newContactInfo = str_input.nextLine();  
                System.out.print("Enter the new book name: ");
				newBookName = str_input.nextLine();  
					
				while((record2 = br2.readLine()) != null) 
                {  
					data = record2.split("::::");
					if(data[1].toString().equals(id)) 
                    {
						bw.write(newName.toUpperCase() + "::::" + newId.toUpperCase() + "::::" + newSection.toUpperCase() + "::::" + newContactInfo.toUpperCase() + "::::" + newBookName.toUpperCase());
					} 
                    else 
                    {
						bw.write(record2);	
					}    	
                    		
					bw.flush();
					bw.newLine();
				}
				
				bw.close();
				br2.close();    		
				db.delete();    		
				success = tempDB.renameTo(db);   
			}
		} 
        catch(IOException e) 
        {
			System.out.println (e.toString());
		}
    }

    public static void DeleteRecordByID() throws IOException 
    {
		try 
        {
    		Scanner str_input = new Scanner(System.in);
            String[] data ;
            String id, record;
    		File temporaryDB;
    		File db;
    		BufferedReader br;
    		BufferedWriter bw;

            temporaryDB = new File("employee_db_temp.txt");
            db = new File("employee_db.txt");
            br = new BufferedReader(new FileReader(db));
            bw = new BufferedWriter(new FileWriter(temporaryDB));

    		ViewAllRecord(false);
    		System.out.println("Delete borrower's record");    		
    		System.out.print("Enter the borrowers ID: ");
    		id = str_input.nextLine();

            id = id.toUpperCase();
    		while((record = br.readLine()) != null) 
            {
    			data = record.split("::::");
    			if(data[1].toString().equals(id)) 
    			{
                    continue;
                }
                
                bw.write(record);
    			bw.flush();
    			bw.newLine();
    		}
    		
    		br.close();
    		bw.close();
    		db.delete();
    		temporaryDB.renameTo(db);
		} 
        catch(IOException e) 
        {
			System.out.println (e.toString());
		}
    }
    
    public static void SearchRecordbyIDOrName() throws IOException 
    {
        Scanner str_input = new Scanner(System.in);
        String searchQuery, record;
        String[] data;
        BufferedReader br;
        boolean isFound;

        isFound = false;
        br = new BufferedReader(new FileReader("employee_db.txt"));

        System.out.print("Enter id or name of borrower: ");
        searchQuery = str_input.nextLine();

        searchQuery = searchQuery.toUpperCase();

        while((record = br.readLine()) != null)
        {
            data = record.split("::::");

            if(data[1].toString().equals(searchQuery) || data[0].toString().contains(searchQuery))
            { 
                isFound = true;
                if(isFound)
                {
                    showTableHeader();
                }
                
                showCensoredRecord(data);
            }
        }

        if(!isFound)
        {
            System.out.println("ID\\NAME " + searchQuery + " is not found");
        }

        br.close();
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