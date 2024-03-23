package in.example.code.Chatsy.DiscoverElements;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

//import in.example.code.Chatsy.GlobalStory.GlobalDiscover;


public class DiscoverPageAdapter extends FragmentPagerAdapter {




    public DiscoverPageAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

        switch(position) {








            default:
                return  null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Feeds";

            case 1:
                return "Messages";

            default:
                return null;
        }
    }


}
