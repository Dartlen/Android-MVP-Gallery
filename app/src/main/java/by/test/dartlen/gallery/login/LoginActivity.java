package by.test.dartlen.gallery.login;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import by.test.dartlen.gallery.R;


/***
 * Created by Dartlen on 26.10.2017.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");

        tabSpec.setContent(R.id.login);
        tabSpec.setIndicator("Login");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.register);
        tabSpec.setIndicator("Regisnter");
        tabHost.addTab(tabSpec);



        tabHost.setCurrentTab(0);
    }


}
