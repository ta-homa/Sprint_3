package createCourier;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierRequests {

    //логин курьера
    public Response courierLogin(Courier courier){

        Response response =
                given()
                     //   .log().all()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post("/api/v1/courier/login");
        return response;
    }
    //создание курьера
    public Response courierCreate(Courier courier){
        Response response =
                given()
                     //   .log().all()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");
        return response;
    }
    //удаление курьера
    public Response courierDelete(int i){
        Response response =
                given()
                  //  .log().all()
                    .header("Content-type", "application/json")
                    .when()
                    .delete("/api/v1/courier/" + i)
                    .then()
                    .extract().response();
        return response;
    }
}
