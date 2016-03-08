package hackathon.com.taghit;

import android.support.v4.app.Fragment;

import java.util.List;

public class BaseFragment extends Fragment {

    private boolean mShowingChild;

    protected PageFragmentListener mListener;

    protected boolean isShowingChild() {
        return mShowingChild;
    }

    protected void setShowingChild(boolean showingChild) {
        mShowingChild = showingChild;
    }

    public interface PageFragmentListener {
        void onSwitchToNextFragment(String key, List<String> values);
    }

}

