package edu.rupp.firstite;

public class Item {
    private final String name;
    private final String image;

    public Item(String image, String name){
        this.image = image;
        this.name = name;
    }

    public String getImage(){
        return image;
    }
    public String getName(){
        return name;
    }
}
