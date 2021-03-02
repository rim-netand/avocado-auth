package com.netand.commons.exception;

import com.netand.commons.model.ResultStatus;

public interface HiwareExceptions {

	String getPrefix();

	ResultStatus getResultStatus();

	int getSerialNumber();

	String getFormat();

}
