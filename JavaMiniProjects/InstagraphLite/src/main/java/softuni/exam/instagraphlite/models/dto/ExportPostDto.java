package softuni.exam.instagraphlite.models.dto;

public class ExportPostDto {

    private String caption;

    private double pictureSize;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }


    public double getPictureSize() {
        return pictureSize;
    }

    public void setPictureSize(double pictureSize) {
        this.pictureSize = pictureSize;
    }
}
