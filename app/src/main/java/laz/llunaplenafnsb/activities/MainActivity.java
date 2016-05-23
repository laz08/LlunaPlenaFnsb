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
import android.util.Log;
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
    public static final String CURRENT_FRAGMENT = "CURRENT_FRAGMENT";

    @Bind(R.id.drawer)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.navigation_view)
    NavigationView mNavigationView;

    private Fragment mCurrentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initialize(savedInstanceState);
    }

    /**
     * Initializes the activity.
     *
     * @param savedInstanceState Saved instance state.
     */
    private void initialize(Bundle savedInstanceState) {


        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                return onItemDrawerSelected(item);
            }
        });

        if (savedInstanceState != null) {

            mCurrentFragment = getSupportFragmentManager().getFragment(savedInstanceState, CURRENT_FRAGMENT);
        } else {

            changeToHomeFeedFragment();
        }
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
                return false;

            case R.id.drawer_search:
                startSearchActivity();
                mDrawerLayout.closeDrawers();
                return false;

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

        Fragment fmtById = manager.findFragmentById(R.id.fgmt_container);
        if (!(fmtById instanceof AboutFragment)) {

            FragmentTransaction fmtTransaction = manager.beginTransaction();

            AboutFragment instance = AboutFragment.getInstance();
            mCurrentFragment = instance;
            fmtTransaction.replace(R.id.fgmt_container, instance);
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

            HomeFeedFragment homeFmt = HomeFeedFragment.getInstance();
            mCurrentFragment = homeFmt;

            if (manager.getBackStackEntryCount() != 0) {

                Log.v(TAG, "Popping");
                manager.popBackStack();
            } else {

                FragmentTransaction fmtTransaction = manager.beginTransaction();

                fmtTransaction.add(R.id.fgmt_container, homeFmt);
                fmtTransaction.commit();
            }
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
            FragmentManager manager = getSupportFragmentManager();
            Fragment fmt = manager.findFragmentById(R.id.fgmt_container);
            for (int i = 0; i < mNavigationView.getMenu().size(); i++) {

                mNavigationView.getMenu().getItem(0).setChecked(false);
            }

            if (fmt instanceof HomeFeedFragment) {
                mNavigationView.getMenu().getItem(0).setChecked(true);
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        FragmentManager manager = getSupportFragmentManager();
        if(mCurrentFragment.isAdded()){

            manager.putFragment(outState, CURRENT_FRAGMENT, mCurrentFragment);
        }
    }
}
