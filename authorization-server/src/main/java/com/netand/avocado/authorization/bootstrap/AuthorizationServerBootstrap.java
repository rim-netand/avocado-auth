package com.netand.avocado.authorization.bootstrap;

import com.netand.avocado.authorization.configuration.AuthorizationConfigurations;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.cli.*;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableAspectJAutoProxy
@SpringBootApplication(
		scanBasePackages = "com.netand.avocado.authorization",
		exclude = {
				DataSourceTransactionManagerAutoConfiguration.class,
				DataSourceAutoConfiguration.class,
				HibernateJpaAutoConfiguration.class
		} )
@EnableRedisRepositories(basePackages = "com.netand.avocado.authorization")
public class AuthorizationServerBootstrap {

	private static final Map< String, String > ENVIRONMENTS = new LinkedHashMap<>();

	private static final Options OPTIONS = new Options();
	private static final String APP_NAME = String.format( "%s-%s.jar", AuthorizationConfigurations.SERVICE_NAME, AuthorizationConfigurations.SERVICE_VERSION );

	static {

		Option propOption = new Option( "properties", true, "properties file path" );
		propOption.setRequired( false );
		OPTIONS.addOption( propOption );

		Option logOption = new Option( "logging", true, "logback configuration file" );
		logOption.setRequired( false );
		OPTIONS.addOption( logOption );
	}

	public static String getEnvironmentValue( String name ) {

		return ENVIRONMENTS.get( name );
	}

	private static String[] createSpringArguments( String[] args ) throws ParseException {

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine commandLine = null;

		try {

			commandLine = parser.parse( OPTIONS, args );
//			ENVIRONMENTS.put( "properties", commandLine.getOptionValue( "properties", "./conf/authorization-server.yml" ) );

			return new String[]{ " --logging.config=" + commandLine.getOptionValue( "logging", "conf/authorization-server.logback.xml" ) };

		} catch ( ParseException e ) {

			formatter.printHelp( "java -jar " + APP_NAME, OPTIONS );
			System.err.println( e.getMessage() );

			throw e;
		}
	}

	public static void main( String[] args ) throws ParseException {

		String[] arguments = AuthorizationServerBootstrap.createSpringArguments( args );

		ApplicationPidFileWriter pidWriter = new ApplicationPidFileWriter();
		pidWriter.setTriggerEventType( ApplicationStartedEvent.class );

		SpringApplication application = new SpringApplicationWithMDC( AuthorizationServerBootstrap.class );
		application.setBannerMode( Banner.Mode.LOG );
		application.addListeners( pidWriter );
//		application.addListeners( new ApplicationStartedEventListener() );
		application.run( arguments );
	}
}
