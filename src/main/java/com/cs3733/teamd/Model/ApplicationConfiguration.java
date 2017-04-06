package com.cs3733.teamd.Model;

/**
 * Singleton which holds the configuration for our application
 * Created by Stephen on 4/5/2017.
 */
public class ApplicationConfiguration {
    private static ApplicationConfiguration config = null;

    private ApplicationConfiguration(){

    }

    public static ApplicationConfiguration getInstance() {
        if(config == null) {
            config = new ApplicationConfiguration();
        }
        return config;
    }

    public SQL_LOG_LEVEL getSqlLoggingLevel() {
        return SQL_LOG_LEVEL.FULL;
    }

    public enum SQL_LOG_LEVEL {
        NONE,
        FULL
    }
}
