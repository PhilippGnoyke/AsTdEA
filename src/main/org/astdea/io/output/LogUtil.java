package org.astdea.io.output;

import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogUtil
{
    private static Logger LOGGER;
    private static final String format = "[%1$tF %1$tT] [%2$-4s] %3$s %n";

    static
    {
        LOGGER = Logger.getLogger(LogUtil.class.getName());
        LOGGER.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter()
        {
            @Override
            public synchronized String format(LogRecord lr)
            {
                return String.format(format,
                    new Date(lr.getMillis()),
                    lr.getLevel().getLocalizedName(),
                    lr.getMessage()
                );
            }
        });
        LOGGER.addHandler(handler);
    }

    public static void log(String text)
    {
        LOGGER.info(text);
    }
}
