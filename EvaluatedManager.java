import java.util.ArrayList;
import java.util.List;

public class EvaluatedManager {
    private List<Evaluated> evaluatedList = new ArrayList<>();

    public void addEvaluated(Evaluated evaluated) {
        evaluatedList.add(evaluated);
    }

    public boolean CandidateExists(int code) {
        for (Evaluated candidate : evaluatedList) {
            if (candidate.getCode() == code) {
                return true;
            }
        }
        return false;
    }


    
    public void displayAllEvaluations() {
        if (!evaluatedList.isEmpty()) {
            for (Evaluated evaluated : evaluatedList) {
                System.out.println("Code: " + evaluated.getCode());
                System.out.println("Surname: " + evaluated.getSurname());
                System.out.println("Name: " + evaluated.getName());
                System.out.println("-----------------------------");
            }
        } else {
            System.out.println("No evaluations found.");
        }
    }

}