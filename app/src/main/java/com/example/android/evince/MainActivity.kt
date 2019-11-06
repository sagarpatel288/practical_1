package com.example.android.evince

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.evince.adapter.RvMatrixAdapter
import com.example.android.evince.constants.AppConstants
import com.example.android.evince.database.AppDatabase
import com.example.android.evince.databinding.ActivityMainBinding
import com.example.android.evince.pojo.Matrix
import com.example.android.evince.utils.KeyboardUtils
import com.example.android.evince.utils.SharedPrefs
import com.example.android.evince.utils.StringUtils
import com.example.android.evince.utils.Utils
import com.example.android.evince.viewutils.ViewUtils

class MainActivity : AppCompatActivity(), View.OnClickListener, MainContract.MainView {

    private var mBinding: ActivityMainBinding? = null
    private var mAdapter: RvMatrixAdapter? = null
    private var mPresenter: MainContract.Presenter? = null

    /**
     * 11/6/2019
     * Apply validation before trying to generate matrix
     *
     * @author srdpatel
     * @since $1.0$
     */
    private val isValidInput: Boolean
        get() {
            if (!ViewUtils.hasTextValue(mBinding!!.viewTietRows) || Integer.parseInt(StringUtils.getString(mBinding!!.viewTietRows, "0")) == 0) {
                showMessage(getString(R.string.st_error_row_can_not_be_empty))
                return false
            } else if (ViewUtils.getInt(mBinding!!.viewTietRows) > AppConstants.AppConstants.Limits.MAX_ROW_COLUMNS) {
                showMessage(getString(R.string.st_error_row_cannot_be_more_than).replace("#", AppConstants.AppConstants.Limits.MAX_ROW_COLUMNS.toString()))
                return false
            } else if (!ViewUtils.hasTextValue(mBinding!!.viewTietColumns) || Integer.parseInt(StringUtils.getString(mBinding!!.viewTietColumns, "0")) == 0) {
                showMessage(getString(R.string.st_error_column_can_not_be_empty))
                return false
            } else if (ViewUtils.getInt(mBinding!!.viewTietColumns) > AppConstants.AppConstants.Limits.MAX_ROW_COLUMNS) {
                showMessage(getString(R.string.st_error_column_cannot_be_more_than).replace("#", AppConstants.AppConstants.Limits.MAX_ROW_COLUMNS.toString()))
                return false
            }
            return true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mPresenter = Presenter(this, AppDatabase.getDatabase(this)!!.getAppDao(), SharedPrefs.getSharedPref(this)!!)
        handleViews()
        ViewUtils.setOnClickListener(this, mBinding!!.viewMbtnApply, mBinding!!.viewMbtnRandom)
    }

    private fun handleViews() {
        mPresenter!!.handleViews()
    }

    /**
     * 11/6/2019
     * Useful to restore user preferences
     *
     * @author srdpatel
     * @since $1.0$
     */
    override fun setRows(mRows: Int, setViewValue: Boolean) {
        if (mBinding != null && mRows >= 0 && setViewValue) {
            mBinding!!.viewTietRows.setText(mRows.toString())
        }
    }

    /**
     * 11/6/2019
     * Useful to restore user preferences
     *
     * @author srdpatel
     * @since $1.0$
     */
    override fun setColumns(mColumns: Int, setViewValue: Boolean) {
        if (mBinding != null && mColumns >= 0 && setViewValue) {
            mBinding!!.viewTietColumns.setText(mColumns.toString())
        }
    }

    /**
     * 11/6/2019
     * Show generated random number on text view
     *
     * @author srdpatel
     * @since $1.0$
     */
    override fun setRandomNumber(randomNumber: Int, setViewValue: Boolean) {
        if (setViewValue && randomNumber != -1) {
            mBinding!!.viewTvRandomNumber.text = randomNumber.toString()
        }
    }

    /**
     * 11/6/2019
     * Show generated random color code on text view
     *
     * @author srdpatel
     * @since $1.0$
     */
    override fun setRandomColor(randomColor: Int, setViewValue: Boolean) {
        if (setViewValue) {
            mBinding!!.viewTvRandomColor.text = randomColor.toString()
            mBinding!!.viewTvRandomColor.setTextColor(randomColor)
        }
    }

    /**
     * 11/6/2019
     * Restore user preference / last saved data
     *
     * @author srdpatel
     * @since $1.0$
     */
    override fun setDefaultData(rows: Int, columns: Int, matrixList: MutableList<Matrix>, randomList: MutableList<Int>, positionOfLastStoredRandomNumberIfAny: Int) {
        setRecyclerView(rows, columns, matrixList)
        if (mPresenter!!.randomNumber != -1 && mPresenter!!.randomColor != -1) {
            highLightRandomMatch()
        }
    }

    /**
     * 11/6/2019
     * Resets the recyclerView after {@link #onClickApply} success
     *
     * @author srdpatel
     * @since $1.0$
     */
    override fun setRecyclerView(rows: Int, columns: Int, mList: MutableList<Matrix>) {
        // comment by srdpatel: 11/5/2019 if it is vertical, then span means columns. If orientation is horizontal, then span means rows.
        var span: Int
        if (columns > rows) {
            span = rows
        } else {
            span = columns
        }
        // comment by srdpatel: 11/5/2019 To prevent span 0 exception
        if (span == 0) {
            span = 1
        }
        val gridLayoutManager = GridLayoutManager(this, span, if (span == rows) RecyclerView.HORIZONTAL else RecyclerView.VERTICAL, false)
        mBinding!!.viewRv.layoutManager = gridLayoutManager
        mAdapter = RvMatrixAdapter(this, mList)
        mBinding!!.viewRv.adapter = mAdapter
    }

    /**
     * 11/6/2019
     * As per discussion, after all possible random numbers have been generated, we will re-initialize the
     * random number generation
     * @author srdpatel
     * @since $1.0$
     */
    override fun onReInitRandomList() {
        showMessage(getString(R.string.st_error_no_more_unique_random_number_left))
    }

    /**
     * 11/6/2019
     * As per discussion, we will directly show the generated random number in matrix with generated random color
     *
     * @author srdpatel
     * @since $1.0$
     */
    override fun highLightRandomMatch() {
        if (mAdapter != null) {
            mAdapter!!.clearSelection()
            mAdapter!!.highlightItem(mPresenter!!.randomNumber, mPresenter!!.randomColor)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.view_mbtn_apply -> if (isValidInput) {
                onClickApply()
            }
            R.id.view_mbtn_random -> if (isValidInput) {
                if (mAdapter != null && Utils.isNotNullNotEmpty(mAdapter!!.list) && Utils.isNotNullNotEmpty(mPresenter!!.randomList)) {
                    onClickRandom()
                } else {
                    showMessage(getString(R.string.st_error_random_number_cannot_be_generated_before_matrix))
                }
            }
        }
    }

    /**
     * 11/6/2019
     * We are re-setting the values as soon as user clicks on apply button
     *
     * @author srdpatel
     * @since $1.0$
     */
    private fun onClickApply() {
        mBinding!!.viewTvRandomNumber.text = ""
        mBinding!!.viewTvRandomColor.text = ""
        KeyboardUtils.hideSoftKeyboard(this)
        mPresenter!!.onClickApply(Integer.parseInt(StringUtils.getString(mBinding!!.viewTietRows, "0")),
                Integer.parseInt(StringUtils.getString(mBinding!!.viewTietColumns, "0")))
    }

    /**
     * 11/6/2019
     * Generate random number
     *
     * @author srdpatel
     * @since $1.0$
     */
    private fun onClickRandom() {
        mPresenter!!.onClickRandom()
    }

    /**
     * 11/6/2019
     * Show any success, error or toast message
     *
     * @author srdpatel
     * @since $1.0$
     */
    private fun showMessage(message: String) {
        makeText(this, message, LENGTH_SHORT).show()
    }
}
