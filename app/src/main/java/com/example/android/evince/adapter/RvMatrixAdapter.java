package com.example.android.evince.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.evince.R;
import com.example.android.evince.databinding.ItemMatrixBinding;
import com.example.android.evince.pojo.Matrix;
import com.example.android.evince.utils.Utils;
import com.example.android.evince.viewutils.ViewUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RvMatrixAdapter extends RecyclerView.Adapter {

    private List<Matrix> mList;
    private Context mContext;
    private int color;

    public RvMatrixAdapter(Context mContext, List<Matrix> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public List<Matrix> getList() {
        return mList;
    }

    public void setList(List<Matrix> mList) {
        this.mList = mList;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matrix, parent, false);
        ItemMatrixBinding mBinding = ItemMatrixBinding.bind(view);
        return new ItemViewHolder(view, mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position != -1) {
            if (holder instanceof ItemViewHolder) {
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                Matrix matrix = mList.get(position);
                itemViewHolder.mBinding.viewMbtn.setText(String.valueOf(matrix.getNumber()));
                ViewUtils.setEnable(matrix.isSelected(), itemViewHolder.mBinding.viewMbtn);
                if (matrix.getColor() != 0 && matrix.getColor() != -1) {
                    itemViewHolder.mBinding.viewMbtn.setBackgroundColor(matrix.getColor());
                } else {
                    itemViewHolder.mBinding.viewMbtn.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorWhiteFfA100));
                }
            }
        }
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getItemCount() {
        return Utils.isNotNullNotEmpty(mList) ? mList.size() : 0;
    }

    public void updateItem(int position, Matrix matrix) {
        if (Utils.hasElement(mList, position)) {
            mList.set(position, matrix);
            notifyItemChanged(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ItemMatrixBinding mBinding;

        public ItemViewHolder(@NonNull View itemView, ItemMatrixBinding mBinding) {
            super(itemView);
            this.mBinding = mBinding;
            mBinding.viewMbtn.setOnClickListener(view -> {
                int clickedPosition = getAdapterPosition();
                Matrix matrix = mList.get(clickedPosition);
                matrix.setColor(getColor());
                updateItem(clickedPosition, matrix);
            });
        }
    }
}
