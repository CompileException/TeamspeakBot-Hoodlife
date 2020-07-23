/*
 * Copyright (c) 19.7.2020.
 *
 * Bot by Lucass
 */

package de.lucas.teamspeakbot.load;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.lucas.teamspeakbot.events.Events;
import de.lucas.teamspeakbot.mysql.MySQL;
import de.lucas.teamspeakbot.mysql.MySQLAPI;

import java.text.SimpleDateFormat;
import java.util.*;

public class Datasave {

    /*
    START STRINGS
     */

    public static final String botversion = "3.5";
    public static final String lastupdate = "20.07.2020 || 02 Uhr";

    public static final String botname = "Hoodlife-Query[" + getInstance().getRandomNumber(1, 20) + "]";

    /*
    UTILS
     */
    public static Random random;
    public static final ArrayList<Integer> onlinesups = new ArrayList<>();
    public static final ArrayList<String> words = new ArrayList<>();

    /*
    TEAM RÄNGE!!
     */
    public static final int serveradminint = 48499;
    public static final int leitungint = 49001;
    public static final int headadminrangint = 48522;
    public static final int adminrangint = 48523;
    public static final int devrangint = 48505;

    public static final int modrangint = 48506;
    public static final int testmodrangint = 48507;
    public static final int supleitungrangint = 48508;

    public static final int supporterrangint = 48509;
    public static final int testsuprangint = 48510;
    public static final int helferrangint = 48511;

    /*
    SUPPORT RANG
     */
    public static final int suprangint = 56072;
    public static final int togglebotrangint = 56073;
    public static final int chat = 49009;
    public static final int poke = 49007;

    /*
    CHANNEL ID´s
     */
    public static final int supportchannel = 44419;
    public static final int whitelistchannel = 56279;

    public static final int eingangshalle = 44309;
    public static final int ingamechannel = 44427;
    public static final int afkchannel = 44448;

    public String getDatePrefix() {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("[HH:mm:ss] ");
        return simpleDateFormat.format(Calendar.getInstance().getTime());
    }

    public void addonlineSupport() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (Client client : api.getClients()) {

                    if (client.isInServerGroup(Datasave.suprangint)) {
                        onlinesups.add(client.getId());
                    }
                    if (client.getIdleTime() > 1 * 60 * 5000) {
                        if (client.getChannelId() != ingamechannel && client.getChannelId() != afkchannel) {
                            if (!(client.isInServerGroup(Datasave.serveradminint))) {
                                api.moveClient(client.getId(), Datasave.afkchannel);
                                api.sendPrivateMessage(client.getId(), "Da du länger als 5 min weg warst, wurdest du in den AFK Channel gemoved!");
                                if (client.isInServerGroup(Datasave.suprangint)) {
                                    api.moveClient(client.getId(), Datasave.afkchannel);
                                    api.removeClientFromServerGroup(Datasave.suprangint, client.getDatabaseId());
                                    api.sendPrivateMessage(client.getId(), "Der Support Rang wurde entfernt!");
                                }
                            }
                        }
                    }
                }
            }
        }, 1000, 60);
    }

    /*
    JOINMESSAGE DEV & SERVERADMIN
     */

    public void sendJoinMessageDev(Client client) {
        if (client.isInServerGroup(Datasave.devrangint) || client.isInServerGroup(Datasave.serveradminint)) {
            if (!client.isInServerGroup(Datasave.togglebotrangint)) {
                api.sendPrivateMessage(client.getId(), "[color=green]Hood-Life Query-Bot ist online![/color]");
                api.sendPrivateMessage(client.getId(), "Version: " + Datasave.botversion);
                api.sendPrivateMessage(client.getId(), "Letztes Update: " + Datasave.lastupdate);
            }
        }
    }

    /*
    JOINMESSAGE TEAM
     */

    public void sendJoinMessageTeam(Client client) {
        if(!client.isInServerGroup(Datasave.togglebotrangint)) {
            api.sendPrivateMessage(client.getId(), "[color=green]Du wurdest als Teammitglied erkannt![/color]");
            api.sendPrivateMessage(client.getId(), "Folgende Commands stehen für dich bereit!");
            api.sendPrivateMessage(client.getId(), "!addsupport - Bekomme den Support Rang");
            api.sendPrivateMessage(client.getId(), "!removesupport - Entziehe dir den Support Rang");
            api.sendPrivateMessage(client.getId(), "!opensupport - Öffne den Support");
            api.sendPrivateMessage(client.getId(), "!closesupport - Schließe den Support");
            api.sendPrivateMessage(client.getId(), "!togglebot - Bekomme diese Nachricht vom Bot nichtmehr");
        }
        Datasave.onlinesups.add(client.getId());
        if (!client.isInServerGroup(Datasave.suprangint)) {
            if(!client.isServerQueryClient()) {
                api.addClientToServerGroup(Datasave.suprangint, client.getDatabaseId());
            }
        }
    }

    /*
    MYSQL CONNECT DATA
     */

    public MySQL mySQL = new MySQL("localhost", "root", "", "fivem");
    public MySQLAPI mySQLAPI;

    /*
    TSAPI METHODS
     */

    public static final TS3Config config = new TS3Config();
    public static final TS3Query query = new TS3Query(config);
    public static final TS3Api api = query.getApi();

    /*
    [MAIN] START TEAMSPEAKBOT
     */

    public final void startTeamspeakBot() {
        sendStartScreen();
        /*
        CONFIG
         */
        config.setHost("185.223.28.104");
        config.setQueryPort(40014);

        /*
        QUERY
         */
        query.connect();

        /*
        API
         */
        api.login("HoodLifeQuery", "WMqBOCFF");
        api.selectVirtualServerByPort(9106);
        //api.selectVirtualServerById(1);
        api.setNickname(Datasave.botname);
        System.out.println(getDatePrefix() + "[START] Hoodlife-Query started!");

        try {
            Events.registerEvents();
            Datasave.onlinesups.clear();
            addonlineSupport();

            updateSupport();

            System.out.println(getDatePrefix() + "[LISTENER] Alle Events wurden geladen");
        } catch (Exception e) {
            System.out.println(getDatePrefix() + "[LISTENER]FEHLER:");
            System.out.println(e.getMessage());
        }

        for(Client admin : Datasave.api.getClients()) {
            if(admin.isInServerGroup(Datasave.serveradminint) || admin.isInServerGroup(Datasave.devrangint)) {
                if(!admin.isInServerGroup(Datasave.chat)) {
                    Datasave.api.sendPrivateMessage(admin.getId(), "[color=green]Hoodlife Query wieder online![/color]");
                }
            }
        }

/*
        try {
            connectMySQL();
        } catch (Exception e) {
            System.out.println("FEHLER BEIM STARTEN!");
            System.out.println("MYSQL: " + e.getMessage());
        }

 */

    }


    /*
    GET INSTANCE OF DATASAVE
     */
    public static Datasave getInstance() { return new Datasave(); }

    /*
    CONNECT MYSQL TO SERVER
     */

    private void connectMySQL() {
        if (!mySQL.isConnected()) {
            mySQL.connect();
            mySQL.createTable("teamspeak_stats", "UID VARCHAR(100), Name VARCHAR(100), Job VARCHAR(100), JobID INT, Cash BIGINT");
        }
    }

    /*
    [MAIN] SEND START SCREEN
     */

    private void sendStartScreen() {
        System.out.println("");
        System.out.println("╔╦╗┌─┐┌─┐┌┬┐┌─┐┌─┐┌─┐┌─┐┬┌─╔╗ ┌─┐┌┬┐");
        System.out.println(" ║ ├┤ ├─┤│││└─┐├─┘├┤ ├─┤├┴┐╠╩╗│ │ │ ");
        System.out.println(" ╩ └─┘┴ ┴┴ ┴└─┘┴  └─┘┴ ┴┴ ┴╚═╝└─┘ ┴ ");
        System.out.println("");
        System.out.println(getDatePrefix() + "[INFO] Version: " + Datasave.botversion);
        System.out.println(getDatePrefix() + "[INFO] Author: Lucas");
        System.out.println(getDatePrefix() + "[INFO] Last Update: " + Datasave.lastupdate);
        System.out.println(getDatePrefix() + "[INFO] Bot Name: " + Datasave.botname);
    }

    /*
    GET A RANDOM NUMER
     */

    public int getRandomNumber(int min, int max) { return (int) ((Math.random() * (max - min)) + min); }

    /*
    GET MYSQL
     */

    public MySQL getMySQL() { return mySQL; }
    public MySQLAPI getMySQLAPI() { return mySQLAPI; }

    public static void updateSupport() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onlinesups.clear();
                for(Client c : api.getClients()) {
                    if(c.isInServerGroup(Datasave.suprangint)) {
                        onlinesups.add(c.getId());
                    }
                }
                Map<ChannelProperty, String> property = new HashMap<ChannelProperty, String>();
                if(onlinesups.size() == 0) {
                    if(!api.getChannelInfo(Datasave.supportchannel).getName().contains("---> Warte auf Support [CLOSED]")) {
                        property.put(ChannelProperty.CHANNEL_NAME, "---> Warte auf Support [CLOSED]");
                        Datasave.api.addChannelPermission(Datasave.supportchannel, "i_channel_needed_join_power", 75);
                        Datasave.api.editChannel(Datasave.supportchannel, property);
                        property.clear();

                        for(Client client : Datasave.api.getClients()) {
                            if(client.getChannelId() == Datasave.supportchannel) {
                                if(!client.getNickname().equalsIgnoreCase("Hood-Life x Support")) {
                                    Datasave.api.moveClient(client.getId(), Datasave.eingangshalle);
                                    Datasave.api.sendPrivateMessage(client.getId(), "Der Support wurde geschlossen, da keine Supporter online sind!");
                                }
                            }
                        }
                    }
                }else {
                    if(!api.getChannelInfo(Datasave.supportchannel).getName().contains("---> Warte auf Support [OPEN]")) {
                        property.put(ChannelProperty.CHANNEL_NAME, "---> Warte auf Support [OPEN]");
                        Datasave.api.addChannelPermission(Datasave.supportchannel, "i_channel_needed_join_power", 1);
                        Datasave.api.editChannel(Datasave.supportchannel, property);
                        property.clear();
                    }
                }
            }
        }, 1000, 1000);
    }
}

