package softuni.exam.instagraphlite.models.dto;

import java.util.List;

public class ExportUsersDto {
    private String username;
    private long countOfPosts;
    private List<ExportPostDto> posts;

    public ExportUsersDto() {

    }

    public ExportUsersDto(String username, long countOfPosts, List<ExportPostDto> posts) {
        this.username = username;
        this.countOfPosts = countOfPosts;
        this.posts = posts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getCountOfPosts() {
        return countOfPosts;
    }

    public void setCountOfPosts(long countOfPosts) {
        this.countOfPosts = countOfPosts;
    }

    public List<ExportPostDto> getPosts() {
        return posts;
    }

    public void setPosts(List<ExportPostDto> posts) {
        this.posts = posts;
    }
}
