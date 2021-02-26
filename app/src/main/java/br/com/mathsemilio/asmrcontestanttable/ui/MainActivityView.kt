package br.com.mathsemilio.asmrcontestanttable.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.BaseObservableView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivityView(layoutInflater: LayoutInflater, parent: ViewGroup?) :
    BaseObservableView<MainActivityContract.View.Listener>(), MainActivityContract.View {

    private lateinit var materialToolbarApp: MaterialToolbar
    private lateinit var frameLayoutScreenContainer: FrameLayout
    private lateinit var bottomNavViewApp: BottomNavigationView

    init {
        rootView = layoutInflater.inflate(R.layout.activity_main, parent, false)
        initializeViews()
        attachOnBottomNavViewOnNavigationItemSelectedListener()
    }

    private fun initializeViews() {
        materialToolbarApp = findViewById(R.id.material_toolbar_app)
        frameLayoutScreenContainer = findViewById(R.id.frame_layout_screen_container)
        bottomNavViewApp = findViewById(R.id.bottom_nav_view_app)
    }

    private fun attachOnBottomNavViewOnNavigationItemSelectedListener() {
        bottomNavViewApp.setOnNavigationItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.bottom_nav_item_contestants_table -> {
                    NavDestination.CONTESTANTS_TABLE.let {
                        setToolbarTitleBasedOnDestination(it)
                        setToolbarMenuBasedOnDestination(it)
                        onBottomNavItemClicked(it)
                    }
                    true
                }
                R.id.bottom_nav_item_week_highlights -> {
                    NavDestination.WEEK_HIGHLIGHTS.let {
                        setToolbarTitleBasedOnDestination(it)
                        setToolbarMenuBasedOnDestination(it)
                        onBottomNavItemClicked(it)
                    }
                    true
                }
                else -> false
            }
        }
    }

    override val fragmentContainer: FrameLayout get() = frameLayoutScreenContainer

    override fun setToolbarTitleBasedOnDestination(destination: NavDestination) {
        materialToolbarApp.title = when (destination) {
            NavDestination.CONTESTANTS_TABLE -> context.getString(R.string.contestants_table)
            NavDestination.WEEK_HIGHLIGHTS -> context.getString(R.string.week_highlights)
        }
    }

    override fun setToolbarMenuBasedOnDestination(destination: NavDestination) {
        val toolbarMenu = materialToolbarApp.menu
        when (destination) {
            NavDestination.CONTESTANTS_TABLE ->
                toolbarMenu.setGroupVisible(R.id.toolbar_menu_group_contestants_table, true)
            NavDestination.WEEK_HIGHLIGHTS ->
                toolbarMenu.setGroupVisible(R.id.toolbar_menu_group_contestants_table, false)
        }
    }

    private fun onBottomNavItemClicked(destination: NavDestination) {
        listeners.forEach { it.onBottomNavigationItemClicked(destination) }
    }
}