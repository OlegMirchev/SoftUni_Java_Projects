package softuni.exam.instagraphlite.models.dto;

public class ExportPicturesWithHigherSizeDto {

    private double size;

    private String path;

    public ExportPicturesWithHigherSizeDto() {

    }

    public ExportPicturesWithHigherSizeDto(double size, String path) {
        this.size = size;
        this.path = path;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
