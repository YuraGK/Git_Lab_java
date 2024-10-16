package com.epam.gym.atlass_gym;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Order;
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
import com.epam.gym.atlass_gym.service.DataManager;
import com.epam.gym.atlass_gym.service.TraineeService;
import com.epam.gym.atlass_gym.service.TrainerService;
import com.epam.gym.atlass_gym.service.TrainingService;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


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
	
	@Autowired
	private DataManager dataManager;
	
	private String[][] dataTrainee = {};
	private String[][] dataTrainer = {};
	private String[][] dataTraining = {};
	
	public AtlassGymApplicationTests() {
		String[][] dataTrainee = {
				{"A","B",(LocalDateTime.of(1999, 9, 9, 9, 9).toString()),"Dnipro","1"},
				{"C","D",(LocalDateTime.of(1986, 9, 9, 9, 8).toString()),"Dnipro","2"},
				{"E","F",(LocalDateTime.of(1998, 9, 9, 9, 7).toString()),"Dnipro","3"}
				};
		this.dataTrainee = dataTrainee;
		
		String[][] dataTrainer = {
				{"A","B","light","1"},
				{"C","D","light","2"},
				{"E","F","heavy","3"}
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
	@Order(1)
	void testStartApp() {
		AtlassGymApplication.main(new String[]{""});
	}

	@Test
	@Order(1)
	void testTraineeServiceCreate() {
		
		for(String[] data : dataTrainee) {
			Trainee trainee = new Trainee(data[0],data[1],data[0]+"."+data[1],LocalDateTime.parse(data[2]),data[3],Long.parseLong(data[4]));
			traineeService.createTrainee(trainee);
			System.out.println(trainee.hashCode());
			assertEquals(trainee, traineeService.selectTrainee(data[0]+"."+data[1]));
		}
		
	}
	
	@Test
	@Order(2)
	void testTraineeServiceUpdate() {
		
		Trainee trainee = new Trainee(dataTrainee[0][0],dataTrainee[0][1],dataTrainee[0][0]+"."+dataTrainee[0][1],LocalDateTime.parse(dataTrainee[0][2]),dataTrainee[0][3],Long.parseLong(dataTrainee[0][4]));
		traineeService.createTrainee(trainee);
		assertEquals(trainee, traineeService.selectTrainee(dataTrainee[0][0]+"."+dataTrainee[0][1]));
		
		trainee.setFirstName("C");
		trainee.setLastName("D");
		trainee.setDateOfBirth(LocalDateTime.of(2000, 5, 5, 5, 5));
		trainee.setAddress("Kyev");
		
		traineeService.updateTrainee(dataTrainee[0][0]+"."+dataTrainee[0][1], "C","D", LocalDateTime.of(2000, 5, 5, 5, 5), "Kyev");
		assertEquals(trainee, traineeService.selectTrainee(dataTrainee[0][0]+"."+dataTrainee[0][1]));
	}
	
	@Test
	@Order(3)
	void testTraineeServiceDelete() {
		
		Trainee trainee = new Trainee(dataTrainee[0][0],dataTrainee[0][1],dataTrainee[0][0]+"."+dataTrainee[0][1],LocalDateTime.parse(dataTrainee[0][2]),dataTrainee[0][3],Long.parseLong(dataTrainee[0][4]));
		traineeService.createTrainee(trainee);
		assertEquals(trainee, traineeService.selectTrainee(dataTrainee[0][0]+"."+dataTrainee[0][1]));
		
		traineeService.deleteTrainee(dataTrainee[0][0]+"."+dataTrainee[0][1]);
		
		assertThrows(NoSuchElementException.class,() ->  traineeService.selectTrainee(dataTrainee[0][0]+"."+dataTrainee[0][1]));
	}
	
	
	
	@Test
	@Order(1)
	void testTrainerServiceCreate() {
		
		for(String[] data : dataTrainer) {
			Trainer trainer = new Trainer(data[0], data[1], data[0]+"."+data[1], new Training_type(data[2]), Long.parseLong(data[3]));
			trainerService.createTrainer(trainer);
			System.out.println(trainer.hashCode());
			assertEquals(trainer, trainerService.selectTrainer(data[0]+"."+data[1]));
		}
		
	}
	
	@Test
	@Order(2)
	void testTrainerServiceUpdate() {
		
		Trainer trainer = new Trainer(dataTrainer[0][0], dataTrainer[0][1], dataTrainer[0][0]+"."+dataTrainer[0][1], new Training_type(dataTrainer[0][2]), Long.parseLong(dataTrainer[0][3]));
		trainerService.createTrainer(trainer);
		assertEquals(trainer, trainerService.selectTrainer(dataTrainer[0][0]+"."+dataTrainer[0][1]));
		
		Training_type updatedTrType = new Training_type("plain");
		
		trainer.setFirstName("C");
		trainer.setLastName("D");
		trainer.setSpecialisation(updatedTrType);
		
		trainerService.updateTrainer(dataTrainer[0][0]+"."+dataTrainer[0][1], "C","D", updatedTrType);
		assertEquals(trainer, trainerService.selectTrainer(dataTrainer[0][0]+"."+dataTrainer[0][1]));
	}
	
	@Test
	@Order(1)
	void testTrainingServiceCreate() {
		
		for(String[] data : dataTraining) {
			Training training = new Training(data[0], new Training_type(data[1]), Long.parseLong(data[2]));
			trainingService.createTraining(training);
			System.out.println(training.hashCode());
			assertEquals(training, trainingService.selectTraining(data[0]));
		}

		trainingService.createTraining(dataTraining[0][0]+"2", new Training_type(dataTraining[0][1]), Long.parseLong(dataTraining[0][2]));
		assertDoesNotThrow(() ->  trainingService.selectTraining(dataTraining[0][0]+"2"));
	}

	@Test
	@Order(2)
	void testTrainingTrainerTraineeId() {
		Training training = new Training(dataTraining[0][0]+"1", new Training_type(dataTraining[0][1]), Long.parseLong(dataTraining[0][2]));

		training.addTrainee(dataTrainee[0][4]);
		training.addTrainer(dataTrainer[0][3]);
		System.out.println(training.toString());
		System.out.println(training.getTraineeIds());
		System.out.println(training.getTrainerIds());
		trainingService.createTraining(training);

		assertEquals(training, trainingService.selectTraining(dataTraining[0][0]+"1"));

		training.removeTrainee(dataTrainee[0][4]);
		training.removeTrainer(dataTrainer[0][3]);
		assertTrue(training.getTraineeIds().size()==0);
		assertTrue(training.getTrainerIds().size()==0);
	}
	
	@Test
	@Order(4)
	void testSaveData() {
		for(String[] data : dataTrainee) {
			Trainee trainee = new Trainee(data[0],data[1],data[0]+"."+data[1],LocalDateTime.parse(data[2]),data[3],Long.parseLong(data[4]));
			traineeService.createTrainee(trainee);
			assertEquals(trainee, traineeService.selectTrainee(data[0]+"."+data[1]));
		}
		
		for(String[] data : dataTrainer) {
			Trainer trainer = new Trainer(data[0], data[1], data[0]+"."+data[1], new Training_type(data[2]), Long.parseLong(data[3]));
			trainerService.createTrainer(trainer);
			assertEquals(trainer, trainerService.selectTrainer(data[0]+"."+data[1]));
		}
		
		for(String[] data : dataTraining) {
			Training training = new Training(data[0], new Training_type(data[1]), Long.parseLong(data[2]));
			trainingService.createTraining(training);
			assertEquals(training, trainingService.selectTraining(data[0]));
		}
		
		assertTrue(dataManager.saveAllData());
	}
	
	@Test
	@Order(5)
	void testRetrieveData() {
		assertTrue(dataManager.loadAllData());
		
		Trainee testTrainee = new Trainee(dataTrainee[0][0],dataTrainee[0][1],dataTrainee[0][0]+"."+dataTrainee[0][1],LocalDateTime.parse(dataTrainee[0][2]),dataTrainee[0][3],Long.parseLong(dataTrainee[0][4]));
		Trainer testTrainer = new Trainer(dataTrainer[0][0], dataTrainer[0][1], dataTrainer[0][0]+"."+dataTrainer[0][1], new Training_type(dataTrainer[0][2]), Long.parseLong(dataTrainer[0][3]));
		Training testTraining = new Training(dataTraining[0][0], new Training_type(dataTraining[0][1]), Long.parseLong(dataTraining[0][2]));
		
		assertTrue(testTrainee.getDateOfBirth().equals(traineeService.selectTrainee(dataTrainee[0][0]+"."+dataTrainee[0][1]).getDateOfBirth()));
		assertTrue(testTrainer.getFirstName().equals(trainerService.selectTrainer(dataTrainer[0][0]+"."+dataTrainer[0][1]).getFirstName()));
		assertTrue(testTraining.equals(trainingService.selectTraining(dataTraining[0][0])));
	}

}
