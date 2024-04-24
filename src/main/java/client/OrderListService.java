package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderListService extends OrderList{
    public OrderListService(Integer courierId, String nearestStation, Integer limit, Integer page) {
        super(courierId, nearestStation, limit, page);
    }
    @Step("Формирование данных для запроса списка заказов без параметров")
    public static OrderListService getDataForListOfOrdersWithoutParameters() {
        return new OrderListService(
                null,
                null,
                null,
                null
        );
    }
    private final String GET_ORDER_LIST = "/api/v1/orders";

    @Step("Получение списка заказов")
    public ValidatableResponse getListOfOrders(OrderList orderList) {

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
