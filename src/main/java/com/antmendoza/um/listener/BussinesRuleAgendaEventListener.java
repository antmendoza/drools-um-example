package com.antmendoza.um.listener;

import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BussinesRuleAgendaEventListener extends DefaultAgendaEventListener {
	private Logger log = LoggerFactory
			.getLogger(BussinesRuleAgendaEventListener.class);

	@Override
	public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {

		log.info("Ejecutando grupo de reglas: "
				+ event.getRuleFlowGroup().getName());

		KieSession kses = (KieSession) event.getKieRuntime();
		kses.fireAllRules();
	}
	
	
	

}
