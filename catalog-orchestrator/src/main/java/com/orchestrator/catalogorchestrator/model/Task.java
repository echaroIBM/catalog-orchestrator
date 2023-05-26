package com.orchestrator.catalogorchestrator.model;

import java.time.LocalDateTime;

public class Task {
	private String taskName;
	private String taskDescription;
	private LocalDateTime taskTime;
	public Status status;
	
	public Task(String taskName, String taskDescription, LocalDateTime taskTime, Status status) {
		super();
		this.taskName = taskName;
		this.taskDescription = taskDescription;
		this.taskTime = taskTime;
		this.status = status;
	}
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	public LocalDateTime getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(LocalDateTime taskTime) {
		this.taskTime = taskTime;
	}
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	public static enum Status{
		DONE, PENDING, CANCEL
	}

	
}
