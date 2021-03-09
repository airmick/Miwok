package com.example.android.miwok;

public class Word {
    /** Default translation for the word */
    private String defaultTranslation;

    /** Miwok translation for the word */
    private String miwokTranslation;

    /** Miwok audio sound played**/
    private int audioResourceId;

    /** Icon Image**/
    private int imageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId)
    {
        this.defaultTranslation = defaultTranslation;
        this.miwokTranslation = miwokTranslation;
        this.audioResourceId = audioResourceId;
    }

    /**@param audioResourceId is the resource ID for the audio file associated with this word
     **/
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId) {
        this.defaultTranslation = defaultTranslation;
        this.miwokTranslation = miwokTranslation;
        this.imageResourceId = imageResourceId;
        this.audioResourceId = audioResourceId;
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

    public int getAudioResourceId() {
        return audioResourceId;
    }

    public boolean hasImage(){
        return imageResourceId != NO_IMAGE_PROVIDED;
    }

    @Override
    public String toString() {
        return "Word{" +
                "defaultTranslation='" + defaultTranslation + '\'' +
                ", miwokTranslation='" + miwokTranslation + '\'' +
                ", audioResourceId=" + audioResourceId +
                ", imageResourceId=" + imageResourceId +
                '}';
    }
}
