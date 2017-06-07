package xiazdong.me.fragmentdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import xiazdong.me.fragmentdemo.fragment.CommunicateFragment1;
import xiazdong.me.fragmentdemo.fragment.CommunicateFragment2;

public class CommunicateActivity extends AppCompatActivity implements CommunicateFragment1.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicate);
        getSupportFragmentManager().beginTransaction().add(R.id.container1, CommunicateFragment1.newInstance("Activity Data"), "f1").commit();
        getSupportFragmentManager().beginTransaction().add(R.id.container2, CommunicateFragment2.newInstance("Activity Data"), "f2").commit();

    }

    @Override
    public void onFragmentInteraction(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toAnotherFragment(String str) {
        CommunicateFragment2 f2 = (CommunicateFragment2) getSupportFragmentManager().findFragmentByTag("f2");
        f2.receiveData(str);
    }
}
