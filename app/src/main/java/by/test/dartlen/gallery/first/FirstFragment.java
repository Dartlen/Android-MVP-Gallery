package by.test.dartlen.gallery.first;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.test.dartlen.gallery.R;
import by.test.dartlen.gallery.login.LoginFragment;
import by.test.dartlen.gallery.register.RegisterFragment;
import by.test.dartlen.gallery.login.ViewPagerAdapter;

/***
 * Created by Dartlen on 21.12.2017.
 */

public class FirstFragment extends Fragment implements FirstContract.View{

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

    private FirstContract.Presenter mFirstPresenter;

    private LoginFragment mLoginFragment;
    private RegisterFragment mRegisterFragment;

    @Override
    public void setPresenter(FirstContract.Presenter presenter) {
        this.mFirstPresenter = presenter;
    }

    @Override
    public void setViewFragments(LoginFragment loginFragment, RegisterFragment registerFragment) {
        mLoginFragment    = loginFragment;
        mRegisterFragment = registerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.firstpage_fragment,container , false);
        ButterKnife.bind(this, root);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

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
