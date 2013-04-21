
package com.example.myexamcustomcursorloader.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * DB、テーブル、クエリURLの定義
 */
public class PlaceManager {
    public static final String AUTHORITY = "com.example.myexamcustomcursorloader.provider.place";
    public static final String DATABASE_NAME = "place.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_PLACE = "place";

    public static final class Place implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/place");

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.myexamcustomcursorloader.place";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.myexamcustomcursorloader.place";

        // Place Table Columns names
        public static final String KEY_ID = "_id";
        public static final String KEY_PLACE_ID = "place_id";
        public static final String KEY_PLACE = "place";
        public static final String KEY_URL = "url";

    }
}
