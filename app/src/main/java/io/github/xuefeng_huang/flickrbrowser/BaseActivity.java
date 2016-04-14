package io.github.xuefeng_huang.flickrbrowser;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Lenovo on 4/14/2016.
 */
public class BaseActivity extends ActionBarActivity {
    private Toolbar mToolBar;

    protected Toolbar activateToolBar() {
        if (mToolBar == null) {
            mToolBar = (Toolbar) findViewById(R.id.app_bar);
            if (mToolBar != null) {
                setSupportActionBar(mToolBar);
            }
        }
        return mToolBar;
    }
}
