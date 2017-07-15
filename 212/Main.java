
//Name: Phu Pham
//Couse: CSE 223
// To approach and solve the file, firstly I Open the file , then reading a whole text and just only taking uppercase word without punctuation.
// Then  I make them to arrays and store each letter to WordList linked list. For the location I ch
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

	  public static void main(String[] args) {
	        String filename = "TestFile.txt";// whatever input file to put in
	        WordIndex fb =new WordIndex(filename);// the file come to function WordIndex to check do many others function here.
	        if(fb.status()==true) { // if there is a file, it opens
	            fb.print(); // the method print inside the function WordIndex will cover the printing part all program.
	            System.out.println("File Opened");}// announcement  of open the file
	        else System.out.println("Not found a file"); //announcement of not found the file.
	    }
	}
