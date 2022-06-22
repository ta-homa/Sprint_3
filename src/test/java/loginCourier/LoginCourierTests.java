package loginCourier;

import  static testData.TestData.*;
import createCourier.Courier;
import createCourier.CourierRequests;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class LoginCourierTests {
    CourierRequests courierRequests = new CourierRequests();
    Courier courier = new Courier();
    @Before
    public void before() {
        RestAssured.baseURI = URL;
    }

    @Test
    @DisplayName("Проверка успешного логина курьера") // имя теста
    public void loginCourierOk() {
        courier.setLogin(LOGIN);
        courier.setPassword(PASSWORD);
        courier.setFirstName(FIRSTNAME);
        //создание курьера
        Response response = create(courier);
        response.then().assertThat().statusCode(CREATED_COURIER_STATUS)
                .and().body("ok", equalTo(true));

        Courier courierOK = new Courier();
        courierOK.setLogin(courier.getLogin());
        courierOK.setPassword(courier.getPassword());
        //логин проверка,
        response = login(courierOK);
        response.then().assertThat().statusCode(LOGINOK_COURIER_STATUS)
                .and().body("id", notNullValue());

        //ОЧИЩЕНИЕ
        //получаем id курьера
        response = login(courier);
        response.then().assertThat().statusCode(LOGINOK_COURIER_STATUS);
        int id = courierRequests.courierLogin(courier).path("id");
        //удаление созданного курьера
        response = delete(id);
        response.then().assertThat().statusCode(DELETEOK_COURIER_STATUS)
                .and().body("ok",equalTo(true));
    }

    @Test
    @DisplayName("Проверка неуспешного логина курьера без логина или пароля") // имя теста
    public void loginCourierBadRequest() {
        courier.setLogin(LOGIN);
        courier.setPassword(PASSWORD);
        courier.setFirstName(FIRSTNAME);
        //создание курьера
        Response response = create(courier);
        response.then().assertThat().statusCode(CREATED_COURIER_STATUS)
                .and().body("ok", equalTo(true));

        //логин проверка ,без пароля
        courier.setPassword("");
        response = login(courier);
        response.then().assertThat().statusCode(LOGIN_BR_COURIER_STATUS)
                .and().body("message", equalTo(LOGIN_BAD_REQUEST_MESSAGE));

        //возвращаем пароль
        courier.setPassword(PASSWORD);
        //логин проверка ,без логина
        courier.setLogin("");
        response = login(courier);
        response.then().assertThat().statusCode(LOGIN_BR_COURIER_STATUS)
                .and().body("message", equalTo(LOGIN_BAD_REQUEST_MESSAGE));
        //возвращем логин
        courier.setLogin(LOGIN);

        //ОЧИЩЕНИЕ
        //получаем id курьера
        response = login(courier);
        response.then().assertThat().statusCode(LOGINOK_COURIER_STATUS);
        int id = courierRequests.courierLogin(courier).path("id");
        //удаление созданного курьера
        response = delete(id);
        response.then().assertThat().statusCode(DELETEOK_COURIER_STATUS)
                .and().body("ok",equalTo(true));
    }

    @Test
    @DisplayName("Проверка неуспешного логина курьера с неверным логином или паролем") // имя теста
    public void loginCourierNotFound() {
        courier.setLogin(LOGIN);
        courier.setPassword(PASSWORD);

        //логин проверка ,без логина
        Response response = login(courier);
        response.then().assertThat().statusCode(LOGIN_NF_COURIER_STATUS)
                .and().body("message", equalTo(LOGIN_NOT_FOUND_MESSAGE));
    }


    @Step("Создание курьера")
    public Response create(Courier courier) {
        return courierRequests.courierCreate(courier);
    }

    @Step("Логин курьера")
    public Response login(Courier courier) {
        return courierRequests.courierLogin(courier);
    }

    @Step("Удаление курьера")
    public Response delete(int id) {
        return courierRequests.courierDelete(id);
    }
}
