/*
 * Sara Hrnciar
 * CSC 301 Prog1
 * Dr. Lori
 * Program containing brute, kmp, and boyer moore algorithms. Searches for patterns input by users in file 
 * file defined in line 132
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
//Special thanks to the man on stack overflow who taught me how to set up a clock in java!
//https://stackoverflow.com/questions/180158/how-do-i-time-a-methods-execution-in-java
public class searchCompare {
    //converts from string to linked list. yay.
    public static List<Character> stringToLinkedList(String str) {
        List<Character> list = new List<>();
        for (int i = 0; i < str.length(); i++) {
            list.InsertAfter(str.charAt(i));
        }
        return list;
    }
    //a helper function to get char at certain position of linked list. couldve put this in the class, but...
    public static char getCharAt(List<Character> list, int index) {
        int post = list.GetPos();
        list.SetPos(index);
        char v = list.GetValue();
        list.SetPos(post);
        return v;
    }
    // Bad character heuristic for BM. Returns how much to shift over based on the mismatched char
    public static int badchar(String str, int index, char ch) {
        int newSpot = str.lastIndexOf(ch, index - 1); 
        return (newSpot == -1) ?  index + 1: index - newSpot; 
    }
    //good suffix heuristic for bm. Returns how much to shift based on the good suffix
    public static int goodSuffix(String str, int index, String suffix) {
        String check = str.substring(0, index);
        int newSpot = check.lastIndexOf(suffix);
        return (newSpot == -1) ? str.length(): index - newSpot; 
    }
    //bm algo using bad character and good suffix heur
    public static ArrayList<Integer> boyerMoore(List<Character> str, String substr) {
        ArrayList<Integer> toRet = new ArrayList<>();
        int n = str.GetSize();
        int m = substr.length();
        int i = m - 1;
        int j = m - 1;

        while (i < n) {
            str.SetPos(i);
            if (str.GetValue() == substr.charAt(j)) {
                if (j == 0) {//match
                    toRet.add(i); 
                    i += m;  
                    j = m-1; 
                } else {
                    i--; j--;
                }
            } else {//mismatch
                int bc = badchar(substr, j, str.GetValue());
                int gs = (j == m - 1) ? 1 : goodSuffix(substr, j, substr.substring(j + 1, m));
                i += Math.max(bc, gs);
                j = m - 1;  
            }
        }
        return toRet;
    }
    //brute force algo
    public static ArrayList<Integer> brute(List<Character> str, String substr){
        ArrayList<Integer> toRet = new ArrayList<Integer>();
        for (int i = 0; i<=str.GetSize()- substr.length(); i++){
            int it = i; int j = 0;
                while (getCharAt(str, it)== substr.charAt(j) && j <= substr.length()-1){ //iterate until the chars arent equal or j ran thru
                    if (j == (substr.length() - 1)){ //if youve iterated through the entire substring
                        toRet.add(i); //add the potential index to the list
                        break;
                    }
                    it++; j++;   
                }
        }
        return toRet;
    }
    //fail function retuns array of where to go upon failure ;(
    public static ArrayList<Integer> failFunc(String str){
        ArrayList<Integer> lps = new ArrayList<Integer>(); //start with list of str.length 0s
        for (int i = 0; i<str.length();i++){
            lps.add(0);
        }
        int len = 0; int i = 1;
        while (i<str.length()){
            if (str.charAt(i) == str.charAt(len)){
                len++;
                lps.set(i, len);
                i++;
            }else {
                if (len>0){ //if last was a match
                    len = lps.get(len-1);
                }else{  //if prev was a miss/ len of longest match is 0
                    lps.set(i,0);
                    i++;
                }
            }
        }
        return lps;
    }
        //real  func which uses failure func
    public static ArrayList<Integer> kmp(List<Character> str, String substr){
        ArrayList<Integer> toRet = new ArrayList<Integer>();
        ArrayList<Integer> lps = failFunc(substr);
        int i = 0; int j = 0; int potentialIndex= 0;
        while (i<str.GetSize()){
            potentialIndex = i;
            if (getCharAt(str, i) == substr.charAt(j)){
                if (j == substr.length()-1){
                    toRet.add(potentialIndex-substr.length()+1);
                    j = lps.get(j-1);
                }
                j++; i++;
            }else{
                if (j==0){
                    i++;
                }else{
                    j = lps.get(j-1);
                }
            }
        }
        return toRet;
    }
    //main func takes file and asks for patterns to search for
    public static void main(String args[]){
        File strings = new File("prog1input2.txt");
        Scanner inp = new Scanner(System.in);
        List<Character> strL = new List<>();

        //populate arraylist with file input
        try (Scanner sc = new Scanner(strings)){ 
            strL = stringToLinkedList(sc.nextLine());
            System.out.println(strL);
        }catch (FileNotFoundException e){
            System.err.println(e);
        }
        while (true) { //go til user ends input
            System.out.println("What substring do you want to search for?");
            String search = inp.nextLine();
            //call+time inputs and display results+times
            long bmTime = System.nanoTime();
            ArrayList<Integer> positions6 = boyerMoore(strL, search);
            bmTime = System.nanoTime() - bmTime;
            long bruteTime= System.nanoTime();
            ArrayList<Integer> positions1 = brute(strL, search);
            bruteTime = System.nanoTime() - bruteTime;
            long kmpTime = System.nanoTime();
            ArrayList<Integer> positions = kmp(strL, search);
            kmpTime =System.nanoTime() - kmpTime;
        
            System.out.println("Here is the result found with KMP algo: " + positions);
            System.out.println("Here is the result found with BM algo: " + positions6);
            System.out.println("Here is the result found with Brute algo: " + positions1);
            System.out.println("Brute took " + bruteTime + " nanoseconds");
            System.out.println("KMP took " + kmpTime + " nanoseconds");
            System.out.println("Boyer Moore took " + bmTime + " nanoseconds\n");

            String boyerIdeal = "Boyer Moore was the fastest. I may assume the input file had a lot of text, and I may assume the pattern either didnt appear much in the text or the pattern was large. I also may assume the text didnt have a lot of repeats.";
            String kmpIdeal = "KMP was the fastest. I assume the text and/or pattern were quite repetitive, or that the pattern appeared often in the text. The file may have also not had a lot of text in it.";
            String bruteIdeal = "This was likely a very small problem";
            System.out.println(bmTime<kmpTime && bmTime<bruteTime ? boyerIdeal : (kmpTime < bruteTime ? kmpIdeal : bruteIdeal));

            /*
            If you are really, truly, unsure of the situation between the pattern and the string, go with KMP 
            KMP is most efficient of the three in its worst case. 
            At its WORST case, BM is just as bad as brute force. 
            BM's 'worst case' is when there are many partial matches between the string and pattern
            Boyer Moore can, however, be the best choice when there is much text to parse through 
            and when the text is not expected to repeat very often in the text. 
            Brute force is a quick, easy to implement algorithm-- 
            but it is not optimal as it will always check every possible pattern match
            */
        }
    }   
}
