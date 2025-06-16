import io.qameta.allure.junit4.DisplayName;
import model.UserCredentials;
import org.junit.After;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginApiTest extends BaseUserTest {

    private static final String INCORRECT_PASSWORD = "111";

    @Test
    @DisplayName("Тест на авторизацию пользователя")
    public void userCanBeLogin() {
        userClient.create(user);
        accessToken = userClient.login(UserCredentials.from(user))
                .statusCode(SC_OK)
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .extract().path("accessToken");
    }

    @Test
    @DisplayName("Тест на авторизацию пользователя с некорректным паролем")
    public void userLoginWithIncorrectPassword() {
        user.setPassword(INCORRECT_PASSWORD);
        accessToken = userClient.login(UserCredentials.from(user))
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"))
                .extract().path("accessToken");
    }

    @Test
    @DisplayName("Тест на авторизацию с неправильным email, но правильным паролем и именем")
    public void loginWithIncorrectEmailButCorrectCredentials() {
        user.setEmail("wrong" + user.getEmail());
        accessToken = userClient.login(UserCredentials.from(user))
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"))
                .extract().path("accessToken");
    }

}
