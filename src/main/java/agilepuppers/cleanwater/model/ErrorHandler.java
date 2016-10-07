package agilepuppers.cleanwater.model;

public class ErrorHandler {

    public void error(String message) {
        System.out.println(message);
    }

    public void error() {
        error("");
    }

    public void fatalError(String message) {
        error(message);
        System.exit(0);
    }

    public void fatalErorr() {
        fatalError("");
    }

}
