import java.util.*;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileReader;


public class Main {

    //λίστα πόρων που θα ενημερωθεί
    static QuestionManager questions=new QuestionManager();
    static EvaluatedManager participants=new EvaluatedManager();
    static AnswerManager answers=new AnswerManager();

    
    public static void parseQuestionList(String aFileName) {
       try {
           FileReader fw = new FileReader(aFileName);           
           BufferedReader buff=new BufferedReader(fw);
           //System.out.println("File opened");
           StringTokenizer lineTokens;
           String token;
           String line;
           Question q = null;
           boolean eof = false;
           boolean ok;
           while (!eof) {
               line = buff.readLine();               
               if (line == null)
                   eof = true;
               else {
                  //System.out.println(line);
                  if (line.trim().equals("QUESTION")) {
                     line = buff.readLine();  
                     if (line.trim().equals("{")) {
                       buff.mark(2048);
                       while ( !(line.trim().equals("}")) && (!eof) ) {
                          line = buff.readLine();
                          //System.out.println(line);
                          lineTokens = new StringTokenizer(line);
                          token = lineTokens.nextToken();  
                          if (token.toUpperCase().equals("TYPE")) {
                              token = lineTokens.nextToken(); 
                              if (token.equals("MC")) 
                                 q = new MultipleChoiceQuestion();
                              else if (token.equals("WORD")) 
                                 q = new WordAnswerQuestion();
                              else if (token.equals("FILL")) 
                                 q = new FillInTheBlanksQuestion();          
                              //System.out.println("New resource");                              
                              ok= q.parse(buff);
                              if (ok) 
                                    questions.addQuestion(q);                            
                              break;                               
                          }
                       }    
                     }
                  }
                  //System.out.println(token);
               }    
           }           
           
           buff.close();
       }
       catch (IOException ex) {
            System.err.println("Error reading file");
       }       
    }
  

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        QuestionManager questions=new QuestionManager();
        EvaluatedManager participants=new EvaluatedManager();
        AnswerManager answers=new AnswerManager();

        // add candidates
        participants.addEvaluated(new Evaluated(1, "papamanoli", "ioanna"));
        participants.addEvaluated(new Evaluated(2, "papa", "giorgos"));
        participants.addEvaluated(new Evaluated(3, "savoulidi", "despoina"));
        participants.addEvaluated(new Evaluated(4, "palaiologou", "margarita"));
        /*
        // add multiple choice
        String[] choices = {"1) x=1, y=8","2) x=1, y=9", "3) x=0, y=3","4) x=0, y=0]"};
        ArrayList<Integer> correct = new  ArrayList<Integer>();
        correct.add(1);
        correct.add(2);
        MultipleChoiceQuestion curQuestion =new MultipleChoiceQuestion(1001, "For what x and y is y=5x+3;");
        curQuestion.setChoices(choices);
        curQuestion.setCorrectAnswers(correct);
        questions.addMultipleChoiceQuestion(curQuestion);

        String[] choices2 = {"1) 105","2) 90+11", "3) 110","4)50+51"};
        ArrayList<Integer> correct2 = new  ArrayList<Integer>();
        correct2.add(1);
        correct2.add(2);
        correct2.add(4);
        curQuestion =new MultipleChoiceQuestion(1002, "Which is bigger than 100;");
        curQuestion.setChoices(choices2);
        curQuestion.setCorrectAnswers(correct2);
        questions.addMultipleChoiceQuestion(curQuestion);

        String[] choices3 = {"1) 1","2) 2", "3) 3","4)15"};
        ArrayList<Integer> correct3 = new  ArrayList<Integer>();
        correct3.add(4);
        curQuestion =new MultipleChoiceQuestion(1003, "Which is bigger than 10;");
        curQuestion.setChoices(choices3);
        curQuestion.setCorrectAnswers(correct3);
        questions.addMultipleChoiceQuestion(curQuestion);

        //add word answer
        questions.addWordAnswerQuestion(new WordAnswerQuestion(2001, "How must a class be declared to not have subclasses", "final"));
        questions.addWordAnswerQuestion(new WordAnswerQuestion(2002, "How much is 37+37;", "54"));
        questions.addWordAnswerQuestion(new WordAnswerQuestion(2003, "How much is 10+20;", "30"));

        // add fill blanks
        String[] words1 = {"apogonos","progonos"};
        String[] orderwords1 = {"progonos","apogonos"};
        questions.addFillInTheBlanksQuestion(new FillInTheBlanksQuestion(3001, "If a class X is ? of Y then Y is ? of X", words1, orderwords1));

        String[] words2 = {"10","20","10"};
        String[] orderwords2 = {"10","10","20"};
        questions.addFillInTheBlanksQuestion(new FillInTheBlanksQuestion(3001, "The sum of ? and ? is ?.", words2, orderwords2));

        String[] words3 = {"1000","13","134"};
        String[] orderwords3 = {"13","134","1000"};
        questions.addFillInTheBlanksQuestion(new FillInTheBlanksQuestion(3001, "Place the words in ascending order.", words3, orderwords3));

        */

        System.out.println("Welcome to...");
        System.out.println("\r\n" + //
                        " ________         ____              \r\n" + //
                        "/_  __/ /  ___   / __/_ _____ ___ _ \r\n" + //
                        " / / / _ \\/ -_) / _/ \\ \\ / _ `/  ' \\\r\n" + //
                        "/_/ /_//_/\\__/ /___//_\\_\\\\_,_/_/_/_/\r\n" + //
                        "                                    \r\n" + //
                        ""); 

        do {
            System.out.println("Choose an action 0 through 8: ");
            System.out.println("1. Add new candidate");
            System.out.println("2. Add new question");
            System.out.println("3. Add new answer");
            System.out.println("4. Show all questions");
            System.out.println("5. Show a candidate's answers");
            System.out.println("6. Show the number of correct answers by candidate");
            System.out.println("7. Show the percentage of correct answers for each question");
            System.out.println("8. Show the percentage of correct answers by candidate");
            System.out.println("0. Exit");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e){
                scanner.nextLine(); // clear line
                choice=99; // invalid number to cause repond
            }


            switch (choice) {
                case 1:
                    // Add new candidate
                    System.out.println("Add new candidate");
                    while(true) { // Start of the loop
                        System.out.print("Enter code: ");
                        if (scanner.hasNextInt()) { // Check if the next token can be parsed as an integer
                            int code = scanner.nextInt();
                            scanner.nextLine(); // Consume the newline left-over by nextInt()
                
                            if (participants.CandidateExists(code)) {
                                System.out.println("Error: Candidate code already exists.");
                                continue; // Skip the rest of the loop and prompt for code again
                            }

                            System.out.print("Enter lastname: ");
                            String surname = scanner.next();
                            System.out.print("Enter firstname: ");
                            String name = scanner.next();
                
                            participants.addEvaluated(new Evaluated(code, surname, name));
                            System.out.println("Candidate added successfully!");
                
                            break; // Break out of the loop once a valid input is received
                        } else {
                            System.out.println("Invalid input. Please enter an integer.");
                            scanner.nextLine(); // Clear the invalid input
                        }
                    }
                    break; // End of case 1
                case 2:
                    addNewQuestion(scanner, questions);
                    break;
                case 3:
                    enterAnswer(scanner, questions, answers);
                    break;
                case 4:
                    // Print all questions
                    System.out.println("Print all questions");
                    questions.displayAllQuestions();
                    break;
                case 5:
                    //print Answers For Candidate
                    System.out.println("Displaying all candidates:");
                    participants.displayAllEvaluations();
                    System.out.print("Type a candidate's code to display their answers: ");
                    int evaluatorCode = scanner.nextInt();
                    answers.printAnswersByEvaluator(evaluatorCode);
                    break;
                case 6:
                    // number of correct anwswers by each candidate
                    answers.printNumberOfCorrectAnswersPerEvaluator(questions);
                    break;
                case 7:
                    //  correct percentage for each question
                    questions.printPercentageOfCorrectAnswersForEachQuestion(answers);
                    break;
                case 8:
                    // correct percentage for each candidate
                    questions.printPercentageOfCorrectAnswersPerCandidate(answers);
                    break;
                case 0:
                    System.out.println("Bye!");
                    break;
                default:
                    System.out.println("Invalid input, please try again.");
            }
        } while (choice != 0);
        scanner.close();
    }


    private static void addNewQuestion(Scanner scanner, QuestionManager questions) {
        // Add new question

        System.out.println("Add new question");
        // Loop to ensure a valid question code is entered
        boolean validQ = false;
        int code = 0;
        do {
            System.out.print("Enter New Question Number: ");
            String input = scanner.nextLine(); // Read the input as a string
            try {
                code = Integer.parseInt(input); // Attempt to parse the input as an integer
                if (questions.QuestionExists(code))
                    throw new ArithmeticException("Question Number already exists. Please try again.");
                validQ = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
                continue;  //Error - Go from the beginning of while
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
            }
        }
        while (!validQ);


        // Proceed to enter description
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        // Loop for entering question type
        while (true) {
            System.out.print("Enter question type (1 for multiple choice, 2 for word answer, 3 for fill in the blanks): ");
            String questionTypeInput = scanner.nextLine(); // Read the input as a string

            int questionType = Integer.parseInt(questionTypeInput); // Attempt to parse the input as an integer
            switch (questionType) {
                case 1:
                    System.out.print("Enter options, separated by commas: ");
                    String[] choices = scanner.next().split(",");
                    System.out.print("Enter correct options, separated by commas: ");
                    List<Integer> correctAnswers = Arrays.stream(scanner.next().split(","))
                            .map(Integer::valueOf)
                            .collect(Collectors.toList());
                    //List<Integer> correctAnswers = scanner.nextInt().split(",");
                    MultipleChoiceQuestion curQuestion=new MultipleChoiceQuestion(code, description);
                    curQuestion.setChoices(choices);
                    curQuestion.setCorrectAnswers(correctAnswers);
                    questions.addMultipleChoiceQuestion(curQuestion);
                    break;
                case 2:
                    System.out.print("Enter correct answer: ");
                    String correctAnswer = scanner.next();
                    questions.addWordAnswerQuestion(new WordAnswerQuestion(code, description, correctAnswer));
                    // System.out.println("Word answer question added successfully.");
                    break;
                case 3:
                    System.out.print("Enter options, separated by commas: ");
                    String[] words = scanner.next().split(",");
                    System.out.print("Enter correct order, separated by commas: ");
                    String[] correctOrder = scanner.next().split(",");
                    questions.addFillInTheBlanksQuestion(new FillInTheBlanksQuestion(code, description, words, correctOrder));
                    //System.out.println("Fill-in-the-blanks question added successfully.");
                    break;
                default:
                    System.out.println("Invalid question type.");
            }// close switch
            // If the question type is valid, break out of the inner loop
            break;
        } //while
    } // addNewQuestion()

    private static void enterAnswer(Scanner scanner, QuestionManager questions, AnswerManager answers ){

        // Handle adding a new answer
        System.out.print("Enter evaluated code: ");
        while(!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer.");
            scanner.next(); // Consume the invalid input
        }
        int evaluatedCode = scanner.nextInt();
        scanner.nextLine(); // Consume the newline left-over by nextInt()

        System.out.print("Enter question code: ");
        while(!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer.");
            scanner.next(); // Consume the invalid input
        }
        int questionCode = scanner.nextInt();
        scanner.nextLine(); // Consume the newline left-over by nextInt()
        Question question=questions.getQuestionById(questionCode);
        System.out.println(question);

        if (answers.AnswerExists(evaluatedCode,questionCode)) {
            System.out.println("Error: This candidate has already answered this question.");
//            continue; // Skip the rest of the loop and prompt for code again
        }

        // Determine the question type and handle the answer accordingly
//        Question question = QuestionManager.getQuestionById(questionCode);
        if (question!= null) {
            if (question instanceof MultipleChoiceQuestion) {
                System.out.println("Enter your answer (comma-separated integers): ");
                String input = scanner.nextLine();
                List<Integer> chosenOptions = Arrays.stream(input.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
    //            System.out.println(chosenOptions);
                answers.addAnswer(new MultipleChoiceAnswer(evaluatedCode, question, chosenOptions));
                //break;
            } else if (question instanceof WordAnswerQuestion) {

                System.out.println("Enter your answer: ");
                String wordAnswer = scanner.nextLine();
                answers.addAnswer(new WordAnswer(evaluatedCode, question, wordAnswer));
                //break;
            } else if (question instanceof FillInTheBlanksQuestion) {
                System.out.println("Enter your answer (comma-separated strings): ");
                String fillInTheBlanksInput = scanner.nextLine();
                List<String> fillInTheBlanksAnswer = Arrays.asList(fillInTheBlanksInput.split(","));
                answers.addAnswer(new FillInTheBlanksAnswer(evaluatedCode, question, fillInTheBlanksAnswer));
                //break;
            } else {
                System.out.println("Invalid question type.");
            }
        } else {
            System.out.println("Question not found.");
        }
    }
} //class
