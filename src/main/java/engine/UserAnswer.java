package engine;

/**
 * <h2>The Class defining and storing temporal JSON body model with user solve</h2>
 *
 * The solution consists of an integer array
 * that can store several elements (answer numbers)
 * or can be empty if there are no correct answers.
 *
 */
public class UserAnswer {

    private int[] answer;

    /* Getters and Setters */
    public int[] getAnswer() {
        return answer;
    }
    public void setAnswer(int[] answer) {
        this.answer = answer;
    }
}