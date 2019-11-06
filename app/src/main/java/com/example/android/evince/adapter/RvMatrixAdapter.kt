package com.example.android.evince.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.android.evince.R
import com.example.android.evince.apputils.AppUtils
import com.example.android.evince.databinding.ItemMatrixBinding
import com.example.android.evince.pojo.Matrix
import com.example.android.evince.utils.Utils
import com.example.android.evince.viewutils.ViewUtils
import com.google.common.base.Predicate

class RvMatrixAdapter(var context: Context, private var mList: MutableList<Matrix>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var color: Int = 0

    /**
     * 11/6/2019
     * Get the current {@see mList} the adapter is currently holding
     *
     * @author srdpatel
     * @since $1.0$
     */
    var list: MutableList<Matrix>
        get() = mList
        set(mList) {
            this.mList = mList
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_matrix, parent, false)
        val mBinding = ItemMatrixBinding.bind(view)
        return ItemViewHolder(view, mBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position != -1) {
            if (holder is ItemViewHolder) {
                val matrix = mList[position]
                holder.mBinding.viewMbtn.text = matrix.number.toString()
                ViewUtils.setEnable(false, holder.mBinding.viewMbtn)
                if (matrix.color != 0 && matrix.color != -1) {
                    holder.mBinding.viewMbtn.setTextColor(matrix.color)
                    holder.mBinding.viewMbtn.background = ContextCompat.getDrawable(context, R.drawable.dr_white_rect)
                } else {
                    holder.mBinding.viewMbtn.setTextColor(ContextCompat.getColor(context, R.color.colorWhiteFfA100))
                    holder.mBinding.viewMbtn.background = ContextCompat.getDrawable(context, R.drawable.dr_gradient_rect)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (Utils.isNotNullNotEmpty(mList)) mList.size else 0
    }

    /**
     * 11/6/2019
     * Highlights (Sets number and color) for the item
     *
     * @author srdpatel
     * @since $1.0$
     */
    fun highlightItem(number: Int, color: Int) {
        AppUtils.setSelectedInDb(context, number, color)
        val matchedMatrixList = Utils.getFilteredList(mList, Predicate { input -> input != null && input.number == number })
        if (Utils.isNotNullNotEmpty(matchedMatrixList)) {
            var matrix = matchedMatrixList!![0]
            val index = mList.indexOf(matrix)
            if (index != -1) {
                matrix = mList[index]
                matrix.isSelected = true
                matrix.color = color
                updateItem(index, matrix)
            }
        }
    }

    /**
     * 11/6/2019
     * Update the item
     *
     * @author srdpatel
     * @since $1.0$
     */
    fun updateItem(position: Int, matrix: Matrix) {
        if (Utils.hasElement(mList, position)) {
            mList[position] = matrix
            notifyItemChanged(position)
            notifyItemRangeChanged(position, itemCount)
        }
    }

    /**
     * 11/6/2019
     * Re-set each item color to 0 and isSelected field to false
     *
     * @author srdpatel
     * @since $1.0$
     */
    fun clearSelection() {
        AppUtils.setSelectedFalseInDb(context)
        if (Utils.isNotNullNotEmpty(mList)) {
            for (matrix in mList) {
                matrix.color = 0
                matrix.isSelected = false
            }
            notifyDataSetChanged()
        }
    }

    inner class ItemViewHolder(itemView: View, val mBinding: ItemMatrixBinding) : RecyclerView.ViewHolder(itemView)
}
