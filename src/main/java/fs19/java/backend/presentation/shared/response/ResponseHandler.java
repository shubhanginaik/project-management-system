package fs19.java.backend.presentation.shared.response;

import fs19.java.backend.presentation.shared.status.ResponseStatus;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Work as response Handler for define error codes
 */
public class ResponseHandler {

    /**
     * Convert repose status to Error
     *
     * @param status
     * @return
     */
    public static List<ErrorItem> convertResponseStatusToError(ResponseStatus status) {
        List<ErrorItem> errors = new ArrayList<>();
        switch (status) {
            case ROLE_NAME_NOT_FOUND ->
                    errors.add(new ErrorItem("Please Define a valid Role name", "" + status.getStatus()));
            case ROLE_ID_NOT_FOUND ->
                    errors.add(new ErrorItem("Please Define a valid Role Id", "" + status.getStatus()));
            case INVALID_INFORMATION_ROLE_DETAILS_NOT_FOUND ->
                    errors.add(new ErrorItem("No Role Result found!", "" + status.getStatus()));
            case RECORD_ALREADY_CREATED ->
                    errors.add(new ErrorItem("Already Result found With Given Name", "" + status.getStatus()));
            case PERMISSION_NAME_NOT_FOUND ->
                    errors.add(new ErrorItem("No Permission Name found!", "" + status.getStatus()));
            case PERMISSION_ID_NOT_FOUND ->
                    errors.add(new ErrorItem("No Permission ID found!", "" + status.getStatus()));
            case INVALID_INFORMATION_PERMISSION_DETAILS_NOT_FOUND ->
                    errors.add(new ErrorItem("No Permission Result found!", "" + status.getStatus()));
            case ROLE_RESULT_NOT_FOUND->
                    errors.add(new ErrorItem("Role Result not found!", "" + status.getStatus()));
            case PERMISSION_RESULT_NOT_FOUND->
                    errors.add(new ErrorItem(" Permission Result not found!", "" + status.getStatus()));
        }
        return errors;
    }

    /**
     * get response status according to expected code and responses
     *
     * @param expectedCode
     * @param status
     * @return
     */
    public static HttpStatus getResponseCode(HttpStatus expectedCode, ResponseStatus status) {
        switch (status) {
            case ROLE_NAME_NOT_FOUND, ROLE_ID_NOT_FOUND, INVALID_INFORMATION_ROLE_DETAILS_NOT_FOUND,
                 PERMISSION_NAME_NOT_FOUND, INVALID_INFORMATION_PERMISSION_DETAILS_NOT_FOUND,
                 PERMISSION_ID_NOT_FOUND,ROLE_RESULT_NOT_FOUND,PERMISSION_RESULT_NOT_FOUND -> {
                return HttpStatus.NOT_FOUND;
            }
            case RECORD_ALREADY_CREATED -> {
                return HttpStatus.NOT_ACCEPTABLE;
            }
        }
        return expectedCode;

    }
}
