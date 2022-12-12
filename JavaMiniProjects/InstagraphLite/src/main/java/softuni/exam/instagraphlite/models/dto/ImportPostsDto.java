package softuni.exam.instagraphlite.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "posts")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportPostsDto {

    @XmlElement(name = "post")
    private List<PostDto> posts;

    public ImportPostsDto() {

    }

    public List<PostDto> getPosts() {
        return posts;
    }
}
