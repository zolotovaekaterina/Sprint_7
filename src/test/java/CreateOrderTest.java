import client.Order;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final String rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;

    public CreateOrderTest(String firstName, String lastName, String address, String metroStation, String phone, String rentTime, String deliveryDate, String comment, String[] color) {
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
    @Parameterized.Parameters(name = "DataFotTests")
    public static Object[][] getDataTestForScooter() {
        return new Object[][]{
                {"Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", "5", "2024-06-06", "Saske, come back to Konoha", new String[]{"BLACK"}},
                {"Naruto", "Uchiha", "Kanoha, 142 apt.", "4", "+7 800 355 35 35", "5", "2024-06-06", "Saske, come back to Konoha", new String[]{"GREY"}},
                {"Naruto", "Uchiha", "Kanoha, 142 apt.", "4", "+7 800 355 35 35", "5", "2024-06-06", "Saske, come back to Konoha", new String[]{"BLACK", "GREY"}},
                {"Naruto", "Uchiha", "Kanoha, 142 apt.", "4", "+7 800 355 35 35", "5", "2024-06-06", "Saske, come back to Konoha", null}
        };
    }
    @Test
    @DisplayName("Создание заказов с разными цветами")
    @Description("При создании заказа:" +
            "можно указать один из цветов — BLACK или GREY;\n" +
            "можно указать оба цвета;\n" +
            "можно совсем не указывать цвет")
    public void createOrdersWithVariousColorScooter() {
        System.out.println(1);

        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        System.out.println(1);

        int orderTrack = order.createOrder();

        assertNotNull(orderTrack);
}
}
