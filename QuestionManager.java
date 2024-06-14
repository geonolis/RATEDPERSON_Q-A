import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionManager {
    private Map<Integer, Question> questionMap = new HashMap<Integer, Question>();

    public String GetAllQuestions(){
        String string = "";
        for (Question question : questionMap.values()) {

                    if (question instanceof MultipleChoiceQuestion) {
                        string += "QUESTION"+"\n"+"{"+"\n"+"\t"+"TYPE "+ "MC"
                                    + "\n"+"\t"+"CODE "+ question.getCode()
                                    + "\n"+"\t"+"DESCR "	+ question.getDescription()
                                    + "\n"+"\t"+"ANSWERS "	+ ((MultipleChoiceQuestion)question).getAnswers()
                                    + "\n"+"\t"+"CORRECT_ANSWERS " + ((MultipleChoiceQuestion)question).getCorrectAnswers()
                                    + "\n"+"}"+"\n";
                    }// mc
                    else if (question instanceof FillInTheBlanksQuestion) {
                        string += "QUESTION"+"\n"+"{"+"\n"+"\t"+"TYPE "+ "FILL"
                                    + "\n"+"\t"+"CODE "+ question.getCode()
                                    + "\n"+"\t"+"DESCR "	+ question.getDescription()
                                    + "\n"+"\t"+"ANSWERS "	+ ((FillInTheBlanksQuestion)question).getWords()
                                    + "\n"+"\t"+"CORRECT_ANSWERS " + ((FillInTheBlanksQuestion)question).getCorrectOrder()
                                    + "\n"+"}"+"\n";
                    }//fill
                    else if (question instanceof WordAnswerQuestion) {
                        string += "QUESTION"+"\n"+"{"+"\n"+"\t"+"TYPE "+ "FILL"
                                    + "\n"+"\t"+"CODE "+ question.getCode()
                                    + "\n"+"\t"+"DESCR "	+ question.getDescription()
                                    + "\n"+"\t"+"WORD "	+ ((WordAnswerQuestion)question).getCorrectAnswer()
                                    + "\n"+"}"+"\n";
                    }//fill
        }
        return string;
    }
    
    public void addMultipleChoiceQuestion(MultipleChoiceQuestion question) {
   //    question.setChoices(choices);
    //   question.setCorrectAnswers(correctAnswers);
        questionMap.put(question.getCode(), question);
        System.out.println("Multiple choice question added successfully.");
    }// add multiple

    public void addWordAnswerQuestion(WordAnswerQuestion question) {
        questionMap.put(question.getCode(), question);
        System.out.println("Word answer question added successfully.");
    }//add word answer

    public void addFillInTheBlanksQuestion(FillInTheBlanksQuestion question) {
        questionMap.put(question.getCode(), question);
        System.out.println("Fill-in-the-blanks question added successfully.");
    }// add fill blanks

    public Question getQuestionById(int id) {
        return questionMap.get(id);
    }// get question

    public void addQuestion(Question question) {
        questionMap.put(question.getCode(), question);
        System.out.println("Question added successfully.");
    }// add fill blanks


    public boolean QuestionExists(int code) {
        return questionMap.containsKey(code);
    }

    public void displayAllQuestions() {
        if (!questionMap.isEmpty()) {
            for (Question question : questionMap.values()) {
                System.out.println(question.toString());
                /* 
                System.out.println("Code: " + question.getCode());
                System.out.println("Description: " + question.getDescription());
                // System.out.println("Type: " + question.get());
                System.out.println("-----------------------------");
                */
            }
        } else {
            System.out.println("No questions found.");
        }
    }// display question

    public boolean isMultipleChoiceAnswerCorrect(int questionCode, List<Integer> chosenOptions) {
        // Retrieve the question from the map
        Question question = questionMap.get(questionCode);
        if (!(question instanceof MultipleChoiceQuestion)) {
            throw new IllegalArgumentException("The question is not a multiple choice question.");
        }
        MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion) question;
        List<Integer> correctOptions = mcQuestion.getCorrectAnswers(); // Directly use the list
        // Check if the chosen options match the correct options
        return chosenOptions.equals(correctOptions);
    }// check multiple

    public boolean isWordAnswerCorrect(int questionCode, String wordAnswer) {
        // Retrieve the question from the map
        Question question = questionMap.get(questionCode);
        if (!(question instanceof WordAnswerQuestion)) {
            throw new IllegalArgumentException("The question is not a word answer question.");
        }
        
        WordAnswerQuestion waQuestion = (WordAnswerQuestion) question;
        String correctAnswer = waQuestion.getCorrectAnswer(); // Assuming you have a getter
        
        // Check if the provided word answer matches the correct answer
        return wordAnswer.equalsIgnoreCase(correctAnswer);
    }// check one word

    public boolean isFillInTheBlanksAnswerCorrect(int questionCode, List<String> fillInTheBlanksAnswer) {
        // Retrieve the question from the map
        Question question = questionMap.get(questionCode);
        if (!(question instanceof FillInTheBlanksQuestion)) {
            throw new IllegalArgumentException("The question is not a fill-in-the-blanks question.");
        }
        
        FillInTheBlanksQuestion fibQuestion = (FillInTheBlanksQuestion) question;
        List<String> correctOrder = Arrays.asList(fibQuestion.getCorrectOrder()); // Assuming you have a getter
        
        // Check if the provided answer matches th
        return fillInTheBlanksAnswer.equals(correctOrder);
    }// check fill


    private boolean isAnswerCorrect(Answer answer) {
        // Determine if the answer is correct based on its type
        if (answer instanceof MultipleChoiceAnswer) {
            return this.isMultipleChoiceAnswerCorrect(answer.getQuestionCode(), ((MultipleChoiceAnswer) answer).getChosenOptions());
        } else if (answer instanceof WordAnswer) {
            return this.isWordAnswerCorrect(answer.getQuestionCode(), ((WordAnswer) answer).getWordAnswer());
        } else if (answer instanceof FillInTheBlanksAnswer) {
            return this.isFillInTheBlanksAnswerCorrect(answer.getQuestionCode(), ((FillInTheBlanksAnswer) answer).getFillInTheBlanksAnswer());
        } else {
            return false; // Default to false if the answer type is not recognized
        }
    }// check answer by type



    public void printPercentageOfCorrectAnswersForEachQuestion(AnswerManager answers) {
        Map<Integer, Integer> questionAnswerCount = new HashMap<>();
        Map<Integer, Integer> questionCorrectAnswerCount = new HashMap<>();
    
        for (Answer answer : answers.answerList) {
            int questionCode = answer.getQuestionCode();
            questionAnswerCount.put(questionCode, questionAnswerCount.getOrDefault(questionCode, 0) + 1);
    
            if (isAnswerCorrect(answer)) {
                questionCorrectAnswerCount.put(questionCode, questionCorrectAnswerCount.getOrDefault(questionCode, 0) + 1);
            }
        }
    
        for (Map.Entry<Integer, Integer> entry : questionAnswerCount.entrySet()) {
            int totalAnswers = entry.getValue();
            int correctAnswers = questionCorrectAnswerCount.getOrDefault(entry.getKey(), 0);
            double percentage = ((double) correctAnswers / totalAnswers) * 100;
    
            System.out.printf("Question Code: %d | Total Answers: %d | Correct Answers: %d | Percentage: %.2f%%\n", entry.getKey(), totalAnswers, correctAnswers, percentage);
        }
        System.out.println("-----------------------------");

    }// percent by question
    
    
    public void printPercentageOfCorrectAnswersPerCandidate(AnswerManager answers) {
        Map<Integer, Integer> candidateAnswerCount = new HashMap<>();
        Map<Integer, Integer> candidateCorrectAnswerCount = new HashMap<>();
    
        for (Answer answer : answers.answerList) {
            int evaluatorCode = answer.getEvaluatedCode();
            candidateAnswerCount.put(evaluatorCode, candidateAnswerCount.getOrDefault(evaluatorCode, 0) + 1);
    
            if (isAnswerCorrect(answer)) {
                candidateCorrectAnswerCount.put(evaluatorCode, candidateCorrectAnswerCount.getOrDefault(evaluatorCode, 0) + 1);
            }
        }
    
        for (Map.Entry<Integer, Integer> entry : candidateAnswerCount.entrySet()) {
            int totalAnswers = entry.getValue();
            int correctAnswers = candidateCorrectAnswerCount.getOrDefault(entry.getKey(), 0);
            double percentage = ((double) correctAnswers / totalAnswers) * 100;
    
            System.out.printf("Candidate Code: %d | Total Answers: %d | Correct Answers: %d | Percentage: %.2f%%\n", entry.getKey(), totalAnswers, correctAnswers, percentage);
        }
        System.out.println("-----------------------------");

    }// percent by candidate
    
   
}