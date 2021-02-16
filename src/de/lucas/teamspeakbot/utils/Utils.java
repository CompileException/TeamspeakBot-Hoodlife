/*
 * Copyright (c) 24.7.2020.
 *
 * Bot by Lucas
 */

package de.lucas.teamspeakbot.utils;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.sun.prism.shader.Texture_ImagePattern_AlphaTest_Loader;
import de.lucas.teamspeakbot.load.Datasave;

import java.util.Timer;
import java.util.TimerTask;

public class Utils {

    public void serveradmininfos() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (Client all : Datasave.api.getClients()) {
                    if(all.getUniqueIdentifier().equalsIgnoreCase("") || all.getUniqueIdentifier().equalsIgnoreCase("") || all.getUniqueIdentifier().equalsIgnoreCase("")) {
                        if(all.isInServerGroup(Datasave.serveradminint)) {

                        }
                    } else {
                        Datasave.api.banClient(all.getId(), 10, "Du darfst diesen Rang nicht haben!");
                    }
                }
            }
        }, 1000, 60);
    }


}
