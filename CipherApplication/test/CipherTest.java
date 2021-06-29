// Name: Omar Abdulaziz Hassan Alqurashi - ID: 1742589 - Course: CPCS425

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author omar-
 */
public class CipherTest {
    
    public CipherTest() {
    }
    

    /**
     * Test of main method, of class Cipher.
     */
//    @Test
//    public void testMain() {
//        System.out.println("main");
//        String[] args = null;
//        Cipher.main(args);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of encrypt method, of class Cipher.
     */
    @Test
    public void testEncrypt() throws Exception {
        System.out.println("encrypt");
        BufferedReader inStream = new BufferedReader(new StringReader("abcdefghijklmnopqrstuvwxyz"));
        StringWriter result = new StringWriter();
        PrintWriter outStream = new PrintWriter(result);;
        Cipher instance = new Cipher();
        instance.encrypt(inStream, outStream);
        assertEquals("LM#Q&$+U^W%@BYZCD=FGH!?KN*", result.toString());
    }

    /**
     * Test of decrypt method, of class Cipher.
     */
    @Test
    public void testDecrypt() throws Exception {
        System.out.println("decrypt");
        BufferedReader inStream = new BufferedReader(new StringReader("LM#Q&$+U^W%@BYZCD=FGH!?KN*"));
        StringWriter result = new StringWriter();
        PrintWriter outStream = new PrintWriter(result);;
        Cipher instance = new Cipher();
        instance.decrypt(inStream, outStream);
        assertEquals("abcdefghijklmnopqrstuvwxyz",result.toString());
    }
}
