import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserJson {
    @JsonProperty("name")
    private String name;
    @JsonProperty("job")
    private String job;

    public CreateUserJson(String name, String job) {
        this.name = name;
        this.job = job;
    }
}
