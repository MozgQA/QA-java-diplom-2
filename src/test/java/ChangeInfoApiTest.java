import client.UserClient;
import generator.UserGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import model.User;
import model.UserCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ChangeInfoApiTest extends BaseUserTest{

    private static final String CHANGED_EMAIL = "email124789237504932562043875";
    private static final String CHANGED_PASSWORD = "password124789237504932562043875";
    private static final String CHANGED_NAME = "name124789237504932562043875";

    @Override
    @Before
    public void setUp() {
        super.setUp();
        userClient.create(user);
    }

    @Test
    @DisplayName("Тест на изменение email с авторизацией")
    public void changeEmailWithAuthorization() {
       accessToken = userClient.login(UserCredentials.from(user))
                .statusCode(SC_OK)
                .body("accessToken", notNullValue())
                .extract().path("accessToken");

        user.setEmail(CHANGED_EMAIL);
        userClient.changeInfo(getAccessToken(), UserCredentials.from(user))
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Тест на изменение пароля с авторизацией")
    public void changePasswordWithAuthorization() {
        accessToken = userClient.login(UserCredentials.from(user))
                .statusCode(SC_OK)
                .body("accessToken", notNullValue())
                .extract().path("accessToken");

        user.setPassword(CHANGED_PASSWORD);
        userClient.changeInfo(getAccessToken(), UserCredentials.from(user))
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Тест на изменение имени с авторизацией")
    public void changeNameWithAuthorization() {
        accessToken = userClient.login(UserCredentials.from(user))
                .statusCode(SC_OK)
                .body("accessToken", notNullValue())
                .extract().path("accessToken");

        user.setName(CHANGED_NAME);
        userClient.changeInfo(getAccessToken(), UserCredentials.from(user))
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Тест на изменение email без авторизации ")
    public void changeEmailWithoutAuthorization() {
        accessToken = userClient.login(UserCredentials.from(user))
                .statusCode(SC_OK)
                .body("accessToken", notNullValue())
                .extract().path("accessToken");

        user.setEmail("email124789237504932562043875");
        userClient.changeInfo("", UserCredentials.from(user))
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Тест на попытку изменения пароля без авторизации")
    public void changePasswordWithoutAuthorization() {
        accessToken = userClient.login(UserCredentials.from(user))
                .statusCode(SC_OK)
                .body("accessToken", notNullValue())
                .extract().path("accessToken");
        user.setPassword(CHANGED_PASSWORD);
        userClient.changeInfo("", UserCredentials.from(user))
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Тест на попытку изменения имени без авторизации")
    public void changeNameWithoutAuthorization() {
        accessToken = userClient.login(UserCredentials.from(user))
                .statusCode(SC_OK)
                .body("accessToken", notNullValue())
                .extract().path("accessToken");
        user.setName(CHANGED_NAME);
        userClient.changeInfo("", UserCredentials.from(user))
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
