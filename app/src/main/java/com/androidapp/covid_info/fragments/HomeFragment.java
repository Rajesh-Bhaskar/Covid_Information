package com.androidapp.covid_info.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidapp.covid_info.R;
import com.androidapp.covid_info.Utils.Constants;
import com.androidapp.covid_info.Utils.Helper;
import com.androidapp.covid_info.Utils.SpacesItemDecoration;
import com.androidapp.covid_info.adaptors.HomepageAdaptor;
import com.androidapp.covid_info.model.CountriesResponse;
import com.androidapp.covid_info.model.Country;
import com.androidapp.covid_info.rest.ApiService;
import com.androidapp.covid_info.rest.RestClient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private ApiService mApiService;
    HomepageAdaptor mSearchAdapter;
    public static final String TAG = HomeFragment.class.getSimpleName();
    private String mParam1;
    private String mParam2;
    public HomeFragment() {
        // Required empty public constructor
    }
    @BindView(R.id.swife_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mSearchList;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.NewConfirmednum)
    TextView NewConfirmednum;
    @BindView(R.id.NewDeathsnum)
    TextView NewDeathsnum;
    @BindView(R.id.totalConfirmednum)
    TextView totalConfirmednum;
    @BindView(R.id.NewRecoverednum)
    TextView NewRecoverednum;
    @BindView(R.id.totalDeathnum)
    TextView totalDeathnum;
    @BindView(R.id.totalrecoverednum)
    TextView totalrecoverednum;
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View pageItem = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, pageItem);
        mApiService = new RestClient(getContext()).getApiService();
        setupadaptor();
        getData();
        return pageItem;
    }
private void setupadaptor(){
    final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


    GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
    mSearchList.setLayoutManager(manager);
    mSearchAdapter = new HomepageAdaptor(getActivity());
    mSearchList.addItemDecoration(new SpacesItemDecoration((int) getResources().getDimension(R.dimen.px_10),
            (int) getResources().getDimension(R.dimen.px_0),
            (int) getResources().getDimension(R.dimen.px_5),
            (int) getResources().getDimension(R.dimen.px_5)));
    mSearchList.setAdapter(mSearchAdapter);

    mSearchAdapter.setOnItemClickListener(data -> {
 if(data.getCountry() != null){
NotificationFragment fragment = new NotificationFragment();
     Bundle bundle = new Bundle();
     bundle.putString(Constants.COUNTRY_KEY, data.getCountry().toString());
     fragment.setArguments(bundle);
     Helper.addFragment(getActivity(),fragment,NotificationFragment.TAG);
 }

    });
}
    private void getData() {
        swipeRefreshLayout.setRefreshing(false);
        progress_bar.setVisibility(View.VISIBLE);
        mApiService.getHomeScreenDetails()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CountriesResponse>() {
                    @Override
                    public void call(CountriesResponse homeScreenResponse) {

                  if(homeScreenResponse != null){
                      Log.e(TAG, "aste aste");
                      progress_bar.setVisibility(View.GONE);
                      if(homeScreenResponse.getGlobal()!= null){
                        NewConfirmednum.setText(homeScreenResponse.getGlobal().getNewConfirmed());
                         NewDeathsnum.setText(homeScreenResponse.getGlobal().getNewDeaths());
                          NewRecoverednum.setText(homeScreenResponse.getGlobal().getNewRecovered());
                          totalConfirmednum.setText(homeScreenResponse.getGlobal().getTotalConfirmed());
                  totalDeathnum.setText(homeScreenResponse.getGlobal().getTotalDeaths());
                         totalrecoverednum.setText(homeScreenResponse.getGlobal().getTotalRecovered());
                      }
                      if (mSearchAdapter != null) {
                          mSearchAdapter.updateItems(homeScreenResponse.getCountries());
                      }
                  }else {
                      Log.e(TAG, "sadyaa   guru");
                  }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, throwable.toString());
                        progress_bar.setVisibility(View.GONE);

                    }
                });
    }

    @Override
    public void onRefresh() {
getData();
    }
}