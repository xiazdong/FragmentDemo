package xiazdong.me.fragmentdemo;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import xiazdong.me.fragmentdemo.fragment.ProgressDialogFragment;

public class DialogActivity extends AppCompatActivity {

    @BindView(R.id.btn_dialog)
    Button mDialogBtn;

    ProgressDialogFragment fragment = ProgressDialogFragment.newInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
        mDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.show(getSupportFragmentManager(), "progressDialog");
                new Thread() {
                    public void run() {
                        SystemClock.sleep(5000);
                        if (fragment == null) {
                            fragment = (ProgressDialogFragment) getSupportFragmentManager().findFragmentByTag("progressDialog");
                        }
                        fragment.dismiss();
                    }
                }.start();
            }
        });

    }
}
