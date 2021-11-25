package com.androidapp.covid_info.adaptors;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidapp.covid_info.R;
import com.androidapp.covid_info.model.Country;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsPageAdaptor extends RecyclerView.Adapter {

    private Context context;
    List<Country> mitems;

    public DetailsPageAdaptor(Context context) {
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
        Layout = LayoutInflater.from(context).inflate(R.layout.details_item, parent, false);
        DetailspageHolder homepageViewHolder = new DetailspageHolder(Layout);
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
        DetailspageHolder detailspageHolder = (DetailspageHolder) holder;
        final Country item = mitems.get(position);

        if(item!= null){
            String dateString = item.getDate();
            String[] separated = dateString.split("T");
         //   Log.i("DATE", separated[0]);
            detailspageHolder.date.setText(separated[0]);
            detailspageHolder.casesnum.setText(item.getCases());
            detailspageHolder.statusnum.setText(item.getStatus());
        }

        detailspageHolder.mParentItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   if (listener != null) {
                    listener.onItemClick(item);
                }*/
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

    class DetailspageHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.parent_view)
        CardView mParentItem;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.statusnum)
        TextView statusnum;
        @BindView(R.id.Casesnum)
        TextView casesnum;

        public DetailspageHolder(@NonNull View itemView) {
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

