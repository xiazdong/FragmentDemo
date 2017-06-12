package xiazdong.me.fragmentdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_1)
    Button mButton1;
    @BindView(R.id.btn_2)
    Button mButton2;
    @BindView(R.id.btn_3)
    Button mButton3;
    @BindView(R.id.btn_4)
    Button mButton4;
    @BindView(R.id.btn_5)
    Button mButton5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("[onCreate] BEGIN");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        mButton5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                goToActivity(BasicActivity.class);
                break;
            case R.id.btn_2:
                goToActivity(CommunicateActivity.class);
                break;
            case R.id.btn_3:
                goToActivity(Demo3Activity.class);
                break;
            case R.id.btn_4:
                goToActivity(DialogActivity.class);
                break;
            case R.id.btn_5:
                goToActivity(LazyActivity.class);
                break;
        }
    }

    void goToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
