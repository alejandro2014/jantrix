package jantrix;

/**
 * Class Scores, stores the maximum scores
 * @author alejandro
 */
public class Scores {
    private final int MAX_ENTRIES = 10;
    private String[] names = new String[MAX_ENTRIES];
    private int[] maxScores = new int[MAX_ENTRIES];
    private int[] levels = new int[MAX_ENTRIES];

    public Scores() {
        
    }
    
    /**
     * Stores the given score, if it's one of the higher scores
     * asks for the player game and save the new record
     * @param score The new score given by the player
     * @param level The last level of the
     */
    public void storeEntry(int score, int level) {
        
    }
    
    public void readFile() {
        
    }
    
    public void writeFile() {
        
    }
}
