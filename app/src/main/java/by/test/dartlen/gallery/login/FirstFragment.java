package by.test.dartlen.gallery.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.test.dartlen.gallery.R;
import by.test.dartlen.gallery.util.Injection;

/***
 * Created by Dartlen on 21.12.2017.
 */

public class FirstFragment extends Fragment {

    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    public FirstFragment(){}

    @Nullable
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Nullable
    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    private Unbinder unbinder;

    private LoginFragment mLoginFragment;
    private LoginPresenter mLoginPresenter;

    private RegisterFragment mRegisterFragment;
    //private RegisterPresenter mRegisterPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mLoginFragment == null) {
            mLoginFragment = mLoginFragment.newInstance();
            //ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mLoginFragment, R.id.fr);
        }

        if(mRegisterFragment ==null)
            mRegisterFragment = mRegisterFragment.newInstance();

        mLoginPresenter = new LoginPresenter(Injection.provideGalleryRepository(getContext().getApplicationContext()),
                mLoginFragment, mRegisterFragment);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.firstpage_fragment,container , false);
        ButterKnife.bind(this, root);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(mLoginFragment, "Login");
        adapter.addFragment(mRegisterFragment, "Register");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
