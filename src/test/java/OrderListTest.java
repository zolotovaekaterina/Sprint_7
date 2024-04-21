import client.OrderList;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTest {

    OrderList orderList;

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверь, что в тело ответа возвращается список заказов.")
    public void getListOfOrders() {

        orderList = OrderList.getDataForListOfOrdersWithoutParameters();
        orderList.getListOfOrders(orderList)
                .statusCode(200)
                .body("orders", notNullValue());
    }
}
