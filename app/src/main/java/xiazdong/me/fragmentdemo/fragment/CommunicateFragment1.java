package xiazdong.me.fragmentdemo.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zhy.fabridge.lib.Fabridge;

import xiazdong.me.fragmentdemo.R;

public class CommunicateFragment1 extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    public static final String FAB_ITEM_CLICK = "fab_item_click";
    private String mParam1;
    private OnFragmentInteractionListener mListener;
    private Activity mActivity;

    public static CommunicateFragment1 newInstance(String param1) {
        CommunicateFragment1 fragment = new CommunicateFragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1); //activity向fragment传数据
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParam1 = getArguments().getString(ARG_PARAM1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_communicate_fragment1, container, false);
        Button button = (Button) view.findViewById(R.id.btn_comm);
        Button button2 = (Button) view.findViewById(R.id.btn_comm2);
        Button button3 = (Button) view.findViewById(R.id.btn_comm3);
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(mParam1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteraction("Fragment Data");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.toAnotherFragment("Frag1 data");
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fabridge.call(mActivity,FAB_ITEM_CLICK,"Frag1 data fab");
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            mActivity = (Activity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String str);

        void toAnotherFragment(String str);
    }
}
