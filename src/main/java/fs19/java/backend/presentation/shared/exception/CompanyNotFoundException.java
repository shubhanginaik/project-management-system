package fs19.java.backend.presentation.shared.exception;


public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(String message) {
        super(message);
    }
}
