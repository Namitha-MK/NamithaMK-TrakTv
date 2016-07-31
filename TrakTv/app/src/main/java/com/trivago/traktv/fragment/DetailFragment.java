package com.trivago.traktv.fragment;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trivago.traktv.R;
import com.trivago.traktv.bean.DetailBean;

public  class DetailFragment extends DialogFragment {
    static DetailBean mDetailBean;

    /**
     * Create a new instance of DetailFragment, providing "num"
     * as an argument.
     */
    static DetailFragment newInstance(DetailBean detailBean) {
        DetailFragment f = new DetailFragment();
        mDetailBean = detailBean;


        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_detail, container, false);

        TextView tvTitle = (TextView)v.findViewById(R.id.title);
        TextView tvYear = (TextView)v.findViewById(R.id.year);
        TextView tvOverview = (TextView)v.findViewById(R.id.overview);
        TextView noImage = (TextView)v.findViewById(R.id.no_image);
        ImageView imageView = (ImageView)v.findViewById(R.id.iv_row_banner);
        tvTitle.setText(mDetailBean.getmTitle());
        tvYear.setText(mDetailBean.getmYear());
        tvOverview.setText(mDetailBean.getmOverview());
        String url = mDetailBean.getmImageUrl();

        if (url == null){
            noImage.setVisibility(View.VISIBLE);
            noImage.setVisibility(View.GONE);
        }else {
            try {
                Picasso.with(getContext())
                        .load(url).resize(150, 150)
                        //.centerCrop()
                        .into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return v;
    }
}