/*
 * Copyright (c) 19.7.2020.
 *
 * Bot by Lucass
 */

package de.lucas.teamspeakbot.load;


import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import de.lucas.teamspeakbot.events.Events;
import de.lucas.teamspeakbot.mysql.MySQL;

public class Load {

    public static void main(String[] args) {
        try {
            Datasave.getInstance().startTeamspeakBot();
        } catch (Exception e) {
            System.out.println("TEAMSPEAK BOT NICHT GESTARTET!");
        }
    }
}