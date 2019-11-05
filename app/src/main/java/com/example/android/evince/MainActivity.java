package com.example.android.evince;

import android.os.Bundle;
import android.view.View;

import com.example.android.evince.adapter.RvMatrixAdapter;
import com.example.android.evince.apputils.AppUtils;
import com.example.android.evince.constants.AppConstants;
import com.example.android.evince.databinding.ActivityMainBinding;
import com.example.android.evince.pojo.Matrix;
import com.example.android.evince.utils.SharedPrefs;
import com.example.android.evince.utils.StringUtils;
import com.example.android.evince.utils.Utils;
import com.example.android.evince.viewutils.ViewUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding mBinding;
    private int mRows;
    private int mColumns;
    private int randomColor;
    private int randomNumber;
    private List<Matrix> mList = new ArrayList<>();

    public int getRandomColor() {
        return randomColor;
    }

    public int getRandomNumber() {
        return randomNumber;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initControls();
        handleViews();
        ViewUtils.setOnClickListener(this, mBinding.viewMbtnApply, mBinding.viewMbtnRandom);
    }

    private void initControls() {
        if (SharedPrefs.getInt(this, AppConstants.STR_ROWS, -1) != -1) {
            setRows(SharedPrefs.getInt(this, AppConstants.STR_ROWS, -1), true, false);
        } else {
            setRows(AppConstants.DEFAULT_ROW_COLUMNS, true, true);
        }
        if (SharedPrefs.getInt(this, AppConstants.STR_COLUMNS, -1) != -1) {
            setColumns(SharedPrefs.getInt(this, AppConstants.STR_COLUMNS, -1), true, false);
        } else {
            setColumns(AppConstants.DEFAULT_ROW_COLUMNS, true, true);
        }
        if (SharedPrefs.getInt(this, AppConstants.STR_RANDOM_NUMBER, -1) != -1) {
            setRandomNumber(SharedPrefs.getInt(this, AppConstants.STR_RANDOM_NUMBER, -1), true, false);
        }
        if (SharedPrefs.getInt(this, AppConstants.STR_RANDOM_COLOR, -1) != -1) {
            setRandomColor(SharedPrefs.getInt(this, AppConstants.STR_RANDOM_COLOR, -1), true, false);
        }
    }

    private void handleViews() {
//        if (Utils.isNotNullNotEmpty(AppDatabase.getInstance(this).getAppDao().getAllMatrices())) {
//            mList = AppDatabase.getInstance(this).getAppDao().getAllMatrices();
//        } else {
            mList = AppUtils.getMatrix(getRows(), getColumns());
//        }
        setRecyclerView(getRows(), getColumns());
    }

    public void setRows(int mRows, boolean setViewValue, boolean savePrefs) {
        this.mRows = mRows;
        if (mBinding != null && mRows >= 0 && setViewValue) {
            mBinding.viewTietRows.setText(String.valueOf(mRows));
        }
        if (savePrefs) {
            SharedPrefs.saveInt(this, AppConstants.STR_ROWS, mRows);
        }
    }

    public void setColumns(int mColumns, boolean setViewValue, boolean savePrefs) {
        this.mColumns = mColumns;
        if (mBinding != null && mColumns >= 0 && setViewValue) {
            mBinding.viewTietColumns.setText(String.valueOf(mColumns));
        }
        if (savePrefs) {
            SharedPrefs.saveInt(this, AppConstants.STR_COLUMNS, mColumns);
        }
    }

    public void setRandomNumber(int randomNumber, boolean setViewValue, boolean savePrefs) {
        this.randomNumber = randomNumber;
        if (setViewValue) {
            mBinding.viewTvRandomNumber.setText(String.valueOf(randomNumber));
        }
        if (savePrefs) {
            SharedPrefs.saveInt(this, AppConstants.STR_RANDOM_NUMBER, randomNumber);
        }
    }

    public void setRandomColor(int randomColor, boolean setViewValue, boolean savePrefs) {
        this.randomColor = randomColor;
        if (setViewValue) {
            mBinding.viewTvRandomColor.setText(String.valueOf(randomColor));
        }
        if (savePrefs) {
            SharedPrefs.saveInt(this, AppConstants.STR_RANDOM_COLOR, randomColor);
        }
    }

    private void setRecyclerView(int rows, int columns) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, columns != 0 ? columns : 5, columns > rows ? RecyclerView.HORIZONTAL : RecyclerView.VERTICAL, false);
        mBinding.viewRv.setLayoutManager(gridLayoutManager);
        RvMatrixAdapter rvMatrixAdapter = new RvMatrixAdapter(this, mList);
        mBinding.viewRv.setAdapter(rvMatrixAdapter);
    }

    public int getRows() {
        return mRows;
    }

    public int getColumns() {
        return mColumns;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_mbtn_apply:
                if (isValidInput()) {
                    setRecyclerView();
                }
                break;
            case R.id.view_mbtn_random:
                if (isValidInput()) {
                    if (Utils.isNotNullNotEmpty(mList)) {
                        generateRandomNumber();
                    } else {
                        showMessage(getString(R.string.st_error_random_number_cannot_be_generated_before_matrix));
                    }
                }
                break;
        }
    }

    private boolean isValidInput() {
        if (!ViewUtils.hasTextValue(mBinding.viewTietRows)) {
            showMessage(getString(R.string.st_error_row_can_not_be_empty));
            return false;
        } else if (!ViewUtils.hasTextValue(mBinding.viewTietColumns)) {
            showMessage(getString(R.string.st_error_column_can_not_be_empty));
            return false;
        }
        return true;
    }

    private void setRecyclerView() {
        setRows(Integer.parseInt(StringUtils.getString(mBinding.viewTietRows, "0")), false, true);
        setColumns(Integer.parseInt(StringUtils.getString(mBinding.viewTietColumns, "0")), false, true);
        setRecyclerView(getRows(), getColumns());
    }

    private void generateRandomNumber() {
        int randomNumber = Utils.getRandomNumber(getRows(), getColumns());
        setRandomNumber(randomNumber, true, true);

        int randomColor = Utils.getRandomHSVColor();
        setRandomColor(randomColor, true, true);
    }

    private void showMessage(String message) {
        Snackbar.make(mBinding.viewCoor, message, Snackbar.LENGTH_LONG).show();
    }
}
