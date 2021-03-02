package br.com.mathsemilio.asmrcontestanttable.ui

import android.widget.FrameLayout

interface MainActivityContract {

    interface View {
        interface Listener {
            fun onBottomNavigationItemClicked(destination: NavDestination)
            fun onToolbarActionResetContestClicked()
        }

        val fragmentContainer: FrameLayout

        fun setToolbarTitleBasedOnDestination(destination: NavDestination)

        fun setToolbarMenuBasedOnDestination(destination: NavDestination)
    }
}