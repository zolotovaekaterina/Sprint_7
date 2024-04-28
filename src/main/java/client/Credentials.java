package client;

import org.apache.commons.lang3.RandomStringUtils;

public class Credentials {
    private String login;
    private String password;



    public static Credentials fromCourier(Courier courier) {
        return new Credentials(
                courier.getLogin(),
                courier.getPassword());
    }
    public static Credentials fromCourierWithoutLogin(Courier courier) {
        return new Credentials(
                null,
                courier.getPassword()
        );
    }
    public static Credentials fromCourierWithoutPassword(Courier courier) {
        return new Credentials(
                courier.getLogin(),
                null
        );
    }
    public static Credentials fromCourierWithWrongPassword(Courier courier) {
        return new Credentials(
                courier.getLogin(),
                RandomStringUtils.randomAlphanumeric(10)
        );
    }
    public static Credentials fromCourierWithWrongLogin(Courier courier) {
        return new Credentials(
                RandomStringUtils.randomAlphanumeric(10),
                courier.getPassword()
        );
    }

    public static Credentials fromCourierWithWrongLoginAndPassword(Courier courier) {
        return new Credentials("BadLoginForMe", "BadPasswordForMe");
    }

    public Credentials(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
