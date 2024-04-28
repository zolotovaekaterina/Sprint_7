package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderListService{

    @Step("Формирование данных для запроса списка заказов без параметров")
    public static OrderListService getDataForListOfOrdersWithoutParameters() {
        return new OrderListService();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getListOfOrders(OrderListService orderList) {

        String GET_ORDER_LIST = "/api/v1/orders";
        return given()
                .log()
                .all()
                .baseUri("http://qa-scooter.praktikum-services.ru/")
                .header("Content-Type", "application/json")
                .body(orderList)
                .when()
                .get(GET_ORDER_LIST)
                .then().log().all();
    }
}
