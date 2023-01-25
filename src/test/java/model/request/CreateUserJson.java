package model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
//@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIgnoreProperties({"typeName"})
public class CreateUserJson {
    @JsonProperty("name")
    private String name;
//    @JsonProperty("job")
//    private String job;

    public CreateUserJson(String name, String job) {
        this.name = name;
//        this.job = job;
    }

    public CreateUserJson(String name) {
        this.name = name;
    }

    public CreateUserJson() {
        this.name = name;
//        this.job = job;
    }

}
