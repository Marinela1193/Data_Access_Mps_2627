package TEMA1.Ejercicio3;

import java.util.ArrayList;
import java.util.List;

public class Room {
    
    private String name;
    private List<Artwork> artworks = new ArrayList<>();
    private static final int MAX_ARTWORKS = 10;

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addArtwork(Artwork artwork) {
        if(artworks.size() >= MAX_ARTWORKS) {
            System.out.println("The room is full, you cannot add more artworks in there");
        } else {
            artworks.add(artwork);
        }
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }
}
