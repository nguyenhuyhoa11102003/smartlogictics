package com.tdtu.logistics_shipments_service.exception.wrapper;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DuplicatedException extends RuntimeException {
	private String message;

	public DuplicatedException(String errorCode, Object... var2) {
//		this.message = MessagesUtils.getMessage(errorCode, var2);
	}
}
