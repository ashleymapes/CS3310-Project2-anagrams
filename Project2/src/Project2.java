/*
 * Ashley Mapes
 * Student ID: 016866278
 * CS 3310, Fall 2024
 * Programming Assignment 2
 * -----------------------------------
 * this program reads a file and groups words that are anagramsof each other
 * 
 * (ASSUMING ANAGRAMS ARE CONSIDERED WORDS WITH THE SAME CHARACTERS IN A DIFFERENT ORDER)
 * (ALSO ASSUMING THAT WORDS IN THE SAME ORDER BUT WITH ' SOMEWHERE IN IT ARE CONSIDERED THE SAME WORD)
 */
import java.io.*;
import java.util.*;


public class Project2 {

    /*
     * Method: main
     * Purpose: calls methods to read file and perform anagram analysis then prints output
     * Parameters: String[] args: command line arguments
     * Returns: void
     */
    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);

        HashMap<String, HashSet<String>> anagrams = new HashMap<>();

        // ask user for file path
        System.out.println("Please enter the file path of the input file:");        
        String filePath = scanner.nextLine();
        scanner.close();

        
        processFile(filePath, anagrams);
        cleanHashMap(anagrams);
        
        writeAnagramSetsToFile(anagrams, "output.txt");

        
        printAnagramSets(anagrams);
    }


    /*
     * Method: processFile
     * Purpose: reads each line of the file and adds each word to the anagram HashMap
     * Parameters: String filePath - path to the file
     *             HashMap<String, HashSet<String>> anagrams - stores sets of anagrams with corresponding keys
     * Returns: void
     */
    private static void processFile(String filePath, HashMap<String,HashSet<String>> anagrams){
        // open file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // read file line by line and add each word to the anagram hashmap
            String line;
            while ((line = br.readLine()) != null) {
                addWord(anagrams, line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }

    /*
     * Method: printAnagramSets
     * Purpose: prints all sets of anagrams with more than one word and displays the total number of anagram sets
     * Parameters: HashMap<String, HashSet<String>> anagrams
     *             - stores sets of anagrams
     * Returns: void
     */

    private static void printAnagramSets(HashMap<String, HashSet<String>> anagrams){
        // print header
        System.out.println("List of anagrams:");

        // print out list of anagrams  with more than 1 word
        anagrams.forEach((key, value) -> System.out.println(value));
        System.out.println();
        System.out.println("Number of anagram sets: " + anagrams.size());

    }

    /*
     * Method: writeAnagramSetsToFile
     * Purpose: writes all sets of anagrams within the HashMap to a file and includes info about the number of anagram sets
     * Parameters: HashMap<String, HashSet<String>> anagrams - stores sets of anagrams
     *  String filePath - path to the file
     * Returns: void
     */
    private static void writeAnagramSetsToFile(HashMap<String, HashSet<String>> anagrams, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("List of anagrams:\n");

            // Write each set of anagrams with more than one word
            for (HashSet<String> set : anagrams.values()) {
                if (set.size() > 1) {
                    writer.write(set.toString());
                    writer.newLine();
                }
            }
            
            // Write the total number of anagram sets
            writer.write("\nNumber of anagram sets: " + anagrams.size());
            System.out.println("Anagram sets written to file: " + filePath);
            
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
    
    /*
     * Method: cleanHashMap
     * Purpose: removes all entries from the HashMap with a size of 1
     * Parameters: HashMap<String, HashSet<String>> anagrams - stores sets of anagrams
     * Returns: void
     */
    private static void cleanHashMap(HashMap<String, HashSet<String>> anagrams){
        anagrams.entrySet().removeIf(entry -> entry.getValue().size() == 1);
    }

    /*
     * Method: addWord
     * Purpose: takes in and adds the given word to the given hashmap after cleaning and sorting it
     * Paramters: HashMap<String,HashSet<String>> - hashmap of words sorted by what are anagrams of each other
     *            String word - given word from file
     * Returns: void
     */
    private static void addWord(HashMap<String,HashSet<String>> anagrams, String word){
        // clean word to add to hashmap and create a sorted word to use as the key
        String cleanedWord = cleanWord(word);
        String sortedWord = sortWord(cleanedWord);

        // add to anagrams hashmap
        anagrams.computeIfAbsent( sortedWord, k -> new HashSet<String>()).add(cleanedWord);
    }

    /*
     * Method: cleanedWord
     * Purpose: cleans the word by converting it to lowercase and removing non-alphanumberic characters
     * Paramters: String word - the word to clean
     * Returns: String - the cleaned word
     */
    private static String cleanWord(String word){
        // remove non-alphanumeric characters and convert to lowercase
        return word.replaceAll("[^\\p{IsAlphabetic}]", "").toLowerCase();
    }

    /*
     * Method: sortWord
     * Purpose: sorts the characters in a word alphabetically
     * Paramters: String word - the word to sort
     * Returns: String - the sorted word
     */
    private static String sortWord(String word){
        // turn word into an array of characters and sort it
        char[] arr = word.toCharArray();
        Arrays.sort(arr);
        return new String(arr);
    }

}

