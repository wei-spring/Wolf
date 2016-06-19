package me.chunsheng.wolf.hall;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import me.chunsheng.wolf.R;

public class WolfHallActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {


    private ViewPager contentPager;
    private mPagerAdapter adapter;
    private PagerSlidingTabStrip tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wolf_hall);

        setPager();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "直播功能,即将上线,敬请期待", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private void setPager() {
        contentPager = (ViewPager) findViewById(R.id.content_pager);
        contentPager.setOnPageChangeListener(this);
        adapter = new mPagerAdapter(getSupportFragmentManager());
        contentPager.setAdapter(adapter);
        contentPager.setOffscreenPageLimit(4);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(contentPager);
    }

    private class mPagerAdapter extends FragmentStatePagerAdapter {

        private String Title[] = {"   热门   ", "   最新  ", "   狼榜   ", "   游戏   "};

        public mPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return HotFragment.newInstance(arg0);
        }

        @Override
        public int getCount() {
            return Title.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Title[position];
        }

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int position) {
        getActionBar().setSelectedNavigationItem(position);
    }
}
