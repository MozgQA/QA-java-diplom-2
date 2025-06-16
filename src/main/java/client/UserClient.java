package client;

import io.restassured.response.ValidatableResponse;
import model.User;
import model.UserCredentials;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class UserClient extends RestClient {

    private static final String USER_PATH = "api/auth/register";
    private static final String LOGIN_PATH = "api/auth/login";
    private static final String DELETE_PATH = "api/auth/user";
    private static final String PATCH_PATH = "api/auth/user";

    @Step("Регистрация нового пользователя")
    public ValidatableResponse create(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(USER_PATH)
                .then();
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse login(UserCredentials userCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(userCredentials)
                .when()
                .post(LOGIN_PATH)
                .then();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse delete(String accessToken, UserCredentials userCredentials) {
        return given()
                .spec(getBaseSpec())
                .header("authorization", accessToken)
                .body(userCredentials)
                .when()
                .delete(DELETE_PATH)
                .then();
    }

    @Step("Изменение информации пользователя")
    public ValidatableResponse changeInfo(String accessToken, UserCredentials userCredentials) {
        return given()
                .spec(getBaseSpec())
                .header("authorization", accessToken)
                .body(userCredentials)
                .when()
                .patch(PATCH_PATH)
                .then();
    }
}
