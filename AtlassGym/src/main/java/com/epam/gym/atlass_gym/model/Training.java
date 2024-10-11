package com.epam.gym.atlass_gym.model;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Training {

	@Getter 
	@Setter
	private List<String> traineeId;
	
	@Getter 
	@Setter
	private List<String> trainerId;
	
	@Getter 
	@Setter
	private String trainingName;
	
	@Getter 
	@Setter
	private Training_type trainingType;
	
	@Getter 
	@Setter
	private Long trainingDuration;
	
	public Training() {
		traineeId = new LinkedList<String>();
		trainerId = new LinkedList<String>();
	}
	
	public Training(String trainingName, Training_type trainingType,Long trainingDuration) {
		traineeId = new LinkedList<String>();
		trainerId = new LinkedList<String>();
		this.trainingName = trainingName;
		this.trainingType = trainingType;
		this.trainingDuration = trainingDuration;
	}
	
	public Training(List<String> traineeId, List<String> trainerId,String trainingName,Training_type trainingType,Long trainingDuration) {
		this.traineeId = traineeId;
		this.trainerId = trainerId;
		this.trainingName = trainingName;
		this.trainingType = trainingType;
		this.trainingDuration = trainingDuration;
	}
	
	public String getTrainingName() {return trainingName;}
	public void setTrainingName(String trainingName) {this.trainingName = trainingName;}
	public Training_type getTrainingType() {return trainingType;}
	public void setTrainingType(Training_type trainingType) {this.trainingType = trainingType;}
	public Long getTrainingDuration() {return trainingDuration;}
	public void setTrainingDuration(Long trainingDuration) {this.trainingDuration = trainingDuration;}
	
	public void addTrainee(String id) {
		traineeId.stream()
		  .filter(tId -> id.equals(tId))
		  .findAny()
		  .orElse(traineeId.add(id)+"");
	}
	
	public void removeTrainee(String id) {
		traineeId.stream()
		  .filter(tId -> id.equals(tId))
		  .findFirst()
		  .ifPresent(tId -> traineeId.remove(id));
	}
	
	public void addTrainer(String id) {
		trainerId.stream()
		  .filter(tId -> id.equals(tId))
		  .findAny()
		  .orElse(trainerId.add(id)+"");
	}
	
	public void removeTrainer(String id) {
		trainerId.stream()
		  .filter(tId -> id.equals(tId))
		  .findFirst()
		  .ifPresent(tId -> trainerId.remove(id));
	}
	
	@Override 
	public String toString() {
		return String.join(":", traineeId)+" "+String.join(":", trainerId)+" "+trainingName+" "+trainingType.getTraining_type()+" "+trainingDuration; 
		
	}
}
