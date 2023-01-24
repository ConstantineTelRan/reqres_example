
public class CreateUser {
    private String name;
    private String job;

    public void setName(String name) {
        this.name = name;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public CreateUser(String name, String job) {
        this.name = name;
        this.job = job;
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\": \"" + name + "\",\n" +
                "\"job\": \"" + job + "\"" +
                "}";
    }



}
