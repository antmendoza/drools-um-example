package com.antmendoza.um.listener;

import org.kie.api.event.process.DefaultProcessEventListener;
import org.kie.api.event.process.ProcessVariableChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VariableChangeEventListener extends DefaultProcessEventListener {

	private Logger log = LoggerFactory
			.getLogger(VariableChangeEventListener.class);

	public void beforeVariableChanged(ProcessVariableChangedEvent event) {
		log.info("beforeVariableChanged:VariableId=[" + event.getVariableId()
				+ "]; OldValue=[" + event.getOldValue() + "]; NewValue=["
				+ event.getNewValue() + "]");

	}

}
