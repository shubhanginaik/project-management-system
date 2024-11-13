package fs19.java.backend.presentation.global.response;

import java.util.List;

public class GlobalStatus<T> {
    public final static String SUCCESS = "SUCCESS";
    public final static String ERROR = "ERROR";

    private final String status;
    private final int code;
    private final T data;
    private final List<ErrorItem> errors;

    public GlobalStatus(int code, List<ErrorItem> errors) {
        this.status = ERROR;
        this.code = code;
        this.data = null;
        this.errors = errors;
    }

    public GlobalStatus(int code, T data) {
        this.status = SUCCESS;
        this.code = code;
        this.data = data;
        this.errors = null;
    }

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public List<ErrorItem> getErrors() {
        return errors;
    }
}
