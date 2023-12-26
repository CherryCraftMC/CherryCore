package net.cherrycraft.cherrycore.languageSystem.data;

import java.util.HashMap;
import java.util.Map;

public class LanguageData {

    private final String LanguageID;
    private Map<String, String> file = new HashMap<>();

    public LanguageData(String LanguageName, Map<String, String> LangFile) {
        this.LanguageID = LanguageName;
        this.file = LangFile;
    }

    // Get
    public String getLanguageName() {
        return LanguageID;
    }

    public Map<String, String> getLanguageFile() {
        return file;
    }
}
