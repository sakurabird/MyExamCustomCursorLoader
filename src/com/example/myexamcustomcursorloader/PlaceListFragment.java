
package com.example.myexamcustomcursorloader;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myexamcustomcursorloader.data.PlaceManager;

/**
 * CursorLoaderを使ってテーブルのデータをListViewに表示します
 * 
 * @author sakura
 */
public class PlaceListFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private ListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // adapterをセットします
        mAdapter = new ListAdapter(getActivity(), null, true);
        setListAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        // Loaderの初期化
        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Loaderの廃棄
        getLoaderManager().destroyLoader(0);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        // CursorLoader生成（検索条件の指定）
        // ここでは全件取得
        return new CursorLoader(this.getActivity(),
                PlaceManager.Place.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // 画面を更新する
        mAdapter.swapCursor(cursor);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

}
