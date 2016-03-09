package hackathon.com.taghit;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
                android.R.layout.simple_list_item_1, tags)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.parseColor("#393A3B"));

                return textView;
            }

        };
        mTagsLst.setAdapter(adapter);

        registerForContextMenu(mTagsLst);

        FloatingActionButton addButton = (FloatingActionButton)returnView.findViewById(R.id.addTagButton);
        addButton.setOnClickListener(this);

        return returnView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,  ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        Object item = adapter.getItem(info.position);

        menu.setHeaderTitle(item.toString());
        menu.add(0, v.getId(), 0, "Delete");

//        MenuInflater inflater = getActivity().getMenuInflater();
//        inflater.inflate(R.menu.tag_context_menu, menu);
    }

    private int getAdapterItemPosition(long id)
    {
        for (int position=0; position<adapter.getCount(); position++)
            if (adapter.getItemId(position) == id)
                return position;
        return 0;
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + item.getItemId());
        System.out.println("!!!!!!!!!!" + adapter.getItem(getAdapterItemPosition(item.getItemId())));

        String tagName2Delete = adapter.getItem(getAdapterItemPosition(item.getItemId()));
        GroupsTags.getTags(mGroupName).remove(tagName2Delete);
        adapter.notifyDataSetChanged();
        return super.onContextItemSelected(item);
//        if (item.getTitle() == "Delete") {
//            mTagsLst.get
//        }
//            deleteContact(item.getItemId());
//
//
//        switch (item.getItemId()) {
//            case R.id.tag_delete:
//                Object selectedTag = mTagsLst.getSelectedItem();
//                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//               // GroupsTags.getTags()
////                mAdapter.remove(info.position);
//                return true;
//            default:
//                return super.onContextItemSelected(item);
//        }
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
                    (ViewGroup)getActivity().findViewById(R.id.popup_add_tag));
            // create a 300px width and 470px height PopupWindow
            pw = new PopupWindow(layout, 500, 470, true);
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
