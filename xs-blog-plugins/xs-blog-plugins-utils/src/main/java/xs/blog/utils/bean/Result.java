package xs.blog.utils.bean;

import java.io.Serializable;

/**
 * 数据交互专用
 * Created by xs on 2018/7/4
 */
public class Result implements Serializable {

    private Integer code;
    private String errorCode;
    private String message;
    private Object data;
    public Result() {
    }
    public Result(Integer code, String errorCode, String message, Object data) {
        this.code = code;
        this.errorCode = errorCode;
        this.message = message;
        this.data = data;
    }
    public static boolean isSuccess(Result rs) {
        return rs != null && rs.getCode() != null && rs.getCode() > 0;
    }

    public static Result hystrixFail() {return new Result(-1, "HYSTRIX_FALLBACK", "fail", null);}
    public static Result buildSuccess(Object obj) {
        return new Result(1,null,"success",obj);
    }
    public static Result buildSuccess() {
        return new Result(1,null,"success",null);
    }
    public static Result buildFail() {
        return new Result(-1,null,"fail",null);
    }
    public static Result buildFail(String msg) {
        return new Result(-1,null,msg,null);
    }
    public static Result buildFail(String errorCode, String msg) {
        return new Result(-1, errorCode, msg, null);
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
