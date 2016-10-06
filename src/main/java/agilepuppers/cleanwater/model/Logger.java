package agilepuppers.cleanwater.model;

import java.util.ArrayList;

public final class Logger {

    private ArrayList<String> logs;

    public void log(String message) {
        System.out.println(message);
        logs.add(message);
    }

    public void log(String message, String source) {
        log(source + ": " + message);
    }

    public void saveToFile(String path) {
        // TODO
    }

}
