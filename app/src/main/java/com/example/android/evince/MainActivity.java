package com.example.android.evince;

import android.os.Bundle;
import android.view.View;

import com.example.android.evince.adapter.RvMatrixAdapter;
import com.example.android.evince.apputils.AppUtils;
import com.example.android.evince.constants.AppConstants;
import com.example.android.evince.database.AppDatabase;
import com.example.android.evince.databinding.ActivityMainBinding;
import com.example.android.evince.pojo.Matrix;
import com.example.android.evince.utils.SharedPrefs;
import com.example.android.evince.utils.StringUtils;
import com.example.android.evince.utils.Utils;
import com.example.android.evince.viewutils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding mBinding;
    private int mRows;
    private int mColumns;
    private int randomColor;
    private int randomNumber;
    private List<Matrix> mList = new ArrayList<>();
    private List<Integer> randomList = new ArrayList<>();
    private int position;
    private RvMatrixAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        handleViews();
        ViewUtils.setOnClickListener(this, mBinding.viewMbtnApply, mBinding.viewMbtnRandom);
    }

    private void handleViews() {
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
        if (Utils.isNotNullNotEmpty(AppDatabase.getInstance(this).getAppDao().getAllMatrices())) {
            mList = AppDatabase.getInstance(this).getAppDao().getAllMatrices();
            randomList = AppUtils.shuffleList(mList);
            if (getRandomNumber() != -1) {
                position = randomList.indexOf(getRandomNumber());
                // comment by srdpatel: 11/5/2019 If no matched number found, reset position to 0 to prevent indexOutOfBound
                if (position == -1) {
                    position = 0;
                }
            }
        } else {
            mList = AppUtils.getMatrix(getRows(), getColumns());
            randomList = AppUtils.shuffleList(mList);
        }
        setRecyclerView(getRows(), getColumns());
        if (getRandomNumber() != -1 && getRandomColor() != -1) {
            highlightRandomMatch();
        }
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
            mBinding.viewTvRandomColor.setTextColor(randomColor);
        }
        if (savePrefs) {
            SharedPrefs.saveInt(this, AppConstants.STR_RANDOM_COLOR, randomColor);
        }
    }

    public int getRandomNumber() {
        return randomNumber;
    }

    public int getRows() {
        return mRows;
    }

    public int getColumns() {
        return mColumns;
    }

    private void setRecyclerView(int rows, int columns) {
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

    public int getRandomColor() {
        return randomColor;
    }

    private void highlightRandomMatch() {
        if (mAdapter != null) {
            mAdapter.clearSelection();
            mAdapter.highlightItem(getRandomNumber(), getRandomColor());
        }
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
                    if (Utils.isNotNullNotEmpty(mList) && Utils.isNotNullNotEmpty(randomList)) {
                        generateRandomNumber();
                    } else {
                        showMessage(getString(R.string.st_error_random_number_cannot_be_generated_before_matrix));
                    }
                }
                break;
        }
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

    private void setRecyclerView() {
        setRows(Integer.parseInt(StringUtils.getString(mBinding.viewTietRows, "0")), false, true);
        setColumns(Integer.parseInt(StringUtils.getString(mBinding.viewTietColumns, "0")), false, true);
        mList = AppUtils.getMatrix(getRows(), getColumns());
        randomList = AppUtils.shuffleList(mList);
        setRecyclerView(getRows(), getColumns());
    }

    private void generateRandomNumber() {
        if (Utils.hasElement(randomList, position)) {
            int randomNumber = randomList.get(position);
            setRandomNumber(randomNumber, true, true);

            int randomColor = Utils.getRandomColor();
            setRandomColor(randomColor, true, true);
            highlightRandomMatch();
            position++;
        } else {
            showMessage(getString(R.string.st_error_no_more_unique_random_number_left));
            position = 0;
            randomList = AppUtils.shuffleList(mList);
            generateRandomNumber();
        }
    }

    private void showMessage(String message) {
        makeText(this, message, LENGTH_SHORT).show();
    }
}
