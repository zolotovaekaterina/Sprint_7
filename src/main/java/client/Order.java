package client;


import io.qameta.allure.Step;

import static io.restassured.RestAssured.*;

public class Order {
    private static final String CREATE_ORDER_ENDPOINT = "/api/v1/orders";
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final String rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;

    public Order(String firstName, String lastName, String address, String metroStation, String phone, String rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }
    @Step("Создание заказа")
    public int createOrder() {
        return given().log().all()
                .baseUri("http://qa-scooter.praktikum-services.ru/")
                .header("Content-Type", "application/json")
                .body(this)
                .when()
                .post(CREATE_ORDER_ENDPOINT)
                .then().log().all()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("track");
    }



}
