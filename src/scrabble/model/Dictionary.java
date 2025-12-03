package src.scrabble.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Dictionary represents a collection of valid Scrabble words.
 * 
 * @author Ashfaqul Alam, 101195158
 * @version Nov 23rd, Milestone 3
 */
public class Dictionary {
    private Set<String> validWords = new HashSet();

    /**
     * Creates a Dictionary and immediately loads all words from the file
     * at the given path.
     *
     * @param var1 the path to the dictionary text file
     */
    public Dictionary(String var1) {
        this.loadDictionary(var1);
    }

    /**
     * Loads words from the given file into the set.
     *
     * @param var1 path to the dictionary text file
     */
    private void loadDictionary(String var1) {
        try (BufferedReader var2 = new BufferedReader(new FileReader(var1))) {
            while(true) {
                String var3;
                if ((var3 = var2.readLine()) == null) {
                    System.out.println(" Dictionary loaded: " + this.validWords.size() + " words");
                    break;
                }

                this.validWords.add(var3.trim().toUpperCase());
            }
        } catch (IOException var7) {
            System.err.println(" Could not load dictionary: " + var7.getMessage());
            System.err.println("Continuing without word validation");
        }

    }

    /**
     * Checks whether the given word is contained in this dictionary.
     * The check is case-insensitive; the input word is converted to uppercase
     * before lookup.
     *
     * @param var1 the word to check
     * @return true if the word exists in the dictionary
     */
    public boolean isValid(String var1) {
        return this.validWords.contains(var1.toUpperCase());
    }

    /**
     * Returns the number of words currently stored in this dictionary.
     *
     * @return the size of the dictionary
     */
    public int size() {
        return this.validWords.size();
    }

    /**
     * Indicates whether this dictionary contains no words.
     *
     * @return true if the dictionary has zero words,
     */
    public boolean isEmpty() {
        return this.validWords.isEmpty();
    }
}
