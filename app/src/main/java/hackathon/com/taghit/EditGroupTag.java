package hackathon.com.taghit;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import hackathon.com.taghit.model.GroupsTags;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditGroupTag extends BaseFragment implements ImageButton.OnClickListener{

    String mGroupName = "";
    List<String> mTags;
    ArrayAdapter<String> adapter;
    ListView mTagsLst;
    TextView mGroupNameTxt;
    EditText mNewTagTxt;

    public EditGroupTag() {
        // Required empty public constructor
    }

    public EditGroupTag(String key, List<String> values) {
        // Required empty public constructor
        setData(key, values);
    }

    public void setData(String key, List<String> values)
    {
        mGroupName = key;
        mTags = values;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View returnView =  inflater.inflate(R.layout.edit_group_tag_fragement, container, false);
        // Inflate the layout for this fragment
        mGroupNameTxt = (TextView)returnView.findViewById(R.id.groupName);
        mGroupNameTxt.setText(mGroupName);

        mTagsLst = (ListView)returnView.findViewById(R.id.listTags);
        List<String> tags = GroupsTags.getTags(mGroupName);
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, tags);
        mTagsLst.setAdapter(adapter);

        ImageButton addButton = (ImageButton)returnView.findViewById(R.id.fab);
        addButton.setOnClickListener(this);

        return returnView;
    }

    @Override
    public void onClick(View v) {
        initiatePopupWindow();
//        EditText mNewTagTxt = (EditText)getActivity().findViewById(R.id.newTagText);
//        GroupsTags.addTag(mGroupName, mNewTagTxt.getText().toString());
    }

    private PopupWindow pw;
    private void initiatePopupWindow() {
        try {
            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater)getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.add_tag_popup,
                    (ViewGroup)getActivity().findViewById(R.id.popup_element));
            // create a 300px width and 470px height PopupWindow
            pw = new PopupWindow(layout, 500, 370, true);
            // display the popup in the center
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);

            mNewTagTxt = (EditText) layout.findViewById(R.id.new_tag_text);
            Button addButton = (Button) layout.findViewById(R.id.add_button_popup);
            //makeBlack(addButton);
            addButton.setOnClickListener(cancel_button_click_listener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            GroupsTags.addTag(mGroupName, mNewTagTxt.getText().toString());
            pw.dismiss();
        }
    };


}
