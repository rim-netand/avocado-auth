package com.netand.avocado.authorization.listener.restful.configuration;

import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;

@Component
public class ServletContainerCustomizer implements WebServerFactoryCustomizer< ConfigurableServletWebServerFactory > {

//	private final CloudManagerProperties properties = CloudManagerProperties.getInstance();

	@Override
	public void customize( ConfigurableServletWebServerFactory container ) {

		if ( container instanceof UndertowServletWebServerFactory ) {

			UndertowServletWebServerFactory factory = ( UndertowServletWebServerFactory ) container;

//			//TODO : 외부 설정으로 분리
//			factory.setPort( properties.getIntegerProperty( Keys.httpServer_port ) );
//			factory.setIoThreads( properties.getIntegerProperty( Keys.httpServer_io_maxThreads ) );
//			factory.setWorkerThreads( properties.getIntegerProperty( Keys.httpServer_worker_maxThreads ) );
			factory.setBufferSize( ( int ) DataSize.ofKilobytes( 16 ).toBytes() );
			factory.setUseDirectBuffers( true );
			factory.setEagerInitFilters( true );
			////////////////////////////////////////////////////////////////////////////////////////////////////////////
//			factory.setAccessLogEnabled( properties.getBooleanProperty( Keys.httpServer_accessLog_enabled ) );
//			factory.setAccessLogDirectory( new File( properties.getProperty( Keys.httpServer_accessLog_dir ) ) );
			factory.setAccessLogPattern( "%t %a \"%r\" %s (%D ms)" );
			factory.setAccessLogPrefix( "access_log." );
			factory.setAccessLogSuffix( "log" );
			factory.setAccessLogRotate( true );

//			if ( properties.getBooleanProperty( Keys.httpServer_ssl_enabled ) ) {
//
//				Ssl ssl = new Ssl();
//				ssl.setKeyStore( properties.getProperty( Keys.httpServer_ssl_keyStore ) );
//				ssl.setKeyStoreType( properties.getProperty( Keys.httpServer_ssl_keyStoreType ) );
//				ssl.setKeyStorePassword( properties.getProperty( Keys.httpServer_ssl_keyStorePassword ) );
//				ssl.setEnabledProtocols( properties.getArrayProperty( Keys.httpServer_ssl_enabledProtocols, "," ) );
//
//				factory.setSsl( ssl );
//			}

			factory.addDeploymentInfoCustomizers( deploymentInfo -> {

				WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
				webSocketDeploymentInfo.setBuffers( new DefaultByteBufferPool( false, 1024 ) );
				deploymentInfo.addServletContextAttribute( "io.undertow.websockets.jsr.WebSocketDeploymentInfo", webSocketDeploymentInfo );
			} );
		}
	}
}
