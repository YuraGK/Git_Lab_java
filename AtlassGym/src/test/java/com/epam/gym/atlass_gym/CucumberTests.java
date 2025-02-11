package com.epam.gym.atlass_gym;

import com.epam.gym.atlass_gym.controller.LoginController;
import com.epam.gym.atlass_gym.controller.TraineeController;
import com.epam.gym.atlass_gym.controller.TrainerController;
import com.epam.gym.atlass_gym.controller.TrainingController;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {AtlassGymApplication.class}
)
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/TestRest.feature")
@ContextConfiguration(classes = AtlassGymApplication.class)
public class CucumberTests {
    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new LoginController(), new TraineeController(), new TrainerController(), new TrainingController()).build();
    }
}
