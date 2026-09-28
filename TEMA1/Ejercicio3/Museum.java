package TEMA1.Ejercicio3;

import java.util.ArrayList;
import java.util.List;

public class Museum {
    
    private String name;
    private String address;
    private String city;
    private String country;
    private List<Room> rooms = new ArrayList<>();

    public Museum(String name, String address, String city, String country) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void showRooms() {
       
        for (Room room : rooms) {
            if(room.getArtworks().isEmpty()) {
                    System.out.println("The room does not have any artworks.");
                } 
            else {
                for (Artwork artwork : room.getArtworks()) {
                    if(artwork instanceof Painting) {
                        System.out.println("Room: " + room.getName() + ", Painting: " + artwork.getTitle() + ", Artist: " + artwork.getAuthor().getName() + ", Type of painting: " + ((Painting) artwork).getPaintingType());
                    } else if(artwork instanceof Sculpture) {
                        System.out.println("Room: " + room.getName() + ", Sculpture: " + artwork.getTitle() + ", Artist: " + artwork.getAuthor().getName() + ", Material: " + ((Sculpture) artwork).getMaterial());   
                    } 
                }       
            }
        }
    }
}
