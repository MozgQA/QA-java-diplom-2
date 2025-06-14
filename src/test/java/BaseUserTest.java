import client.UserClient;
import generator.UserGenerator;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.Setter;
import model.User;
import model.UserCredentials;
import org.junit.After;
import org.junit.Before;

@Getter
public class BaseUserTest {

    private UserClient userClient;
    private User user;
    @Setter
    private String accessToken;

    @Before
    @Step("Подготовка тестовых данных")
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandom();
    }

    @After
    @Step("Очистка тестовых данных")
    public void cleanUp() {
        if (accessToken != null) {
            getUserClient().delete(accessToken, UserCredentials.from(getUser()));
        }
    }

}
