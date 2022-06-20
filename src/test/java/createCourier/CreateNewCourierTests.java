package createCourier;

import static testData.TestData.*;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

public class CreateNewCourierTests {
    CourierRequests courierRequests = new CourierRequests();
    Courier courier = new Courier();

    String login = LOGIN;
    String  password = PASSWORD;
    String  firstName = FIRSTNAME;

    @Before
    public void before() {
        RestAssured.baseURI = URL;
    }

    @After
    public  void after(){
        //ОЧИЩЕНИЕ
        //получаем id курьера
        courierRequests.courierLogin(courier)
                .then().assertThat().statusCode(200);
        int id = courierRequests.courierLogin(courier).path("id");
        //удаление созданного курьера
        courierRequests.courierDelete(id)
                .then().assertThat().statusCode(200)
                .and().body("ok",equalTo(true));
    }

    @Test
    @DisplayName("Проверка успешного создания курьера") // имя теста
    public void createNewCourier() {
        courier.setLogin(login);
        courier.setPassword(password);
        courier.setFirstName(firstName);
        //проверка

        Response response =  create(courier);
        response.then().assertThat().statusCode(201)
                .and().body("ok",equalTo(true));
    }

    @Test
    @DisplayName("Проверка невозможности создания двух одинаковых курьеров") // имя теста
    public void createTwoIdenticalCourier() {
          courier.setLogin(login);
          courier.setPassword(password);
          courier.setFirstName(firstName);
          //первое успешное создание

        Response response =  create(courier);
        response.then().assertThat().statusCode(201)
                .and().body("ok",equalTo(true));
        //второе неуспешное создание
        response =  create(courier);
        response.then().assertThat().statusCode(409)
                .and().body("message",equalTo("Этот логин уже используется"));
    }

    @Step("Создание курьера")
    public Response create(Courier courier){
        return   courierRequests.courierCreate(courier);
    }


}