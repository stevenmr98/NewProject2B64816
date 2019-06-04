/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import javafx.scene.media.AudioClip;

/**
 *
 * @author Steven
 */
public class Music {

    private static Music instance = new Music();
    private AudioClip audio;

    public Music() {

    }

    public static Music getInstance() {
        return instance;
    }

    public AudioClip getAudio() {
        return audio;
    }

    public void setAudio(AudioClip audio) {
        this.audio = audio;
    }

}
