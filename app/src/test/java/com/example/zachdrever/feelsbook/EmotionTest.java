package com.example.zachdrever.feelsbook;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmotionTest {

    @Test
    public void testLove(){
        Emotion e = Emotion.LOVE;
        assertTrue("Emotion.LOVE == Love", e.getEmotion() == "Love");
    }

    @Test
    public void testValues() {
        String[] emotions = [
                "Love",

        ]
        assertTrue("Emotion values correct", )
    }
}
