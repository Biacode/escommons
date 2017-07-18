package com.sfl.coolmonkey.escommons.core.exception;

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
public class EsCoreRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -7661819825156314518L;

    //region Constructors
    public EsCoreRuntimeException(final String message) {
        super(message);
    }

    public EsCoreRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }
    //endregion
}
