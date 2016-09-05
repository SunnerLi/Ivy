package edu.sunner.ivy.activity;

// http://www.cnblogs.com/bluesky4485/archive/2011/11/30/2269198.html
// https://gist.github.com/ownwell/c32878440216f1866842

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import edu.sunner.ivy.Constant;
import edu.sunner.ivy.R;
import edu.sunner.ivy.fragment.ChooseFromFourFragment;
import edu.sunner.ivy.fragment.ListenFragment;
import edu.sunner.ivy.fragment.MainFragment;

/**
 * This Activity is the first activity that the program would enter
 *
 * @author sunner
 * @since 9/3/16.
 */
public class MainActivity extends AppCompatActivity {
    // View & layout object
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    // The drawerToggle object.
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNavigationDrawer();
        setMainFragment();
    }

    /**
     * Set the navigation view and drawer view object.
     */
    private void setNavigationDrawer() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        setupDrawerContent(navigationView);

        // http://stackoverflow.com/questions/28450066/show-the-three-lines-at-navigation-drawer
        drawerToggle = new ActionBarDrawerToggle(this,
            drawerLayout, toolbar,
            R.string.drawer_open,
            R.string.drawer_close);
    }

    /**
     * Set the main fragment to the main layout.
     */
    private void setMainFragment() {
        Fragment fragment = new MainFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    /**
     * Implement the navigation listener.
     *
     * @param navigationView The navigation view object
     */
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    menuItem.setChecked(true);
                    drawerLayout.closeDrawers();
                    return practice(menuItem);
                }
            });
    }

    /**
     * Override this method to show the three-line pattern.
     * You can open the drawer layout by pressing this pattern
     *
     * @param savedInstanceState The override object
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    /**
     * Jump to the particular fragment.
     *
     * @param menuItem The override object
     * @return always true
     */
    public boolean practice(final MenuItem menuItem) {
        menuItem.setChecked(true);

        Fragment fragment = null;
        Bundle bundle = new Bundle();
        switch (menuItem.getItemId()) {
            case R.id.fundamental_mode:
                fragment = new ChooseFromFourFragment();
                bundle.putInt(Constant.MODE_KEY, Constant.FUNDAMENTAL);
                break;
            case R.id.advance_mode:
                fragment = new ChooseFromFourFragment();
                bundle.putInt(Constant.MODE_KEY, Constant.ADVANCE);
                break;
            case R.id.strengthen_mode:
                fragment = new ChooseFromFourFragment();
                bundle.putInt(Constant.MODE_KEY, Constant.STRENGTHEN);
                break;
            case R.id.listen_mode:
                fragment = new ListenFragment();
                bundle.putInt(Constant.MODE_KEY, Constant.LISTEN);
                break;
            case R.id.quit:
                finish();
                return true;
            case R.id.How:
                try {
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this,
                                DirectionActivity.class);
                            startActivity(intent);
                        }
                    }.start();
                    return true;
                } catch (NullPointerException err) {
                    err.printStackTrace();
                    break;
                }

            default:
                Log.e("MAY_TAG", "error mode");
        }
        fragment.setArguments(bundle);

        // Jump to the fragment
        setTitle(menuItem.getTitle());
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

        return true;
    }

    @Override
    public void onBackPressed() {
        // Forbid back press
    }
}
