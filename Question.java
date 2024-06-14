import java.util.List;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

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
        return "Descripion: " +	this.description + "\n" + "Code: "+ this.code;
    }
    abstract public boolean parse(BufferedReader br);    

    // Abstract method to be overridden by subclasses
    //public abstract QuestionType getQuestionType();    
}

class MultipleChoiceQuestion extends Question {
    private String[] choices;
    private List<Integer> correctAnswers;

    public MultipleChoiceQuestion(int code, String description) {
        super(code, description);
    }
    public MultipleChoiceQuestion() {
        super(0,"");
    }

    public void setChoices(String[] choices){
        this.choices = choices;
    }
    
    public void setCorrectAnswers(List<Integer> correctAnswers){
        this.correctAnswers = correctAnswers;
    }

    public String[] getAnswers() {
        return this.choices;
    }
    public List<Integer> getCorrectAnswers() {
        return this.correctAnswers;
    }

    public String toString(){
        return "Question Details : \n"
                + super.toString()
                +  "\nchoices: " + Arrays.toString(this.choices) 
                + "\ncorrect answers: " + this.correctAnswers.stream()
                                  .map(String::valueOf) // Convert integers to strings
                                  .collect(Collectors.joining(", ", " ", "") ) // Join them into a single string
               + "\n**************************";
    }

    public boolean parse(BufferedReader buff){
        try {
            buff.reset();
            StringTokenizer lineTokens;
            String token;
            String line;    
            line = buff.readLine();             
            while ( !(line.trim().equals("}")) ) {
                lineTokens = new StringTokenizer(line);
                token = lineTokens.nextToken();  
                if (token.equals("CODE")) {
                   token = lineTokens.nextToken(); 
                   code = Integer.parseInt(token);
                }
                else if (token.equals("DECSR")) {
                    StringBuilder descriptionBuilder = new StringBuilder();
                    // Move to the next token which should be the start of the description
                    token = lineTokens.nextToken(); // Consume the next token which is the actual start of the description
                    descriptionBuilder.append(token); // Now correctly starts with the description content

                    // Continue accumulating the description until the end of the current question block
                    while (lineTokens.hasMoreTokens() &&!token.equals("}")) {
                        descriptionBuilder.append(" ").append(lineTokens.nextToken()); // Append a space for readability, remove if not desired
                    }
                    description = descriptionBuilder.toString().trim(); // Convert StringBuilder to String and trim excess spaces
                }
                else if (token.equals("ANSWERS")) {
                   token = lineTokens.nextToken(); 
                   choices = token.split("\\+");
                }  
                else if (token.equals("CORRECT_ANSWERS")) {
                    token = lineTokens.nextToken(); 
                    String cleanToken = token.replace("\"", "").trim(); // Remove quotes and trim spaces
                    List<Integer> tokenList = Arrays.stream(cleanToken.split("\\+"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());            
                    correctAnswers = tokenList;
                 }              
                
                line = buff.readLine();
                //System.out.println(line);                                
            }   
        } catch (IOException ex) {
            System.out.println("Error reading file");
        }
        
        return true;
    };    
}

class WordAnswerQuestion extends Question {
    String correctAnswer;

    public WordAnswerQuestion(int code, String description, String correctAnswer) {
        super(code, description);
        this.correctAnswer = correctAnswer;
    }
    public WordAnswerQuestion() {
        super(0, "");
        this.correctAnswer = "";
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public boolean parse(BufferedReader buff){
        try {
            buff.reset();
            StringTokenizer lineTokens;
            String token;
            String line;    
            line = buff.readLine();             
            while ( !(line.trim().equals("}")) ) {
                lineTokens = new StringTokenizer(line);
                token = lineTokens.nextToken();  
                if (token.equals("CODE")) {
                   token = lineTokens.nextToken(); 
                   code = Integer.parseInt(token);
                }
                else if (token.equals("DECSR")) {
                    StringBuilder descriptionBuilder = new StringBuilder();
                    // Move to the next token which should be the start of the description
                    token = lineTokens.nextToken(); // Consume the next token which is the actual start of the description
                    descriptionBuilder.append(token); // Now correctly starts with the description content
                
                    // Continue accumulating the description until the end of the current question block
                    while (lineTokens.hasMoreTokens() &&!token.equals("}")) {
                        descriptionBuilder.append(" ").append(lineTokens.nextToken()); // Append a space for readability, remove if not desired
                    }
                    description = descriptionBuilder.toString().trim(); // Convert StringBuilder to String and trim excess spaces
                }
                else if (token.equals("WORD")) {
                   token = lineTokens.nextToken(); 
                   correctAnswer = token;
                }             
                
                line = buff.readLine();
                //System.out.println(line);                                
            }   
        } catch (IOException ex) {
            System.out.println("Error reading file");
        }
        
        return true;
    }; 
    public String toString(){
        return "Question Details :\n" 
                + super.toString()
                +  "\ncorrectAnswer: " + this.correctAnswer
                + "\n********************************";
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
    public FillInTheBlanksQuestion() {
        super(0, ""); // Call the constructor of the superclass (Question)
    }
    public String[] getCorrectOrder() {
        return this.correctOrder;
    }
    public String[] getWords() {
        return this.words;
    }

    public boolean parse(BufferedReader buff){
        try {
            buff.reset();
            StringTokenizer lineTokens;
            String token;
            String line;    
            line = buff.readLine();             
            while ( !(line.trim().equals("}")) ) {
                lineTokens = new StringTokenizer(line);
                token = lineTokens.nextToken();  
                if (token.equals("CODE")) {
                   token = lineTokens.nextToken(); 
                   code = Integer.parseInt(token);
                }
                else if (token.equals("DECSR")) {
                    StringBuilder descriptionBuilder = new StringBuilder();
                    // Move to the next token which should be the start of the description
                    token = lineTokens.nextToken(); // Consume the next token which is the actual start of the description
                    descriptionBuilder.append(token); // Now correctly starts with the description content
                
                    // Continue accumulating the description until the end of the current question block
                    while (lineTokens.hasMoreTokens() &&!token.equals("}")) {
                        descriptionBuilder.append(" ").append(lineTokens.nextToken()); // Append a space for readability, remove if not desired
                    }
                    description = descriptionBuilder.toString().trim(); // Convert StringBuilder to String and trim excess spaces
                }
                else if (token.equals("ANSWERS")) {
                   token = lineTokens.nextToken(); 
                   words = token.split("\\+");
                }  
                else if (token.equals("CORRECT_ANSWER")) {
                    token = lineTokens.nextToken(); 
                    correctOrder = token.split("\\+");
                 }              
                
                line = buff.readLine();
                //System.out.println(line);                                
            }   
        } catch (IOException ex) {
            System.out.println("Error reading file");
        }
        
        return true;
    }; 
    public String toString(){
        return "Question Details : \n"
                + super.toString()
                +  "\nwords: " + Arrays.toString(this.words)
                + "\ncorrect order: "+  Arrays.toString(this.correctOrder)
                + "\n********************************";
    }     

}