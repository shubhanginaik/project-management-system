package fs19.java.backend.presentation.shared.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String message) {
      super(message);
    }
}
