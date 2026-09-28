package TEMA1.Ejercicio3;

public class Painting extends Artwork {

    private PaintingType paintingType;
    private String format;

    public Painting(String title, Author author, PaintingType paintingType, String format) {
        super(title, author);
        this.paintingType = paintingType;
        this.format = format;
    }

    public PaintingType getPaintingType() {
        return paintingType;
    }

    public void setPaintingType(PaintingType paintingType) {
        this.paintingType = paintingType;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
    
}
