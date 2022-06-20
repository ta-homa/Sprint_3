package order;
import static testData.TestData.*;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsNull.notNullValue;

public class GetOrderTests {
    OrderRequests orderRequests = new OrderRequests();
    Order order = new Order();

    @Before
    public void before() {
        RestAssured.baseURI = URL;
    }

    @Test
    @DisplayName("Проверка получения списка заказов между станциями метро") // имя теста
    public void getListOrder(){
        Response response = getOrder(order);
        response.then().assertThat().statusCode(200)
            .and().body("orders",notNullValue());
    }

    @Step
    public Response getOrder(Order order){
        return   orderRequests.getOrder(order);

    }

}
