package com.mhj.web;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.springframework.http.HttpStatus;

/**
 * @author mhj
 * @date 2019/11/15
 */
public class ResponseObject<T> extends AbstractResponse{
    private T data;
    public static final ResponseObject UNAUTHORIZED = unauthorized();
    public static final ResponseObject NOT_FOUND = notFound();

    public ResponseObject() {
    }

    public static <S> ResponseObject<S> success() {
        ResponseObject<S> responseObject = new ResponseObject();
        responseObject.setStatus(HttpStatus.OK.value());
        responseObject.setMessage("success");
        return responseObject;
    }

    public static <S> ResponseObject<S> success(S payload) {
        ResponseObject<S> responseObject = new ResponseObject();
        responseObject.setStatus(HttpStatus.OK.value());
        responseObject.setMessage("success");
        responseObject.setData(payload);
        return responseObject;
    }

    public static <S> ResponseObject<S> internalError() {
        ResponseObject<S> responseObject = new ResponseObject();
        responseObject.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return responseObject;
    }

    public static <S> ResponseObject<S> unauthorized() {
        ResponseObject<S> responseObject = new ResponseObject();
        responseObject.setStatus(HttpStatus.UNAUTHORIZED.value());
        return responseObject;
    }

    public static <S> ResponseObject<S> unauthorized(String message) {
        ResponseObject<S> responseObject = new ResponseObject();
        responseObject.setStatus(HttpStatus.UNAUTHORIZED.value());
        responseObject.setMessage(message);
        return responseObject;
    }

    public static <S> ResponseObject<S> remoteServiceUnavailable() {
        ResponseObject<S> responseObject = new ResponseObject();
        responseObject.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
        responseObject.setMessage("远程服务不可用");
        return responseObject;
    }

    public static <S> ResponseObject<S> remoteServiceUnavailable(String message) {
        ResponseObject<S> responseObject = new ResponseObject();
        responseObject.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
        responseObject.setMessage(message);
        return responseObject;
    }

    private static <S> ResponseObject<S> notFound() {
        ResponseObject<S> responseObject = new ResponseObject();
        responseObject.setStatus(HttpStatus.NOT_FOUND.value());
        return responseObject;
    }

    public static <S> ResponseObject<S> internalError(String message) {
        Preconditions.checkArgument(message != null && !Strings.isNullOrEmpty(message), "message不能为空");
        ResponseObject<S> responseObject = new ResponseObject();
        responseObject.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseObject.setMessage(message);
        return responseObject;
    }

    public static <S> ResponseObject<S> badRequest(String message) {
        Preconditions.checkArgument(message != null && !Strings.isNullOrEmpty(message), "message不能为空");
        ResponseObject<S> responseObject = new ResponseObject();
        responseObject.setStatus(HttpStatus.BAD_REQUEST.value());
        responseObject.setMessage(message);
        return responseObject;
    }

    public static <S> ResponseObject<S> fail(int code, String message) {
        Preconditions.checkArgument(code > 0, "code必须大于0,请参考http状态的定义");
        Preconditions.checkArgument(message != null && !Strings.isNullOrEmpty(message), "message不能为空");
        ResponseObject<S> responseObject = new ResponseObject();
        responseObject.setStatus(code);
        responseObject.setMessage(message);
        return responseObject;
    }

    public static <S> ResponseObject<S> fail(int code, S payload, String message) {
        Preconditions.checkArgument(code > 0, "code必须大于0,请参考http状态的定义");
        Preconditions.checkArgument(message != null && !Strings.isNullOrEmpty(message), "message不能为空");
        ResponseObject<S> responseObject = new ResponseObject();
        responseObject.setStatus(code);
        responseObject.setData(payload);
        responseObject.setMessage(message);
        return responseObject;
    }

    public boolean checkOk() {
        return this.status == HttpStatus.OK.value();
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseObject{data=" + this.data + ", status=" + this.status + ", message='" + this.message + '\'' + ", path='" + this.path + '\'' + '}';
    }
}
