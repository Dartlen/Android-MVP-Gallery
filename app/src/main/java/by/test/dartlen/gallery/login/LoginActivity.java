package by.test.dartlen.gallery.login;

import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import by.test.dartlen.gallery.R;
import by.test.dartlen.gallery.data.GalleryRepository;

import by.test.dartlen.gallery.util.Injection;

/***
 * Created by Dartlen on 26.10.2017.
 */

public class LoginActivity extends AppCompatActivity {

    private LoginPresenter mLoginPresenter;
    private GalleryRepository mGalleryRepository;
    private LoginFragment mLoginFramgent;
    private RegisterFragment mRegisterFragment;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mLoginFramgent = LoginFragment.newInstance();
        mRegisterFragment = RegisterFragment.newInstance();

        mLoginPresenter = new LoginPresenter(Injection.provideGalleryRepository(getApplicationContext()), mLoginFramgent, mRegisterFragment);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(mLoginFramgent, "Logind");
        adapter.addFragment(mRegisterFragment, "Register");
        viewPager.setAdapter(adapter);
    }

}
