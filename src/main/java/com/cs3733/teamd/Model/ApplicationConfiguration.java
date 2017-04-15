package com.cs3733.teamd.Model;

import java.util.Locale;

/**
 * Singleton which holds the configuration for our application
 * Created by Stephen on 4/5/2017.
 */
public class ApplicationConfiguration {
    private static ApplicationConfiguration config = null;

    private Language currentLangauge;

    private ApplicationConfiguration(){
        currentLangauge = Language.ENGLISH;
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

    public enum Language {
        ENGLISH,
        SPANISH
    }

    public Language getCurrentLanguage() {
        return currentLangauge;
    }

    public void setCurrentLangauge(Language newLanguage) {
        this.currentLangauge = newLanguage;
    }

    public static Locale getEnglishLocale() {
        return new Locale("en", "US");
    }

    public static Locale getSpanishLocale() {
        return new Locale("es","SP");
    }


}
