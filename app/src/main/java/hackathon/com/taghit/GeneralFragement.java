package hackathon.com.taghit;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class GeneralFragement extends BaseFragment {


    public GeneralFragement() {
        // Required empty public constructor
    }

    public GeneralFragement( BaseFragment.PageFragmentListener listener) {
        mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.general_fragement, container, false);
//
//        Button buttonOne = (Button)view.findViewById(R.id.buttonTest);
//        buttonOne.setOnClickListener(new Button.OnClickListener() {
//            public void onClick(View v) {
////                DBReader reader = new DBReader();
////                reader.readWhatsappMessage();
//            }
//        });

        // Inflate the layout for this fragment
        return view;
    }

    public static GeneralFragement newInstance( ) {
        return new GeneralFragement();
    }
}
