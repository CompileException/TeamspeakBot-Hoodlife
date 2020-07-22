/*
 * Copyright (c) 21.7.2020.
 *
 * Bot by Lucass
 */

package de.lucas.teamspeakbot.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLAPI {

    public MySQL mySQL;

    public boolean playerExists(final String uid) {
        try {
            final ResultSet rs = mySQL.getResult("SELECT * FROM teamspeak_stats WHERE UID='" + uid + "'");
            return rs.next() && rs.getString("UID") != null;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createPlayer(final String uid, final String name) {
        if (!playerExists(uid)) {
            mySQL.update("INSERT INTO teamspeak_stats (UID, Name, Job, JobID, Cash) VALUES ('" + uid + "', '" + name + "', 'unused', '0', '0')");
        }
    }
}
