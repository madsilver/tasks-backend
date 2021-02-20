package br.ce.wcaquino.taskbackend.controller;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TaskControllerTest {
	
	@Mock
	private TaskRepo taskRepo;
	
	@InjectMocks
	private TaskController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void mustNotSaveTaskWithoutDescription() {
		Task task = new Task();
		task.setDueDate(LocalDate.now());
		
		try {
			controller.save(task);
			Assert.fail("Shouldn't get this far");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the task description", e.getMessage());
		}
	}
	
	@Test
	public void mustNotSaveTaskWithoutDate() {
		Task task = new Task();
		task.setTask("Description...");
		
		try {
			controller.save(task);
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the due date", e.getMessage());
		}
	}
	
	@Test
	public void mustNotSaveTestWithPastDate() {
		Task task = new Task();
		task.setDueDate(LocalDate.of(2010, 01, 01));
		task.setTask("Description...");

		try {
			controller.save(task);
		} catch (ValidationException e) {
			Assert.assertEquals("Due date must not be in past", e.getMessage());
		}
	}
	
	@Test
	public void mustSaveTaskWithSuccess() throws ValidationException {
		Task task = new Task();
		task.setDueDate(LocalDate.now());
		task.setTask("Description...");

		controller.save(task);
		
		Mockito.verify(taskRepo).save(task);
	}
	
}
