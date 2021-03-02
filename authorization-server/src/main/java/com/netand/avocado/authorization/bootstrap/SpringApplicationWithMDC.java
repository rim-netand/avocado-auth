package com.netand.avocado.authorization.bootstrap;

import com.netand.avocado.authorization.configuration.AuthorizationConfigurations;
import com.netand.avocado.authorization.model.enums.MDCKeys;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;

public class SpringApplicationWithMDC extends SpringApplication {

	public SpringApplicationWithMDC( Class< ? >... primarySources ) {

		super( primarySources );

		MDC.put( MDCKeys.Service_Name, AuthorizationConfigurations.SERVICE_NAME );
		MDC.put( MDCKeys.Service_Version, AuthorizationConfigurations.SERVICE_VERSION );
		MDC.put( MDCKeys.Service_HostName, AuthorizationConfigurations.HOST_NAME );
		MDC.put( MDCKeys.Service_Environment_ProfileName, AuthorizationConfigurations.ENVIRONMENT_NAME );

		MDC.put( MDCKeys.Application_InstanceId, AuthorizationConfigurations.INSTANCE_ID );
	}
}