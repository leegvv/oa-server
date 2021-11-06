package net.arver.oa.common.util;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回结果包装类.
 * @param <T> 泛型
 * @author leegvv
 */
@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class R<T> {

    /**
     * 是否成功.
     */
    private boolean succeed = true;

    /**
     * 业务状态码.
     */
    private int code = HttpStatus.OK.value();

    /**
     * 提示消息.
     */
    private String msg;

    /**
     * 数据对象.
     */
    private T data;

    /**
     * 额外的列属性.
     */
    private Map<String, Object> additionalProperties = new HashMap<>();

    /**
     * 默认的构造器.
     */
    public R() {
    }

    /**
     * 使用指定的数据对象构造.
     *
     * @param data 指定的数据对象
     */
    public R(final T data) {
        this.data = data;
    }

    /**
     * 使用指定的成功标识、业务返回码以及提示信息构造.
     *
     * @param succeed 指定的成功标识
     * @param code 指定的业务返回码
     * @param msg 指定的提示信息
     */
    public R(final boolean succeed, final int code, final String msg) {
        this.succeed = succeed;
        this.code = code;
        this.msg = msg;
    }

    /**
     * 使用指定的成功标识、数据对象、提示信息构造.
     *
     * @param succeed 指定的成功标识
     * @param data 指定的数据对象
     * @param msg 指定的提示信息
     */
    public R(final boolean succeed, final T data, final String msg) {
        this.succeed = succeed;
        this.data = data;
        this.msg = msg;
    }

    /**
     * 使用指定的成功标识、数据对象、业务返回码以及提示信息构造.
     *
     * @param succeed 指定的成功标识
     * @param data 指定的数据对象
     * @param code 指定的业务返回码
     * @param msg 指定的提示信息
     */
    public R(final boolean succeed, final T data, final int code, final String msg) {
        this.succeed = succeed;
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    /**
     * 使用指定的成功标识、提示信息构造.
     *
     * @param succeed 指定的成功标识
     * @param msg 指定的提示信息
     */
    public R(final boolean succeed, final String msg) {
        this.succeed = succeed;
        this.msg = msg;
    }

    public boolean getSucceed() {
        return succeed;
    }

    public void setSucceed(final boolean succeed) {
        this.succeed = succeed;
    }

    public int getCode() {
        return code;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(final T data) {
        this.data = data;
    }

    /**
     * getter.
     * @return 额外的属性
     */
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    /**
     * setter.
     * @param key key
     * @param value value
     */
    @JsonAnySetter
    public void setAdditionalProperties(final String key, final Object value) {
        this.additionalProperties.put(key, value);
    }

    /**
     * getter.
     * @param name key
     * @return value
     */
    @JsonAnyGetter
    public Object getAdditionalProperties(final String name) {
        return this.additionalProperties.get(name);
    }

    /**
     * 错误信息提示.
     * @return 错误信息提示
     */
    public static R error() {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "未知异常，请联系管理员");
    }

    /**
     * 错误信息提示.
     * @param msg 信息内容
     * @return 错误信息提示
     */
    public static R error(final String msg) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    /**
     * 错误信息提示.
     * @param code 状态码
     * @param msg 信息内容
     * @return 错误信息提示
     */
    public static R error(final int code, final String msg) {
        return new R(false, code, msg);
    }

    /**
     * 成功信息提示.
     * @return 成功信息提示
     */
    public static R ok() {
        return new R();
    }

    /**
     * 成功信息提示.
     * @param data 数据
     * @param <T> 泛型
     * @return 成功信息提示
     */
    public static <T> R<T> ok(final T data) {
        return new R(data);
    }
}
