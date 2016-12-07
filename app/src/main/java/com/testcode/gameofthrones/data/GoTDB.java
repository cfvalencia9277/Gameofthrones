package com.testcode.gameofthrones.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Fabian on 07/12/2016.
 */
@Database(version = GoTDB.VERSION)
public class GoTDB {
    private GoTDB(){}
    public  static final int VERSION = 2;

    @Table(CharacterColumns.class) public static final String CHARACTERS = "Characters";
    @Table(HouseColumns.class) public static final String HOUSES = "Houses";
}
