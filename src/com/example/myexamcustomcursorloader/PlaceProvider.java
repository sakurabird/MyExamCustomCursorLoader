
package com.example.myexamcustomcursorloader;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.myexamcustomcursorloader.data.DatabaseHelper;
import com.example.myexamcustomcursorloader.data.PlaceManager;

/**
 * テーブルアクセス用
 */
public class PlaceProvider extends ContentProvider {

    private static final int PLACE = 1;
    private static final int PLACE_ID = 2;

    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(PlaceManager.AUTHORITY, "place", PLACE);
        URI_MATCHER.addURI(PlaceManager.AUTHORITY, "place/#", PLACE_ID);
    }
    private DatabaseHelper mDBHelper;

    @Override
    public boolean onCreate() {
        mDBHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        if (URI_MATCHER.match(uri) != PLACE) {
            throw new IllegalArgumentException("Unknown URL *** " + uri);
        }

        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        // 追加、又はplace_idが重複の場合は更新
        long rowID = db.replace(PlaceManager.TABLE_PLACE, "NULL", values);

        if (rowID > 0) {
            Uri newUri = ContentUris.withAppendedId(
                    PlaceManager.Place.CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Failed to insert row into " + uri);

    }

    @Override
    public int delete(Uri uri, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int count;

        switch (URI_MATCHER.match(uri)) {
            case PLACE:
                count = db.delete(PlaceManager.TABLE_PLACE, " " +
                        PlaceManager.Place.KEY_ID + " like '%'", null);
                break;
            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int count;
        switch (URI_MATCHER.match(uri)) {
            case PLACE:
                count = db.update(PlaceManager.TABLE_PLACE, values, where, whereArgs);
                break;

            case PLACE_ID:
                String id = uri.getPathSegments().get(1);
                count = db.update(PlaceManager.TABLE_PLACE, values,
                        PlaceManager.Place.KEY_PLACE_ID + "="
                                + id
                                + (!TextUtils.isEmpty(where) ? " AND (" + where
                                        + ')' : ""), whereArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(PlaceManager.TABLE_PLACE);

        Log.d("query", uri.toString());
        switch (URI_MATCHER.match(uri)) {
            case PLACE:
                break;

            case PLACE_ID:
                qb.appendWhere(PlaceManager.Place.KEY_PLACE_ID + "="
                        + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }
        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = PlaceManager.Place.KEY_ID + " DESC"; // 新しい順
        } else {
            orderBy = sortOrder;
        }

        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = qb.query(db, null, null, null, null, null, orderBy);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case PLACE:
                return PlaceManager.Place.CONTENT_TYPE;
            case PLACE_ID:
                return PlaceManager.Place.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }
    }
}
