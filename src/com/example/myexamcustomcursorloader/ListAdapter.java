
package com.example.myexamcustomcursorloader;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myexamcustomcursorloader.data.PlaceManager;

/**
 * CursorAdapterを拡張してCursorの内容をListViewにセットします
 * 
 * @author sakura
 */
public class ListAdapter extends CursorAdapter {

    private LayoutInflater mInflater;

    class ViewHolder {
        TextView id;
        TextView place_id;
        TextView place;
        TextView url;
    }

    /**
     * @param context
     * @param c
     * @param autoRequery
     */
    public ListAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Viewを再利用してデータをセットします
        ViewHolder holder = (ViewHolder) view.getTag();

        // Cursorからデータを取り出します
        final int id = cursor.getInt(cursor.getColumnIndexOrThrow(PlaceManager.Place.KEY_ID));
        final String placeID = cursor.getString(cursor
                .getColumnIndexOrThrow(PlaceManager.Place.KEY_PLACE_ID));
        final String place = cursor.getString(cursor
                .getColumnIndexOrThrow(PlaceManager.Place.KEY_PLACE));
        final String url = cursor.getString(cursor
                .getColumnIndexOrThrow(PlaceManager.Place.KEY_URL));

        // 画面にセットします
        holder.id.setText("id=" + String.valueOf(id));
        holder.place_id.setText("place_id=" + placeID);
        holder.place.setText(place);
        holder.url.setText(url);
    }

    /**
     * @inheritDoc
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        // 新しくViewを作ります
        final View view = mInflater.inflate(R.layout.list_item, null);

        ViewHolder holder = new ViewHolder();
        holder.id = (TextView) view.findViewById(R.id.id);
        holder.place_id = (TextView) view.findViewById(R.id.place_id);
        holder.place = (TextView) view.findViewById(R.id.place);
        holder.url = (TextView) view.findViewById(R.id.url);

        // holder.id.setTextColor(Color.BLUE);
        // holder.place_id.setTextColor(Color.MAGENTA);
        // holder.place.setTextColor(Color.GREEN);
        // holder.url.setTextColor(Color.CYAN);

        view.setTag(holder);

        return view;
    }
}
