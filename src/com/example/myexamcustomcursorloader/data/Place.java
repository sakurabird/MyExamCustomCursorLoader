
package com.example.myexamcustomcursorloader.data;

public class Place {
    /** ID */
    int _id;
    /** 場所ID */
    String _place_id;
    /** 場所名 */
    String _place;
    /** 場所ページへのURL */
    String _url;

    public Place() {
    }

    public Place(int id, String place_id, String place, String url) {
        this._id = id;
        this._place_id = place_id;
        this._place = place;
        this._url = url;
    }

    // constructor
    public Place(String place_id, String place, String url) {
        this._place_id = place_id;
        this._place = place;
        this._url = url;
    }

    public int getID() {
        return this._id;
    }

    public void setID(int id) {
        this._id = id;
    }

    public String getPlaceID() {
        return this._place_id;
    }

    public void setPlaceID(String place_id) {
        this._place_id = place_id;
    }

    public String getPlace() {
        return this._place;
    }

    public void setPlace(String place) {
        this._place = place;
    }

    public String getUrl() {
        return this._url;
    }

    public void setUrl(String url) {
        this._url = url;
    }
}
