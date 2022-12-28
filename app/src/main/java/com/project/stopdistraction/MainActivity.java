package com.project.stopdistraction;

import static com.project.stopdistraction.common.Constants.TAG_FRAGMENT_HOME;
import static com.project.stopdistraction.common.Constants.TAG_FRAGMENT_INFO_SRC;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.project.stopdistraction.common.UIHelper;
import com.project.stopdistraction.ui.HomeFragment;
import com.project.stopdistraction.ui.InformationSrcFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    private LinearLayout ll_nav_drawer_menu;
    private DrawerLayout drawer;
    LinearLayout ll_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll_back = findViewById(R.id.ll_back);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        ll_nav_drawer_menu = (LinearLayout) findViewById(R.id.ll_nav_drawer_menu);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);

//        UIHelper.getInstance().replaceFragment(this, FormsLinkListFragment.newInstance(), TAG_FRAGMENT_FORMS_LINK_LIST,R.id.home_container,
//                true,UIHelper.CustomAnimationType.CUSTOM_ANIMATION_LEFT_AND_RIGHT);

        //Drawer Close and Open
        ll_nav_drawer_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });

        ll_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case R.id.nav_home:
                UIHelper.getInstance().replaceFragment(this, HomeFragment.newInstance(), TAG_FRAGMENT_HOME, R.id.home_container,
                        true, UIHelper.CustomAnimationType.CUSTOM_ANIMATION_LEFT_AND_RIGHT);
                drawer.closeDrawer(GravityCompat.END);
                break;
            case R.id.nav_part:
                Toast.makeText(this, "Part", Toast.LENGTH_SHORT).show();
//                UIHelper.getInstance().replaceFragment(this, ListOfProductFragment.newInstance(), TAG_FRAGMENT_List_OF_PART ,R.id.home_container,
//                        true,UIHelper.CustomAnimationType.CUSTOM_ANIMATION_LEFT_AND_RIGHT);
                drawer.closeDrawer(GravityCompat.END);
            case R.id.nav_info_src:
                UIHelper.getInstance().replaceFragment(this, InformationSrcFragment.newInstance(), TAG_FRAGMENT_INFO_SRC, R.id.home_container,
                        true, UIHelper.CustomAnimationType.CUSTOM_ANIMATION_LEFT_AND_RIGHT);
                drawer.closeDrawer(GravityCompat.END);
                break;
        }
        return true;
    }
}