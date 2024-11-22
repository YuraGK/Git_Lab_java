package com.epam.gym.atlass_gym;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {AtlassGymApplication.class}
)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RESTtests {

    String token;
    @LocalServerPort
    private int port;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void traineeTest() throws Exception {
        String o = "{\"firstName\":\"Dohn\",\"lastName\":\"Huan\",\"dateOfBirth\":\"2024-12-09\",\"address\":\"Dnipro\"}";

        ResultActions result = this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/gym/trainee/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(o))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("username", "Dohn.Huan"));//trainee register


        String pass = result.andReturn().getModelAndView().getModelMap().get("password").toString();
        System.out.println(pass);

        result = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"login\":\"Dohn.Huan\",\"password\":\"" + pass + "\"}"))
                .andDo(print())
                .andExpect(status().isOk());//trainee login

        token = result.andReturn().getModelAndView().getModelMap().get("token").toString();
        System.out.println(token);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/gym/trainee/getProfile")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("Dohn.Huan")
                                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("trainee"));//trainee get

        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/login/change")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "    \"login\": \"Dohn.Huan\",\n" +
                                        "    \"password\": \"" + pass + "\",\n" +
                                        "    \"newPassword\": \"joji\"" +
                                        "}")
                                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk());//trainee change login

        String up = "{\"username\":\"Dohn.Huan\",\n" +
                "    \"firstName\":\"Mann\",\n" +
                "    \"lastName\":\"Cox\",\n" +
                "    \"isActive\":true,\n" +
                "    \"dateOfBirth\":\"2000-01-01\",\n" +
                "    \"address\":\"Rome\"\n}";

        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/gym/trainee/updateProfile")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(up)
                                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("trainee"));//trainee update


    }

    @Test
    @Order(2)
    public void trainerTest() throws Exception {
        String o = "{\n" +
                "    \"firstName\": \"Neo\",\n" +
                "    \"lastName\": \"Lokiii\",\n" +
                "    \"specialisation\": {\n" +
                "        \"training_type\": \"Zoomba\"\n" +
                "    }\n" +
                "}";

        ResultActions result = this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/gym/trainer/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(o))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("username", "Neo.Lokiii"));//trainer register

        String pass = result.andReturn().getModelAndView().getModelMap().get("password").toString();
        System.out.println(pass);

        result = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"login\":\"Neo.Lokiii\",\"password\":\"" + pass + "\"}"))
                .andDo(print())
                .andExpect(status().isOk());//trainer login

        token = result.andReturn().getModelAndView().getModelMap().get("token").toString();
        System.out.println(token);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/login/change")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "    \"login\": \"Neo.Lokiii\",\n" +
                                        "    \"password\": \"" + pass + "\",\n" +
                                        "    \"newPassword\": \"joji\"" +
                                        "}")
                                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk());//trainer change login

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/gym/trainer/getProfile")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("Neo.Lokiii")
                                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("trainer"));//trainer get

        String up = "{\n" +
                "    \"username\":\"Neo.Lokiii\",\n" +
                "    \"firstName\":\"Mann\",\n" +
                "    \"lastName\":\"Cox\",\n" +
                "    \"isActive\":true,\n" +
                "    \"specialization\":{\n" +
                "        \"training_type\": \"Strong\"\n" +
                "    }\n" +
                "}";
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/gym/trainer/updateProfile")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(up)
                                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("trainer"));//trainer update


    }

    @Test
    @Order(3)
    public void trainingTest() throws Exception {

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/gym/trainee/getProfile")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("Dohn.Huan")
                                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("trainee"));//trainee get

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/gym/trainer/getProfile")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("Neo.Lokiii")
                                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("trainer"));//trainer get

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/gym/trainee/getAvailableTrainers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("Dohn.Huan")
                                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("trainers"));//trainee get available trainers


        String up = "{\n" +
                "    \"login\":\"Dohn.Huan\",\n" +
                "    \"trainers\":[\n" +
                "        {\n" +
                "            \"firstName\": \"Neo\",\n" +
                "            \"lastName\": \"Lokiii\",\n" +
                "            \"specialisation\": {\n" +
                "                \"training_type\": \"Strong\"\n" +
                "            }\n" +
                "        }]\n" +
                "}";
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/gym/trainee/updateTraineesTrainersList")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(up)
                                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("trainers"));//trainees trainer list update

        String training = "{\n" +
                "    \"trainee\":\"Dohn.Huan\",\n" +
                "    \"trainer\":\"Neo.Lokiii\",\n" +
                "    \"trainingName\":\"Midnight_slop\",\n" +
                "    \"trainingDate\":\"2024-04-15\",\n" +
                "    \"trainingDuration\":15\n" +
                "}";

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/gym/trainee/getTraineesTrainingsList")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("Dohn.Huan")
                                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/gym/trainer/getTrainersTrainingsList")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("Neo.Lokiii")
                                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/gym/training/getTypes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("types"));//trainee get available trainers


        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/gym/training/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(training)
                                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    public void deleteTrainee() throws Exception {
        this.mockMvc.perform(
                        MockMvcRequestBuilders.patch("/gym/trainee/toggleActive")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "    \"username\":\"Dohn.Huan\",\n" +
                                        "    \"isActive\":false\n" +
                                        "}"))
                .andDo(print())
                .andExpect(status().isOk());//trainee deactivate

        this.mockMvc.perform(
                        MockMvcRequestBuilders.patch("/gym/trainer/toggleActive")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "    \"username\":\"Neo.Lokiii\",\n" +
                                        "    \"isActive\":false\n" +
                                        "}"))
                .andDo(print())
                .andExpect(status().isOk());//trainer deactivate


        this.mockMvc.perform(
                        MockMvcRequestBuilders.delete("/gym/trainee/deleteProfile")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("Dohn.Huan"))
                .andDo(print())
                .andExpect(status().isOk());//trainee delete

    }

    @Test
    @Order(5)
    public void testEmpty() throws Exception {


        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()).andExpect(status().isBadRequest());


        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/login/change")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}"))
                .andDo(print()).andExpect(status().isNotFound());

        assertThrows(Exception.class, () -> {
            this.mockMvc.perform(
                            MockMvcRequestBuilders.post("/gym/trainee/register")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("{\"firstName\":\"l\"}"))
                    .andDo(print());
        });
        assertThrows(Exception.class, () -> {
            this.mockMvc.perform(
                            MockMvcRequestBuilders.post("/gym/trainer/register")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("{\"firstName\":\"l\"}"))
                    .andDo(print());
        });
        assertThrows(Exception.class, () -> {
            this.mockMvc.perform(
                            MockMvcRequestBuilders.put("/gym/trainee/updateProfile")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("{\"firstName\":\"l\"}"))
                    .andDo(print());
        });
        assertThrows(Exception.class, () -> {
            this.mockMvc.perform(
                            MockMvcRequestBuilders.put("/gym/trainer/updateProfile")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("{\"firstName\":\"l\"}"))
                    .andDo(print());
        });
        assertThrows(Exception.class, () -> {
            this.mockMvc.perform(
                            MockMvcRequestBuilders.put("/gym/trainee/updateProfile")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("{\"username\":\"Ded.Ded\",\n" +
                                            "    \"firstName\":\"Mann\",\n" +
                                            "    \"lastName\":\"Cox\",\n" +
                                            "    \"isActive\":true,\n" +
                                            "    \"dateOfBirth\":\"2000-01-01\",\n" +
                                            "    \"address\":\"Rome\"\n}"))
                    .andDo(print());
        });
        assertThrows(Exception.class, () -> {
            this.mockMvc.perform(
                            MockMvcRequestBuilders.put("/gym/trainer/updateProfile")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("{\"username\":\"Ded.Ded\",\n" +
                                            "    \"firstName\":\"Mann\",\n" +
                                            "    \"lastName\":\"Cox\",\n" +
                                            "    \"isActive\":true,\n" +
                                            "    \"dateOfBirth\":\"2000-01-01\",\n" +
                                            "    \"address\":\"Rome\"\n}"))
                    .andDo(print());
        });

        assertThrows(Exception.class, () -> {
            this.mockMvc.perform(
                            MockMvcRequestBuilders.put("/gym/trainee/updateTraineesTrainersList")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("{}"))
                    .andDo(print());
        });
        assertThrows(Exception.class, () -> {
            this.mockMvc.perform(
                            MockMvcRequestBuilders.put("/gym/trainee/updateTraineesTrainersList")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("{\"username\":\"Ded.Ded\"}"))
                    .andDo(print());
        });

        assertThrows(Exception.class, () -> {
            this.mockMvc.perform(
                            MockMvcRequestBuilders.get("/gym/trainee/getTraineesTrainingsList")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("{\"username\":\"Ded.Ded\"}"))
                    .andDo(print());
        });

        assertThrows(Exception.class, () -> {
            this.mockMvc.perform(
                            MockMvcRequestBuilders.patch("/gym/trainee/toggleActive")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("{}"))
                    .andDo(print());
        });

        assertThrows(Exception.class, () -> {
            this.mockMvc.perform(
                            MockMvcRequestBuilders.patch("/gym/trainee/toggleActive")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("{\"username\":\"Ded.Ded\"}"))
                    .andDo(print());
        });

        assertThrows(Exception.class, () -> {
            this.mockMvc.perform(
                            MockMvcRequestBuilders.get("/gym/trainer/getTrainersTrainingsList")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("Ded.Ded"))
                    .andDo(print());
        });

        assertThrows(Exception.class, () -> {
            this.mockMvc.perform(
                            MockMvcRequestBuilders.patch("/gym/trainer/toggleActive")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("{}"))
                    .andDo(print());
        });

        assertThrows(Exception.class, () -> {
            this.mockMvc.perform(
                            MockMvcRequestBuilders.patch("/gym/trainer/toggleActive")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("{\"username\":\"Ded.Ded\"}"))
                    .andDo(print());
        });


    }
}
