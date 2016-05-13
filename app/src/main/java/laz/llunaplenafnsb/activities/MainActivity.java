package laz.llunaplenafnsb.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.fragment.AboutFragment;
import laz.llunaplenafnsb.fragment.HomeFeedFragment;

/**
 * Main activity.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Bind(R.id.drawer)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.navigation_view)
    NavigationView mNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    /**
     * Initializes the activity.
     */
    private void initialize() {

        ButterKnife.bind(this);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                return onItemDrawerSelected(item);
            }
        });

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fmtTransaction = manager.beginTransaction();

        HomeFeedFragment homeFmt = new HomeFeedFragment();
        fmtTransaction.add(R.id.fgmt_container, homeFmt);
        fmtTransaction.commit();
    }


    /**
     * On item drawer selected.
     *
     * @param item Menu item selected.
     * @return Returns true if it has managed the click on the item. False otherwise.
     */

    private boolean onItemDrawerSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {

            case R.id.drawer_home:
                changeToHomeFeedFragment();
                mDrawerLayout.closeDrawers();
                return true;

            case R.id.drawer_forum:

                openForum();
                mDrawerLayout.closeDrawers();
                return true;

            case R.id.drawer_config:

                startSettingsActivity();
                mDrawerLayout.closeDrawers();
                return true;

            case R.id.drawer_search:
                startSearchActivity();
                mDrawerLayout.closeDrawers();
                return true;

            case R.id.drawer_about:
                changeToAboutFragment();
                mDrawerLayout.closeDrawers();
                return true;

            case R.id.drawer_dev:
                //TODO: Start about developer screen


            default:
                mDrawerLayout.closeDrawers();
        }
        return false;
    }

    /**
     * Replaces current fragment to about.
     */
    private void changeToAboutFragment() {

        FragmentManager manager = getSupportFragmentManager();

        Fragment fmt = manager.findFragmentById(R.id.fgmt_container);
        if (!(fmt instanceof AboutFragment)) {

            FragmentTransaction fmtTransaction = manager.beginTransaction();

            AboutFragment aboutFmt = new AboutFragment();
            fmtTransaction.replace(R.id.fgmt_container, aboutFmt);
            fmtTransaction.addToBackStack(null);
            fmtTransaction.commit();
        }
    }

    /**
     * Replaces current fragment to about.
     */
    private void changeToHomeFeedFragment() {

        FragmentManager manager = getSupportFragmentManager();
        Fragment fmt = manager.findFragmentById(R.id.fgmt_container);
        if (!(fmt instanceof HomeFeedFragment)) {

            FragmentTransaction fmtTransaction = manager.beginTransaction();

            HomeFeedFragment homeFmt = new HomeFeedFragment();
            fmtTransaction.replace(R.id.fgmt_container, homeFmt);
            fmtTransaction.commit();
        }
    }

    /**
     * Starts search activity.
     */
    private void startSearchActivity() {

        Intent searchAct = new Intent(this, SearchActivity.class);
        startActivity(searchAct);
    }

    /**
     * Opens forum.
     */
    private void openForum() {

        String forumUrl = getResources().getString(R.string.forum_url);

        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse(forumUrl));
        startActivity(browserIntent);
    }


    /**
     * Starts settings activity.
     */
    private void startSettingsActivity() {

        Intent settingsAct = new Intent(this, SettingsActivity.class);
        startActivity(settingsAct);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {

            mDrawerLayout.closeDrawers();
        } else {

            super.onBackPressed();
        }
    }


    @Override
    protected void onResume() {

        super.onResume();
        mNavigationView.setCheckedItem(R.id.drawer_home);
    }
}
