import client.Courier;
import client.TestConstants;
import client.Credentials;
import client.ScooterServiceClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest {
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
    @DisplayName("Создание курьера")
    @Description("Курьера можно создать")
    public void createCourierSuccess() {

        client = new ScooterServiceClient(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION);

        Courier courier = Courier.create(TestConstants.DEFAULT_TEST_LOGIN, TestConstants.DEFAULT_TEST_PASSWORD, TestConstants.DEFAULT_TEST_FIRSTNAME);
        ValidatableResponse response = client.createCourier(courier);
        ValidatableResponse loginResponse = client.login(Credentials.fromCourier(courier));
        id = loginResponse.extract().jsonPath().getString("id");
        response.assertThat().statusCode(HttpStatus.SC_CREATED).and().body("ok", equalTo(true));
    }


    @Test
    @DisplayName("Создание курьера с повторяющимся логином")
    @Description("Нельзя создать двух одинаковых курьеров")
    public void createCourierDuplicateLogin() {
        client = new ScooterServiceClient(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION);

        Courier courier = Courier.create();
        ValidatableResponse responseFirst = client.createCourier(courier);
        responseFirst.assertThat().statusCode(201).and().body("ok", equalTo(true));
        ValidatableResponse responseSecond =  client.createCourier(courier);
        ValidatableResponse loginResponse = client.login(Credentials.fromCourier(courier));
        id = loginResponse.extract().jsonPath().getString("id");
        responseSecond.assertThat().statusCode(HttpStatus.SC_CONFLICT).body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

    }

    @Test
    @DisplayName("Создание курьера без firstName")
    @Description("Чтобы создать курьера, нужно передать в ручку все обязательные поля")
    public void createCourierWithoutFirstName() {
        client = new ScooterServiceClient(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION);
        Courier courier = Courier.create(TestConstants.DEFAULT_TEST_LOGIN, TestConstants.DEFAULT_TEST_PASSWORD, null);
        ValidatableResponse response = client.createCourier(courier);
        ValidatableResponse loginResponse = client.login(Credentials.fromCourier(courier));
        id = loginResponse.extract().jsonPath().getString("id");
        response.assertThat().statusCode(HttpStatus.SC_CREATED).and().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание курьера без login")
    @Description("Если одного из полей нет (login), запрос возвращает ошибку (400)")
    public void createCourierWithoutLogin() {
        client = new ScooterServiceClient(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION);
        Courier courier =  Courier.create(null, TestConstants.DEFAULT_TEST_PASSWORD, TestConstants.DEFAULT_TEST_FIRSTNAME);
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat().statusCode(HttpStatus.SC_BAD_REQUEST).and().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Создание курьера без password")
    @Description("Если одного из полей нет (password), запрос возвращает ошибку (400)")
    public void createCourierWithoutRassword() {
        client = new ScooterServiceClient(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION);
        Courier courier =  Courier.create(TestConstants.DEFAULT_TEST_LOGIN, null, TestConstants.DEFAULT_TEST_FIRSTNAME);
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat().statusCode(HttpStatus.SC_BAD_REQUEST).and().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без login и password")
    @Description("Если одного из полей нет (login и password), запрос возвращает ошибку (400)")
    public void createCourierWithoutLoginAndRassword() {
        client = new ScooterServiceClient(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION);
        Courier courier =  Courier.create(null, null, TestConstants.DEFAULT_TEST_FIRSTNAME);
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat().statusCode(HttpStatus.SC_BAD_REQUEST).and().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void CleanUp() {
        if (id != null) {
            client.deleteCourierById(id);
        }
    }
    }