import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * The DNASequences class trains a first-order Markov chain model for
 * generating DNA sequences.
 * 
 * @author Your Name Here!
 */
public class DNASequences {
    /** The transitions from each starting character. */
    private Map<Character, Transitions<Character>> transitions;

    /** A sequence of 1000 nucleotides (generated from https://www.bioinformatics.org/sms2/random_dna.html). */
    private static final String DNA = "AAATACGGATAGGCACAATCTTTTTCGATCCACTGTAAGGCTGAAAAATATTCGGAAATGGGTTTTAAGGTCCATCCGGCCTCATAATCAATGTGAGCTACCCAAACCACTATTGTTTAACCCGATATGAGCCCTAGTTCTTAGGTGTACCTTGCTAAGTTCGAGCATATCCGAGTTATAACCTGGTGTATGCTCCTTATAATGATGTTTGCATACCTCGCAGAGTCATCCGCATCACGGTAGAAATTGCGATCGCATCAATGCTCTCAATGCCCTATGCTTCATAGATTGTACACACACTGTTTCTACCTGGTTCTATAGCTAGGGGGTACAAATGCACAAGGCCCGTAAACAAACCAAGCATCGATGTTCTTGACGTTTTCGAGCATCATTACGCATCCTTGGGCAAGAAAATAGACGTATGGTGCCGGCCATATATCTAATCTTAGACTACCTGCCCCATCCCATACATGCTCTTACTTGGTGCGTTGAAGTCTGAGTCGGTAGAGTCAGGGTGTCTGTGCAAAGCATGGTTGGTATAACGAGAATGGCACTAAGTAACCAACGCAAGGGCAGATTCGGCCTCATATTCATGGGGATAACCTCCCCAGCTTTCTACTCTTTTACCAGTCTCCGTTGATATTTCTAGACAGCGGGCATTCTCGATTTTCTACTGAACGGGATATTATGGTCGTGTTCCACGAACAAGCTGCAGCAGCCCCTTGTAACCAAGGAGACATCCGAACAGGAGAAGTGCCCCATGAGCTTTCGGCGAGGTTAACAGGGCATCTATCATTATGATAGCGGTCACGACATCGTGTATTGAATCCCATCGTAGCGCCACGCTTCTGACCGTCGCGCGCATTTTGCCAGTAATGCTTTCATTGCTGAAACTTGTGTTCGGGTCTCGTCGTAGCATGGACGTCTGCTTCCGGCCCGGGATAAGGCTTTATGTCAGTGCAAAGTGGTCAGGGTTTCTAGGTGGCAATAGTGTGTATCA";

    /**
     * Creates a new DNASequences object.
     */
    public DNASequences() {
        this.transitions = new HashMap<Character, Transitions<Character>>();

        // fill the transitions with empty objects
        // NOTE: you should not do this in the MarkovChain class, 
        // but it works well here since there are only 4 possible states/histories
        for (char current : new char[]{'A', 'C', 'G', 'T'}) {
            this.transitions.put(current, new Transitions<Character>());
        }
    }

    /**
     * Trains the Markov Chain model of a given DNA sequence.
     * 
     * @param sequence The DNA sequence to use for training
     */
    public void trainCounts(String sequence) {
        for (int i=0; i<sequence.length() - 1; i++){
            char current = sequence.charAt(i);
            char next = sequence.charAt(i+1);
            this.transitions.get(current).addTransition(next);
        }
    }

    /**
     * Generates a random DNA sequence of a given {@code length} starting with
     * a given {@code start} character.
     * 
     * @param start The first character of the sequence
     * @param length The desired length of the sequence
     * @param rand A {@link Random} number generator
     * 
     * @return A randomly generated DNA sequence String 
     */
    
    private String generate(char start, int length, Random rand) {
        String str = "";
        str = str + start;
        char current = start;
        for (int i = 0; i < length; i++){
            char next = this.transitions.get(current).generateNextState(rand);
            str = str + next;
            current = next;
        }
        return str;
    }

    /**
     * Prints the probabilities of all transitions in the learned model.
     */
    private void printTransitions() {
        // loop through each current character
        for (Map.Entry<Character, Transitions<Character>> entry : transitions.entrySet()) {
            char current = entry.getKey();
            Transitions<Character> counts = entry.getValue();

            // loop through each transition to a next character
            for (Map.Entry<Character, Integer> transition : counts.counts.entrySet()) {
                char next = transition.getKey();
                double prob = ((double) transition.getValue()) / counts.totalCount;

                System.out.println(current + " to " + next + ": " + prob);
            }
        }
    }

    /**
     * Runs the program.
     * 
     * @param args The command line arguments (unused)
     */
    public static void main(String[] args) {
        // TODO: remove this comment to test the trainCounts method.
        // create and train a new DNA sequences object
        DNASequences model = new DNASequences();
        model.trainCounts(DNA);

        // print the transition probabilities
        model.printTransitions();
        //TODO: remove this comment to test the trainCounts method. 

        // generate some random text
        // TODO: remove this comment to test the generate method
        SecureRandom rand = new SecureRandom();
        String generatedText = model.generate('A', 30, rand);
        System.out.println(generatedText);
       // TODO: remove this comment to test the generate method */
    }   
}
