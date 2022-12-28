package com.project.stopdistraction.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.project.stopdistraction.R;

import java.util.List;


/**
 * Common class to handle all UI related components
 */
public class UIHelper {

    /**
     * static instance for UIHelper
     */
    private static @Nullable
    UIHelper helper = null;


    /**
     * Flag to store the state of the screen and is useful to decide when we need to call the full screen method
     */
    public boolean isFullScreen = false;

    /**
     * Dialog instance to manage animated loader
     */
    private Dialog dialog;
    /**
     * Alert dialog instance for force update popup
     */
    private AlertDialog mAlertDialog;
    /**
     * SSL Error Dialog
     */
    private Dialog dialogSSL = null;
    /**
     * Alert dialog to display fingerprint popups
     */
    private AlertDialog alertDialogFingerprintDialog = null;
    /**
     * Alert dialog for session timeout popup
     */
    private AlertDialog alertDialogSessionTimeout;
    /**
     * Alert dialog for all generic popup
     */
    private AlertDialog alertDialogGeneric;

    /**
     * @return - returns single instance of UIHelper
     */
    public static UIHelper getInstance() {
        if (helper == null)
            helper = new UIHelper();
        return helper;
    }

    /**
     * This method hides the keyboard
     *
     * @param context Application context
     */
    private void hideKeyboard(@NonNull Context context) {
        // Check if no view has focus:
        AppCompatActivity appCompatActivity = (AppCompatActivity) context;
        View view = appCompatActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) appCompatActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    /**
     * This method replaces the given fragment in the given layout ID
     *
     * @param context     Activity Context
     * @param fragment    Fragment to be replaces
     * @param tag         Tag to identify the fragment by {@link FragmentManager}
     * @param containerID Layout ID in which the fragment will be replaced
     * @param isResumed   Returns true when the fragment is visible to the user and actively running else false
     */
    public void replaceFragment(Context context,
                                Fragment fragment,
                                String tag,
                                int containerID,
                                boolean isResumed, CustomAnimationType customAnimationType) {
        replaceFragment(context, fragment, tag, containerID, true, isResumed, customAnimationType);
    }

    /**
     * This method replaces the given fragment in the given layout ID
     *
     * @param context        Activity Context
     * @param fragment       Fragment to be replaces
     * @param tag            Tag to identify the fragment by {@link FragmentManager}
     * @param containerID    Layout ID in which the fragment will be replaced
     * @param addToBackStack true if needs to add in back stack, else false
     * @param isResumed      Returns true when the fragment is visible to the user and actively running else false
     */
    public void replaceFragment(Context context,
                                Fragment fragment,
                                String tag,
                                int containerID,
                                boolean addToBackStack,
                                boolean isResumed, CustomAnimationType customAnimationType) {
        try {
            UIHelper.getInstance().hideKeyboard(context);
            if (isResumed) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) context;
                FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (addToBackStack)
                    fragmentTransaction.addToBackStack(tag);
                else
                    fragmentTransaction.addToBackStack(null);
                //To apply different transaction animations at one place
                switch (customAnimationType) {
                    case CUSTOM_ANIMATION_NORMAL:
                        break;
                    case CUSTOM_ANIMATION_LEFT_AND_RIGHT:
                        fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.right_out, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        break;
                    case CUSTOM_ANIMATION_BOTTOM_TO_TOP:
                        fragmentTransaction.setCustomAnimations(R.anim.push_up_in, android.R.anim.fade_out, android.R.anim.fade_in, R.anim.push_down_in);
                        break;
                }
                fragmentTransaction.replace(containerID, fragment, tag);
                fragmentTransaction.commit();
            }
        } catch (Exception ex) {
            Logger.getInstance().printExceptionStackTrace(ex);
        }
    }

    /**
     * This method add the given fragment in the given layout ID with Slide UP/ Slide Left /Normal Animation
     *
     * @param context             Activity Context
     * @param fragment            Fragment to be replaces
     * @param tag                 Tag to identify the fragment by {@link FragmentManager}
     * @param isResumed           is fragment is resumed.
     * @param customAnimationType animation type
     */
    public void addFragmentWithTransitionAnimation(Context context,
                                                   Fragment fragment,
                                                   String tag, boolean isResumed, CustomAnimationType customAnimationType) {
        try {
            UIHelper.getInstance().hideKeyboard(context);
            if (isResumed) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) context;
                FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(tag);

                //To apply different transaction animations at one place
                switch (customAnimationType) {
                    case CUSTOM_ANIMATION_NORMAL:
                        break;
                    case CUSTOM_ANIMATION_LEFT_AND_RIGHT:
                        fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.right_out, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        break;
                    case CUSTOM_ANIMATION_BOTTOM_TO_TOP:
                        fragmentTransaction.setCustomAnimations(R.anim.push_up_in, android.R.anim.fade_out, android.R.anim.fade_in, R.anim.push_down_in);
                        break;
                }
                fragmentTransaction.add(R.id.home_container, fragment, tag);
                fragmentTransaction.commit();
            }
        } catch (Exception ex) {
            Logger.getInstance().printExceptionStackTrace(ex);
        }
    }

    /**
     * This method replaces the given fragment in the given layout ID
     *
     * @param context     Activity Context
     * @param fragment    Fragment to be replaces
     * @param tag         Tag to identify the fragment by {@link FragmentManager}
     * @param containerID Layout ID in which the fragment will be replaced
     * @param isResumed   Returns true when the fragment is visible to the user and actively running else false
     * @param requestCode onActivityResults request code to read incoming data from fragment
     */
    public void addFragmentWithTarget(Context context,
                                      Fragment fragment,
                                      Fragment targetFragment,
                                      String tag,
                                      int containerID,
                                      boolean isResumed,
                                      int requestCode) {
        try {
            UIHelper.getInstance().hideKeyboard(context);
            if (isResumed) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) context;
                FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(tag);
                fragment.setTargetFragment(targetFragment, requestCode);
                fragmentTransaction.add(containerID, fragment, tag);
                fragmentTransaction.commit();
            }
        } catch (Exception e) {
            Logger.getInstance().printExceptionStackTrace(e);
        }
    }

    /**
     * clear the back stack up to n number of steps, where n is defined by the count
     *
     * @param context application context
     * @param count   number of steps that needs to be popped
     */
    private void popBackStack(Context context, int count) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) context;
        FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
        for (int index = 1; index < count; index++) {
            fragmentManager.popBackStack();
        }
        fragmentManager.beginTransaction().commit();
    }

    /**
     * Pop fragment upto given tag
     *
     * @param context Current activity context
     * @param tag     Fragment tag upto which we want to pop
     */
    public void popFragmentUpto(Context context, String tag) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) context;
        if (appCompatActivity != null) {
            appCompatActivity.getSupportFragmentManager().popBackStack(tag, 0);
        }
    }

    /**
     * Method to check if fragment is present in backstack or not
     * Navigate user by checking backstack
     *
     * @param context Current activity context
     * @param tag     Fragment tag
     * @return returns boolean true/false
     */
    public boolean isFragmentPresentInBackStack(Context context, String tag) { AppCompatActivity appCompatActivity = (AppCompatActivity) context;
        if (appCompatActivity != null) {
            Fragment fragmentA = appCompatActivity.getSupportFragmentManager().findFragmentByTag(tag);
            return fragmentA != null;
        }
        return false;
    }

    /**
     * Clear the back stack completely
     *
     * @param context application context
     */
    public void popBackStack(Context context) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) context;
        popBackStack(context, appCompatActivity.getSupportFragmentManager().getBackStackEntryCount());
    }


    public boolean checkIfFragmentPresentInBackStack(Context context, String tag) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) context;
        if (appCompatActivity != null) {
            return appCompatActivity.getSupportFragmentManager().findFragmentByTag(tag) != null;
        }
        return false;
    }



    /**
     * This method is used to hide soft input keyboard
     *
     * @param context - context of activity
     */
    public void hideKeyBoard(Context context) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) context;
        if (appCompatActivity != null) {
            // Check if no view has focus:
            View view = appCompatActivity.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    /**
     * This method is used to show soft input keyboard
     *
     * @param context - context of activity
     */
    public void showKeyboard(Context context) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) context;
        if (appCompatActivity != null) {
            // Check if no view has focus:
            View view = appCompatActivity.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        }
    }

    /**
     * Common method to hide loader after API call successful or failure
     */
    public void hideAnimatedLoader() {
        try {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    dialog.cancel();
                    dialog = null;
                }
            }
        } catch (final Exception e) {
            Logger.getInstance().printExceptionStackTrace(e);
        } finally {
            this.dialog = null;
        }
    }

    /**
     * Method called to open URL in browser
     *
     * @param context   - context of activity
     * @param urlString - URL to open
     */
    public void openUrl(Context context, String urlString) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        try {
            context.startActivity(intent);
        } catch (Exception ex) {
            Logger.getInstance().printExceptionStackTrace(ex);
        }
    }

    /**
     * Fingerprint messages dialog
     *
     * @param context context
     * @param title   dialog title
     * @param msg     message to be given
     */
    public void showDialogFingerprintMessages(final Context context,
                                              String title,
                                              final String msg,
                                              boolean isResumed) {
        if (isResumed) {

            if (alertDialogFingerprintDialog != null) {
                alertDialogFingerprintDialog.dismiss();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(true);
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.setPositiveButton(android.R.string.ok,
                    (dialog, which) -> dialog.dismiss());
            alertDialogFingerprintDialog = builder.create();
            alertDialogFingerprintDialog.show();
        }
    }

    /**
     * Dismiss fingerprint dialog
     */
    public void dismissFingerprintDialog() {
        if (alertDialogFingerprintDialog != null)
            alertDialogFingerprintDialog.dismiss();
    }

    /**
     * to convert string in Camelcase
     *
     * @param string which need to cconvert in camelcase
     * @return string cnvertedin camelcase
     */
    public String camelCase(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        int strLength = chars.length;
        for (int i = 0; i < strLength; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }

    /**
     * Method called to show full screen
     *
     * @param context - Activity context
     */
    public void showFullScreen(Context context) {
        if (context != null) {
            showFullScreen((AppCompatActivity) context);
        }
    }

    /**
     * Method called to show full screen
     *
     * @param activity - Activity context
     */
    public void showFullScreen(Activity activity) {
        if (activity != null) {
            View decorView = activity.getWindow().getDecorView();

            int newUiOptions = decorView.getSystemUiVisibility();
            newUiOptions ^= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

            decorView.setSystemUiVisibility(newUiOptions);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.setStatusBarColor(Color.TRANSPARENT);
            }
            isFullScreen = true;
        }
    }

    /**
     * Method called to exit full screen
     *
     * @param context - Activity context
     */
    public void exitFromFullScreen(Context context) {
        if (context != null) {
            exitFromFullScreen((AppCompatActivity) context);
        }
    }

    /**
     * Method called to exit full screen
     *
     * @param activity - Activity context
     */
    public void exitFromFullScreen(Activity activity) {
        if (activity != null) {
            View decorView = activity.getWindow().getDecorView();
            int newUiOptions = decorView.getSystemUiVisibility();
            newUiOptions ^= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(newUiOptions);
            // Set toolbar color
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.setStatusBarColor(activity.getResources().getColor(R.color.bg_dashboard_main));
            }
            isFullScreen = false;
        }
    }


    /**
     * Hide full screen only needed on fullscreen
     *
     * @param activityContext - Activity context
     */
    public void hideFullScreen(Activity activityContext) {
        View decorView = activityContext.getWindow().getDecorView();
        int newUiOptions = decorView.getSystemUiVisibility();
        newUiOptions ^= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(newUiOptions);
        // Set toolbar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activityContext.getWindow();
            window.setStatusBarColor(activityContext.getResources().getColor(R.color.bg_dashboard_main));
        }
        isFullScreen = false;
    }

    /**
     * This method checks if the application is in foreground or background
     *
     * @param context application context
     * @return true if app is in foreground, false otherwise
     */
    public boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = null;
        if (activityManager != null) {
            appProcesses = activityManager.getRunningAppProcesses();
        }
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Custom enum to represents Transaction Animation type
     */
    public enum CustomAnimationType {
        CUSTOM_ANIMATION_NORMAL,
        CUSTOM_ANIMATION_LEFT_AND_RIGHT,
        CUSTOM_ANIMATION_BOTTOM_TO_TOP
    }

}
