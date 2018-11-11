package com.example.sampleservice;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class NotTest implements Condition{

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		String[] activeProfiles = context.getEnvironment().getActiveProfiles();
		for(String activeProfile : activeProfiles) {
			if(activeProfile.equals("test")) {
				return false;
			}
		}
		return true;
	}

}
