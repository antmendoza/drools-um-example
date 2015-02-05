package com.antmendoza.eventlistener;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.event.process.DefaultProcessEventListener;
import org.kie.api.event.process.ProcessNodeTriggeredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeEventListener extends DefaultProcessEventListener {

	private List<String> nodesName = null;
	private Logger log = LoggerFactory.getLogger(NodeEventListener.class);
	public NodeEventListener() {
		nodesName = new ArrayList<String>();
	}

	public void beforeNodeTriggered(ProcessNodeTriggeredEvent event)  {
		String nodeName = event.getNodeInstance().getNodeName();
		log.debug("beforeNodeTriggered: " +nodeName);
		nodesName.add(nodeName);

	}

	public List<String> getNodesName() {
		return nodesName;
	}

}
