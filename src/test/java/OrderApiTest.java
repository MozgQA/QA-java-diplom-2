import client.OrderClient;
import client.UserClient;
import generator.OrderGenerator;
import generator.UserGenerator;
import io.qameta.allure.junit4.DisplayName;
import model.Order;
import model.User;
import model.UserCredentials;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderApiTest extends BaseUserTest{

    private OrderClient orderClient;
    private Order order;


    @Override
    @Before
    public void setUp() {
        super.setUp();
        userClient.create(user);
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Тест на создание заказа с авторизацией")
    public void createOrderWithAuthorization() {
        accessToken = userClient.login(UserCredentials.from(user))
                .extract().path("accessToken");
        order = OrderGenerator.getOrder();
        orderClient.createOrderWithAuthorization(getAccessToken(), order)
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("order", notNullValue());
    }

    @Test
    @DisplayName("Тест на создание заказа без авторизации")
    public void createOrderWithoutAuthorization() {
        order = OrderGenerator.getOrder();
        orderClient.createOrderWithoutAuthorization(order)
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("order", notNullValue());
    }

    @Test
    @DisplayName("Тест на создание заказа без ингредиентов")
    public void createOrderWithoutIngredients() {
        order = new Order(null);
        orderClient.createOrderWithoutAuthorization(order)
                .statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Тест на создание заказа с некорректными ингредиентами ")
    public void createOrderWithIncorrectIngredients() {
        order = OrderGenerator.getOrderWithIncorrectIngredients();
        orderClient.createOrderWithoutAuthorization(order)
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}
