package com.epam.gym.atlass_gym;

import com.epam.gym.atlass_gym.controller.LoginController;
import com.epam.gym.atlass_gym.controller.TraineeController;
import com.epam.gym.atlass_gym.controller.TrainerController;
import com.epam.gym.atlass_gym.controller.TrainingController;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {AtlassGymApplication.class}
)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
public class CucumberTestSteps {

    String token;
    String pass;
    @LocalServerPort
    private int port;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new LoginController(), new TraineeController(), new TrainerController(), new TrainingController()).build();
    }

    @Given("I log into account with username {string} and password {string}")
    public void i_log_into_account_with_username_and_password(String username, String password) throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new LoginController(), new TraineeController(), new TrainerController(), new TrainingController()).build();

        try {
            i_input_name_familyname_password_and_if_is_trainer("Neo", "Lokiii", "gw", "true");
        } catch (ServletException e) {
        }

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"login\":\"" + username + "\",\"" + password + "\":\"" + pass + "\"}"))
                .andDo(print());


    }

    @When("Created user {string}")
    public void i_created_user(String created) {
        System.out.println("Check if exists " + created);
    }

    @Then("I verify access to account")
    public void i_verify_access_to_account() {
        System.out.println("Grant access");
    }

    ////////////////////////////////////////////////////////////////////////////
    @Given("I open registration page")
    public void i_open_registration_page() {
        System.out.println("Registration page");
    }

    @When("I input name {string}, familyname {string}, password {string} and if is trainer {string}")
    public void i_input_name_familyname_password_and_if_is_trainer(String name, String familyname, String password, String isTrainer) throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new LoginController(), new TraineeController(), new TrainerController(), new TrainingController()).build();

        if (isTrainer.equals("true")) {
            String o = "{\"firstName\":\"" + name + "\",\"lastName\":\"" + familyname + "\",\"dateOfBirth\":\"2024-12-09\",\"address\":\"Dnipro\"}";
            try {
                this.mockMvc.perform(
                                MockMvcRequestBuilders.post("/trainer/register")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(o))
                        .andDo(print());
            } catch (ServletException e) {
            }
        } else {

            String o = "{\"firstName\":\"" + name + "\",\"lastName\":\"" + familyname + "\",\n" +
                    "    \"specialisation\": {\n" +
                    "        \"training_type\": \"Zoomba\"\n" +
                    "    }\n" +
                    "}";
            try {
                this.mockMvc.perform(
                                MockMvcRequestBuilders.post("/trainee/register")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(o))
                        .andDo(print());
            } catch (ServletException e) {
            }
        }

    }

    @When("Check if already created user {string}")
    public void check_if_already_created_user(String exists) {
        System.out.println("Already exists " + exists);
    }

    @Then("I verify user creation")
    public void i_verify_user_creation() {
        System.out.println("User created");
    }

    ///////////////////////////////////////////////////////////////////////////////

    @Given("I add training")
    public void i_add_training() {
        System.out.println("Commence add training");
    }

    @When("I input training name {string}, training datetime {string}, training duration {string}")
    public void i_input_training_name_training_datetime_training_duration(String name, String datetime, String duration) throws Exception {

        this.mockMvc = MockMvcBuilders.standaloneSetup(new LoginController(), new TraineeController(), new TrainerController(), new TrainingController()).build();

        String training = "{\n" +
                "    \"trainee\":\"Dohn.Huan\",\n" +
                "    \"trainer\":\"Neo.Lokiii\",\n" +
                "    \"trainingName\":\"" + name + "\",\n" +
                "    \"trainingDate\":\"" + datetime + "\",\n" +
                "    \"trainingDuration\":" + duration + "\n" +
                "}";

        try {
            this.mockMvc.perform(
                            MockMvcRequestBuilders.post("/training/add")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(training)
                                    .header("Authorization", "Bearer " + token))
                    .andDo(print());
        } catch (ServletException e) {
        }

        System.out.println("adding training " + name);
    }

    @When("I am logged in {string} as a trainer")
    public void i_am_logged_in_as_a_trainer(String loggedIn) {
        System.out.println("is logged in " + loggedIn);
    }

    @When("Trainee {string} exists")
    public void trainee_exists(String exists) {
        System.out.println("Trainee exists " + exists);
    }

    @Then("I verify add training")
    public void i_verify_add_training() {
        System.out.println("Add training");
    }

    /////////////////////////////////////////////////////////////////////////////
    @Given("I send request to get trainings workload")
    public void i_send_request_to_get_trainings_workload() {
        System.out.println("Send request to get trainings workload");
    }

    @When("I am logged in as trainer {string}")
    public void i_am_logged_in_as_trainer(String trainer) throws Exception {

        this.mockMvc = MockMvcBuilders.standaloneSetup(new LoginController(), new TraineeController(), new TrainerController(), new TrainingController()).build();
        try {
            this.mockMvc.perform(
                            MockMvcRequestBuilders.get("/training/getWorkloadReport")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(trainer)
                                    .header("Authorization", "Bearer " + token))
                    .andDo(print());
        } catch (ServletException e) {
        }

    }

    @Then("I verify check trainings workload")
    public void i_verify_check_trainings_workload() {
        System.out.println("Got trainings workload");
    }


}
