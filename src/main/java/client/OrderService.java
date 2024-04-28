package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderService{
    private static final String CREATE_ORDER_ENDPOINT = "/api/v1/orders";



    @Step("Создание заказа")
    public ValidatableResponse createOrder(OrderService orderService) {
        return given().log().all()
                .baseUri("http://qa-scooter.praktikum-services.ru/")
                .header("Content-Type", "application/json")
                .body(this)
                .when()
                .post(CREATE_ORDER_ENDPOINT)
                .then().log().all();

    }
}
