import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

/**
 * The Transitions class represents a set of transition counts for
 * the transition function of a Markov model.
 * 
 * @author CS Professors
 */
public class Transitions<State> {
    /** The counts of next states for these transitions. */
    public HashMap<State, Integer> counts;

    /** The total count of transitions across all possible next states. */
    public int totalCount;

    /**
     * Creates a new Transitions object.
     */
    public Transitions() {
        this.counts = new HashMap<State, Integer>();
        this.totalCount = 0;
    }

    /**
     * Adds an observed transition for a given next State.
     * 
     * @param nextState The next State that we transition to
     */
    public void addTransition(State nextState) {
        // increase the count of this transition by 1
        Integer count = this.counts.get(nextState);
        if (count == null) {
            // the count was missing, so the new count is 1
            count = Integer.valueOf(1);
        } else {
            // the count existed, so add 1 to it
            count = Integer.valueOf(count.intValue() + 1);
        }
        this.counts.put(nextState, count);

        // increase our total count by 1
        this.totalCount++;
    }

    /**
     * Generates a randomly chosen next State based on
     * the roulette wheel algorithm.
     * 
     * @param rand The {@link Random} to use for generating random values
     * 
     * @return A randomly selected next State
     */
    public State generateNextState(Random rand) {
        // pick a random number up to the total count
        int randVal = rand.nextInt(this.totalCount);
        State nextState = null;

        // pick a character based on the random number
        for (Entry<State, Integer> entry : this.counts.entrySet()) {
            Integer count = entry.getValue();
            nextState = entry.getKey();

            // is this our randomly chosen character?
            if (randVal < count) {
                break;
            } else {
                randVal -= count;
            }
        }

        return nextState;
    }
}
