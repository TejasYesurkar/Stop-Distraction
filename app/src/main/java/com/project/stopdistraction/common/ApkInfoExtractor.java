package com.project.stopdistraction.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.project.stopdistraction.R;

import java.util.ArrayList;
import java.util.List;

public class ApkInfoExtractor {

    Context context1;

    public ApkInfoExtractor(Context context2){

        context1 = context2;
    }

    public List<String> GetAllInstalledApkInfo(){

        List<String> ApkPackageName = new ArrayList<>();

//        Intent intent = new Intent(Intent.ACTION_MAIN,null);
//
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED );
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = context1.getPackageManager().queryIntentActivities( mainIntent, 0);

//        List<ResolveInfo> resolveInfoList = context1.getPackageManager().queryIntentActivities(intent,0);
        List<PackageInfo> resolveInfoList = context1.getPackageManager().getInstalledPackages(0);
        for(PackageInfo resolveInfo : resolveInfoList){
//
            ApplicationInfo activityInfo = resolveInfo.applicationInfo;
//
//            if(!isSystemPackage(resolveInfo)){

                ApkPackageName.add(activityInfo.packageName);
            }
//        }

        return ApkPackageName;

    }


    public boolean isSystemPackage(ResolveInfo resolveInfo){

        return ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    public Drawable getAppIconByPackageName(String ApkTempPackageName){

        Drawable drawable;

        try{
            drawable = context1.getPackageManager().getApplicationIcon(ApkTempPackageName);

        }
        catch (PackageManager.NameNotFoundException e){

            e.printStackTrace();

            drawable = ContextCompat.getDrawable(context1, R.mipmap.ic_launcher);
        }
        return drawable;
    }

    public String GetAppName(String ApkPackageName){

        String Name = "";

        ApplicationInfo applicationInfo;

        PackageManager packageManager = context1.getPackageManager();

        try {

            applicationInfo = packageManager.getApplicationInfo(ApkPackageName, 0);

            if(applicationInfo!=null){

                Name = (String)packageManager.getApplicationLabel(applicationInfo);
            }

        }catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }
        return Name;
    }
}