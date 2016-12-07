package com.testcode.gameofthrones.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.Unique;

/**
 * Created by Fabian on 07/12/2016.
 */

public interface HouseColumns {
    @DataType(DataType.Type.INTEGER)@PrimaryKey
    @AutoIncrement
    public static final String _ID = "_id";

    @DataType(DataType.Type.TEXT)@NotNull @Unique public static final String HOUSE_ID = "House_Id";
    @DataType(DataType.Type.TEXT)@NotNull public static final String  HOUSE_NAME= "House_Name";
    @DataType(DataType.Type.TEXT)@NotNull public static final String HOUSE_IMAGE_URL = "House_Image_Url";
}
