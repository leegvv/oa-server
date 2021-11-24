package net.arver.oa.config;

import lombok.extern.slf4j.Slf4j;
import net.arver.oa.exception.ServiceException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ExceptionAdvice.
 * @author leegvv
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * 异常处理.
     * @param e 异常
     * @return 返回结果
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String exceptionHandle(final Exception e) {
        log.error("执行异常", e);
        if (e instanceof MethodArgumentNotValidException) {
            final MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            return exception.getBindingResult().getFieldError().getDefaultMessage();
        } else if (e instanceof ServiceException) {
            final ServiceException exception = (ServiceException) e;
            return exception.getMsg();
        } else if (e instanceof UnauthorizedException) {
            return "您不具备相关权限";
        } else {
            return "服务器异常";
        }
    }
}
