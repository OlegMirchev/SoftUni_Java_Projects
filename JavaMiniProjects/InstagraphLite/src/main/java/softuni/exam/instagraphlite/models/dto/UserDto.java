package softuni.exam.instagraphlite.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class UserDto {

    @Size(min = 2, max = 18)
    @NotNull
    private String username;

    public UserDto() {

    }

    public String getUsername() {
        return username;
    }
}
