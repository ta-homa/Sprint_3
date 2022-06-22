package order;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderRequests {
    //создание заказа
    public Response createOrder(Order order){
        Response response =
                given()
                        //  .log().all()
                        .header("Content-type", "application/json")
                        .body(order)
                        .when()
                        .post("/api/v1/orders");
        return response;
    }
    public Response getOrder(Order order){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(order)
                        .when()
                        .get("/api/v1/orders?nearestStation=[\"1\", \"2\"]" );
        return response;
    }
}
