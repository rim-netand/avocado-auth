package com.netand.avocado.authorization.listener.restful.controller.v1;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class TestController {

	@PostMapping( "/oauth2/v1/receiver" )
	public ResponseEntity< ? > testReceiver( @RequestHeader Map< String, String > headers,
	                                         ServletInputStream inputStream ) {

		try {

			headers.forEach( ( k, v ) -> log.info( "[Header] {} : {}", k, v ) );

			List< String > readLines = IOUtils.readLines( inputStream, StandardCharsets.UTF_8 );
			readLines.forEach( line -> log.info( "{}", line ) );

		} catch ( IOException e ) {

			//TODO : Error
		}

		return ResponseEntity.noContent().build();
	}
}
