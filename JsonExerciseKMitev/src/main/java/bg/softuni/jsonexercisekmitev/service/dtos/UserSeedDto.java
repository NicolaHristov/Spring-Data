package bg.softuni.jsonexercisekmitev.service.dtos;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UserSeedDto {

    @Expose
    @NotNull
    private String firstName;
    @Expose
    @NotNull
    private String lastName;
    @Expose
    @Min(18)
    private int age;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
