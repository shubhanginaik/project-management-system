package fs19.java.backend.presentation.shared.exception;

public class WorkspaceNotFoundException extends RuntimeException{
    public WorkspaceNotFoundException(String message) {
        super(message);
    }
}