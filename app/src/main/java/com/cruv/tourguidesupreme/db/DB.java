package com.cruv.tourguidesupreme.db;

import android.content.res.Resources;

import com.cruv.tourguidesupreme.R;
import com.cruv.tourguidesupreme.bean.DbBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yotam on 9/17/15.
 */
public class DB {

    public static DbBean getDB(final Resources resources) {
        final InputStream inputStream = resources.openRawResource(R.raw.db);
        byte[] json;
        try {
            json = new byte[inputStream.available()];
            inputStream.read(json);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        return new Gson().fromJson(new String(json), DbBean.class);
    }
}
