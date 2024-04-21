import client.Courier;
import client.Credentials;
import client.ScooterServiceClient;
import client.ScooterServiceCliientImpl;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {
    private static final RequestSpecification REQUEST_SPECIFICATION =
            new RequestSpecBuilder()
                    .log(LogDetail.ALL)
                    .addHeader("Content-type", "application/json")
                    .setBaseUri("https://qa-scooter.praktikum-services.ru/")
                    .build();

    private static final ResponseSpecification RESPONSE_SPECIFICATION =
            new ResponseSpecBuilder()
                    .log(LogDetail.ALL)
                    .build();
    private ScooterServiceClient client;
    private String id;

    @Test
    @DisplayName("Авторизация курьера - позитивное")
    @Description("Курьер может авторизоваться")
    public void loginCourierSuccess() {

        client = new ScooterServiceCliientImpl(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION);

        Courier courier = Courier.create("TestsLoginK", "TestsPasswordK", "TestsFirstNameK");
        ValidatableResponse response = client.createCourier(courier);
        ValidatableResponse loginResponse = client.login(Credentials.fromCourier(courier));
        id = loginResponse.extract().jsonPath().getString("id");
        loginResponse.assertThat().statusCode(200).and().body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация курьера без login")
    @Description("Для авторизации нужно передать все обязательные поля (Авторизация без login)")
    public void loginCourierWithoutLogin() {

        client = new ScooterServiceCliientImpl(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION);

        Courier courier = Courier.create("TestsLoginK", "TestsPasswordK", "TestsFirstNameK");
        ValidatableResponse response = client.createCourier(courier);
        ValidatableResponse loginResponse = client.login(Credentials.fromCourierWithoutLogin(courier));
        id = loginResponse.extract().jsonPath().getString("id");
        loginResponse.assertThat().statusCode(400).and().body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test
    @DisplayName("Авторизация курьера без password")
    @Description("Для авторизации нужно передать все обязательные поля (Авторизация без password)")
    public void loginCourierWithoutPassword() {

        client = new ScooterServiceCliientImpl(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION);

        Courier courier = Courier.create("TestsLoginK", "TestsPasswordK", "TestsFirstNameK");
        ValidatableResponse response = client.createCourier(courier);
        ValidatableResponse loginResponse = client.login(Credentials.fromCourierWithoutPassword(courier));
        id = loginResponse.extract().jsonPath().getString("id");
        loginResponse.assertThat().statusCode(400).and().body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test
    @DisplayName("Авторизация курьера с неверным login")
    @Description("Cистема вернёт ошибку, если неправильно указать логин")
    public void loginCourierWithWrongLogin() {

        client = new ScooterServiceCliientImpl(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION);

        Courier courier = Courier.create("TestsLoginK", "TestsPasswordK", "TestsFirstNameK");
        ValidatableResponse response = client.createCourier(courier);
        ValidatableResponse loginResponse = client.login(Credentials.fromCourierWithWrongLogin(courier));
        id = loginResponse.extract().jsonPath().getString("id");
        loginResponse.assertThat().statusCode(404).and().body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    @DisplayName("Авторизация курьера с неверным password")
    @Description("Cистема вернёт ошибку, если неправильно указать логин")
    public void loginCourierWithWrongPassword() {

        client = new ScooterServiceCliientImpl(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION);

        Courier courier = Courier.create("TestsLoginK", "TestsPasswordK", "TestsFirstNameK");
        ValidatableResponse response = client.createCourier(courier);
        ValidatableResponse loginResponse = client.login(Credentials.fromCourierWithWrongPassword(courier));
        id = loginResponse.extract().jsonPath().getString("id");
        loginResponse.assertThat().statusCode(404).and().body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    @DisplayName("Авторизация курьера с неверным login и password")
    @Description("Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void loginCourierWithWrongLoginAndPassword() {

        client = new ScooterServiceCliientImpl(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION);

        Courier courier = Courier.create("TestsLoginK", "TestsPasswordK", "TestsFirstNameK");
        ValidatableResponse response = client.createCourier(courier);
        ValidatableResponse loginResponse = client.login(Credentials.fromCourierWithWrongLoginAndPassword(courier));
        id = loginResponse.extract().jsonPath().getString("id");
        loginResponse.assertThat().statusCode(404).and().body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void CleanUp() {
        if (id != null) {
            client.deleteCourierById(id);
        }
    }
}
