package hackathon.com.taghit;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 44031 on 3/6/2016.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> tabTitles = new ArrayList<>();

    private BaseFragment mFragmentAtPos1; // Fragment at index 1
    private BaseFragment mFragmentAtPos2; // Fragment at index 2

    private final FragmentManager mFragmentManager;

    public ViewPagerAdapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);
        mFragmentManager = fragmentManager;
    }

    public void addFragment(Fragment fragment, String title)
    {
        this.fragments.add(fragment);
        this.tabTitles.add(title);
    }

    public void setTabTitles(ArrayList<String> titles)
    {
        tabTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
//        return fragments.get(position);

        if(position == 1) {
            if (mFragmentAtPos1 == null) {
                mFragmentAtPos1 = GroupsFragement.newInstance(new BaseFragment.PageFragmentListener() {
                    public void onSwitchToNextFragment(String key, List<String> values) {
                        mFragmentManager.beginTransaction().remove(mFragmentAtPos1).commit();
                        mFragmentAtPos1 = new EditGroupTag(key, values);
                        mFragmentAtPos1.setShowingChild(true);
                        notifyDataSetChanged();
                    }
                });
            }
            return mFragmentAtPos1;
        }
        else if(position == 2) {
            if (mFragmentAtPos2 == null) {
                mFragmentAtPos2 = ContactsFragement.newInstance(new BaseFragment.PageFragmentListener() {
                    public void onSwitchToNextFragment(String key, List<String> values) {
//                        mFragmentManager.beginTransaction().remove(mFragmentAtPos2).commit();
//                        mFragmentAtPos2 = ChildFragment.newInstance(STR_CHILD_TAG_3);
//                        mFragmentAtPos2.setShowingChild(true);
                        notifyDataSetChanged();
                    }
                });
            }
            return mFragmentAtPos2;
        }

        return GeneralFragement.newInstance();
    }

    @Override
    public int getItemPosition(Object object)
    {
        if (object instanceof GroupsFragement && mFragmentAtPos1 instanceof EditGroupTag) {
            return POSITION_NONE;
        }
//        else if(object instanceof ContactsFragement && mFragmentAtPos2 instanceof ChildFragment) {
//            return POSITION_NONE;
//        }
        else if(object instanceof EditGroupTag) {
            return POSITION_NONE;
        }
        return POSITION_UNCHANGED;
    }

    @Override
    public int getCount() {
        return tabTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }

    public void replaceChildFragment(BaseFragment oldFrg, int position) {
        switch (position) {
            case 1:
                mFragmentManager.beginTransaction().remove(oldFrg).commit();
                mFragmentAtPos1 = GroupsFragement.newInstance(new BaseFragment.PageFragmentListener() {
                    public void onSwitchToNextFragment(String key, List<String> values) {
                        mFragmentManager.beginTransaction().remove(mFragmentAtPos1).commit();
                        mFragmentAtPos1 = new EditGroupTag(key, values);
                        //((EditGroupTag)mFragmentAtPos1).setData(key, values);
                        mFragmentAtPos1.setShowingChild(true);
                        notifyDataSetChanged();
                    }
                });
                notifyDataSetChanged();
                break;

            case 2:
                mFragmentManager.beginTransaction().remove(oldFrg).commit();
                mFragmentAtPos2 = ContactsFragement.newInstance(new BaseFragment.PageFragmentListener() {
                    public void onSwitchToNextFragment(String key, List<String> values) {
//                        mFragmentManager.beginTransaction().remove(mFragmentAtPos2).commit();
//                        mFragmentAtPos2 = ChildFragment.newInstance(STR_CHILD_TAG_3);
//                        mFragmentAtPos2.setShowingChild(true);
                        notifyDataSetChanged();
                    }
                });
                notifyDataSetChanged();
                break;

            default:
                break;
        }
    }


}
