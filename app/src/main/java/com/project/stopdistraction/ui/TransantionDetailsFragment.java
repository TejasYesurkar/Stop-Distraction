package com.project.stopdistraction.ui;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.stopdistraction.R;
import com.project.stopdistraction.adapter.TranscationMessageAdapter;
import com.project.stopdistraction.roomdb.Message;
import com.project.stopdistraction.roomdb.MessageDao;
import com.project.stopdistraction.roomdb.UserDatabase;

import java.util.ArrayList;
import java.util.List;

public class TransantionDetailsFragment extends Fragment {
    ArrayList<Message> CategoryList =new ArrayList<>();
    RecyclerView rvVendor;
    List<Message> mylist = new ArrayList<>();
    public TransantionDetailsFragment() {
        // Required empty public constructor
    }

    public static TransantionDetailsFragment newInstance() {
        TransantionDetailsFragment fragment = new TransantionDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transantion_details, container, false);
        rvVendor=(RecyclerView) view.findViewById(R.id.recycleProduct);

        showuserAsyctask task = new showuserAsyctask();
        task.execute();


        return view;

    }

    public  class showuserAsyctask extends AsyncTask<Void,Void,Void>
    {
        MessageDao messageDao;

        public showuserAsyctask() {
            UserDatabase db = UserDatabase.getInstance(getContext());
            this.messageDao = db.msgDao();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... voids) {
            String str = null;
            CategoryList = new ArrayList<>();
            mylist = messageDao.getAllUsers();
            for (int i=0;i< mylist.size();i++)
            {
                CategoryList.add(new Message(mylist.get(i).getMessage(),mylist.get(i).getMessageType(),mylist.get(i).getDate(),mylist.get(i).getSenderName()));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            TranscationMessageAdapter adapter = new TranscationMessageAdapter(CategoryList,getContext());
            rvVendor.setLayoutManager(layoutManager);
            rvVendor.setAdapter(adapter);
        }
    }
}