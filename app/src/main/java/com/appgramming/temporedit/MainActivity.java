package com.appgramming.temporedit;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toolbar;

public class MainActivity extends Activity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.edit_text);

        setActionBar((Toolbar) findViewById(R.id.toolbar));
        getActionBar().setDisplayShowTitleEnabled(false);
    }

    /**
     * Inflate the menu items for use in the action bar.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_copy:
                SystemUtils.copyToClipboard(this, getString(R.string.app_name), String.valueOf(mEditText.getText()));
                return true;
            case R.id.action_cut:
                SystemUtils.copyToClipboard(this, getString(R.string.app_name), String.valueOf(mEditText.getText()));
                mEditText.getText().clear();
                return true;
            case R.id.action_share:
                SystemUtils.shareText(this, String.valueOf(mEditText.getText()), getString(R.string.share_title));
                return true;
            case R.id.action_paste:
                String clipText = SystemUtils.pasteTextFromClipboard(this);
                if (clipText != null) mEditText.setText(clipText);
                return true;

            case R.id.action_undo:
//                mEditText.dispatchKeyEvent(new KeyEvent(0, 0, KeyEvent.ACTION_DOWN,
//                        KeyEvent.KEYCODE_Z, 0, KeyEvent.META_CTRL_LEFT_ON));
//                mEditText.dispatchKeyEvent(new KeyEvent(0, 0, KeyEvent.ACTION_UP,
//                        KeyEvent.KEYCODE_Z, 0, KeyEvent.META_CTRL_LEFT_ON));
//                mEditText.dispatchKeyEvent(keyEvent(KeyEvent.KEYCODE_V, KeyEvent.META_CTRL_ON | KeyEvent.META_CTRL_LEFT_ON));
                mEditText.dispatchKeyEvent(keyEvent(KeyEvent.KEYCODE_Z, 0));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public static KeyEvent keyEvent(int keycode, int metaState) {
        final long currentTime = System.currentTimeMillis();
        return new KeyEvent(currentTime, currentTime, KeyEvent.ACTION_DOWN, keycode, 0, metaState);
    }
}