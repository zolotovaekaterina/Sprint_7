package client;

import io.restassured.response.ValidatableResponse;

public interface  ScooterServiceClient {
    ValidatableResponse createCourier(Courier courier);
    ValidatableResponse login(Credentials credentials);
    ValidatableResponse deleteCourierById(String id);

}
