package softuni.exam.instagraphlite.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PostDto {

    @XmlElement
    @Size(min = 21)
    @NotNull
    private String caption;

    @XmlElement(name = "user")
    private UserDto user;

    @XmlElement(name = "picture")
    private PictureDto picture;

    public PostDto() {

    }

    public String getCaption() {
        return caption;
    }

    public UserDto getUser() {
        return user;
    }

    public PictureDto getPicture() {
        return picture;
    }
}
