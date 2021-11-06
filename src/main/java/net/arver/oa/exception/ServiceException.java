package net.arver.oa.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 自定义异常类.
 * @author leegvv
 */
@Data
public class ServiceException extends RuntimeException {
    /**
     * 状态码.
     */
    private int code = HttpStatus.INTERNAL_SERVER_ERROR.value();

    /**
     * 错误信息.
     */
    private String msg;

    /**
     * 构造函数.
     * @param msg 错误信息
     */
    public ServiceException(final String msg) {
        super(msg);
        this.msg = msg;
    }

    /**
     * 构造函数.
     * @param msg 错误信息
     * @param e 异常
     */
    public ServiceException(final String msg, final Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    /**
     * 构造函数.
     * @param msg 错误信息
     * @param code 状态码
     */
    public ServiceException(final String msg, final int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    /**
     * 构造函数.
     * @param msg 错误信息
     * @param code 业务状态码
     * @param e 异常
     */
    public ServiceException(final String msg, final int code, final Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}
