package edu.hitsz.musiccontrol;

public class RunnableTest {
    public static void main(String[] args) {
        new MusicThread("src/videos/bgm.wav").start();
    }
}