package com.cs3733.teamd.Model;

import com.cs3733.teamd.Database.DBHandler;

import java.io.File;
import java.util.Locale;

/**
 * Singleton which holds the configuration for our application
 * Created by Stephen on 4/5/2017.
 */
public class ApplicationConfiguration {
    private static ApplicationConfiguration config = null;

    private Language currentLangauge;

    private SearchAlgorithm currentSearchAlgorithm;

    private Hospital hospital;

    private DBHandler database;

    private ApplicationConfiguration(){
        currentSearchAlgorithm = SearchAlgorithm.A_STAR;
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

    public enum SearchAlgorithm {
        A_STAR,
        BFS,
        DFS
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

    public SearchAlgorithm getCurrentSearchAlgorithm() {
        return currentSearchAlgorithm;
    }

    public void setCurrentSearchAlgorithm(SearchAlgorithm currentSearchAlgorithm) {
        this.currentSearchAlgorithm = currentSearchAlgorithm;
    }

    public void setHospital(Hospital h) {
        this.hospital = h;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public DBHandler getDatabase() {
        return database;
    }

    public void setDatabase(DBHandler database) {
        this.database = database;
    }

    public boolean timeoutEnabled() {
        return false;
    }

    public String getFullFilePath(String relativePath) {
        try {
            String localFileName = getClass().getClassLoader().getResource(relativePath).getFile();
            return new File(localFileName).getAbsolutePath();
        } catch (Exception e) {
            return null;
        }

    }
}
