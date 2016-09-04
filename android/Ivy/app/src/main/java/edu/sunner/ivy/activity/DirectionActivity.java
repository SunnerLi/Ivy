package edu.sunner.ivy.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import edu.sunner.ivy.ListAdapter.DirectionAdapter;
import edu.sunner.ivy.R;
import edu.sunner.ivy.direction_fragment.AdvanceDirectFragment;
import edu.sunner.ivy.direction_fragment.ListenDirectFragment;
import edu.sunner.ivy.direction_fragment.StrengthenDirectFragment;
import edu.sunner.ivy.fragment.FundamentalDirectFragment;

/**
 * Created by sunner on 9/3/16.
 */
public class DirectionActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DirectionAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        initViewPager();

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /*
        Initial view pager and tab layout
     */
    private void initViewPager() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<String> titles = new ArrayList<String>();
        for (int i = 0; i < 4; i++)
            titles.add(" ");

        setupViewPager();
        setupTablayout(titles);
    }

    public void setupViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
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

    public void setupTablayout(List<String> titles) {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);

        //for (int i = 0; i < titles.size(); i++)
        //    tabLayout.addTab(tabLayout.newTab().setText(titles.get(i)));
        for (int i = 0; i < tabLayout.getTabCount(); i++)
            tabLayout.getTabAt(i).setIcon(R.drawable.icon_advance_black_small);

        //给TabLayout设置适配器
        //tabLayout.setTabsFromPagerAdapter(adapter);
    }
}
