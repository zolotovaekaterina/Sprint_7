package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

public class ScooterServiceClient {

    private static final String CREATE_USER_ENDPOINT = "/api/v1/courier";
    private static final String LOGIN_ENDPOINT = "/api/v1/courier/login";
    private static final String DELETE_ENDPOINT = "/api/v1/courier/{id}";

    private final RequestSpecification requestSpecification;
    private final ResponseSpecification responseSpecification;



    public ScooterServiceClient(RequestSpecification requestSpecification, ResponseSpecification responseSpecification) {
        this.requestSpecification = requestSpecification;
        this.responseSpecification = responseSpecification;
    }


    @Step("Создание курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .spec(requestSpecification)
                .body(courier)
                .post(CREATE_USER_ENDPOINT)
                .then()
                .spec(responseSpecification);
    }


    @Step("Авторизация курьера")
    public ValidatableResponse login(Credentials credentials) {
        return given()
                .spec(requestSpecification)
                .body(credentials)
                .post(LOGIN_ENDPOINT)
                .then()
                .spec(responseSpecification);
    }


    @Step("Удаление курьера")
    public ValidatableResponse deleteCourierById(String id) {
        return given()
                .spec(requestSpecification)
                .pathParams("id", id)
                .when()
                .delete(DELETE_ENDPOINT)
                .then()
                .spec(responseSpecification);
    }
}
