import client.UserClient;
import generator.UserGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.ValidatableResponse;
import io.restassured.response.ValidatableResponseOptions;
import model.UserCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import model.User;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class UserApiTest extends BaseUserTest{
    @Test
    @DisplayName("Тест на создание пользователя")
    public void userCanBeCreated() {
        getUserClient().create(getUser())
                .statusCode(SC_OK)
                .body("success", equalTo(true));

        setAccessToken(getUserClient().login(UserCredentials.from(getUser()))
                .statusCode(SC_OK)
                .body("accessToken", notNullValue())
                .extract().path("accessToken"));
    }

    @Test
    @DisplayName("Тест на создание уже зарегистрированного пользователя")
    public void userAlreadyRegistered() {
        getUserClient().create(getUser())
                .statusCode(SC_OK)
                .body("success", equalTo(true));

        setAccessToken(getUserClient().login(UserCredentials.from(getUser()))
                .extract().path("accessToken"));

        getUserClient().create(getUser())
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Тест на создание пользователя без наименования")
    public void userCreateWithoutName() {
        getUser().setPassword(null);
        getUserClient().create(getUser())
                .statusCode(SC_FORBIDDEN)
                .body("message", equalTo("Email, password and name are required fields"));
        setAccessToken(getUserClient().login(UserCredentials.from(getUser()))
                .extract().path("accessToken"));
    }

    @Test
    @DisplayName("Тест на создание пользователя без пароля")
    public void userCreateWithoutPassword() {
        getUser().setPassword(null);
        getUserClient().create(getUser())
                .statusCode(SC_FORBIDDEN)
                .body("message", equalTo("Email, password and name are required fields"));
        setAccessToken(getUserClient().login(UserCredentials.from(getUser()))
                .extract().path("accessToken"));
    }

    @Test
    @DisplayName("Тест на создание пользователя без email")
    public void userCreateWithoutEmail() {
        getUser().setEmail(null);
        getUserClient().create(getUser())
                .statusCode(SC_FORBIDDEN)
                .body("message", equalTo("Email, password and name are required fields"));
        setAccessToken(getUserClient().login(UserCredentials.from(getUser()))
                .extract().path("accessToken"));
    }
}
