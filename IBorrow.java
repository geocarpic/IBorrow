import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class IBorrow {
    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);
        String choice;
        String inPasswd, crrtPassword;
        boolean isValid;

        isValid = false;

        clearScreen();
        System.out.println("\n");
        System.out.print("\tEnter password for the system to use: ");
        crrtPassword = input.nextLine();
        clearScreen();
        
        while(true)
        {
            System.out.println("\n");
            showMenu();
            System.out.print("\n\tEnter choice: ");
            choice = input.nextLine();
            clearScreen();
            System.out.println("\n");
            if(choice.equals("1"))
            {
                try 
                { 
                    showAddRecordAsciiArt();
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
                    showViewRecordAsciiArt();
                    ViewAllRecord(isValid); 
                } 
                catch(IOException e) 
                { 
                    e.printStackTrace(); 
                }
            }
            else if(choice.equals("3"))
            { 
                try 
                {
                    showSearchRecordAsciiArt();
                    SearchRecordbyIdOrName(isValid);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
            else if(choice.equals("4"))
            {
                try 
                {
                    showUpdateRecordAsciiArt();
                    UpdateUserInformationById(isValid);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
            else if(choice.equals("5"))
            {
                try 
                {
                    showDeleteRecordAsciiArt();
                    DeleteRecordByID(isValid);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
            else if(choice.equals("0"))
            {
                showThankYouMessage();
                break;
            }
            else if(choice.equals("6"))
            {
                System.out.print("\tEnter system password to gain access: ");
                inPasswd = input.nextLine();

                if(inPasswd.equals(crrtPassword))
                {
                    isValid = true;
                    System.out.println("\tAccess Granted");
                }
                else
                {
                    System.out.println("\tIncorrect password");
                }
            }
            else if(choice.equals("7"))
            {
                System.out.println("\tPermission rebuked successfully");
                isValid = false;
            }
            else
            {
                System.out.println("\tInvalid Input");
            }

            System.out.print("\n\tPress any key. ");
            input.nextLine();
            clearScreen();
        }

        input.close();
    }
    public static void AddRecord() throws IOException
    {
        try
        {
            Scanner input = new Scanner(System.in);
            BufferedWriter bw;
            String Name, Id, Section, ContactInfo, BookName;

            System.out.print("\tName: ");
            Name = input.nextLine();
            System.out.print("\tStudent ID: ");
            Id = input.nextLine();
            System.out.print("\tSection: ");
            Section = input.nextLine();
            System.out.print("\tContact Info: ");
            ContactInfo = input.nextLine();
            System.out.print("\tBook Name: ");
            BookName = input.nextLine();

            bw = new BufferedWriter(new FileWriter("database.txt", true));
            bw.write(Name.toUpperCase() + "::::" + Id.toUpperCase() + "::::" + Section.toUpperCase() + "::::" + ContactInfo.toUpperCase() + "::::" + BookName.toUpperCase());
            bw.flush();
            bw.newLine();
            bw.close();
        }
        catch(IOException e)
        {
            System.out.println("\t" + e.toString());
        }
    }

    public static void ViewAllRecord(boolean isValid) throws IOException
    {
        try 
        {
            BufferedReader br;
            String[] data;
            String record;
            
            br = new BufferedReader(new FileReader("database.txt"));

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
        catch(IOException e) 
        {
            System.out.println("\t" + e.toString());
        }
        
    }

    public static void SearchRecordbyIdOrName(boolean isValid) throws IOException
    {
        try 
        {
            Scanner input = new Scanner(System.in);
            BufferedReader br;
            String[] data;
            String record;
            String searchQuery;

            br = new BufferedReader(new FileReader("database.txt"));
            
            System.out.print("\tEnter id or name: ");
            searchQuery = input.nextLine();
            searchQuery = searchQuery.toUpperCase();

            showTableHeader();
            while((record = br.readLine()) != null)
            {
                data = record.split("::::");

                if(data[1].toString().equals(searchQuery) || data[0].toString().contains(searchQuery))
                {
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
        }
        catch(IOException e)
        {
            System.out.println("\t" + e.toString());
        }
    }

    public static void UpdateUserInformationById(boolean isValid) throws IOException
    {
        try 
        {
            Scanner input = new Scanner(System.in);
            File db;
            File tempDB;
            BufferedReader br;
            BufferedWriter bw;
            BufferedReader br2;
            String[] data = null;
            String[] foundData = null;
            String record, record2;
            boolean isFound;
            String newName, newSection, newId, newContactInfo, newBookName;
            String upChoice, idSearchQuery;

            db = new File("database.txt");
            tempDB = new File("tmp_database.txt");
            br = new BufferedReader(new FileReader(db));
            br2 = new BufferedReader(new FileReader(db));
            bw = new BufferedWriter(new FileWriter(tempDB));
            isFound = false;

            ViewAllRecord(isValid);	
            System.out.print("\tEnter the borrower's ID: ");
            idSearchQuery = input.nextLine();
            idSearchQuery = idSearchQuery.toUpperCase();

            while((record = br.readLine()) != null)
            {
                data = record.split("::::");

                if(data[1].toString().equals(idSearchQuery))
                {
                    foundData = record.split("::::");
                    isFound = true;
                    
                    if(isFound)
                    {
                        showTableHeader();
                    }

                    if(isValid)
                    {
                        showUnCensoredRecord(foundData);
                    }
                    else
                    {
                        showCensoredRecord(foundData);
                    }
                }
            }

            br.close();

            if(isFound) 
            {
                ViewAllRecord(isValid);
                System.out.println("\n\tChanging info for user with ID of " + idSearchQuery);
                showChangingInfoMenu();
                
                System.out.print("\n\tEnter choice: ");
                upChoice = input.nextLine();
                
                switch(upChoice) {
                    case "1":
                        System.out.print("\tEnter the new name: ");
                        newName = input.nextLine(); 
                        newId = foundData[1];
                        newSection = foundData[2];
                        newContactInfo = foundData[3];
                        newBookName = foundData[4];
                        break;
                    case "2":
                        System.out.print("\tEnter the new ID: ");
                        newId = input.nextLine(); 
                        newName = foundData[0];
                        newSection = foundData[2];
                        newContactInfo = foundData[3];
                        newBookName = foundData[4];
                        break;
                
                    case "3":
                        System.out.print("\tEnter the new section: ");
                        newSection = input.nextLine();
                        newName = foundData[0];
                        newId = foundData[1];
                        newContactInfo = foundData[3];
                        newBookName = foundData[4];
                        break;
                    case "4":
                        System.out.print("\tEnter the new contact info: ");
                        newContactInfo = input.nextLine(); 
                        newName = foundData[0];
                        newId = foundData[1];
                        newSection = foundData[2];
                        newBookName = foundData[4];
                        break;
                    case "5":
                        System.out.print("\tEnter the new book name: ");
                        newBookName = input.nextLine(); 
                        newName = foundData[0];
                        newId = foundData[1];
                        newSection = foundData[2];
                        newContactInfo = foundData[3]; 
                        break;
                    default:
                        System.out.print("\tEnter the new name: ");
                        newName = input.nextLine();   
                        System.out.print("\tEnter the new ID: ");
                        newId = input.nextLine(); 
                        System.out.print("\tEnter the new section: ");
                        newSection = input.nextLine();    	
                        System.out.print("\tEnter the new contact info: ");
                        newContactInfo = input.nextLine();  
                        System.out.print("\tEnter the new book name: ");
                        newBookName = input.nextLine();  
                        
                        break;
                }
                	
				while((record2 = br2.readLine()) != null) 
                {  
					data = record2.split("::::");
					if(data[1].toString().equals(idSearchQuery)) 
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
				tempDB.renameTo(db);   
			}
            else
            {
                System.out.println("\tUser with ID of " + idSearchQuery + " not found");
            }
        }
        catch(IOException e)
        {
            System.out.println("\t" + e.toString());
        }
    }

    public static void DeleteRecordByID(boolean isValid) throws IOException 
    {
		try 
        {
            Scanner input = new Scanner(System.in);
            String[] data;
            String record;
            File tempDB;
            File db;
            BufferedReader br;
            BufferedWriter bw;
            String idSearchQuery;

            tempDB = new File("tmp_database.txt");
            db = new File("database.txt");
            br = new BufferedReader(new FileReader(db));
            bw = new BufferedWriter(new FileWriter(tempDB));

            ViewAllRecord(isValid);
            System.out.println("\tDelete borrower's record");    		
            System.out.print("\tEnter the borrowers ID: ");
            idSearchQuery = input.nextLine();
            idSearchQuery = idSearchQuery.toUpperCase();
            
            while((record = br.readLine()) != null) 
            {
                data = record.split("::::");
                if(data[1].toString().equals(idSearchQuery)) 
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
            tempDB.renameTo(db);
        }
        catch(IOException e)
        {
            System.out.println("\t" + e.toString());
        }
    }

    public static void showTableHeader() 
    {
        System.out.printf("%-100s%n", "\t+ ───────────────────────────────────────────────────────────────────────────────────────────────── +");
        System.out.printf(
            "%-2s %-20s %-15s %-12s %-15s %-30s %-2s %n",
            "\t│ ",
            "Name", 
            "ID", 
            "Section", 
            "Contact Info",
            "Book Name",
            "│ "
        );
        System.out.printf("%-100s%n", "\t+ ───────────────────────────────────────────────────────────────────────────────────────────────── +");
	}


    public static void showUnCensoredRecord(String[] data) 
    {
        System.out.printf(
            "%-2s %-20s %-15s %-12s %-15s %-30s %-2s%n",
            "\t│ ",
            data[0].toString(),
            data[1].toString(), 
            data[2].toString(), 
            data[3].toString(),
            data[4].toString(),
            "│ "
        );

        System.out.printf("%-100s%n", "\t+ ───────────────────────────────────────────────────────────────────────────────────────────────── +");
	}
    
    public static void showCensoredRecord(String[] data) 
    {
        System.out.printf(
            "%-2s %-20s %-15s %-12s %-15s %-30s %-2s %n",
            "\t│ ",
            data[0].toString().replaceAll("[AEIOU]", "*"),
            data[1].toString(), 
            data[2].toString(), 
            data[3].toString(),
            data[4].toString(),
            "│ "
        );

        System.out.printf("%-100s%n", "\t+ ───────────────────────────────────────────────────────────────────────────────────────────────── +");
	}

    public static void showMenu()
    {
        System.out.println("\t+ ────────────────────────────────────────────── +");
        System.out.println("\t│    IBorrow: Book Borrowing Management System   │");
        System.out.println("\t+ ────────────────────────────────────────────── +");
        System.out.println("\t│ Main                                           │");
        System.out.println("\t│ [1] Add borrower                               │");
        System.out.println("\t│ [2] View borrowers                             │");
        System.out.println("\t│ [3] Search borrower                            │");
        System.out.println("\t│ [4] Update borrower's info                     │");
        System.out.println("\t│ [5] Delete a borrower                          │");
        System.out.println("\t│ [0] Exit                                       │");
        System.out.println("\t│                                                │");
        System.out.println("\t│ Add Ons                                        │");
        System.out.println("\t│ [6] Gain priveledge access                     │");
        System.out.println("\t│ [7] Revoke priveledge access                   │");
        System.out.println("\t+ ────────────────────────────────────────────── +");
    }

    public static void showChangingInfoMenu()
    {
        System.out.println("\t+ ────────────────────────────────────────────── +");
        System.out.println("\t│    Which one do you want to change/update?     │");
        System.out.println("\t+ ────────────────────────────────────────────── +");
        System.out.println("\t│ Choices                                        │");
        System.out.println("\t│ [1] Name                                       │");
        System.out.println("\t│ [2] ID                                         │");
        System.out.println("\t│ [3] Section                                    │");
        System.out.println("\t│ [4] Contact Info                               │");
        System.out.println("\t│ [5] Book Name                                  │");
        System.out.println("\t│ [6] All                                        │");
        System.out.println("\t+ ────────────────────────────────────────────── +");
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

    public static void showAddRecordAsciiArt() 
    {
        System.out.println("\t █████╗ ██████╗ ██████╗     ██████╗ ███████╗ ██████╗ ██████╗ ██████╗ ██████╗ ");
        System.out.println("\t██╔══██╗██╔══██╗██╔══██╗    ██╔══██╗██╔════╝██╔════╝██╔═══██╗██╔══██╗██╔══██╗");
        System.out.println("\t███████║██║  ██║██║  ██║    ██████╔╝█████╗  ██║     ██║   ██║██████╔╝██║  ██║");
        System.out.println("\t██╔══██║██║  ██║██║  ██║    ██╔══██╗██╔══╝  ██║     ██║   ██║██╔══██╗██║  ██║");
        System.out.println("\t██║  ██║██████╔╝██████╔╝    ██║  ██║███████╗╚██████╗╚██████╔╝██║  ██║██████╔╝");
        System.out.println("\t╚═╝  ╚═╝╚═════╝ ╚═════╝     ╚═╝  ╚═╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ");
        System.out.println();
    }

    public static void showViewRecordAsciiArt() 
    {
        System.out.println("\t██╗   ██╗██╗███████╗██╗    ██╗     █████╗ ██╗     ██╗         ██████╗ ███████╗ ██████╗ ██████╗ ██████╗ ██████╗ ");
        System.out.println("\t██║   ██║██║██╔════╝██║    ██║    ██╔══██╗██║     ██║         ██╔══██╗██╔════╝██╔════╝██╔═══██╗██╔══██╗██╔══██╗");
        System.out.println("\t██║   ██║██║█████╗  ██║ █╗ ██║    ███████║██║     ██║         ██████╔╝█████╗  ██║     ██║   ██║██████╔╝██║  ██║");
        System.out.println("\t╚██╗ ██╔╝██║██╔══╝  ██║███╗██║    ██╔══██║██║     ██║         ██╔══██╗██╔══╝  ██║     ██║   ██║██╔══██╗██║  ██║");
        System.out.println("\t ╚████╔╝ ██║███████╗╚███╔███╔╝    ██║  ██║███████╗███████╗    ██║  ██║███████╗╚██████╗╚██████╔╝██║  ██║██████╔╝");
        System.out.println("\t  ╚═══╝  ╚═╝╚══════╝ ╚══╝╚══╝     ╚═╝  ╚═╝╚══════╝╚══════╝    ╚═╝  ╚═╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ");
        System.out.println();
    }

    public static void showSearchRecordAsciiArt() 
    {
        System.out.println("\t███████╗███████╗ █████╗ ██████╗  ██████╗██╗  ██╗    ██████╗ ███████╗ ██████╗ ██████╗ ██████╗ ██████╗ ");
        System.out.println("\t██╔════╝██╔════╝██╔══██╗██╔══██╗██╔════╝██║  ██║    ██╔══██╗██╔════╝██╔════╝██╔═══██╗██╔══██╗██╔══██╗");
        System.out.println("\t███████╗█████╗  ███████║██████╔╝██║     ███████║    ██████╔╝█████╗  ██║     ██║   ██║██████╔╝██║  ██║");
        System.out.println("\t╚════██║██╔══╝  ██╔══██║██╔══██╗██║     ██╔══██║    ██╔══██╗██╔══╝  ██║     ██║   ██║██╔══██╗██║  ██║");
        System.out.println("\t███████║███████╗██║  ██║██║  ██║╚██████╗██║  ██║    ██║  ██║███████╗╚██████╗╚██████╔╝██║  ██║██████╔╝");
        System.out.println("\t╚══════╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝    ╚═╝  ╚═╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ");
        System.out.println();
    }

    public static void showUpdateRecordAsciiArt() 
    {
        System.out.println("\t██╗   ██╗██████╗ ██████╗  █████╗ ████████╗███████╗    ██████╗ ███████╗ ██████╗ ██████╗ ██████╗ ██████╗ ");
        System.out.println("\t██║   ██║██╔══██╗██╔══██╗██╔══██╗╚══██╔══╝██╔════╝    ██╔══██╗██╔════╝██╔════╝██╔═══██╗██╔══██╗██╔══██╗");
        System.out.println("\t██║   ██║██████╔╝██║  ██║███████║   ██║   █████╗      ██████╔╝█████╗  ██║     ██║   ██║██████╔╝██║  ██║");
        System.out.println("\t██║   ██║██╔═══╝ ██║  ██║██╔══██║   ██║   ██╔══╝      ██╔══██╗██╔══╝  ██║     ██║   ██║██╔══██╗██║  ██║");
        System.out.println("\t╚██████╔╝██║     ██████╔╝██║  ██║   ██║   ███████╗    ██║  ██║███████╗╚██████╗╚██████╔╝██║  ██║██████╔╝");
        System.out.println("\t ╚═════╝ ╚═╝     ╚═════╝ ╚═╝  ╚═╝   ╚═╝   ╚══════╝    ╚═╝  ╚═╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ");
        System.out.println();
    }

    public static void showDeleteRecordAsciiArt() 
    {
        System.out.println("\t██████╗ ███████╗██╗     ███████╗████████╗███████╗    ██████╗ ███████╗ ██████╗ ██████╗ ██████╗ ██████╗ ");
        System.out.println("\t██╔══██╗██╔════╝██║     ██╔════╝╚══██╔══╝██╔════╝    ██╔══██╗██╔════╝██╔════╝██╔═══██╗██╔══██╗██╔══██╗");
        System.out.println("\t██║  ██║█████╗  ██║     █████╗     ██║   █████╗      ██████╔╝█████╗  ██║     ██║   ██║██████╔╝██║  ██║");
        System.out.println("\t██║  ██║██╔══╝  ██║     ██╔══╝     ██║   ██╔══╝      ██╔══██╗██╔══╝  ██║     ██║   ██║██╔══██╗██║  ██║");
        System.out.println("\t██████╔╝███████╗███████╗███████╗   ██║   ███████╗    ██║  ██║███████╗╚██████╗╚██████╔╝██║  ██║██████╔╝");
        System.out.println("\t╚═════╝ ╚══════╝╚══════╝╚══════╝   ╚═╝   ╚══════╝    ╚═╝  ╚═╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ");
        System.out.println();
    }

    public static void showThankYouMessage()
    {
        System.out.println("\t████████╗██╗  ██╗ █████╗ ███╗   ██╗██╗  ██╗    ██╗   ██╗ ██████╗ ██╗   ██╗    ███████╗ ██████╗ ██████╗     ");
        System.out.println("\t╚══██╔══╝██║  ██║██╔══██╗████╗  ██║██║ ██╔╝    ╚██╗ ██╔╝██╔═══██╗██║   ██║    ██╔════╝██╔═══██╗██╔══██╗    ");
        System.out.println("\t   ██║   ███████║███████║██╔██╗ ██║█████╔╝      ╚████╔╝ ██║   ██║██║   ██║    █████╗  ██║   ██║██████╔╝    ");
        System.out.println("\t   ██║   ██╔══██║██╔══██║██║╚██╗██║██╔═██╗       ╚██╔╝  ██║   ██║██║   ██║    ██╔══╝  ██║   ██║██╔══██╗    ");
        System.out.println("\t   ██║   ██║  ██║██║  ██║██║ ╚████║██║  ██╗       ██║   ╚██████╔╝╚██████╔╝    ██║     ╚██████╔╝██║  ██║    ");
        System.out.println("\t   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝       ╚═╝    ╚═════╝  ╚═════╝     ╚═╝      ╚═════╝ ╚═╝  ╚═╝    ");
        System.out.println("\t                                                                                                            ");
        System.out.println("\t██╗   ██╗███████╗██╗███╗   ██╗ ██████╗     ██╗██████╗  ██████╗ ██████╗ ██████╗  ██████╗ ██╗    ██╗         ");
        System.out.println("\t██║   ██║██╔════╝██║████╗  ██║██╔════╝     ██║██╔══██╗██╔═══██╗██╔══██╗██╔══██╗██╔═══██╗██║    ██║         ");
        System.out.println("\t██║   ██║███████╗██║██╔██╗ ██║██║  ███╗    ██║██████╔╝██║   ██║██████╔╝██████╔╝██║   ██║██║ █╗ ██║         ");
        System.out.println("\t██║   ██║╚════██║██║██║╚██╗██║██║   ██║    ██║██╔══██╗██║   ██║██╔══██╗██╔══██╗██║   ██║██║███╗██║         ");
        System.out.println("\t╚██████╔╝███████║██║██║ ╚████║╚██████╔╝    ██║██████╔╝╚██████╔╝██║  ██║██║  ██║╚██████╔╝╚███╔███╔╝         ");
        System.out.println("\t ╚═════╝ ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝     ╚═╝╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝  ╚══╝╚══╝          ");
        System.out.println();
    }
}








