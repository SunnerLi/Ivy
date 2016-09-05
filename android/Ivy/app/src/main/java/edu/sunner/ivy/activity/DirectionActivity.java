package edu.sunner.ivy.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import edu.sunner.ivy.R;
import edu.sunner.ivy.directionfragment.AdvanceDirectFragment;
import edu.sunner.ivy.directionfragment.FundamentalDirectFragment;
import edu.sunner.ivy.directionfragment.ListenDirectFragment;
import edu.sunner.ivy.directionfragment.StrengthenDirectFragment;
import edu.sunner.ivy.listadapter.DirectionAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * This activity contain the implementation of direction fragments
 * It contain AdvanceDirectFragment, FundamentalDirectFragment,
 * ListenDirecFragment and StrengthenDirectFragment.
 *
 * @author sunner
 * @since 9/3/16.
 */
public class DirectionActivity extends AppCompatActivity {
    // View object
    private TabLayout tabLayout;
    private ViewPager viewPager;

    // The direction adapter object.
    private DirectionAdapter adapter;

    // The image source array.
    private Integer[] imageId = {
        R.drawable.ic_directions_walk_black_big_24dp,
        R.drawable.ic_school_black_big_24dp,
        R.drawable.ic_trending_up_black_big_24dp,
        R.drawable.ic_hearing_black_big_24dp
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        initViewPager();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setImageResource(R.drawable.ic_arrow_back_black_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Initial view pager and tab layout.
     */
    private void initViewPager() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<String> titles = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            titles.add(" ");
        }

        setupViewPager();
        setupTablayout(titles);
    }

    /**
     * Set up the view pager (include add the fragment and set the adapter).
     */
    public void setupViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        adapter = new DirectionAdapter(getSupportFragmentManager());
        adapter.addFrag(new FundamentalDirectFragment(), "1");
        adapter.addFrag(new AdvanceDirectFragment(), "2");
        adapter.addFrag(new StrengthenDirectFragment(), "3");
        adapter.addFrag(new ListenDirectFragment(), "4");
        viewPager.setAdapter(adapter);
    }

    /**
     * Set the lable layout, add the icon tabs.
     *
     * @param titles The array contain the name of the title
     */
    public void setupTablayout(List<String> titles) {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);

        /*
        for (int i = 0; i < titles.size(); i++)
            tabLayout.addTab(tabLayout.newTab().setText(titles.get(i)));
        */
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(imageId[i]);
        }

        //给TabLayout设置适配器
        //tabLayout.setTabsFromPagerAdapter(adapter);
    }
}
