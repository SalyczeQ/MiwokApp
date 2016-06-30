package com.example.android.miwok;

/**
 * Created by salay on 26.06.2016.
 */
public class Word {
    private String defaultTranslatin;
    private String miwokTranslatin;
    private String resourceIdS;

    public Word(String defaultTranslatin, String miwokTranslatin) {
        this.defaultTranslatin = defaultTranslatin;
        this.miwokTranslatin = miwokTranslatin;
        this.resourceIdS = null;
    }


    public Word(String defaultTranslatin, String miwokTranslatin, String resourceIdS) {
        this.defaultTranslatin = defaultTranslatin;
        this.miwokTranslatin = miwokTranslatin;
        this.resourceIdS = resourceIdS;
    }

    public String getDefaultTranslatin() {
        return defaultTranslatin;
    }

    public String getMiwokTranslatin() {
        return miwokTranslatin;
    }

    public String getResourceIdS() {
        return resourceIdS;
    }
}
