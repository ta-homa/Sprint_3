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

    public  static final String CREATE_BAD_REQUEST_MESSAGE = "Недостаточно данных для создания учетной записи";
    public  static final String CREATE_СONFLICT_MESSAGE = "Этот логин уже используется. Попробуйте другой.";
    public  static final String LOGIN_BAD_REQUEST_MESSAGE = "Недостаточно данных для входа";
    public  static final String LOGIN_NOT_FOUND_MESSAGE = "Учетная запись не найдена";

    public  static  final int CREATED_COURIER_STATUS = 201;
    public  static  final int CREATED_ORDER_STATUS = 201;
    public  static  final int LOGINOK_COURIER_STATUS = 200;
    public  static  final int DELETEOK_COURIER_STATUS = 200;
    public  static  final int LOGIN_BR_COURIER_STATUS = 400;
    public  static  final int LOGIN_CONFLICT_COURIER_STATUS = 409;
    public  static  final int LOGIN_NF_COURIER_STATUS = 404;

}
