package hackathon.com.taghit;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.app.Activity;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.*;

import hackathon.com.taghit.model.GroupsTags;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupsFragement extends BaseFragment implements
        AdapterView.OnItemClickListener, View.OnClickListener {

    ArrayAdapter<String> mGroupsListAdapter;
    List<String> mGroupsList;
    EditText mNewGroupTxt;

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

        View returnView = inflater.inflate(R.layout.groups_fragement, container, false);

        ListView lstItems = (ListView)returnView.findViewById(R.id.listGrpView);
        mGroupsList = GroupsTags.getGroups();
        mGroupsListAdapter = new GroupsListAdapter(getActivity(), mGroupsList);
        lstItems.setAdapter(mGroupsListAdapter);
        lstItems.setOnItemClickListener(this);

        FloatingActionButton addButton = (FloatingActionButton)returnView.findViewById(R.id.addGroupButton);
        addButton.setOnClickListener(this);

        return returnView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //String selectedGrpName = ((AppCompatTextView) view).getText().toString();
        String selectedGrpName = mGroupsList.get(+position);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!" + selectedGrpName);

        if(mListener != null) {
            mListener.onSwitchToNextFragment(selectedGrpName, null);
        }

    }

    public static GroupsFragement newInstance( BaseFragment.PageFragmentListener listener) {
        return new GroupsFragement(listener);
    }

    @Override
    public void onClick(View v) {
        initiatePopupWindow();
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

            imageView.setImageResource(R.drawable.group2);
            return rowView;
        }
    }

    private PopupWindow pw;
    private void initiatePopupWindow() {
        try {
            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater)getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.add_group_popup,
                    (ViewGroup)getActivity().findViewById(R.id.popup_add_group));
            // create a 300px width and 470px height PopupWindow
            pw = new PopupWindow(layout, 500, 370, true);
            // display the popup in the center
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);

            mNewGroupTxt = (EditText) layout.findViewById(R.id.new_group_text);
            Button addButton = (Button) layout.findViewById(R.id.add_group_button_popup);
            addButton.setOnClickListener(add_button_click_listener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener add_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            GroupsTags.addGroup(mNewGroupTxt.getText().toString());
            pw.dismiss();
        }
    };

}
