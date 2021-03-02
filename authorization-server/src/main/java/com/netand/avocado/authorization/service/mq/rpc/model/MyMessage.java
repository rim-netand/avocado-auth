package com.netand.avocado.authorization.service.mq.rpc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class MyMessage implements Serializable {

	private String id;
	private String pass;

	public MyMessage( String id, String pass ) {
		this.id = id;
		this.pass = pass;
	}
}
