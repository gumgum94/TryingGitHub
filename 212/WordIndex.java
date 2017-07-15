

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.Buffer;

import java.util.StringTokenizer;

public class WordIndex {

    boolean su;
    WordList list = new WordList(); // create a node list.

   WordIndex(String filename) {


       try {
           FileReader file = new FileReader(filename); // open the file by using filereader.
           BufferedReader reader = new BufferedReader(file); //running the file

           int loc = 0; // the location determines number of node

           String word = ""; // will use this to read the word in line
           String line = reader.readLine();// read the line.

           while (line != null) { // check if checking all the lines weather finishes.
               word += line;
               line = reader.readLine();}

           String alpha = word.replaceAll("[^a-zA-Z  ]", " "); //Replacing all non-alphabet to be a space
           alpha = alpha.toUpperCase();   // uppercase the text
           System.out.println(" The List of Word before Sorting: "+alpha);

           StringTokenizer beta = new StringTokenizer(alpha, " "); // Take a word after  space
           String finalword[] = new String[beta.countTokens()]; // Used to make a new array.
           while (beta.hasMoreElements()) {//Make a loop to the end of list of text.
               finalword[loc] = beta.nextToken(); //Take array of word
               list.add(finalword[loc], loc); //store it to the WordList add function.
               loc++; //increase location after solving one word.
           }
       }
         catch (Exception e) { // Use catch file to see if the file error or not.
               su = false;
               return;
           }
       su = true; // if the file work return to status ==true.
   }

   boolean status() {
       return su;
   } //getting status the file work / not/

   void print() {  // print the WordList.
       list.print();
   }

}
