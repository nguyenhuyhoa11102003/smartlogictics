package com.tdtu.logistics_warehouse_service.exception.wrapper;


import com.tdtu.logistics_warehouse_service.Utils.MessagesUtils;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BadRequestException extends RuntimeException {

	private String message;

	public BadRequestException(String errorCode, Object... var2) {
		this.message = MessagesUtils.getMessage(errorCode, var2);
	}
}
