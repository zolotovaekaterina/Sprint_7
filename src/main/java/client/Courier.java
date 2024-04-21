package client;

public class Courier {
    private String login = null;
    private String password = null;
    private String firstName = null;


    public static Courier create(String login, String password, String firstName) {
        return new Courier(login, password, firstName);
    }

    public static Courier create() {
        return new Courier();
    }

    private Courier (String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    private Courier() {
        login = TestConstants.DEFAULT_TEST_LOGIN;
        password = TestConstants.DEFAULT_TEST_PASSWORD;
        firstName = TestConstants.DEFAULT_TEST_FIRSTNAME;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }
}

