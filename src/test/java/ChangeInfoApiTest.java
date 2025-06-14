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
        getUserClient().create(getUser());
    }

    @Test
    @DisplayName("Тест на изменение email с авторизацией")
    public void changeEmailWithAuthorization() {
       setAccessToken(getUserClient().login(UserCredentials.from(getUser()))
                .statusCode(SC_OK)
                .body("accessToken", notNullValue())
                .extract().path("accessToken"));

        getUser().setEmail(CHANGED_EMAIL);
        getUserClient().changeInfo(getAccessToken(), UserCredentials.from(getUser()))
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Тест на изменение пароля с авторизацией")
    public void changePasswordWithAuthorization() {
        setAccessToken(getUserClient().login(UserCredentials.from(getUser()))
                .statusCode(SC_OK)
                .body("accessToken", notNullValue())
                .extract().path("accessToken"));

        getUser().setPassword(CHANGED_PASSWORD);
        getUserClient().changeInfo(getAccessToken(), UserCredentials.from(getUser()))
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Тест на изменение имени с авторизацией")
    public void changeNameWithAuthorization() {
        setAccessToken(getUserClient().login(UserCredentials.from(getUser()))
                .statusCode(SC_OK)
                .body("accessToken", notNullValue())
                .extract().path("accessToken"));

        getUser().setName(CHANGED_NAME);
        getUserClient().changeInfo(getAccessToken(), UserCredentials.from(getUser()))
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Тест на изменение email без авторизации ")
    public void changeEmailWithoutAuthorization() {
        setAccessToken(getUserClient().login(UserCredentials.from(getUser()))
                .statusCode(SC_OK)
                .body("accessToken", notNullValue())
                .extract().path("accessToken"));

        getUser().setEmail("email124789237504932562043875");
        getUserClient().changeInfo("", UserCredentials.from(getUser()))
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Тест на попытку изменения пароля без авторизации")
    public void changePasswordWithoutAuthorization() {
        setAccessToken(getUserClient().login(UserCredentials.from(getUser()))
                .statusCode(SC_OK)
                .body("accessToken", notNullValue())
                .extract().path("accessToken"));
        getUser().setPassword(CHANGED_PASSWORD);
        getUserClient().changeInfo("", UserCredentials.from(getUser()))
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Тест на попытку изменения имени без авторизации")
    public void changeNameWithoutAuthorization() {
        setAccessToken(getUserClient().login(UserCredentials.from(getUser()))
                .statusCode(SC_OK)
                .body("accessToken", notNullValue())
                .extract().path("accessToken"));
        getUser().setName(CHANGED_NAME);
        getUserClient().changeInfo("", UserCredentials.from(getUser()))
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
