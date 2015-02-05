package com.antmendoza.workitem.mock;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoCompleteWorkItemHandler implements WorkItemHandler {

	private static Logger log = LoggerFactory
			.getLogger(AutoCompleteWorkItemHandler.class);

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {

		log.debug("PARAMS: " + workItem.getParameters());

		manager.completeWorkItem(workItem.getId(), null);

	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {

	}

}
