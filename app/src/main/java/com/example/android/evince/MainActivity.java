package com.example.android.evince;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.example.android.evince.adapter.RvMatrixAdapter;
import com.example.android.evince.constants.AppConstants;
import com.example.android.evince.database.AppDatabase;
import com.example.android.evince.databinding.ActivityMainBinding;
import com.example.android.evince.pojo.Matrix;
import com.example.android.evince.utils.KeyboardUtils;
import com.example.android.evince.utils.SharedPrefs;
import com.example.android.evince.utils.StringUtils;
import com.example.android.evince.utils.Utils;
import com.example.android.evince.viewutils.ViewUtils;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainContract.MainView {

    private ActivityMainBinding mBinding;
    private RvMatrixAdapter mAdapter;
    private MainContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mPresenter = new Presenter(this, AppDatabase.getInstance(this).getAppDao(), SharedPrefs.getSharedPref(this));
        handleViews();
        ViewUtils.setOnClickListener(this, mBinding.viewMbtnApply, mBinding.viewMbtnRandom);
    }

    private void handleViews() {
       mPresenter.handleViews();
    }
    
    @Override
    public void setRows(int mRows, boolean setViewValue) {
        if (mBinding != null && mRows >= 0 && setViewValue) {
            mBinding.viewTietRows.setText(String.valueOf(mRows));
        }
    }

    @Override
    public void setColumns(int mColumns, boolean setViewValue) {
        if (mBinding != null && mColumns >= 0 && setViewValue) {
            mBinding.viewTietColumns.setText(String.valueOf(mColumns));
        }
    }

    @Override
    public void setRandomNumber(int randomNumber, boolean setViewValue) {
        if (setViewValue) {
            mBinding.viewTvRandomNumber.setText(String.valueOf(randomNumber));
        }
    }

    @Override
    public void setRandomColor(int randomColor, boolean setViewValue) {
        if (setViewValue) {
            mBinding.viewTvRandomColor.setText(String.valueOf(randomColor));
            mBinding.viewTvRandomColor.setTextColor(randomColor);
        }
    }

    @Override
    public void setDefaultData(int rows, int columns, List<Matrix> matrixList, List<Integer> randomList, int positionOfLastStoredRandomNumberIfAny) {
        setRecyclerView(rows, columns, matrixList);
        if (mPresenter.getRandomNumber() != -1 && mPresenter.getRandomColor() != -1) {
            highLightRandomMatch();
        }
    }

    @Override
    public void setRecyclerView(int rows, int columns, List<Matrix> mList) {
        // comment by srdpatel: 11/5/2019 if it is vertical, then span means columns. If orientation is horizontal, then span means rows.
        int span;
        if (columns > rows) {
            span = rows;
        } else {
            span = columns;
        }
        // comment by srdpatel: 11/5/2019 To prevent span 0 exception
        if (span == 0) {
            span = 1;
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, span, span == rows ? RecyclerView.HORIZONTAL : RecyclerView.VERTICAL, false);
        mBinding.viewRv.setLayoutManager(gridLayoutManager);
        mAdapter = new RvMatrixAdapter(this, mList);
        mBinding.viewRv.setAdapter(mAdapter);
    }

    @Override
    public void onReInitRandomList() {
        showMessage(getString(R.string.st_error_no_more_unique_random_number_left));
    }

    @Override
    public void highLightRandomMatch() {
        if (mAdapter != null) {
            mAdapter.clearSelection();
            mAdapter.highlightItem(mPresenter.getRandomNumber(), mPresenter.getRandomColor());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_mbtn_apply:
                if (isValidInput()) {
                    onClickApply();
                }
                break;
            case R.id.view_mbtn_random:
                if (isValidInput()) {
                    if (mAdapter != null && Utils.isNotNullNotEmpty(mAdapter.getList()) && Utils.isNotNullNotEmpty(mPresenter.getRandomList())) {
                        onClickRandom();
                    } else {
                        showMessage(getString(R.string.st_error_random_number_cannot_be_generated_before_matrix));
                    }
                }
                break;
        }
    }

    private void onClickApply() {
        mBinding.viewTvRandomNumber.setText("");
        mBinding.viewTvRandomColor.setText("");
        KeyboardUtils.hideSoftKeyboard(this);
        mPresenter.onClickApply(Integer.parseInt(StringUtils.getString(mBinding.viewTietRows, "0")),
                Integer.parseInt(StringUtils.getString(mBinding.viewTietColumns, "0")));
    }

    private boolean isValidInput() {
        if (!ViewUtils.hasTextValue(mBinding.viewTietRows) || Integer.parseInt(StringUtils.getString(mBinding.viewTietRows, "0")) == 0) {
            showMessage(getString(R.string.st_error_row_can_not_be_empty));
            return false;
        } else if (ViewUtils.getInt(mBinding.viewTietRows) > AppConstants.Limits.MAX_ROW_COLUMNS) {
            showMessage(getString(R.string.st_error_row_cannot_be_more_than).replace("#", String.valueOf(AppConstants.Limits.MAX_ROW_COLUMNS)));
            return false;
        } else if (!ViewUtils.hasTextValue(mBinding.viewTietColumns) || Integer.parseInt(StringUtils.getString(mBinding.viewTietColumns, "0")) == 0) {
            showMessage(getString(R.string.st_error_column_can_not_be_empty));
            return false;
        } else if (ViewUtils.getInt(mBinding.viewTietColumns) > AppConstants.Limits.MAX_ROW_COLUMNS) {
            showMessage(getString(R.string.st_error_column_cannot_be_more_than).replace("#", String.valueOf(AppConstants.Limits.MAX_ROW_COLUMNS)));
            return false;
        }
        return true;
    }

    private void onClickRandom() {
       mPresenter.onClickRandom();
    }

    private void showMessage(String message) {
        makeText(this, message, LENGTH_SHORT).show();
    }
}
