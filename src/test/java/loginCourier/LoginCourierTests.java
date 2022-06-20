package loginCourier;

import  static testData.TestData.*;
import createCourier.Courier;
import createCourier.CourierRequests;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class LoginCourierTests {
    CourierRequests courierRequests = new CourierRequests();
    Courier courier = new Courier();

    String login = LOGIN;
    String password = PASSWORD;
    String firstName = FIRSTNAME;

    @Before
    public void before() {
        RestAssured.baseURI = URL;
    }

    @After
    public void after() {
        //ОЧИЩЕНИЕ
        //получаем id курьера
        courierRequests.courierLogin(courier)
                .then().assertThat().statusCode(200);
        int id = courierRequests.courierLogin(courier).path("id");
        //удаление созданного курьера
        courierRequests.courierDelete(id)
                .then().assertThat().statusCode(200)
                .and().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Проверка успешного логина курьера") // имя теста
    public void loginCourierOk() {
        courier.setLogin(login);
        courier.setPassword(password);
        courier.setFirstName(firstName);
        //создание курьера
        Response response = create(courier);
        response.then().assertThat().statusCode(201)
                .and().body("ok", equalTo(true));

        Courier courierOK = new Courier();
        courierOK.setLogin(courier.getLogin());
        courierOK.setPassword(courier.getPassword());
        //логин проверка,
        response = login(courierOK);
        response.then().assertThat().statusCode(200)
                .and().body("id", notNullValue());
    }

    @Test
    @DisplayName("Проверка неуспешного логина курьера без логина или пароля") // имя теста
    public void loginCourierBadRequest() {
        courier.setLogin(login);
        courier.setPassword(password);
        courier.setFirstName(firstName);
        //создание курьера
        Response response = create(courier);
        response.then().assertThat().statusCode(201)
                .and().body("ok", equalTo(true));

        //логин проверка ,без пароля
        courier.setPassword("");
        response = login(courier);
        response.then().assertThat().statusCode(400)
                .and().body("message", equalTo("Недостаточно данных для входа"));

        //возвращаем пароль
        courier.setPassword(password);
        //логин проверка ,без логина
        courier.setLogin("");
        response = login(courier);
        response.then().assertThat().statusCode(400)
                .and().body("message", equalTo("Недостаточно данных для входа"));
        //возвращем логин
        courier.setLogin(login);
    }

    @Step("Создание курьера")
    public Response create(Courier courier) {
        return courierRequests.courierCreate(courier);
    }

    @Step("Логин курьера")
    public Response login(Courier courier) {
        return courierRequests.courierLogin(courier);

    }
}
