
package com.example.myexamcustomcursorloader;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.example.myexamcustomcursorloader.data.Place;
import com.example.myexamcustomcursorloader.data.PlaceManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 元のテストデータ消す
        getContentResolver().delete(PlaceManager.Place.CONTENT_URI, null, null);
        // テストデータ生成(本来はDB処理は非同期にやるのだがサンプルだし手抜き)
        List<Place> places = new ArrayList<Place>();
        Place place = new Place("1", "北海道", "http://www.pref.hokkaido.lg.jp/");
        places.add(place);
        place = new Place("2", "群馬", "http://www.pref.gunma.jp/");
        places.add(place);
        place = new Place("3", "岡山", "http://www.pref.okayama.jp/");
        places.add(place);

        ContentValues values = new ContentValues();
        for (Place p : places) {
            values.clear();
            values.put(PlaceManager.Place.KEY_PLACE_ID, p.getPlaceID());
            values.put(PlaceManager.Place.KEY_PLACE, p.getPlace());
            values.put(PlaceManager.Place.KEY_URL, p.getUrl());
            getContentResolver()
                    .insert(PlaceManager.Place.CONTENT_URI, values);
        }

        // Fragmentの生成
        Fragment placeListFragment = new PlaceListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.LinearLayout01, placeListFragment, "placeListFragment");
        transaction.commit();
    }
}
