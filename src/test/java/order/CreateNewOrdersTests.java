package order;
import  static  testData.TestData.*;

import createCourier.Courier;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(Parameterized.class)
public class CreateNewOrdersTests {
    Order order = new Order();
    OrderRequests orderRequests = new OrderRequests();

    private final String[] color;

    public CreateNewOrdersTests( String[] color) {
        this.color = color;
    }

    @Before
    public void before() {
        RestAssured.baseURI = URL;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][] {
                {COLOR_BLACK},
                { COLOR_ALL},
                { COLOR_NULL},
        };
    }

    @Test
    @DisplayName("Проверка успешного создания заказа") // имя теста
    public void CreateNewOrders(){
        order.setFirstName(FIRSTNAME);
        order.setLastName(LASTNAME);
        order.setAddress(ADDRESS);
        order.setMetroStation(METRO_STATION);
        order.setPhone(PHONE);
        order.setRentTime(RENT_TIME);
        order.setDeliveryDate(DELIVERY_DATE);
        order.setComment(COMMENT);
        order.setColor(color);

        Response response = create(order);
                response.then().assertThat().statusCode(CREATED_ORDER_STATUS)
                .and().body("track",notNullValue());
    }

    @Step("Создание заказа")
    private Response create(Order order){
        return  orderRequests.createOrder(order);
    }

}
