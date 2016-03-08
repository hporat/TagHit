package hackathon.com.taghit;


import android.app.ListFragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragement extends BaseFragment implements
        AdapterView.OnItemClickListener {

    public ContactsFragement() {
        // Required empty public constructor
    }

    public ContactsFragement( BaseFragment.PageFragmentListener listener) {
        mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.contacts_fragement, container, false);

        ListView lstItems = (ListView)view.findViewById(R.id.listView);

        Cursor cursor1 = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        getActivity().startManagingCursor(cursor1);

        String [] from = {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone._ID
        };
        int [] to = {
                android.R.id.text1,
                android.R.id.text2
        };

        SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_2, cursor1, from, to);

        lstItems.setAdapter(listAdapter);
//        lv = getListView();
        lstItems.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lstItems.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println();
    }

    public static ContactsFragement newInstance( BaseFragment.PageFragmentListener listener) {
        return new ContactsFragement(listener);
    }

}
