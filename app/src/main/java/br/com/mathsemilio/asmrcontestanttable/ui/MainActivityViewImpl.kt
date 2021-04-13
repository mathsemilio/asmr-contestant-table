package br.com.mathsemilio.asmrcontestanttable.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.ui.common.navigation.destinations.TopDestination
import br.com.mathsemilio.asmrcontestanttable.ui.common.others.BottomNavigationItem
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivityViewImpl(inflater: LayoutInflater, parent: ViewGroup?) : MainActivityView() {

    private var materialToolbarApp: MaterialToolbar
    private var frameLayoutFragmentContainer: FrameLayout
    private var bottomNavigationViewApp: BottomNavigationView

    init {
        rootView = inflater.inflate(R.layout.activity_main, parent, false)
        materialToolbarApp = findViewById(R.id.material_toolbar_app)
        frameLayoutFragmentContainer = findViewById(R.id.frame_layout_fragment_container)
        bottomNavigationViewApp = findViewById(R.id.bottom_navigation_view_app)
        attachBottomNavViewOnNavigationItemSelectedListener()
    }

    private fun attachBottomNavViewOnNavigationItemSelectedListener() {
        bottomNavigationViewApp.setOnNavigationItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.bottom_nav_item_contestants_table -> {
                    onBottomNavigationItemClicked(BottomNavigationItem.CONTESTANTS_TABLE)
                    true
                }
                R.id.bottom_nav_item_week_highlights -> {
                    onBottomNavigationItemClicked(BottomNavigationItem.WEEK_HIGHLIGHTS)
                    true
                }
                else -> false
            }
        }
    }

    override val toolbar get() = materialToolbarApp

    override val fragmentContainer get() = frameLayoutFragmentContainer

    override fun setToolbarTitleForTopDestination(destination: TopDestination) {
        materialToolbarApp.title = when (destination) {
            TopDestination.CONTESTANTS_TABLE -> getString(R.string.contestants_table)
            TopDestination.WEEK_HIGHLIGHTS -> getString(R.string.week_highlights)
        }
    }

    private fun onBottomNavigationItemClicked(item: BottomNavigationItem) {
        listeners.forEach { listener ->
            listener.onBottomNavigationItemClicked(item)
        }
    }
}