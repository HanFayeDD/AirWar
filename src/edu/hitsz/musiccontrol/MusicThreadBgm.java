package edu.hitsz.musiccontrol;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MusicThreadBgm extends MusicThread{

    private boolean exit;

    public MusicThreadBgm(String filename) {
        super(filename);
        keeprunning = true;
        exit = false;
    }

    @Override
    public void run() {
        while (!exit){
            if(keeprunning) {
                InputStream stream = new ByteArrayInputStream(super.getSamples());
                play(stream);
            }
        }
    }

    public void setExit(){
        this.exit = true;
    }
}
