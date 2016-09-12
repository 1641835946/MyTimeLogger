package com.example.administrator.mytimelogger.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.mytimelogger.R;
import com.example.administrator.mytimelogger.model.SelectIconItem;
import com.tonicartos.superslim.GridSLM;
import com.tonicartos.superslim.LinearSLM;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/12.
 */
public class IconListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int VIEW_TYPE_HEADER = 0;
    private final static int VIEW_TYPE_CONTENT = 1;
    private static final int LINEAR = 0;
    private List<SelectIconItem> mItems;
    private Context mContext;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == VIEW_TYPE_HEADER) {
            view = inflater.inflate(R.layout.item_icon_header, parent, false);
            return new TextViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.item_icon, parent, false);
            return new ImageViewHolder(view);
        }

    }

    public IconListAdapter(Context mContext) {
        initDatas();
        this.mContext = mContext;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final SelectIconItem item = mItems.get(position);
        // A content binding implementation.
        if (holder instanceof TextViewHolder) {
            ((TextViewHolder) holder).tv.setText(item.getItem());

            final GridSLM.LayoutParams params = GridSLM.LayoutParams.from(((TextViewHolder) holder).tv.getLayoutParams());
            params.setSlm(LinearSLM.ID);
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            // Position of the first item in the section. This doesn't have to
            // be a header. However, if an item is a header, it must then be the
            // first item in a section.
            params.setSlm(item.sectionManager == LINEAR ? LinearSLM.ID : GridSLM.ID);
            params.setColumnWidth(mContext.getResources().getDimensionPixelSize(R.dimen.grid_column_width));
            params.setFirstPosition(item.sectionFirstPosition);
//            holder.bindItem(item.getItem());
        } else if (holder instanceof ImageViewHolder){
            ((ImageViewHolder) holder).icon.setBackgroundResource(getResId(item.getItem(), R.drawable.class));

            final GridSLM.LayoutParams params = GridSLM.LayoutParams.from(((ImageViewHolder) holder).icon.getLayoutParams());
            params.setSlm(LinearSLM.ID);
            if (item.getType() == VIEW_TYPE_HEADER) {
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            }
            // Position of the first item in the section. This doesn't have to
            // be a header. However, if an item is a header, it must then be the
            // first item in a section.
            params.setSlm(item.sectionManager == LINEAR ? LinearSLM.ID : GridSLM.ID);
            params.setColumnWidth(mContext.getResources().getDimensionPixelSize(R.dimen.grid_column_width));
            params.setFirstPosition(item.sectionFirstPosition);
            ((ImageViewHolder) holder).icon.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView icon;
        public ImageViewHolder(View view) {
            super(view);
            icon = (ImageView) view.findViewById(R.id.select_item_icon);
        }
    }

    class TextViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public TextViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.select_icon_text);
        }
    }

    private String one2three(int i) {
        if (i < 10) {
            return "00" + i;
        } else if (i < 100) {
            return "0" + i;
        } else {
            return "" + i;
        }
    }
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void initDatas() {
        int sectionManager = -1;
        int itemCount = 0;
        int sectionFirstPosition = 0;
        mItems = new ArrayList<>();
        String[] strings = {"Default", "Eat and Cook", "Family",
                "Finance and Shopping", "Home and Personal Care",
                "Music", "Relax and Party", "Sport", "Study", "Transport and Travel"};
        SelectIconItem item;
        SelectIconItem header;
        for (int i = 0; i < strings.length; i++) {
            sectionManager = (sectionManager + 1) % 2;
            switch (i) {
                case 0:
                    for (int j = 1; j <= 228; j++) {
                        item = new SelectIconItem("cat_" + j, VIEW_TYPE_CONTENT,
                                sectionManager, sectionFirstPosition);
                        mItems.add(item);
                        itemCount++;
                    }
                    break;
                case 1:
                    for (int j = 1; j <= 74; j++) {
                        item = new SelectIconItem("ec_" + one2three(j), VIEW_TYPE_CONTENT,
                                sectionManager, sectionFirstPosition);
                        mItems.add(item);
                        itemCount++;
                    }
                    break;
                case 2:
                    for (int j = 1; j <= 56; j++) {
                        item = new SelectIconItem("fam_" + one2three(j), VIEW_TYPE_CONTENT,
                                sectionManager, sectionFirstPosition);
                        mItems.add(item);
                        itemCount++;
                    }
                    break;
                case 3:
                    for (int j = 1; j <= 43; j++) {
                        item = new SelectIconItem("fs_" + one2three(j), VIEW_TYPE_CONTENT,
                                sectionManager, sectionFirstPosition);
                        mItems.add(item);
                        itemCount++;
                    }
                    break;
                case 4:
                    for (int j = 1; j <= 108; j++) {
                        item = new SelectIconItem("hpcd_" + one2three(j), VIEW_TYPE_CONTENT,
                                sectionManager, sectionFirstPosition);
                        mItems.add(item);
                        itemCount++;
                    }
                    break;
                case 5:
                    for (int j = 1; j <= 48; j++) {
                        item = new SelectIconItem("mi_" + one2three(j), VIEW_TYPE_CONTENT,
                                sectionManager, sectionFirstPosition);
                        mItems.add(item);
                        itemCount++;
                    }
                    break;
                case 6:
                    for (int j = 1; j <= 53; j++) {
                        item = new SelectIconItem("rph_" + one2three(j), VIEW_TYPE_CONTENT,
                                sectionManager, sectionFirstPosition);
                        mItems.add(item);
                        itemCount++;
                    }
                    break;
                case 7:
                    for (int j = 1; j <= 96; j++) {
                        item = new SelectIconItem("sp_" + one2three(j), VIEW_TYPE_CONTENT,
                                sectionManager, sectionFirstPosition);
                        mItems.add(item);
                        itemCount++;
                    }
                    break;
                case 8:
                    for (int j = 1; j <= 67; j++) {
                        item = new SelectIconItem("st_" + one2three(j), VIEW_TYPE_CONTENT,
                                sectionManager, sectionFirstPosition);
                        mItems.add(item);
                        itemCount++;
                    }
                    break;
                case 9:
                    for (int j = 1; j <= 59; j++) {
                        item = new SelectIconItem("tt_" + one2three(j), VIEW_TYPE_CONTENT,
                                sectionManager, sectionFirstPosition);
                        mItems.add(item);
                        itemCount++;
                    }
                    break;
            }
            sectionFirstPosition = i + itemCount;
            header = new SelectIconItem(strings[i], VIEW_TYPE_HEADER,
                    sectionManager, sectionFirstPosition);
            mItems.add(header);
        }
    }
}
