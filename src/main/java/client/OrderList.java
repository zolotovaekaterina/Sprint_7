package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderList {
    private Integer courierId;
    private String nearestStation;
    private Integer limit;
    private Integer page;

    public OrderList(Integer courierId, String nearestStation, Integer limit, Integer page) {
        this.courierId = courierId;
        this.nearestStation = nearestStation;
        this.limit = limit;
        this.page = page;
    }

    @Step("Формирование данных для запроса списка заказов без параметров")
    public static OrderList getDataForListOfOrdersWithoutParameters() {
        return new OrderList(
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
                .then().log().all()
                .assertThat();
    }
}
