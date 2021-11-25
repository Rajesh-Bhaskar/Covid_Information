package com.androidapp.covid_info.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidapp.covid_info.R;
import com.androidapp.covid_info.Utils.Constants;
import com.androidapp.covid_info.Utils.Helper;
import com.androidapp.covid_info.Utils.SpacesItemDecoration;
import com.androidapp.covid_info.adaptors.DetailsPageAdaptor;
import com.androidapp.covid_info.adaptors.HomepageAdaptor;
import com.androidapp.covid_info.model.CountriesResponse;
import com.androidapp.covid_info.model.Country;
import com.androidapp.covid_info.model.DetailsResponse;
import com.androidapp.covid_info.rest.ApiService;
import com.androidapp.covid_info.rest.RestClient;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class NotificationFragment extends Fragment {
    public static final String TAG = NotificationFragment.class.getSimpleName();
    private ApiService mApiService;
    DetailsPageAdaptor mSearchAdapter;
    private String countryname;
    @BindView(R.id.recyclerView)
    RecyclerView mSearchList;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.country)
    TextView country;
    @BindView(R.id.swife_container)
    SwipeRefreshLayout swipeRefreshLayout;
    public NotificationFragment() {
        // Required empty public constructor
    }

    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View pageItem =inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, pageItem);
        mApiService = new RestClient(getContext()).getApiService();

        if (getArguments() != null) {
            countryname = getArguments().getString(Constants.COUNTRY_KEY);
            country.setText(countryname);
        }
        getdata();
        setupadaptor();
return pageItem;
    }
    private void getdata(){
        swipeRefreshLayout.setRefreshing(false);
        mApiService.getDetails(countryname)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Country>>() {
                    @Override
                    public void call(List<Country> homeScreenResponse) {

                        if(homeScreenResponse != null){
                            progress_bar.setVisibility(View.GONE);
                            Collections.reverse(homeScreenResponse);
                            if (mSearchAdapter != null) {
                                mSearchAdapter.updateItems(homeScreenResponse);
                            }
                        }else {

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

    private void setupadaptor(){
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        mSearchList.setLayoutManager(manager);
        mSearchAdapter = new DetailsPageAdaptor(getActivity());
        mSearchList.addItemDecoration(new SpacesItemDecoration((int) getResources().getDimension(R.dimen.px_10),
                (int) getResources().getDimension(R.dimen.px_0),
                (int) getResources().getDimension(R.dimen.px_5),
                (int) getResources().getDimension(R.dimen.px_5)));
        mSearchList.setAdapter(mSearchAdapter);

        mSearchAdapter.setOnItemClickListener(data -> {
            if(data.getCountry() != null){
                NotificationFragment fragment = new NotificationFragment();
                Bundle bundle = new Bundle();
                bundle.putString("CountryName", data.getCountry().toString());
                fragment.setArguments(bundle);
                Helper.addFragment(getActivity(),fragment,NotificationFragment.TAG);
            }

        });
    }
}