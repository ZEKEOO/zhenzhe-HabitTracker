package ca.ualberta.cs.habittracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ualberta.cs.habittracker.entity.Habit;
import ca.ualberta.cs.habittracker.util.FileReadAndWriteUtil;

public class MainActivity extends Activity implements View.OnClickListener {

    public static final String LOG_TAG = "MainActivity";
    private static int REQUEST_ADD_NEW_HABIT_CODE = 1;
    private ArrayList<Habit> saveHabits = null; /* Stored habit data */
    private ArrayList<Habit> showHabits = null; /* Showed habit data */

    private ListView mListView;
    private HabitAdapter mHabitAdapter;
    private FileReadAndWriteUtil mFileReadAndWriteUtil = new FileReadAndWriteUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        /* Put this activity into SysApplication and manage */
        SysApplication.getInstance().addActivity(this);
        /* Delete the existing data */
        /*String[] file_list = fileList();
        for (int i = 1; i < file_list.length; ++i) {
            deleteFile(file_list[i]);
        }*/
    }

    /* Initialize View */
    private void initView() {
        if (!mFileReadAndWriteUtil.checkDataFileExists()) {
            saveHabits = new ArrayList<Habit>();
        } else {
            saveHabits = mFileReadAndWriteUtil.readData();
        }
        Log.d(LOG_TAG, saveHabits.size() + "");
        mListView = (ListView) findViewById(R.id.listView);

        showHabits = new ArrayList<Habit>();
        for (Habit habit : saveHabits) {
            if (habit.getValid()) {
                showHabits.add(habit);
            }
        }
        mHabitAdapter = new HabitAdapter(this, R.layout.simple_habit_item, showHabits);
        mListView.setAdapter(mHabitAdapter);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_add:
                /* Jump to the habit page */
                intent = new Intent(this, NewHabitActivity.class);
                intent.putExtra("habits", saveHabits);
                startActivityForResult(intent, REQUEST_ADD_NEW_HABIT_CODE);
                break;
            case R.id.btn_history:
                /* Jump to the history habit page */
                intent = new Intent(this, HistoryActivity.class);
                startActivity(intent);
                finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /* Accept new data */
        if (requestCode == REQUEST_ADD_NEW_HABIT_CODE && resultCode == RESULT_OK) {
            Habit habit = (Habit) data.getSerializableExtra("habit");
            saveHabits.add(habit);
            showHabits.add(habit);
            mHabitAdapter.notifyDataSetChanged();
        }

    }

    /* Data */
    class HabitAdapter extends ArrayAdapter<Habit> {

        private int resourceId;
        private LayoutInflater mInflater;
        private ArrayList<Habit> habits;

        public HabitAdapter(Context context, int resource, ArrayList<Habit> objects) {
            super(context, resource, objects);
            resourceId = resource;
            mInflater = LayoutInflater.from(context);
            habits = objects;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(resourceId, null);
                holder.tv_name = (TextView) convertView.findViewById(R.id.tv_habit_item_name);
                holder.tv_weeks = (TextView) convertView.findViewById(R.id.tv_habit_item_weeks);
                holder.btn_add_count = (ImageButton) convertView.findViewById(R.id.btn_add_count);
                holder.btn_delete_item = (ImageButton) convertView.findViewById(R.id.btn_delete_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_name.setText(habits.get(position).getName());
            holder.tv_weeks.setText(habits.get(position).getWeeks().toString());
            holder.btn_add_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Habit currHabit = habits.get(position);
                    for (Habit habit : saveHabits) {
                        if (habit.getId() == currHabit.getId()) {
                            habit.setCount(habit.getCount() + 1);
                        }
                    }
                    mFileReadAndWriteUtil.writeData(saveHabits);
                    showHabits.remove(position);
                    mHabitAdapter.notifyDataSetChanged();
                }
            });
            holder.btn_delete_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Habit currHabit = habits.get(position);
                    for (Habit habit : saveHabits) {
                        if (habit.getId() == currHabit.getId()) {
                            habit.setValid(false);
                        }
                    }
                    mFileReadAndWriteUtil.writeData(saveHabits);
                    showHabits.remove(position);
                    mHabitAdapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }

        public final class ViewHolder {
            public TextView tv_name, tv_weeks;
            public ImageButton btn_add_count, btn_delete_item;
        }
    }

    @Override
    public void onBackPressed() {
        SysApplication.getInstance().exitApplication();
    }
}
