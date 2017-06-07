package xiazdong.me.fragmentdemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xiazdong.me.fragmentdemo.R;

public class CommunicateFragment2 extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    private TextView mTextView;

    public static CommunicateFragment2 newInstance(String param1) {
        CommunicateFragment2 fragment = new CommunicateFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1); //activity向fragment传数据
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mParam1 = getArguments().getString(ARG_PARAM1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_communicate_fragment2, container, false);
        mTextView = (TextView) view.findViewById(R.id.text);
        mTextView.setText(mParam1);
        return view;
    }

    public void receiveData(String str) {
        mTextView.setText(str);
    }
}
