package com.example.ktws.util;

import org.json.JSONObject;

public class Emotion {
    private float sadness = 0;

    private float neutral = 0;

    private float disgust = 0;

    private float anger = 0;

    private float surprise = 0;

    private float fear = 0;

    private float happiness = 0;

    public Emotion(JSONObject jsonObject) {
        //TODO:错误检测
        this.setAnger(jsonObject.getFloat("anger"));
        this.setDisgust(jsonObject.getFloat("disgust"));
        this.setSadness(jsonObject.getFloat("sadness"));
        this.setNeutral(jsonObject.getFloat("neutral"));
        this.setSurprise(jsonObject.getFloat("surprise"));
        this.setFear(jsonObject.getFloat("fear"));
        this.setHappiness(jsonObject.getFloat("happiness"));
    }

    public TypeOfFace getMaxType() {
        float v = sadness;
        v = v < neutral? neutral : v;
        v = v < disgust? disgust : v;
        v = v < anger? anger : v;
        v = v < surprise? surprise : v;
        v = v < fear? fear : v;
        v = v < happiness? happiness : v;
        if (v == 0) {
            return TypeOfFace.ALL;
        }
        if (sadness == v) {
            return TypeOfFace.SADNESS;
        } else if (neutral == v) {
            return TypeOfFace.NEUTRAL;
        } else if (disgust == v) {
            return TypeOfFace.DISGUST;
        } else if (anger == v) {
            return TypeOfFace.ANGER;
        } else if (surprise == v) {
            return TypeOfFace.SURPRISE;
        } else if (fear == v) {
            return TypeOfFace.FEAR;
        } else if (happiness == v) {
            return TypeOfFace.HAPPINESS;
        }
        return TypeOfFace.ALL;
    }

    public float getAnger() {
        return anger;
    }

    public float getDisgust() {
        return disgust;
    }

    public float getFear() {
        return fear;
    }

    public float getHappiness() {
        return happiness;
    }

    public float getNeutral() {
        return neutral;
    }

    public float getSadness() {
        return sadness;
    }

    public float getSurprise() {
        return surprise;
    }

    public void setAnger(float anger) {
        this.anger = anger;
    }

    public void setDisgust(float disgust) {
        this.disgust = disgust;
    }

    public void setFear(float fear) {
        this.fear = fear;
    }

    public void setHappiness(float happiness) {
        this.happiness = happiness;
    }

    public void setNeutral(float neutral) {
        this.neutral = neutral;
    }

    public void setSadness(float sadness) {
        this.sadness = sadness;
    }

    public void setSurprise(float surprise) {
        this.surprise = surprise;
    }

}
