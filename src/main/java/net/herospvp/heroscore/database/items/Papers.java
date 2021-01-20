package net.herospvp.heroscore.database.items;

import java.sql.Connection;

public interface Papers {

    void writePaper(Connection connection, Instrument instrument);

}
