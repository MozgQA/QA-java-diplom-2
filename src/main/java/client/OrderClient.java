package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {

    private static final String ORDERS_PATH = "api/orders";

    @Step("Создание заказа с авторизацией")
    public ValidatableResponse createOrderWithAuthorization(String accessToken, Order order) {
        return given()
                .spec(getBaseSpec())
                .header("authorization", accessToken)
                .body(order)
                .when()
                .post(ORDERS_PATH)
                .then();
    }

    @Step("Создание заказа без авторизации")
    public ValidatableResponse createOrderWithoutAuthorization(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDERS_PATH)
                .then();
    }

    @Step("Получение списка заказов с авторизацией")
    public ValidatableResponse getOrdersWithAuthorization(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("authorization", accessToken)
                .when()
                .get(ORDERS_PATH)
                .then();
    }

    @Step("Получение списка заказов без авторизации")
    public ValidatableResponse getOrdersWithoutAuthorization() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDERS_PATH)
                .then();
    }
}
