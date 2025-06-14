import client.OrderClient;
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
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;

public class UsersOrdersApiTest extends BaseUserTest{

    private OrderClient orderClient;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        getUserClient().create(getUser());
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Тест на получение заказов неавторизованного пользователя")
    public void getUsersOrdersWithoutAuthorization() {
        orderClient = new OrderClient();
        orderClient.getOrdersWithoutAuthorization()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Тест на получение заказов авторизованного пользователя")
    public void getUsersOrdersWithAuthorization() {
       setAccessToken(getUserClient().login(UserCredentials.from(getUser()))
                .body("accessToken", notNullValue())
                .extract().path("accessToken"));
        orderClient = new OrderClient();
        orderClient.getOrdersWithAuthorization(getAccessToken())
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("order", not(empty()));
    }
}
