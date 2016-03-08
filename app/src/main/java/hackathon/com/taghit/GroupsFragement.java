package hackathon.com.taghit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.app.Activity;
import android.widget.TextView;

import java.util.*;

import hackathon.com.taghit.model.GroupsTags;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupsFragement extends BaseFragment implements
        AdapterView.OnItemClickListener {

    ArrayAdapter<String> adapter;
    List<String> mGroups;

    public GroupsFragement() {
        // Required empty public constructor
    }

    public GroupsFragement( BaseFragment.PageFragmentListener listener) {
        mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmen

        View view = inflater.inflate(R.layout.groups_fragement, container, false);

        ListView lstItems = (ListView)view.findViewById(R.id.listGrpView);
        mGroups = GroupsTags.getGroups();
        adapter = new GroupsListAdapter(getActivity(), mGroups);
        lstItems.setAdapter(adapter);
        lstItems.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //String selectedGrpName = ((AppCompatTextView) view).getText().toString();
        String selectedGrpName = mGroups.get(+position);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!" + selectedGrpName);

        if(mListener != null) {
            mListener.onSwitchToNextFragment(selectedGrpName, null);
        }

    }

    public static GroupsFragement newInstance( BaseFragment.PageFragmentListener listener) {
        return new GroupsFragement(listener);
    }

    public class GroupsListAdapter extends ArrayAdapter<String>{
        private final Activity context;
        private final List<String> mGroupNames;
        public GroupsListAdapter(Activity context,
                                 List<String> groupNames) {
            super(context, R.layout.groups_fragement, groupNames);
            this.context = context;
            this.mGroupNames = groupNames;
        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView= inflater.inflate(R.layout.list_single, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.groupNameInList);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.groupImg);
            txtTitle.setText(mGroupNames.get(position));

            imageView.setImageResource(R.drawable.group1);
            return rowView;
        }
    }
}
