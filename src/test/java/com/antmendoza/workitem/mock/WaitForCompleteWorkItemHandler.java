package com.antmendoza.workitem.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

public class WaitForCompleteWorkItemHandler implements WorkItemHandler {

	private List<WorkItem> items = new ArrayList<WorkItem>();
	private WorkItemManager manager = null;
	private Map<String, Object> inputParameters;

	private static List<Task> tasks = null;

	public WaitForCompleteWorkItemHandler() {
		tasks = new ArrayList<Task>();
	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {

		inputParameters = new HashMap<String, Object>();
		this.items.add(workItem);
		this.manager = manager;
		for (Map.Entry<String, Object> entry : workItem.getParameters()
				.entrySet()) {
			inputParameters.put(entry.getKey(), entry.getValue());
		}
		String taskName = (String) workItem.getParameter("TaskName");
		String actorId = (String) workItem.getParameter("ActorId");
		long id = workItem.getId();

		tasks.add(new Task(id, taskName, actorId));

	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
	}

	public void completeTask(long taskId, Map<String, Object> paramWI) {
		manager.completeWorkItem(taskId, paramWI);
		removeTaskById(taskId);
	}

	public List<Task> getTaskByActorId(String actorId) {
		List<Task> result = new ArrayList<Task>();

		for (Task task : tasks) {
			if (task.getAutor().equals(actorId)) {
				result.add(task);
			}
		}
		return result;
	}

	private boolean removeTaskById(long taskId) {
		Task taskWithId = null;
		for (Task task : tasks) {
			if (task.getId() == taskId) {
				taskWithId = task;
			}
		}
		return tasks.remove(taskWithId);

	}

}