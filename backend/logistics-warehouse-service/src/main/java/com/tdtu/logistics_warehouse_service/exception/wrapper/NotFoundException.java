package com.tdtu.logistics_warehouse_service.exception.wrapper;


import com.tdtu.logistics_warehouse_service.Utils.MessagesUtils;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NotFoundException extends RuntimeException {

	private String message;

	public NotFoundException(String errorCode, Object... var2) {
		this.message = MessagesUtils.getMessage(errorCode, var2);
	}
}