package com.project.stopdistraction.ui;

import static com.project.stopdistraction.common.Constants.TAG_FRAGMENT_ADD_DAILYTASK;
import static com.project.stopdistraction.common.Constants.TAG_FRAGMENT_ADD_TODOTASK;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.project.stopdistraction.R;
import com.project.stopdistraction.adapter.CategoryAdapter;
import com.project.stopdistraction.common.MyApplication;
import com.project.stopdistraction.common.UIHelper;
import com.project.stopdistraction.model.DailyTaskList;
import com.project.stopdistraction.roomdb.DailyTask;
import com.project.stopdistraction.roomdb.TodoDao;
import com.project.stopdistraction.roomdb.TodoTask;
import com.project.stopdistraction.roomdb.UserDao;
import com.project.stopdistraction.roomdb.UserDatabase;

import java.util.ArrayList;
import java.util.List;

public class TodoTaskFragment extends Fragment {

    ArrayList<DailyTaskList> CategoryList =new ArrayList<>();
    RecyclerView rvVendor;
    EditText edaddNewTask;
    List<TodoTask> mylist = new ArrayList<>();
    public TodoTaskFragment() {
        // Required empty public constructor
    }


    public static TodoTaskFragment newInstance() {
        TodoTaskFragment fragment = new TodoTaskFragment();
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
        View view = inflater.inflate(R.layout.fragment_todo_task, container, false);
        rvVendor=(RecyclerView) view.findViewById(R.id.recycleProduct);
        edaddNewTask=(EditText) view.findViewById(R.id.edadd);

        edaddNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.getInstance().replaceFragment(getContext(), AddNewTodoTaskFragment.newInstance(), TAG_FRAGMENT_ADD_TODOTASK, R.id.home_container,
                        true, UIHelper.CustomAnimationType.CUSTOM_ANIMATION_LEFT_AND_RIGHT);
            }
        });
        showuserAsyctask task = new showuserAsyctask();
        task.execute();

        return view;
    }
    private static class updateuserAsyncTask extends AsyncTask<Void, Void, Boolean> {
        private int id;
        private boolean status;

        private TodoTask new_dailyTask;
        TodoDao userDao;
        private Boolean is_new;

        private updateuserAsyncTask(int id, boolean status) {

            this.status = status;
            this.id = id;

            UserDatabase db = UserDatabase.getInstance(MyApplication.getAppContext());
            this.userDao = db.todoDao();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

                userDao.updateStatus(this.id, this.status);
                is_new =true;
            return is_new;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

        }
    }

        public class showuserAsyctask extends AsyncTask<Void,Void,Void>
    {
        TodoDao todoDao;

        public showuserAsyctask() {
            UserDatabase db = UserDatabase.getInstance(getActivity().getApplication());
            this.todoDao = db.todoDao();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... voids) {
            String str = null;
            CategoryList = new ArrayList<>();
            mylist = todoDao.getAllUsers();
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

    public static void updateTask(int id,boolean status){
        updateuserAsyncTask task = new updateuserAsyncTask(id,status);
        task.execute();
    }
}