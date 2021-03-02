package com.netand.avocado.authorization.model.enums;

public interface MDCKeys {

	//@formatter:off
	String Request_Id                       = "request.id";
	String Request_Time                     = "request.time";
	String Request_RemoteAddress            = "request.remote-address";
	String Request_RemotePort               = "request.remote-port";

	String Response_Status                  = "response.remote-status";

	String Service_Name                     = "service.name";
	String Service_Version                  = "service.version";
	String Service_HostName                 = "service.host-name";
	String Service_Environment_ProfileName  = "service.environment.profile-name";

	String Application_InstanceId           = "application.instance-id";
	//@formatter:on
}
