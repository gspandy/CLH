package com.cl.service.exception;

import com.cl.ws.base.ClResultEnum;

public class ExceptionFactory {
	
	public static CreditsysBizException newClException(ClResultEnum esupplierResultEnum,
														String errorMsg) {
		return new CreditsysBizException(esupplierResultEnum, errorMsg);
	}
}
