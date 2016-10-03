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
import ca.ualberta.cs.habittracker.util.DateUtil;
import ca.ualberta.cs.habittracker.util.FileReadAndWriteUtil;

public class HistoryActivity extends Activity implements View.OnClickListener{

    public static final String LOG_TAG = "HistoryActivity";
    private ArrayList<Habit> saveHabits = null;

    private ListView mListView;
    private HabitAdapter mHabitAdapter;
    private FileReadAndWriteUtil mFileReadAndWriteUtil = new FileReadAndWriteUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        SysApplication.getInstance().addActivity(this);
        initView();
    }

    private void initView() {
        if (!mFileReadAndWriteUtil.checkDataFileExists()) {
            saveHabits = new ArrayList<Habit>();
        } else {
            saveHabits = mFileReadAndWriteUtil.readData();
        }
        Log.d(LOG_TAG, saveHabits.size() + "");
        mListView = (ListView) findViewById(R.id.listView);

        mHabitAdapter = new HabitAdapter(this, R.layout.simple_completion_habit_item, saveHabits);
        mListView.setAdapter(mHabitAdapter);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_now:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
        }
    }

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
                holder.tv_date = (TextView) convertView.findViewById(R.id.tv_habit_item_date);
                holder.tv_count = (TextView) convertView.findViewById(R.id.tv_habit_item_count);
                holder.btn_recover = (ImageButton) convertView.findViewById(R.id.btn_recover_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_name.setText("Habit Name:" + habits.get(position).getName());
            holder.tv_date.setText("Create Date:" + DateUtil.getFormatDateString(habits.get(position).getDate()));
            holder.tv_count.setText("Completion Times:" + habits.get(position).getCount() + " ");
            holder.btn_recover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Habit currhabit = habits.get(position);
                    for (Habit habit : saveHabits) {
                        if (habit.getId() == currhabit.getId())
                            habit.setValid(true);
                    }
                    mFileReadAndWriteUtil.writeData(saveHabits);
                }
            });
            return convertView;
        }

        public final class ViewHolder {
            public TextView tv_name, tv_date, tv_count;
            public ImageButton btn_recover;
        }
    }

    @Override
    public void onBackPressed() {
        SysApplication.getInstance().exitApplication();
    }
}
