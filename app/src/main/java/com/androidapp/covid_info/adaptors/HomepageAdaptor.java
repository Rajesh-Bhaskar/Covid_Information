package com.androidapp.covid_info.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidapp.covid_info.R;
import com.androidapp.covid_info.model.Country;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomepageAdaptor extends RecyclerView.Adapter {

    private Context context;
    List<Country> mitems;

    public HomepageAdaptor(Context context) {
        this.context = context;
    }

    public void updateItems(List<Country> items) {
        this.mitems = items;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View Layout;
        Layout = LayoutInflater.from(context).inflate(R.layout.homepage_item, parent, false);
        HomepageHolder homepageViewHolder = new HomepageHolder(Layout);
        return homepageViewHolder;
    }
    private ItemClickListener listener;

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(Country item);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HomepageHolder homepageHolder = (HomepageHolder) holder;
        final Country item = mitems.get(position);

        if(item!= null){
            homepageHolder.country_name.setText(item.getCountry());
            homepageHolder.NewConfirmednum.setText(item.getNewConfirmed());
            homepageHolder.NewDeathsnum.setText(item.getNewDeaths());
            homepageHolder.NewRecoverednum.setText(item.getNewRecovered());
            homepageHolder.totalConfirmednum.setText(item.getTotalConfirmed());
            homepageHolder.totalDeathnum.setText(item.getTotalDeaths());
            homepageHolder.totalrecoverednum.setText(item.getTotalRecovered());
        }

        homepageHolder.mParentItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mitems != null)
            return mitems.size();
        else {
            return 0;
        }
    }

    class HomepageHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.parent_view)
        CardView mParentItem;
        @BindView(R.id.country_name)
        TextView country_name;
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
        public HomepageHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mParentItem.setOnClickListener(view -> {

              /*  if (listener != null) {
                    listener.onClickSearchItem((Country) itemView.getTag());
                }*/
            });
        }
    }
}
