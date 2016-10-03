package ca.ualberta.cs.habittracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;

import ca.ualberta.cs.habittracker.entity.Habit;
import ca.ualberta.cs.habittracker.entity.WEEK;
import ca.ualberta.cs.habittracker.util.FileReadAndWriteUtil;

public class NewHabitActivity extends Activity implements View.OnClickListener{


    //  Mon, Tues, Wed, Thurs, Fri, Sat, Sun
    private EditText etName;
    private CheckBox cbMon, cbTues, cbWed, cbThurs, cbFri, cbSat, cbSun;

    ArrayList<Habit> mHabits = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_habit);
        mHabits = (ArrayList<Habit>) getIntent().getSerializableExtra("habits");
        initView();
    }

    private void initView() {
        etName = (EditText) findViewById(R.id.et_name);
        cbMon = (CheckBox) findViewById(R.id.cb_mon);
        cbTues = (CheckBox) findViewById(R.id.cb_tue);
        cbWed = (CheckBox) findViewById(R.id.cb_wed);
        cbThurs = (CheckBox) findViewById(R.id.cb_thu);
        cbFri = (CheckBox) findViewById(R.id.cb_fri);
        cbSat = (CheckBox) findViewById(R.id.cb_sat);
        cbSun = (CheckBox) findViewById(R.id.cb_sun);
    }

    @Override
    public void onClick(View v) {
        Intent resultDate = new Intent();
        switch (v.getId()) {
            case R.id.btn_save:
                ArrayList<WEEK> weeks = new ArrayList<WEEK>();
                String habitName = etName.getText().toString().trim();
                if (cbMon.isChecked())
                    weeks.add(WEEK.Mon);
                if (cbTues.isChecked())
                    weeks.add(WEEK.Tues);
                if (cbWed.isChecked())
                    weeks.add(WEEK.Wed);
                if (cbThurs.isChecked())
                    weeks.add(WEEK.Thurs);
                if (cbFri.isChecked())
                    weeks.add(WEEK.Fri);
                if (cbSat.isChecked())
                    weeks.add(WEEK.Sat);
                if (cbSun.isChecked())
                    weeks.add(WEEK.Sun);
                Habit habit = new Habit(habitName, new Date());
                habit.setWeeks(weeks);
                FileReadAndWriteUtil fileReadAndWriteUtil = new FileReadAndWriteUtil();
                if (mHabits == null)
                    mHabits = new ArrayList<Habit>();
                int id = mHabits.size() + 1;
                habit.setId(id);

                mHabits.add(habit);
                fileReadAndWriteUtil.writeData(mHabits);

                resultDate.putExtra("habit", habit);

                setResult(RESULT_OK, resultDate);
                finish();
                break;
        }
    }
}
