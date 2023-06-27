package model.log;

import localization.InternalString;

public final class Logger {
    @InternalString
    public static final String loggerDefaultMessage = "The protocol works";
    private static final LogWindowSource defaultLogSource;

    static {
        defaultLogSource = new LogWindowSource(100);
    }

    private Logger() {
    }

    public static void debug(String strMessage) {
        defaultLogSource.append(LogLevel.Debug, strMessage);
    }

    public static void error(String strMessage) {
        defaultLogSource.append(LogLevel.Error, strMessage);
    }

    public static LogWindowSource getDefaultLogSource() {
        return defaultLogSource;
    }
}
