package org.brohede.marcus.sqliteapp;

/**
 * Created by marcus on 2018-04-25.
 */

public class Mountain {

    public static final String COLUMN_NAME_ID = "ID";
    public static final String COLUMN_NAME_Name = "Name";
    public static final String COLUMN_NAME_Location = "Location";
    public static final String COLUMN_NAME_Height = "Height";
    public static final String COLUMN_NAME_Img_url = "Img_url";
    public static final String COLUMN_NAME_InfoUrl = "InfoUrl";

    private String Name;
    private String Location;
    private String Height;
    private String Img_url;
    private String InfoUrl;

    public Mountain(String inName, String inLocation, String inHeight, String inImg_url,String inInfoUrl) {
        Name = inName;
        Location = inLocation;
        Height = inHeight;
        Img_url = inImg_url;
        InfoUrl= inInfoUrl;

    }

    public Mountain(String inName) {
        Name = inName;
        Location = "";

    }

    @Override
    public String toString() {
        return Name;
    }

    public String info() {
        String str = Name;
        str += " is located in ";
        str += Location;
        str += " and has an height of ";
        str += Height;
        str += "m. ";

        return str;
    }

    public String bild() {
        String bild = Img_url;
        return bild;
    }

    public void setName(String newName) {
        Name = newName;
    }

    public void setLocation(String newLocation) {
        Location = newLocation;
    }

    public void setImg_url(String newImg_url) {
        Img_url = newImg_url;
    }
    public void setInfoUrl(String newInfoUrl) {
        InfoUrl = newInfoUrl;

    }
    public void setHeight(String newHeight) {
        Height = newHeight;
    }

    public String getName() {
        String getname = Name;
        return getname;
    }

    public String getLocation() {
        String getloc = Location;
        return getloc;
    }

    public String getHeight() {
        String getheight = Height + "m";
        return getheight;
    }

    public String getImg_url() {
        String getImg_url = Img_url;
        return getImg_url;


    }
    public String getInfoUrl() {
        String getInfoUrl = InfoUrl;
        return getInfoUrl;


    }
}
