// Name: Deepak Kumar Gunturu
// Course: CIS 3360
// PID: 3924312
// NID: de143131
// Assignment 1: Program to encrypt a given plaintext file using cipher block chaining

import java.util.*;
import java.io.*;

public class VigenereEncipher{

    // Function that takes in the plaintext, keyword, and initialization vector as arguments to encrypt the plaintext
    public static void encrypt(ArrayList<Character>plainText,String keyWord, String initVector){

        // Case for when the arguments passed to the function are null
        if(plainText == null || keyWord == null || initVector == null)
            return;

        int i;
        
        // Padding in extra characters till the block length is even for every block 
        while(plainText.size() % keyWord.length() != 0)
            plainText.add('x');

        for(i = 0; i < plainText.size(); i++){

            // Using the initialization vector for encryption
            if(i < keyWord.length()){
                plainText.set(i,(char)(((plainText.get(i)+initVector.charAt(i)-2*97)%26+keyWord.charAt(i)-97)%26+97));
            }

            // Using the encrypted characters from previous block to encrypt instead of using the initialization vector
            else{
                plainText.set(i,(char)((((int)plainText.get(i)+(int)(plainText.get(i-keyWord.length())) - 2*97)%26 + (int)keyWord.charAt(i%keyWord.length()) - 97)% 26 + 97));
            }
        }
    }

    public static void main(String []args){

        try{

            // If the lengths of the keyword and the initialization vector are not the same
            if(args[1].length() != args[2].length()){
                System.out.println("This encryption cannot be done as the lengths of the keyword and the initialization vector are not the same.");
                return;
            }

            int i, origLen;
            File file = new File(args[0]);
            Scanner scanner = new Scanner(new FileInputStream(file));
            String s = new String();
            ArrayList<Character> plainText = new ArrayList<Character>();
            PrintWriter pw = new PrintWriter("cipher.txt");            

            System.out.println();

            // Reading through the plaintext file and pre-processing 
            while(scanner.hasNext()){
                s = scanner.nextLine();

                for(i = 0; i < s.length(); i++)
                {
                    if(Character.isLetter(s.charAt(i))){
                        plainText.add(Character.toLowerCase(s.charAt(i)));
                    }
                }
            }
            
            origLen = plainText.size();

            // Printing out the different 
            System.out.println("Original plaintext filename: "+args[0]);
            System.out.println("Key: "+args[1]);
            System.out.println("IV: "+args[2]);
            System.out.println("Block size: "+args[1].length());
            System.out.println("\nPlaintext (after preprocessing)\n");

            for(i = 0; i < plainText.size(); i++)
            {
                System.out.print(plainText.get(i));
            }

            System.out.println("\n\nNumber of characters in plaintext (before padding): "+plainText.size());

            // Encrypting the plaintext
            encrypt(plainText,args[1],args[2]);

            // Printing out the ciphertext and writing it to an external file
            System.out.println("\nCiphertext\n");

            for(i = 0; i < plainText.size(); i++)
            {
                System.out.print(plainText.get(i));
                pw.write(plainText.get(i));
            }

            System.out.println("\n\nName of the ciphertext file: cipher.txt");
            System.out.println("Number of padding characters: "+(plainText.size()-origLen)+"\n");
            
            // Cleaning up
            if(pw != null)
                pw.close();
        }

        catch(Exception e){
            e.printStackTrace();
        }
    }

}
