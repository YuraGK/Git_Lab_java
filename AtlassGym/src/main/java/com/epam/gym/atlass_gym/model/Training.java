package com.epam.gym.atlass_gym.model;

import java.util.LinkedList;
import java.util.List;


public class Training {

	private List<String> traineeId;
	private List<String> trainerId;
	private String trainingName;
	private Training_type trainingType;
	private Long trainingDuration;
	
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

	public List<String> getTraineeIds() {return traineeId;}
	public void setTraineeIds(List<String> traineeId) {this.traineeId = traineeId;}

	public List<String> getTrainerIds() {return trainerId;}
	public void setTrainerIds(List<String> trainerId) {this.traineeId = trainerId;}
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
	
	@Override 
	public boolean equals(Object obj) {
		
		return obj.toString().equals(toString());
	}
	
	@Override
    public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((traineeId == null) ? 0 : traineeId.hashCode());
		result = prime * result + ((trainerId == null) ? 0 : trainerId.hashCode());
		result = prime * result + ((trainingName == null) ? 0 : trainingName.hashCode());
		result = prime * result + ((trainingType == null) ? 0 : trainingType.hashCode());
		result = prime * result + trainingDuration.intValue();
		return result;
    }
}
