package com.example.android.miwok;

public class Word {
    /** Default translation for the word */
    private String defaultTranslation;

    /** Miwok translation for the word */
    private String miwokTranslation;

    /** Icon Image**/
    private int imageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    public Word(String defaultTranslation, String miwokTranslation)
    {
        this.defaultTranslation = defaultTranslation;
        this.miwokTranslation = miwokTranslation;
    }

    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId) {
        this.defaultTranslation = defaultTranslation;
        this.miwokTranslation = miwokTranslation;
        this.imageResourceId = imageResourceId;
    }

    public String getDefaultTranslation()
    {
        return defaultTranslation;
    }

    public String getMiwokTranslation()
    {
        return miwokTranslation;
    }

    public int getImageResourceId()
    {
        return imageResourceId;
    }

    public boolean hasImage(){
        return imageResourceId != NO_IMAGE_PROVIDED;
    }
}
