package com.example.easypeasy.screens.common.fragmentframehelper;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class FragmentFrameHelper {

    private final Activity mActivity;
    private final FragmentFrameWrapper mFragmentFrameWrapper;
    private final FragmentManager mFragmentManager;

    public FragmentFrameHelper(@NonNull Activity activity,
                               @NonNull FragmentFrameWrapper fragmentFrameWrapper,
                               @NonNull FragmentManager fragmentManager) {
        mActivity = activity;
        mFragmentFrameWrapper = fragmentFrameWrapper;
        mFragmentManager = fragmentManager;
    }

    public void replaceFragment(@NonNull Fragment newFragment) {
        replaceFragment(newFragment, true, false);
    }

    public void replaceFragmentAndRemoveCurrentFromHistory(@NonNull Fragment newFragment) {
        replaceFragment(newFragment, false, false);
    }

    public void replaceFragmentAndClearHistory(@NonNull Fragment newFragment) {
        replaceFragment(newFragment, false, true);
    }

    public void navigateBack() {

        if (mFragmentManager.isStateSaved()) {
            // BACK NAVIGATION CAN BE SILENTLY ABORTED
            // since this flow involves popping the backstack, we can't execute it safely after
            // the state is saved
            // I asked a question about this: https://stackoverflow.com/q/52165653/2463035
            return;
        }

        if (goBackInFragmentsHistory()) {
            return; // up navigation resulted in going back in fragments history
        }

        finishActivity();

    }

    public void navigateUp() {

        if (mFragmentManager.isStateSaved()) {
            // UP NAVIGATION CAN BE SILENTLY ABORTED
            // since this flow involves popping the backstack, we can't execute it safely after
            // the state is saved
            // I asked a question about this: https://stackoverflow.com/q/52165653/2463035
            return;
        }

        if (goBackInFragmentsHistory()) {
            return; // up navigation resulted in going back in fragments history
        }

        Fragment currentFragment = getCurrentFragment();

        if (currentFragment instanceof HierarchicalFragment) {
            Fragment parentFragment =
                    ((HierarchicalFragment) currentFragment).getHierarchicalParentFragment();
            if (parentFragment != null) {
                replaceFragment(parentFragment, false, true);
                return; // up navigation resulted in going to hierarchical parent fragment
            }
        }

        if (mActivity.onNavigateUp()) {
            return; // up navigation resulted in going to hierarchical parent activity
        }

        finishActivity(); // no "up" navigation targets - just finish the activity
    }

    private boolean goBackInFragmentsHistory() {
        if (getFragmentsHistoryCount() > 0) {
            // A call to popBackStack can leave the currently visible fragment on screen. Therefore,
            // we start with manual removal of the current fragment.
            // Description of the issue can be found here: https://stackoverflow.com/q/45278497/2463035
            removeCurrentFragment();

            mFragmentManager.popBackStackImmediate();
            return true;
        }
        return false;
    }

    private void replaceFragment(@NonNull Fragment newFragment,
                                 boolean addToBackStack,
                                 boolean clearBackStack) {

        FragmentTransaction ft = mFragmentManager.beginTransaction();

        if (clearBackStack) {
            if (mFragmentManager.isStateSaved()) {
                // If the state is saved we can't clear the back stack. Simply not doing this, but
                // still replacing fragment is a bad idea. Therefore we abort the entire operation.
                return;
            }

            /*
              Due to the way backstack works, either just clearing backstack or removing fragments
              won't work reliably:
              Just pop backstack -> existing fragment on the backstack can become visible during transition
              Just remove fragments -> screws up default back button behavior
              Therefore, we need to do both.
              Remove all entries from back stack
            */

            if (mFragmentManager.getBackStackEntryCount() > 0) {
                mFragmentManager.popBackStack(mFragmentManager.getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }

            // Remove all fragments
            for (Fragment fragment : mFragmentManager.getFragments()) {
                ft.remove(fragment);
            }
        }

        if (addToBackStack) {
            ft.addToBackStack(null);
        }

        // Change to a new fragment
        ft.replace(getFragmentFrameId(), newFragment);

        commitFragmentTransactionSafely(ft);
    }

    private void removeCurrentFragment() {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.remove(getCurrentFragment());
        commitFragmentTransactionSafely(ft);

        // not sure it is needed; will keep it as a reminder to myself if there will be problems
        // mFragmentManager.executePendingTransactions();
    }

    private void commitFragmentTransactionSafely(FragmentTransaction ft) {
        // TODO: add mechanism for notifications about commits that allow state loss
        if (mFragmentManager.isStateSaved()) {
            // We acknowledge the possibility of losing this transaction if the app undergoes
            // save&restore flow after it is committed.
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }
    }

    private int getFragmentsHistoryCount() {
        // TODO: double check that fragments history count equals to backstack entry count
        return mFragmentManager.getBackStackEntryCount();
    }

    public @Nullable
    Fragment getCurrentFragment() {
        return mFragmentManager.findFragmentById(getFragmentFrameId());
    }

    private int getFragmentFrameId() {
        return mFragmentFrameWrapper.getFragmentContainer().getId();
    }

    private void finishActivity() {
        ActivityCompat.finishAfterTransition(mActivity);
    }
}
