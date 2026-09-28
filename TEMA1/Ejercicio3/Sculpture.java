package TEMA1.Ejercicio3;

public class Sculpture extends Artwork {
    
    private Materials material;
    private Styles place;

    public Sculpture(String title, Author author, Materials material, Styles place) {
        super(title, author);
        this.material = material;
        this.place = place;
    }

    public Materials getMaterial() {
        return material;
    }

    public void setMaterial(Materials material) {
        this.material = material;
    }

    public Styles getPlace() {
        return place;
    }

    public void setPlace(Styles place) {
        this.place = place;
    }
    
}
