package com.healthbank.groupvideocall.openvcall.model;

import com.healthbank.GlobalValues;

public class CurrentUserSettings {
    public int mEncryptionModeIndex=0;

    public String mEncryptionKey="kirti";

    public String mChannelName="AES-128-XTS";

    public CurrentUserSettings() {
        reset();
    }

    public void reset() {
        mEncryptionModeIndex = 0;
       mChannelName = Integer.toString(GlobalValues.docid);
       mEncryptionKey="AES-128-XTS";
    }
}
