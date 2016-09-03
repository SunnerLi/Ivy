package edu.sunner.ivy.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import edu.sunner.ivy.Constant;
import edu.sunner.ivy.MainFragment;
import edu.sunner.ivy.R;
import edu.sunner.ivy.chooseFrom4;
import edu.sunner.ivy.fragment.ListenFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNavigationDrawer();
        setMainFragment();
    }

    /*
        Set the navigation view and drawer view object
     */
    private void setNavigationDrawer() {
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        setupDrawerContent(mNavigationView);

        // http://stackoverflow.com/questions/28450066/show-the-three-lines-at-navigation-drawer
        drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    /*
        Set the main fragment to the main layout
     */
    private void setMainFragment() {
        Fragment fragment = new MainFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return practice(menuItem);
                    }
                });
    }

    // 3 lines...
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    // Jump to the particular fragment
    public boolean practice(MenuItem menuItem) {
        menuItem.setChecked(true);

        Fragment fragment = null;
        Bundle bundle = new Bundle();
        switch (menuItem.getItemId()) {
            case R.id.fundamental_mode:
                fragment = new chooseFrom4();
                bundle.putInt(Constant.MODE_KEY, Constant.FUNDAMENTAL);
                break;
            case R.id.advance_mode:
                fragment = new chooseFrom4();
                bundle.putInt(Constant.MODE_KEY, Constant.ADVANCE);
                break;
            case R.id.strengthen_mode:
                fragment = new chooseFrom4();
                bundle.putInt(Constant.MODE_KEY, Constant.STRENGTHEN);
                break;
            case R.id.listen_mode:
                fragment = new ListenFragment();
                bundle.putInt(Constant.MODE_KEY, Constant.LISTEN);
                break;
            default:
                Log.e("??", "error mode");
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
