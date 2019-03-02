package com.appgramming.temporedit;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends Activity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read and apply the theme setting
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        final String themeName = pref.getString(getString(R.string.pref_theme_key), getString(R.string.pref_theme_entry_value_light));
        if (!themeName.equals(getString(R.string.pref_theme_entry_value_light)))
            setTheme(SettingsHelper.parseTheme(this, themeName));

        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.edit_text);

        // Set the default preferences, and load first preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);


    }

    /**
     * Loads preferences when the activity is resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadPreferences();
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
            case R.id.action_clear:
                mEditText.getText().clear();
                return true;
            case R.id.action_paste:
                String clipText = SystemUtils.pasteTextFromClipboard(this);
                if (clipText != null) mEditText.setText(clipText);
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadPreferences() {
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        // Apply font typeface and style settings
        final String typefaceName = pref.getString(getString(R.string.pref_typeface_key), getString(R.string.pref_typeface_default));
        final String styleName = pref.getString(getString(R.string.pref_font_style_key), getString(R.string.pref_font_style_default));
        mEditText.setTypeface(SettingsHelper.parseFontTypeface(typefaceName), SettingsHelper.parseFontStyle(styleName));

        // Apply font size setting
        final String fontSizeString = pref.getString(getString(R.string.pref_font_size_key), getString(R.string.pref_font_size_default));
        mEditText.setTextSize(Integer.parseInt(fontSizeString));

        final String themeName = pref.getString(getString(R.string.pref_theme_key), getString(R.string.pref_theme_entry_value_light));
        TypedValue outValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.themeName, outValue, true);
        if(!outValue.string.equals(themeName)){
            Log.e("outValue", String.valueOf(outValue.string));
            Log.e("themeName", themeName);
            recreate();
        }
    }
}
