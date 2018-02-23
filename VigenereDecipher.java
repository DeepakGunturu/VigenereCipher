// Name: Deepak Kumar Gunturu
// Course: CIS 3360
// PID: 3924312
// NID: de143131
// Assignment 1: Program to decrypt a given cipher file using cipher block chaining

import java.util.*;
import java.io.*;
import java.lang.*;

public class VigenereDecipher{

    // Function that takes in the ciphertext, keyword, and initialization vector as arguments to encrypt the ciphertext
    public static void decrypt(ArrayList<Character>cipherText,String keyWord, String initVector){

        // Case for when the arguments passed to the function are null
        if(cipherText == null || keyWord == null || initVector == null)
            return;

        int i, a, b, c;

        // For storing the encrypted characters from previous blocks
        ArrayList<Character>temp = new ArrayList<Character>();

        for(i = 0; i < cipherText.size(); i++){

            // Using the initialization vector for decryption
            if(i < keyWord.length()){

                temp.add(i,cipherText.get(i));
                cipherText.set(i,(char)(((((cipherText.get(i)-97)-(initVector.charAt(i)-97)-(keyWord.charAt(i%keyWord.length())-97))%(26))+(26))%(26)+97));
            }

             // Using the encrypted characters from previous block to decrypt instead of using the initialization vector
            else{
                
                a = (int)(cipherText.get(i)-97);
                b = (int)(temp.get(i%keyWord.length())-97);
                c = (int)(keyWord.charAt(i%keyWord.length())-97);
                temp.set(i%keyWord.length(),cipherText.get(i));
                cipherText.set(i,(char)((((a-b-c)%(26))+(26))%(26)+97));
            }
        }
    }

    public static void main(String []args){

        try{

            // If the lengths of the keyword and the initialization vector are not the same
            if(args[1].length() != args[2].length()){
                System.out.println("This decryption cannot be done as the lengths of the keyword and the initialization vector are not the same.");
                return;
            }

            int i;
            File file = new File(args[0]);
            Scanner scanner = new Scanner(new FileInputStream(file));
            String s = new String();
            ArrayList<Character> cipherText = new ArrayList<Character>();

            System.out.println("\nCiphertext file name: "+args[0]);

            // Reading the ciphertext into the file
            while(scanner.hasNext()){
                s = scanner.nextLine();

                for(i = 0; i < s.length(); i++)
                {
                    cipherText.add(Character.toLowerCase(s.charAt(i)));    
                }
            }
            
            System.out.println("Number of characters in ciphertext: "+cipherText.size()+"\n");

            // Running the decryption algorithm on the cipherText
            decrypt(cipherText,args[1],args[2]);

            System.out.println("Recovered plaintext:\n");

            // Printing out the recovered plaintext
            for(i = 0; i < cipherText.size(); i++)
            {
                System.out.print(cipherText.get(i));
            }

            System.out.println("\n");
        }

        catch(Exception e){
            e.printStackTrace();
        }

    }

}
