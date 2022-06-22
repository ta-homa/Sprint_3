package createCourier;

import static testData.TestData.*;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

public class CreateCourierTests {
    CourierRequests courierRequests = new CourierRequests();
    Courier courier = new Courier();

    @Before
    public void before() {
        RestAssured.baseURI = URL;
    }

    @Test
    @DisplayName("Проверка успешного создания курьера") // имя теста
    public void createNewCourier() {
        courier.setLogin(LOGIN);
        courier.setPassword(PASSWORD);
        courier.setFirstName(FIRSTNAME);
        //проверка

        Response response =  create(courier);
        response.then().assertThat().statusCode(CREATED_COURIER_STATUS)
                .and().body("ok",equalTo(true));

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
    @DisplayName("Проверка создания курьера без логина и пароля")
    public void createCourierBadRequest() {
        courier.setLogin("");
        courier.setPassword(PASSWORD);
        courier.setFirstName(FIRSTNAME);

        Response response =  create(courier);
        response.then().assertThat().statusCode(LOGIN_BR_COURIER_STATUS)
                .and().body("message",equalTo(CREATE_BAD_REQUEST_MESSAGE));

        courier.setLogin(LOGIN);
        courier.setPassword("");

        response =  create(courier);
        response.then().assertThat().statusCode(LOGIN_BR_COURIER_STATUS)
                .and().body("message",equalTo(CREATE_BAD_REQUEST_MESSAGE));
    }

    @Test
    @DisplayName("Проверка невозможности создания двух одинаковых курьеров") // имя теста
    public void createTwoIdenticalCourier() {
        courier.setLogin(LOGIN);
        courier.setPassword(PASSWORD);
        courier.setFirstName(FIRSTNAME);
        //первое успешное создание

        Response response =  create(courier);
        response.then().assertThat().statusCode(CREATED_COURIER_STATUS)
                .and().body("ok",equalTo(true));
        //второе неуспешное создание
        response =  create(courier);
        response.then().assertThat().statusCode(LOGIN_CONFLICT_COURIER_STATUS)
                .and().body("message",equalTo(CREATE_СONFLICT_MESSAGE));

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


    @Step("Создание курьера")
    public Response create(Courier courier){
        return   courierRequests.courierCreate(courier);
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