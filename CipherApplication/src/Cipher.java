// Name: Omar Abdulaziz Hassan Alqurashi - ID: 1742589 - Course: CPCS425

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author omar-
 */
public class Cipher {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Cipher cipher = new Cipher();
        File f;
        FileReader fr;
        BufferedReader inStream;
        FileWriter fw;
        PrintWriter outStream;
        Scanner sc = new Scanner(System.in);
        String command;
        
        System.out.println("*************** Welcome **************");
        
        while(true){
            System.out.print("---------------- Main ----------------"
                    + "\nWhich Operation? (Enter 1, or 2, or 0)"
                    + "\n1 - Encription"
                    + "\n2 - Decription"
                    + "\n0 - Exit"
                    + "\n\nEnter: "
            );
            command = sc.nextLine().trim();
            switch(command){
                case "1":
                    while(true){
                        System.out.print("--------------- Encription --------------"
                                + "\nEnter the file name (e.g. abc.txt), and make sure the file is on the root directory of the app."
                                + "\n(You can also go back to the main menu by entering: 0)"
                                + "\n\nEnter: "
                        );
                        command = sc.nextLine().trim();
                        if(command.equals("0")){break;} // back to main screen
                        f = new File(command); // (command) = filename
                        if(!f.exists()){
                            System.out.println("\nError: File not found with the name: (" + command + "). Try Again:");
                            continue;
                        }
                        try {
                            fr = new FileReader(f);
                            inStream = new BufferedReader(fr);
                            fw = new FileWriter("cipher.txt");
                            outStream = new PrintWriter(fw);
                            cipher.encrypt(inStream, outStream);
                            inStream.close();
                            outStream.close();
                            break;
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Cipher.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(Cipher.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (Exception ex) {
                            Logger.getLogger(Cipher.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                case "2":
                    while(true){
                        System.out.print("--------------- Decription --------------"
                                + "\nEnter the file name (e.g. abc.txt), and make sure the file is on the root directory of the app."
                                + "\n(You can also go back to the main menu by entering: 0)"
                                + "\n\nEnter: "
                        );
                        command = sc.nextLine().trim();
                        if(command.equals("0")){break;} // back to main screen
                        f = new File(command); // (command) = filename
                        if(!f.exists()){  // if (f) doesn't exist...
                            System.out.println("\nError: File not found with the name: (" + command + "). Try Again");
                            continue;
                        }
                        try {
                            fr = new FileReader(f);
                            inStream = new BufferedReader(fr);
                            fw = new FileWriter("decrypt.txt");
                            outStream = new PrintWriter(fw);
                            cipher.decrypt(inStream, outStream);
                            inStream.close();
                            outStream.close();
                            break;
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Cipher.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(Cipher.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (Exception ex) {
                            Logger.getLogger(Cipher.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                case "0":
                    System.out.println("************** Thank You **************");
                    System.exit(0);
                default: // if wrong input...
                    System.out.println("\nWrong Input: Please enter the available choice number");
            }
        }
    }
    
    // Encrypt a given file, then produce the encrypted message as cipher.txt (if num. chars per line > 3)
    public void encrypt( BufferedReader inStream, PrintWriter outStream ) throws Exception
    {
        String line;  // for storing and manipulating a line
        StringBuilder encryptedLines = new StringBuilder(); // for storing enciphered lines and print it later using PrintWriter
        while((line = inStream.readLine()) != null){
            line = line.trim().toUpperCase(); // 1st & 2nd Steps
            
            if(line.length() < 4){ // to check if a line has 4 or more characters
                System.out.println("\nError: Could not complete encryption; Num. characters per line must be >= 4");
                return;
            }
            
            line = performSubstitution(line); // 3rd Step
            
            // 4th Step (Moving the first half of the string to be the last half)
            if((line.length()%2) == 0) // even
            {
                line = line.substring((line.length()/2)) + line.substring(0,line.length()/2);
            }
            else // odd
            {
                line = line.substring((line.length()/2) + 1) + line.substring(0,(line.length()/2) + 1);
            }
            
            // 5th Step (Swapping the first 2 characters of the line with the last two characters)
            line = line.substring(line.length() - 2)
                    + line.substring(2,line.length() - 2)
                    + line.substring(0,2);
            
            /* 6th Step
            (Swapping the two characters immediately to the left of the middle
            of the string with the two characters that immediately follow them)
            */
            line = line.substring(0,(line.length()/2) - 2)
                    + line.substring((line.length()/2), (line.length()/2) + 2)
                    + line.substring((line.length()/2) - 2, (line.length()/2))
                    + line.substring((line.length()/2) + 2);
            
            encryptedLines.append(line);
            encryptedLines.append("\n");
        }
        outStream.print(encryptedLines.toString().trim()); // to the intermediate file (cipher.txt). (Also remove the last '\n')
        outStream.flush(); // to make sure the (println) has written the line
        System.out.println("Done! (output file: cipher.txt)\n");
    }
    
    // Decrypt a given file, then produce the decrypted message as decrypt.txt (if num. chars per line > 3)
    public void decrypt( BufferedReader inStream, PrintWriter outStream ) throws Exception
    {
        String line;  // for storing and manipulating a line
        StringBuilder decryptedLines = new StringBuilder(); // for storing deciphered lines and print it later using PrintWriter
        while((line = inStream.readLine()) != null){
            line = line.trim(); // 1st Steps
            
            if(line.length() < 4){ // to check if a line has 4 or more characters
                System.out.println("\nError: Could not complete decryption; Num. characters per line must be >= 4");
                return;
            }
            
            /* 2th Step
            (the two characters immediately to the right of the middle of
            the string with the two characters that immediately precede them)
            */
            if((line.length()%2) == 0) // even
            {
                line = line.substring(0,(line.length()/2) - 2)
                    + line.substring((line.length()/2), (line.length()/2) + 2)
                    + line.substring((line.length()/2) - 2, (line.length()/2))
                    + line.substring((line.length()/2) + 2);
            }
            else // odd
            {
                line = line.substring(0,(line.length()/2) - 2)
                    + line.substring((line.length()/2), (line.length()/2) + 2)
                    + line.substring((line.length()/2) - 2, (line.length()/2))
                    + line.substring((line.length()/2) + 2);
            }
            
            // 3th Step (Swapping the first 2 characters of the line with the last two characters)
            line = line.substring(line.length() - 2)
                    + line.substring(2,line.length() - 2)
                    + line.substring(0,2);
          
            // 4th Step (Moving the first half of the string to be the last half)
            if((line.length()%2) == 0) // even
            {
                line = line.substring((line.length()/2)) + line.substring(0,line.length()/2);
            }
            else // odd
            {
                line = line.substring((line.length()/2)) + line.substring(0,(line.length()/2));
            }
            
            line = performSubstitution(line); // 5th Step
            
            line = line.toLowerCase(); // 6th Step
                   
            decryptedLines.append(line);
            decryptedLines.append("\n");
        }
        outStream.print(decryptedLines.toString().trim()); // to the intermediate file (decrypt.txt). (also remove the last '\n')
        outStream.flush(); // to make sure the (println) has written the line
        System.out.println("Done! (output file: decrypt.txt)\n");
    }
    
    // Perform substitutuion, then return the substituted line as String
    private String performSubstitution(String text) {
        // (StringBuilder) is more efficient than (String) in concatenation  processes
        StringBuilder substitutedText = new StringBuilder();
        char c;
        for (int i = 0;i < text.length();i++){
            switch(c = text.charAt(i)){
                case 'A':
                    substitutedText.append("@");
                    break;
                case 'E':
                    substitutedText.append("=");
                    break;
                case 'I':
                    substitutedText.append("!");
                    break;
                case 'J':
                    substitutedText.append("?");
                    break;
                case 'O':
                    substitutedText.append("*");
                    break;
                case 'P':
                    substitutedText.append("#");
                    break;
                case 'R':
                    substitutedText.append("&");
                    break;
                case 'S':
                    substitutedText.append("$");
                    break;
                case 'T':
                    substitutedText.append("+");
                    break;
                case 'V':
                    substitutedText.append("^");
                    break;
                case 'X':
                    substitutedText.append("%");
                    break;
                case ' ':
                    substitutedText.append("_");
                    break;
                // -----------------------------
                case '@':
                    substitutedText.append("A");
                    break;
                case '=':
                    substitutedText.append("E");
                    break;
                case '!':
                    substitutedText.append("I");
                    break;
                case '?':
                    substitutedText.append("J");
                    break;
                case '*':
                    substitutedText.append("O");
                    break;
                case '#':
                    substitutedText.append("P");
                    break;
                case '&':
                    substitutedText.append("R");
                    break;
                case '$':
                    substitutedText.append("S");
                    break;
                case '+':
                    substitutedText.append("T");
                    break;
                case '^':
                    substitutedText.append("V");
                    break;
                case '%':
                    substitutedText.append("X");
                    break;
                case '_':
                    substitutedText.append(" ");
                    break;
                //------------------------------
                default:
                    substitutedText.append(c );
            }
        }
        return substitutedText.toString();
    }
}
