package testData;

import java.util.Random;

public class TestData {
    public static final  String URL = "http://qa-scooter.praktikum-services.ru";

    public static String LOGIN = "Login" + new Random().nextInt(999) ;
    public static String  PASSWORD = "Password" + new Random().nextInt(999);
    public static String  FIRSTNAME = "FirstName" + new Random().nextInt(999);

    public static final String LASTNAME = "lastName";
    public static final String ADDRESS = "address";
    public static final String METRO_STATION = "metroStation";
    public static final String PHONE = "+7 800 355 35 35";
    public static final Number RENT_TIME = 4;
    public static final String DELIVERY_DATE = "2020-06-06";
    public static final String COMMENT = "comment";

    public static final String[] COLOR_BLACK = new String[]{"BLACK"};
    public static final String[] COLOR_ALL = new String[]{"BLACK","GRAY"};
    public static final String[] COLOR_NULL = new String[]{""};


}
