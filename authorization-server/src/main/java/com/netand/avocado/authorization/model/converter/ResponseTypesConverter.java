package com.netand.avocado.authorization.model.converter;

import com.netand.avocado.authorization.model.enums.ResponseTypes;
import org.springframework.core.convert.converter.Converter;

public class ResponseTypesConverter implements Converter< String, ResponseTypes > {

	@Override
	public ResponseTypes convert( String source ) {

		return ResponseTypes.findByCode( source ).orElse( null );
	}
}
