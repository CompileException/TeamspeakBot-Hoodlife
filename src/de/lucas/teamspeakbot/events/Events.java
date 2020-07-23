/*
 * Copyright (c) 20.7.2020.
 *
 * Bot by Lucass
 */

package de.lucas.teamspeakbot.events;

import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.*;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.lucas.teamspeakbot.load.Datasave;
import de.lucas.teamspeakbot.load.Load;
import de.lucas.teamspeakbot.mysql.MySQLAPI;

import java.util.HashMap;
import java.util.Map;

public class Events {

    private static Map<ChannelProperty, String> property = new HashMap<>();


    public static void registerEvents() {
        Datasave.api.registerAllEvents();
        Datasave.api.addTS3Listeners(new TS3Listener() {

            /*
            CHATEVENT
             */
            @Override
            public void onTextMessage(TextMessageEvent event) {
                Client client = Datasave.api.getClientInfo(event.getInvokerId());
                /*
                ADD SUPPORTRANG COMMAND
                 */
                if (event.getMessage().equalsIgnoreCase("!addsupport")) {
                    if (client.isInServerGroup(Datasave.supporterrangint) || client.isInServerGroup(Datasave.modrangint) || client.isInServerGroup(Datasave.serveradminint) || client.isInServerGroup(Datasave.devrangint) || client.isInServerGroup(Datasave.helferrangint) || client.isInServerGroup(Datasave.leitungint) || client.isInServerGroup(Datasave.headadminrangint) || client.isInServerGroup(Datasave.adminrangint) || client.isInServerGroup(Datasave.testmodrangint) || client.isInServerGroup(Datasave.testsuprangint) || client.isInServerGroup(Datasave.supleitungrangint) || client.isInServerGroup(Datasave.leitungint)) {
                        if (!client.isInServerGroup(Datasave.suprangint)) {
                            Datasave.api.addClientToServerGroup(Datasave.suprangint, client.getDatabaseId());
                        } else {
                            Datasave.api.sendPrivateMessage(client.getId(), "Du hast den Support-Rang bereits (Verwende !removesupport)");
                            return;
                        }
                    } else {
                        Datasave.api.sendPrivateMessage(client.getId(), "[color=red]Du bist kein Teammitglied![/color]");
                    }
                /*
                REMOVE SUPPORTRANG COMMAND
                */
                } else if (event.getMessage().equalsIgnoreCase("!removesupport")) {
                    if (client.isInServerGroup(Datasave.supporterrangint) || client.isInServerGroup(Datasave.modrangint) || client.isInServerGroup(Datasave.serveradminint) || client.isInServerGroup(Datasave.devrangint) || client.isInServerGroup(Datasave.helferrangint) || client.isInServerGroup(Datasave.leitungint) || client.isInServerGroup(Datasave.headadminrangint) || client.isInServerGroup(Datasave.adminrangint) || client.isInServerGroup(Datasave.testmodrangint) || client.isInServerGroup(Datasave.testsuprangint) || client.isInServerGroup(Datasave.supleitungrangint) || client.isInServerGroup(Datasave.leitungint)) {
                        if (client.isInServerGroup(Datasave.suprangint)) {
                            Datasave.api.removeClientFromServerGroup(Datasave.suprangint, client.getDatabaseId());
                        } else {
                            Datasave.api.sendPrivateMessage(client.getId(), "Du hast den Support-Rang nicht (Verwende !addsupport)");
                            return;
                        }
                    } else {
                        Datasave.api.sendPrivateMessage(client.getId(), "[color=red]Du bist kein Teammitglied![/color]");
                    }
                    /*
                    TOGGLEBOT
                     */
                } else if (event.getMessage().equalsIgnoreCase("!togglebot")) {
                    if (client.isInServerGroup(Datasave.supporterrangint) || client.isInServerGroup(Datasave.modrangint) || client.isInServerGroup(Datasave.serveradminint) || client.isInServerGroup(Datasave.devrangint) || client.isInServerGroup(Datasave.helferrangint) || client.isInServerGroup(Datasave.leitungint) || client.isInServerGroup(Datasave.headadminrangint) || client.isInServerGroup(Datasave.adminrangint) || client.isInServerGroup(Datasave.testmodrangint) || client.isInServerGroup(Datasave.testsuprangint) || client.isInServerGroup(Datasave.supleitungrangint) || client.isInServerGroup(Datasave.leitungint)) {
                        if (!client.isInServerGroup(Datasave.togglebotrangint)) {
                            Datasave.api.addClientToServerGroup(Datasave.togglebotrangint, client.getDatabaseId());
                            Datasave.api.sendPrivateMessage(client.getId(), "Du hast nun den ToggleBot Rang!");
                        }
                    }
                    /*
                    BROADCAST
                     */
                }
            }

            /*
            JOIN EVENT
             */

            @Override
            public void onClientJoin(ClientJoinEvent event) {
                try {
                    Client client = Datasave.api.getClientInfo(event.getClientId());
                    if(Datasave.getInstance().getMySQL().isConnected()) {
                        Datasave.getInstance().getMySQLAPI().createPlayer(client.getUniqueIdentifier().toString(), client.getNickname());
                        System.out.println("!!!!!!!! NEW PLAYER CREATED !!!!!!!!!");
                        System.out.println("Player Name: " + client.getNickname());
                        System.out.println("Player UID: " + client.getUniqueIdentifier().toString());
                        System.out.println(" ");
                    } else { System.out.println("MYSQL NOT CONNECTED!"); }

                    Datasave.getInstance().sendJoinMessageDev(client);
                    if(client.isInServerGroup(Datasave.supporterrangint) || client.isInServerGroup(Datasave.modrangint) || client.isInServerGroup(Datasave.serveradminint) || client.isInServerGroup(Datasave.devrangint) || client.isInServerGroup(Datasave.helferrangint) || client.isInServerGroup(Datasave.leitungint) || client.isInServerGroup(Datasave.headadminrangint) || client.isInServerGroup(Datasave.adminrangint) || client.isInServerGroup(Datasave.testmodrangint) || client.isInServerGroup(Datasave.testsuprangint) || client.isInServerGroup(Datasave.supleitungrangint) || client.isInServerGroup(Datasave.leitungint)) {
                        Datasave.getInstance().sendJoinMessageTeam(client);
                    }
                } catch (Exception e) {}
            }


            @Override
            public void onClientLeave(ClientLeaveEvent event) {
                //Client client = Load.api.getClientInfo(event.getClientId());
            }

            @Override
            public void onServerEdit(ServerEditedEvent event) {
                //Client client = Load.api.getClientInfo(event.getInvokerId());
            }

            @Override
            public void onChannelEdit(ChannelEditedEvent event) {
                //Client client = Load.api.getClientInfo(event.getInvokerId());
            }

            @Override
            public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent event) {
                //Client client = Load.api.getClientInfo(event.getInvokerId());
            }

            @Override
            public void onClientMoved(ClientMovedEvent event) {
                Client client = Datasave.api.getClientInfo(event.getClientId());
                /*
                WHITELIST WARTERAUM MESSAGE
                 */
                if(event.getTargetChannelId() == Datasave.whitelistchannel) {
                    if(!client.isInServerGroup(Datasave.suprangint)) {
                        Datasave.api.pokeClient(client.getId(), "Bitte lies dir das Regelwerk des Servers durch!!");
                        Datasave.api.pokeClient(client.getId(), "https://docs.google.com/document/d/1vd-olAba8GE-qv2R8D_ZmoH-f7cg9gY9b9Yv8KieXjs/edit");

                    } else {
                        Datasave.api.moveClient(client.getId(), Datasave.eingangshalle);
                        Datasave.api.sendPrivateMessage(client.getId(), "Du bist ein Teammitglied!");
                    }

                    /*
                    SUPPORT WARTERAUM MESSAGE
                     */
                } else if(event.getTargetChannelId() == Datasave.supportchannel) {
                    if(!client.isInServerGroup(Datasave.suprangint)) {
                        int i1 = 0;
                        for(Client sup : Datasave.api.getClients()) {
                            if (!(sup.isServerQueryClient())) {
                                for (int i = 0; i < sup.getServerGroups().length; i++) {
                                    if (sup.getServerGroups()[i] == Datasave.suprangint) {
                                        i1++;
                                        Datasave.api.pokeClient(sup.getId(), "Der Spieler " + client.getNickname() + " wartet im Support!");
                                    }
                                }
                            }
                        }
                        Datasave.api.sendPrivateMessage(event.getClientId(), "[color=red]Es wurden [B]" + i1 + "[/B] Teammitglieder benachrichtigt![/color]");
                    } else {
                        Datasave.api.moveClient(client.getId(), Datasave.eingangshalle);
                        Datasave.api.sendPrivateMessage(client.getId(), "Du bist ein Teammitglied!");
                    }
                }
                }

            @Override
            public void onChannelCreate(ChannelCreateEvent event) {
                //Client client = Load.api.getClientInfo(event.getInvokerId());
            }

            @Override
            public void onChannelDeleted(ChannelDeletedEvent event) {
                //Client client = Load.api.getClientInfo(event.getInvokerId());
            }

            @Override
            public void onChannelMoved(ChannelMovedEvent event) {
                //Client client = Load.api.getClientInfo(event.getInvokerId());
            }

            @Override
            public void onChannelPasswordChanged(ChannelPasswordChangedEvent event) {
                //Client client = Load.api.getClientInfo(event.getInvokerId());
            }

            @Override
            public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent event) {
                //Client client = Load.api.getClientInfo(event.getInvokerId());
            }
        });
    }


}
