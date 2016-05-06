package laz.llunaplenafnsb.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.helper.NotificationHelper;
import laz.llunaplenafnsb.notification.NotificationEventReceiver;
import laz.llunaplenafnsb.pref.PreferencesManager;

/**
 * Settings activity.
 */
public class SettingsActivity extends AppCompatActivity {

    public static final String TAG = "SettingsActivity";

    @Bind(R.id.check_upd_checkb)
    CheckBox mCheckForUpdates;

    @Bind(R.id.radioGroup)
    RadioGroup mRadioGroup;

    @Bind(R.id.freq_1_h)
    RadioButton mFreq1H;

    @Bind(R.id.freq_3_h)
    RadioButton mFreq3H;

    @Bind(R.id.freq_6_h)
    RadioButton mFreq6H;

    @Bind(R.id.freq_12_h)
    RadioButton mFreq12H;

    @Bind(R.id.freq_24_h)
    RadioButton mFreq1Day;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            actionBar.setTitle(getResources().getText(R.string.config));
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ButterKnife.bind(this);

        boolean checkForUpdates = PreferencesManager.getCheckForUpdates(this);
        mCheckForUpdates.setChecked(checkForUpdates);
        mCheckForUpdates.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                toggleCheckForUpdates();
            }
        });

        configureRadioGroup(checkForUpdates);
        loadFrequencyOfUpdates();

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                storeChosenFrequency(i);
            }
        });
    }


    /**
     * Stores chosen frequency.
     *
     * @param id Radio button id.
     */
    private void storeChosenFrequency(int id) {

        Log.v(TAG, "Storing chosen frequency");
        switch (id) {

            case R.id.freq_1_h:
                PreferencesManager.storeFrequencyCheckUpdates(this, HOUR_1);
                break;

            case R.id.freq_3_h:
                PreferencesManager.storeFrequencyCheckUpdates(this, HOUR_3);
                break;

            case R.id.freq_6_h:
                PreferencesManager.storeFrequencyCheckUpdates(this, HOUR_6);
                break;

            case R.id.freq_12_h:
                PreferencesManager.storeFrequencyCheckUpdates(this, HOUR_12);
                break;

            case R.id.freq_24_h:
                PreferencesManager.storeFrequencyCheckUpdates(this, HOUR_24);
                break;
        }

        NotificationEventReceiver.cancelAlarm(this);
        NotificationEventReceiver.setUpAlarm(this);

    }

    /**
     * Loads the preferred frequency for updates check.
     */
    private void loadFrequencyOfUpdates() {

        int freq = PreferencesManager.getFrequencyCheckUpdates(this);
        switch (freq) {

            case HOUR_1:
                mFreq1H.setChecked(true);
                break;

            case HOUR_3:
                mFreq3H.setChecked(true);
                break;

            case HOUR_6:
                mFreq6H.setChecked(true);
                break;

            case HOUR_12:
                mFreq12H.setChecked(true);
                break;

            case HOUR_24:
                mFreq1Day.setChecked(true);
                break;
        }
    }

    /**
     * Toggles check for updates value.
     */
    private void toggleCheckForUpdates() {

        boolean isChecked = mCheckForUpdates.isChecked();

        PreferencesManager.setCheckForUpdates(this, isChecked);
        configureRadioGroup(isChecked);

        if (isChecked) {

            NotificationHelper.enableAutoCheck(this);
            NotificationEventReceiver.setUpAlarm(this);
        } else {

            NotificationHelper.disableAutoCheckUpdates(this);
            NotificationEventReceiver.cancelAlarm(this);
        }
    }

    /**
     * Configures radio group.
     *
     * @param checkForUpdates Check for updates.
     */
    private void configureRadioGroup(boolean checkForUpdates) {

        for (int i = 0; i < mRadioGroup.getChildCount(); i++) {

            mRadioGroup.getChildAt(i).setEnabled(checkForUpdates);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private static final int HOUR_1 = 1;
    private static final int HOUR_3 = 3;
    private static final int HOUR_6 = 6;
    private static final int HOUR_12 = 12;
    private static final int HOUR_24 = 24;
}
