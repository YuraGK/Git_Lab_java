package com.epam.gym.atlass_gym;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.epam.gym.atlass_gym.model.Trainee;
import com.epam.gym.atlass_gym.model.Trainer;
import com.epam.gym.atlass_gym.model.Training;
import com.epam.gym.atlass_gym.model.Training_type;
import com.epam.gym.atlass_gym.service.TraineeService;
import com.epam.gym.atlass_gym.service.TrainerService;
import com.epam.gym.atlass_gym.service.TrainingService;


@ExtendWith(SpringExtension.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = {AtlassGymApplication.class}
		)
@AutoConfigureMockMvc
class AtlassGymApplicationTests {
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TrainingService trainingService;
	
	@Autowired
	private TrainerService trainerService;
	
	@Autowired
	private TraineeService traineeService;
	
	private String[][] dataTrainee = {};
	private String[][] dataTrainer = {};
	private String[][] dataTraining = {};
	
	public AtlassGymApplicationTests() {
		String[][] dataTrainee = {
				{"A","B","A.B","fsmk7fgjnk",(LocalDateTime.of(1999, 9, 9, 9, 9).toString()),"Dnipro","1"},
				{"C","D","C.D","fsmk7fgjnk",(LocalDateTime.of(1986, 9, 9, 9, 8).toString()),"Dnipro","2"},
				{"E","F","E.F","fsmk7fgjnk",(LocalDateTime.of(1998, 9, 9, 9, 7).toString()),"Dnipro","3"}
				};
		this.dataTrainee = dataTrainee;
		
		String[][] dataTrainer = {
				{"A","B","A.B","fsmk7fgjnk","light","1"},
				{"C","D","C.D","fsmk7fgjnk","light","2"},
				{"E","F","E.F","fsmk7fgjnk","heavy","3"}
				};
		this.dataTrainer = dataTrainer;
		
		String[][] dataTraining = {
				{"A","light","15"},
				{"B","heavy","45"},
				{"C","med","60"}
				};
		this.dataTraining = dataTraining;
		
	}
	
	

	@Test
	void testTraineeServiceCreate() {
		
		for(String[] data : dataTrainee) {
			Trainee trainee = new Trainee(data[0],data[1],data[2],data[3],LocalDateTime.parse(data[4]),data[5],Long.parseLong(data[6]));
			traineeService.createTrainee(trainee);
			assertEquals(trainee, traineeService.selectTrainee(data[2]));
		}
		
	}
	
	@Test
	void testTraineeServiceUpdate() {
		
		Trainee trainee = new Trainee(dataTrainee[0][0],dataTrainee[0][1],dataTrainee[0][2],dataTrainee[0][3],LocalDateTime.parse(dataTrainee[0][4]),dataTrainee[0][5],Long.parseLong(dataTrainee[0][6]));
		traineeService.createTrainee(trainee);
		assertEquals(trainee, traineeService.selectTrainee(dataTrainee[0][2]));
		
		trainee.setFirstName("C");
		trainee.setLastName("D");
		trainee.setDateOfBirth(LocalDateTime.of(2000, 5, 5, 5, 5));
		trainee.setAddress("Kyev");
		
		traineeService.updateTrainee(dataTrainee[0][2], "C","D", LocalDateTime.of(2000, 5, 5, 5, 5), "Kyev");
		assertEquals(trainee, traineeService.selectTrainee(dataTrainee[0][2]));
	}
	
	@Test
	void testTraineeServiceDelete() {
		
		Trainee trainee = new Trainee(dataTrainee[0][0],dataTrainee[0][1],dataTrainee[0][2],dataTrainee[0][3],LocalDateTime.parse(dataTrainee[0][4]),dataTrainee[0][5],Long.parseLong(dataTrainee[0][6]));
		traineeService.createTrainee(trainee);
		assertEquals(trainee, traineeService.selectTrainee(dataTrainee[0][2]));
		
		traineeService.deleteTrainee(dataTrainee[0][2]);
		
		assertThrows(NoSuchElementException.class,() ->  traineeService.selectTrainee(dataTrainee[0][2]));
	}
	
	
	
	@Test
	void testTrainerServiceCreate() {
		
		for(String[] data : dataTrainer) {
			Trainer trainer = new Trainer(data[0], data[1], data[2], data[3], new Training_type(data[4]), Long.parseLong(data[5]));
			trainerService.createTrainer(trainer);
			assertEquals(trainer, trainerService.selectTrainer(data[2]));
		}
		
	}
	
	@Test
	void testTrainerServiceUpdate() {
		
		Trainer trainer = new Trainer(dataTrainer[0][0], dataTrainer[0][1], dataTrainer[0][2], dataTrainer[0][3], new Training_type(dataTrainer[0][4]), Long.parseLong(dataTrainer[0][5]));
		trainerService.createTrainer(trainer);
		assertEquals(trainer, trainerService.selectTrainer(dataTrainer[0][2]));
		
		Training_type updatedTrType = new Training_type("plain");
		
		trainer.setFirstName("C");
		trainer.setLastName("D");
		trainer.setSpecialisation(updatedTrType);
		
		trainerService.updateTrainer(dataTrainer[0][2], "C","D", updatedTrType);
		assertEquals(trainer, trainerService.selectTrainer(dataTrainer[0][2]));
	}
	
	@Test
	void testTrainingServiceCreate() {
		
		for(String[] data : dataTraining) {
			Training training = new Training(data[0], new Training_type(data[1]), Long.parseLong(data[2]));
			
			trainingService.createTraining(training);
			
			assertEquals(training, trainingService.selectTraining(data[0]));
		}
		
	}

}
