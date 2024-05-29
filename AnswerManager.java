import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnswerManager {
//    public static List<Question> questionList = new ArrayList<>();
    public List<Answer> answerList = new ArrayList<>();

    // addAnswer Overloaded method:
    public void addAnswer(MultipleChoiceAnswer answer) {
        Question question=answer.getQuestion();
        if (question!= null) {
                // Handle multiple choice answer
                // Assuming answer is a List<Integer> for multiple choice
                if (answer.getChosenOptions() instanceof List) {
                    List<?> list = (List<?>) answer.getChosenOptions();
                    if (list.stream().allMatch(item -> item instanceof Integer)) {// checks all are integers
                        answerList.add(answer);
                        System.out.println("Multiple choice answer added successfully.");
                    } else {
                        System.out.println("Invalid answer format for multiple choice question.");
                    }
                } else {
                    System.out.println("Invalid answer format for multiple choice question.");
                }
        } else {
            System.out.println("Question not found.");
        }
    }


    public void addAnswer(WordAnswer answer) {
        Question question = answer.getQuestion();
        if (question != null) {
            // Handle word answer
            // Assuming answer is a String for word answer
            if (answer.getWordAnswer() instanceof String) {
                //                   WordAnswer wordAnswer = new WordAnswer(evaluatedCode,  questionCode, (String) answer);
                answerList.add(answer);
                System.out.println("Word answer added successfully.");
            } else {
                System.out.println("Invalid answer format for word answer question.");
            }
        } else {
            System.out.println("Question not found.");
        }
    }

    public void addAnswer(FillInTheBlanksAnswer answer) {
        Question question = answer.getQuestion();
        if (question != null) {
            // Handle word answer
            // Assuming answer is a String for word answer
            if (answer.getFillInTheBlanksAnswer() instanceof List) {
                answerList.add(answer);
                System.out.println("Fill-in-the-blanks answer added successfully.");
            } else {
                System.out.println("Invalid answer format for fill-in-the-blanks question.");
            }
        } else {
            System.out.println("Question not found.");
        }
    }

    public void printAnswersByEvaluator(int evaluatorCode) {
        for (Answer answer : answerList) {
            if (answer.getEvaluatedCode() == evaluatorCode) {
                System.out.println("Candidate: " + evaluatorCode);
                System.out.println("Question Code: " + answer.getQuestionCode());
                if (answer instanceof MultipleChoiceAnswer) {
                    System.out.println("Chosen Options: " + ((MultipleChoiceAnswer) answer).getChosenOptions());
                } else if (answer instanceof WordAnswer) {
                    System.out.println("Answer: " + ((WordAnswer) answer).getWordAnswer());
                } else if (answer instanceof FillInTheBlanksAnswer) {
                    System.out.println("Words in order: " + ((FillInTheBlanksAnswer) answer).getFillInTheBlanksAnswer());
                }
                System.out.println("-----------------------------");
            }
        }
    }// print by candidate


    public void printNumberOfCorrectAnswersPerEvaluator(QuestionManager questions) {
        Map<Integer, Integer> correctAnswersCount = new HashMap<>();
    
        for (Answer answer : answerList) {
            int evaluatorCode = answer.getEvaluatedCode();
            int questionCode = answer.getQuestionCode();
            boolean isCorrect = false;
    
            if (answer instanceof MultipleChoiceAnswer) {
                MultipleChoiceAnswer mcAnswer = (MultipleChoiceAnswer) answer;
                isCorrect = questions.isMultipleChoiceAnswerCorrect(questionCode, mcAnswer.getChosenOptions());
            } else if (answer instanceof WordAnswer) {
                WordAnswer waAnswer = (WordAnswer) answer;
                isCorrect = questions.isWordAnswerCorrect(questionCode, waAnswer.getWordAnswer());
            } else if (answer instanceof FillInTheBlanksAnswer) {
                FillInTheBlanksAnswer fibAnswer = (FillInTheBlanksAnswer) answer;
                isCorrect = questions.isFillInTheBlanksAnswerCorrect(questionCode, fibAnswer.getFillInTheBlanksAnswer());
            }
    
            if (isCorrect) {
                correctAnswersCount.put(evaluatorCode, correctAnswersCount.getOrDefault(evaluatorCode, 0) + 1);
            }
        }

        // if everything is wrong...
        if(correctAnswersCount.isEmpty()) {
            System.out.println("Sorry, no right answers." );
            return;
        }
        // Sort the map entries by value in descending order
        List<Map.Entry<Integer, Integer>> sortedEntries = correctAnswersCount.entrySet()
               .stream()
               .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
               .collect(Collectors.toList());
    
        // Print the sorted results
        for (Map.Entry<Integer, Integer> entry : sortedEntries) {
            System.out.println("Candidate: " + entry.getKey());
            System.out.println("Correct Answers: " + entry.getValue());
            System.out.println("-----------------------------");
        }
    }

    public boolean AnswerExists(int cancode, int qcode) {
        for (Answer obj : answerList) {
            if (obj.getEvaluatedCode() == cancode && obj.getQuestionCode()==qcode) {
                return true;
            }
        }
        return false;
    }
}