package engine;


/**
 * <h2>The Class that temporarily stores and defines the JSON body,
 * which is returned to the user in response to his solve</h2>
 *
 */
public class OptionAnswer {

    private final boolean success;
    private final String feedback;


    /**
     * The class constructor that defines its fields
     * @see engine.controller.QuizController#postAnswer(Long, UserAnswer) postAnswer
     *
     * @param isCorrect boolean value as result of check correctness of answer
     */
    public OptionAnswer(boolean isCorrect) {
        if (isCorrect) {
            this.feedback = "Congratulations, you're right!";
            this.success = true;
        } else {
            this.feedback = "Wrong answer! Please, try again.";
            this.success = false;
        }
    }

    /* Getters and Setters */

    public boolean isSuccess() {
        return success;
    }
    public String getFeedback() {
        return feedback;
    }
}