package br.com.mathsemilio.asmrcontestanttable.ui

import android.widget.FrameLayout
import br.com.mathsemilio.asmrcontestanttable.ui.common.navigation.destinations.TopDestination
import br.com.mathsemilio.asmrcontestanttable.ui.common.others.BottomNavigationItem
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.BaseObservableView
import com.google.android.material.appbar.MaterialToolbar

abstract class MainActivityView : BaseObservableView<MainActivityView.Listener>() {

    interface Listener {
        fun onBottomNavigationItemClicked(item: BottomNavigationItem)
    }

    abstract val toolbar: MaterialToolbar

    abstract val fragmentContainer: FrameLayout

    abstract fun setToolbarTitleForTopDestination(destination: TopDestination)
}