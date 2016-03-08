package hackathon.com.taghit;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import hackathon.com.taghit.model.GroupsTags;

public class MainActivity extends  AppCompatActivity {

    private NotificationReceiver nReceiver;
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GroupsTags.load();
        System.out.println("MainActivity onCreate() has been initialized");
        setContentView(R.layout.activity_main);
        nReceiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.tagHit");
        filter.addAction(ACTION_NOTIFICATION_LISTENER_SETTINGS);
        registerReceiver(nReceiver, filter);


        toolbar = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        ArrayList<String> titles = new ArrayList<String>();
        titles.add("General");titles.add("Groups");titles.add("Contacts");

        viewPagerAdapter.setTabTitles(titles);
//        viewPagerAdapter.addFragment(new GeneralFragement(), "General");
//        viewPagerAdapter.addFragment(new GroupsFragement(), "Groups");
//        viewPagerAdapter.addFragment(new ContactsFragement(), "Contacts");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onDestroy() {
        GroupsTags.save();
        super.onDestroy();
        unregisterReceiver(nReceiver);
    }

    /**
     * Identifies whether the back is called form a child fragment or a parent fragment
     * and takes necessary steps then.
     */
    @Override
    public void onBackPressed() {
        Fragment fragment = (Fragment) getSupportFragmentManager().
                findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + viewPager.getCurrentItem());

        if (fragment != null && fragment instanceof BaseFragment) // could be null if not instantiated yet
        {
            if (fragment.getView() != null) {
                BaseFragment bf = (BaseFragment)fragment;
                if(bf.isShowingChild()) {
                    replaceChild(bf, viewPager.getCurrentItem());
                }
                else {
                    backButton();
                }
            }
        }
    }
    // Back Button Pressed
    private void backButton() {
        finish();
    }
    public void replaceChild(BaseFragment oldFrg, int position) {
        viewPagerAdapter.replaceChildFragment(oldFrg, position);
    }
    class NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
