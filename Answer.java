import java.util.List;

class Answer {
    private int evaluatedCode;
    //int questionType;
    private Question question;

    public Answer(int evaluatedCode, Question question) {
        this.evaluatedCode = evaluatedCode;
        //this.questionType = questionType;
        this.question = question;
    }

    public Question getQuestion() {
        return this.question;
    }

    public int getEvaluatedCode(){
        return this.evaluatedCode;
    }

   

    public int getQuestionCode(){
        return this.question.getCode();
    }
    }

class MultipleChoiceAnswer extends Answer {
    List<Integer> chosenOptions;

    public MultipleChoiceAnswer(int evaluatedCode, Question question, List<Integer> chosenOptions) {
        super(evaluatedCode, question);
        this.chosenOptions = chosenOptions;
    }
    public List<Integer> getChosenOptions() {
        return chosenOptions;
    }
}

class WordAnswer extends Answer {
    String wordAnswer;

    public WordAnswer(int evaluatedCode,  Question question, String wordAnswer) {
        super(evaluatedCode, question);
        this.wordAnswer = wordAnswer;
    }

    public String getWordAnswer() {
        return this.wordAnswer;
    }
}

class FillInTheBlanksAnswer extends Answer {
    List<String> fillInTheBlanksAnswer;

    public FillInTheBlanksAnswer(int evaluatedCode, Question question, List<String> fillInTheBlanksAnswer) {
        super(evaluatedCode,  question);
        this.fillInTheBlanksAnswer = fillInTheBlanksAnswer;
    }
    public List<String> getFillInTheBlanksAnswer() {
        return this.fillInTheBlanksAnswer;
    }
}
