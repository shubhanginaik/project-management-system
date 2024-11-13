package fs19.java.backend.presentation.shared.response;

import java.util.List;

public class GlobalResponse<T>{
    public final static  String SUCCESS = "SUCCESS";
    public final static String ERROR = "ERROR";

    private final String status;
    private final int code;
    public final T data;
    private final List<ErrorItem> errors;


    public GlobalResponse(int code, List<ErrorItem> errors) {
        this.status = ERROR;
        this.code = code;
        this.data = null;
        this.errors = errors;
    }

    public GlobalResponse(int code, T data) {
        this.status = SUCCESS;
        this.code = code;
        this.data = data;
        this.errors = null;
    }

}
