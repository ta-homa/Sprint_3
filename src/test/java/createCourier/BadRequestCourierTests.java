package createCourier;

import static testData.TestData.*;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

public class BadRequestCourierTests {
    CourierRequests courierRequests = new CourierRequests();
    Courier courier = new Courier();

    String login = LOGIN;
    String  password = PASSWORD;
    String  firstName = FIRSTNAME;

    @Before
    public void before() {
        RestAssured.baseURI = URL;
    }

    @Test
    @DisplayName("Проверка создания курьера без логина и пароля")
    public void createCourierBadRequest() {
        courier.setLogin("");
        courier.setPassword(password);
        courier.setFirstName(firstName);

        Response response =  create(courier);
        response.then().assertThat().statusCode(400)
                .and().body("message",equalTo("Недостаточно данных для создания учетной записи"));

        courier.setLogin(login);
        courier.setPassword("");

        response =  create(courier);
        response.then().assertThat().statusCode(400)
                .and().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("Создание курьера")
    public Response create(Courier courier){
      return   courierRequests.courierCreate(courier);
    }


}
