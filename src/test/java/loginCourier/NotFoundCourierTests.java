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

public class NotFoundCourierTests {
    CourierRequests courierRequests = new CourierRequests();
    Courier courier = new Courier();

    @Before
    public void before() {
        RestAssured.baseURI = URL;
    }

    @Test
    @DisplayName("Проверка неуспешного логина курьера с неверным логином или паролем") // имя теста
    public void loginCourierNotFound() {
        courier.setLogin(LOGIN);
        courier.setPassword(PASSWORD);

        //логин проверка ,без логина
        Response response = login(courier);
        response.then().assertThat().statusCode(404)
                .and().body("message", equalTo("Учетная запись не найдена"));
    }

    @Step("Логин курьера")
    public Response login(Courier courier) {
        return courierRequests.courierLogin(courier);
    }
}
