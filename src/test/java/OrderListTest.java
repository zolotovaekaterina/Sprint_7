import client.OrderList;
import client.OrderListService;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTest {

    OrderListService orderListService;

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверь, что в тело ответа возвращается список заказов.")
    public void getListOfOrders() {

        orderListService = OrderListService.getDataForListOfOrdersWithoutParameters();
        ValidatableResponse validatableResponse = orderListService.getListOfOrders(orderListService);
        validatableResponse.statusCode(200).body("orders", notNullValue());
    }
}
