/*
 * Copyright (c) 19.7.2020.
 *
 * Bot by Lucass
 */

package de.lucas.teamspeakbot.load;



public class Load {

    public static void main(String[] args) {
        try {
            Datasave.getInstance().startTeamspeakBot();
        } catch (Exception e) {
            System.out.println("TEAMSPEAK BOT NICHT GESTARTET!");
        }
    }
}