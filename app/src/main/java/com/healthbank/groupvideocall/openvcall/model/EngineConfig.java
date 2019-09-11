package com.healthbank.groupvideocall.openvcall.model;


import io.agora.rtc.video.VideoEncoderConfiguration;

public class EngineConfig {
    public int mUid;
    public String mChannel;
    VideoEncoderConfiguration.VideoDimensions mVideoDimension;

    EngineConfig() {
    }

    public void reset() {
        mChannel = null;
    }
}
