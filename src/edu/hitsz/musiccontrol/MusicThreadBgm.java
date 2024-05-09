package edu.hitsz.musiccontrol;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MusicThreadBgm extends MusicThread{

    public MusicThreadBgm(String filename) {
        super(filename);
        keeprunning = true;
    }

    @Override
    public void run() {
        while (keeprunning){
            InputStream stream = new ByteArrayInputStream(super.getSamples());
            play(stream);
        }
    }
    public void stopRunning(){
        keeprunning = false;
    }
}
