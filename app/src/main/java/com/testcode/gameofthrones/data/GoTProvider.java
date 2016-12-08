package com.testcode.gameofthrones.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Fabian on 07/12/2016.
 */
@ContentProvider(authority = GoTProvider.AUTHORITY,database = GoTDB.class)
public class GoTProvider {

    public static final String AUTHORITY = "com.testcode.gameofthrones";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);

    interface Path{
        String CHARACTERS = "Characters";
        String HOUSES = "Houses";
    }

    private static Uri buildUri(String ... paths){
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for(String path: paths){
            builder.appendPath(path);
        }
        return builder.build();
    }
    @TableEndpoint(table = GoTDB.CHARACTERS)public static class Characters{
        @ContentUri(
                path = Path.CHARACTERS,
                type = "vnd.android.cursor,dir/Characters",
                defaultSort = CharacterColumns.NAME + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.CHARACTERS);
    }
    @InexactContentUri(
            name = "CHARACTER_ID",
            path = Path.CHARACTERS+"/#",
            type = "vnd.android.cursor,dir/Characters",
            whereColumn = CharacterColumns._ID,
            pathSegment = 1)
    public static Uri CharacterwithID(long id){
        return buildUri(Path.CHARACTERS, String.valueOf(id));
    }
    @InexactContentUri(
            name = "CHARACTER_HOUSE",
            path = Path.CHARACTERS+"/#",
            type = "vnd.android.cursor.item/Characters",
            whereColumn = CharacterColumns.HOUSE_ID,
            pathSegment = 1)
    public static Uri CharacterwithHOUSEID(String id){
        return buildUri(Path.CHARACTERS, id);
    }
    @TableEndpoint(table = GoTDB.HOUSES)public static class Houses{
        @ContentUri(
                path = Path.HOUSES,
                type = "vnd.android.cursor,dir/Trailers",
                defaultSort = HouseColumns.HOUSE_NAME_HOUSE+ " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.HOUSES);
    }
    @InexactContentUri(
            name = "HOUSE_ID",
            path = Path.HOUSES+"/#",
            type = "vnd.android.cursor,dir/Houses",
            whereColumn = HouseColumns._ID,
            pathSegment = 1)
    public static Uri HousewithID(long id){
        return buildUri(Path.HOUSES, String.valueOf(id));
    }
}
