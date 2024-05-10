package edu.hitsz.application;

import edu.hitsz.musiccontrol.MusicThread;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MusicManager {
    private static final Map<String, String> MUSIC_WAV = new HashMap<>();
    public static String BGM = "src/videos/bgm.wav";
    public static String BGM_BOSS = "src/videos/bgm_boss.wav";
    public static String BOMB_EXPLOSION = "src/videos/bomb_explosion.wav";
    public static String BULLET_HIT = "src/videos/bullet_hit.wav";
    public static String GAME_OVER = "src/videos/game_over.wav";
    public static String GET_SUPPLY = "src/videos/get_supply.wav";
}
