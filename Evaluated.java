public class Evaluated {
    private int code;
    private String surname;
    private String name;

    // Constructor
    public Evaluated(int code, String surname, String name) {
        this.code = code;
        this.surname = surname;
        this.name = name;
    }

    // Getters and setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
