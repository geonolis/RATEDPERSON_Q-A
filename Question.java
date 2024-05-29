import java.util.List;
import java.util.Arrays;

public abstract class Question {
    int code;
    String description;


    public Question(int code, String description) {
        this.code = code;
        this.description = description;
    }
   
    public int getCode() {
        return this.code;
    }
    public String getDescription() {
        return this.description;
    }
    public String toString() {
        return 	this.description + "\n";
    }

    // Abstract method to be overridden by subclasses
    //public abstract QuestionType getQuestionType();    
}

class MultipleChoiceQuestion extends Question {
    private String[] choices;
    private List<Integer> correctAnswers;

    public MultipleChoiceQuestion(int code, String description) {
        super(code, description);
    }

    public void setChoices(String[] choices){
        this.choices = choices;
    }
    
    public void setCorrectAnswers(List<Integer> correctAnswers){
        this.correctAnswers = correctAnswers;
    }

    public List<Integer> getCorrectAnswers() {
        return this.correctAnswers;
    }

    public String toString(){
        return "Question Details :"
                + super.toString()
                + "\nchoices: " + Arrays.toString(this.choices)
                + "\n**************************";
    }
}

class WordAnswerQuestion extends Question {
    String correctAnswer;

    public WordAnswerQuestion(int code, String description, String correctAnswer) {
        super(code, description);
        this.correctAnswer = correctAnswer;
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }
}

class FillInTheBlanksQuestion extends Question {
    private String[] words;
    private String[] correctOrder;

    // Constructor accepting int, String, List<String>, and List<String>
    public FillInTheBlanksQuestion(int code, String description, String[] words, String[] correctOrder) {
        super(code, description); // Call the constructor of the superclass (Question)
        this.words = words;
        this.correctOrder = correctOrder;
    }
    public String[] getCorrectOrder() {
        return this.correctOrder;
    }


}