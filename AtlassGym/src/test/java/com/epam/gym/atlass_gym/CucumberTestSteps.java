package com.epam.gym.atlass_gym;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CucumberTestSteps {

    @Given("I log into account")
    public void i_log_into_account() {
        System.out.println("Login page");
    }

    @When("I input username {string}")
    public void i_input_username(String username) {
        System.out.println("Type username " + username);
    }

    @When("I input password {string}")
    public void i_input_password(String password) {
        System.out.println("Type password " + password);
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

    @When("I input name {string}")
    public void i_input_name(String name) {
        System.out.println("Type name " + name);
    }

    @When("I input familyname {string}")
    public void i_input_familyname(String fname) {
        System.out.println("Type fname " + fname);
    }

    @When("If is trainer {string}")
    public void if_is_trainer(String password) {
        System.out.println("Type password " + password);
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

    @When("I am logged in {string} as a trainer")
    public void i_am_logged_in_as_a_trainer(String trainerusername) {
        System.out.println("Logged in as " + trainerusername);
    }

    @When("Trainee {string} exists")
    public void trainee_exists(String traineeusername) {
        System.out.println("Trainee exists " + traineeusername);
    }

    @When("I input training name {string}")
    public void i_input_training_name(String training_name) {
        System.out.println("Training name: " + training_name);
    }

    @When("I input training datetime {string}")
    public void i_input_training_datetime(String datetime) {
        System.out.println("Training date: " + datetime);
    }

    @When("I input training duration {string}")
    public void i_input_training_duration(String duration) {
        System.out.println("Training duration: " + duration);
    }

    @Then("I verify add training")
    public void i_verify_add_training() {
        System.out.println("Aadd training");
    }
}
