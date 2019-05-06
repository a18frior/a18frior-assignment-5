package org.brohede.marcus.sqliteapp;

/**
 * Created by marcus on 2018-04-25.
 */

public class Mountain {

        public static final String TABLE_NAME = "mountains";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_HEIGHT = "height";
        public static final String COLUMN_LOCATION= "location";
        public static final String COLUMN_IMG_URL= "img";
    public static final String COLUMN_IMG_INFO= "info";
       private String Name;
         private String Location;
        private int Height;
        private String Img_url;
        private String  Info_url;


        // Create table SQL query
        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "("
                        + COLUMN_NAME+ " TEXT PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_HEIGHT + " INTEGER"
                        + COLUMN_LOCATION + "TEXT "
                        + COLUMN_IMG_URL + " TEXT,"
                        + COLUMN_IMG_INFO + " TEXT"
                        + ")";

        public Mountain() {
        }

        public Mountain(String Name, String Location, int Height, String Img_url, String Info_url) {
            this.Name = Name;
            this.Location = Location;
            this.Height = Height;
            this.Img_url =Img_url;
            this.Info_url= Info_url;
        }

        public String getName() {
            return Name;
        }
    public String getHeight() {
        return Integer.toString(Height);
    }

        public String getLocation() {
            return Location;
        }

    public String getImg_url() {
        return Img_url;
    }

    public String getInfo_url(){
        return Info_url;
    }

        public void setName(String Name) {
            this.Name = Name;
        }
         public void setImg_url(String Img_url) {
        this.Img_url = Img_url;
    }
    public void setInfo_url_url(String Info_url) {
        this.Info_url = Info_url;}

        public String Location() {
            return Location;
        }
        public void setHeight(int Height) {
            this.Height = Height;
        }

        public void setLocation(String Locstion) {
            this.Location = Locstion;
        }
    public String info(){
        String str= Name;
        str+=" is located in ";
        str+=Location;
        str+= " and has an height of ";
        str+= Integer.toString(Height);
        str+="m. ";

        return str;
    }
    }