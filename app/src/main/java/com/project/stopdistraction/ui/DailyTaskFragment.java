package com.project.stopdistraction.ui;

import static com.project.stopdistraction.common.Constants.TAG_FRAGMENT_ADD_DAILYTASK;
import static com.project.stopdistraction.common.Constants.TAG_FRAGMENT_DAILY_TASK;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.project.stopdistraction.R;
import com.project.stopdistraction.adapter.CategoryAdapter;
import com.project.stopdistraction.common.UIHelper;
import com.project.stopdistraction.model.DailyTaskList;
import com.project.stopdistraction.model.category_row;
import com.project.stopdistraction.roomdb.DailyTask;
import com.project.stopdistraction.roomdb.UserDao;
import com.project.stopdistraction.roomdb.UserDatabase;

import java.util.ArrayList;
import java.util.List;

public class DailyTaskFragment extends Fragment {
    ArrayList<DailyTaskList> CategoryList =new ArrayList<>();
    RecyclerView rvVendor;
    EditText edaddNewTask;
    List<DailyTask> mylist = new ArrayList<>();
    public DailyTaskFragment() {
        // Required empty public constructor
    }
    public static DailyTaskFragment newInstance() {
        DailyTaskFragment fragment = new DailyTaskFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_task, container, false);
        rvVendor=(RecyclerView) view.findViewById(R.id.recycleProduct);
        edaddNewTask=(EditText) view.findViewById(R.id.edadd);

        edaddNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.getInstance().replaceFragment(getContext(), AddNewDailyTaskFragment.newInstance(), TAG_FRAGMENT_ADD_DAILYTASK, R.id.home_container,
                        true, UIHelper.CustomAnimationType.CUSTOM_ANIMATION_LEFT_AND_RIGHT);
            }
        });
        showuserAsyctask task = new showuserAsyctask();
        task.execute();

        return view;
    }

    public  class showuserAsyctask extends  AsyncTask<Void,Void,Void>
    {
        UserDao userDao;

        public showuserAsyctask() {
            UserDatabase db = UserDatabase.getInstance(getActivity().getApplication());
            this.userDao = db.userDao();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... voids) {
            String str = null;
            CategoryList = new ArrayList<>();
            mylist = userDao.getAllUsers();
            for (int i=0;i<mylist.size();i++)
            {
                CategoryList.add(new DailyTaskList(mylist.get(i).getId(),mylist.get(i).getTask(),
                        mylist.get(i).getCategory(),mylist.get(i).getTime(),mylist.get(i).getDate(),mylist.get(i).isStatus()));

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
            CategoryAdapter adapter = new CategoryAdapter(CategoryList,getActivity());
            rvVendor.setLayoutManager(layoutManager);
            rvVendor.setAdapter(adapter);
        }
    }
}