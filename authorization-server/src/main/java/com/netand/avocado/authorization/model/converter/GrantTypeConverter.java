package com.netand.avocado.authorization.model.converter;

import com.netand.avocado.authorization.model.enums.GrantTypes;
import org.springframework.core.convert.converter.Converter;

public class GrantTypeConverter implements Converter< String, GrantTypes > {

	@Override
	public GrantTypes convert( String source ) {

		return GrantTypes.findByCode( source ).orElse( null );
	}
}