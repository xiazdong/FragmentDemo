package xiazdong.me.fragmentdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.btn_1)
    Button mButton1;
    @BindView(R.id.btn_2)
    Button mButton2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("[onCreate] BEGIN");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1: goToActivity(BasicActivity.class); break;
            case R.id.btn_2: goToActivity(CommunicateActivity.class);break;
        }
    }
    void goToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
