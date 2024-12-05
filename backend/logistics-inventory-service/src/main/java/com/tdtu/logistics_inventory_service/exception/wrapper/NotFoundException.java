<<<<<<< HEAD
package com.tdtu.logistics_inventory_service.exception.wrapper;

import com.tdtu.logistics_goods_service.Utils.MessagesUtils;

public class NotFoundException extends RuntimeException {

  private String message;

  public NotFoundException(String errorCode, Object... var2) {
    this.message = MessagesUtils.getMessage(errorCode, var2);
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
=======
package com.tdtu.logistics_inventory_service.exception.wrapper;

import com.tdtu.logistics_goods_service.Utils.MessagesUtils;

public class NotFoundException extends RuntimeException {

  private String message;

  public NotFoundException(String errorCode, Object... var2) {
    this.message = MessagesUtils.getMessage(errorCode, var2);
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
>>>>>>> develop
}