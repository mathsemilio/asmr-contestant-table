/*
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package br.com.mathsemilio.asmrcontestanttable.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.ui.common.navigation.Destination
import br.com.mathsemilio.asmrcontestanttable.ui.common.others.BottomNavigationItem
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivityViewImpl(inflater: LayoutInflater, parent: ViewGroup?) : MainActivityView() {

    private var materialToolbarApp: MaterialToolbar
    private var frameLayoutFragmentContainer: FrameLayout
    private var bottomNavigationViewApp: BottomNavigationView

    private var currentCheckedBottomNavigationItem = BottomNavigationItem.CONTESTANTS_TABLE

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
                R.id.bottom_navigation_item_contestants_table -> {
                    onBottomNavigationItemClicked(BottomNavigationItem.CONTESTANTS_TABLE)
                    currentCheckedBottomNavigationItem = BottomNavigationItem.CONTESTANTS_TABLE
                    true
                }
                R.id.bottom_navigation_item_week_highlights -> {
                    onBottomNavigationItemClicked(BottomNavigationItem.WEEK_HIGHLIGHTS)
                    currentCheckedBottomNavigationItem = BottomNavigationItem.WEEK_HIGHLIGHTS
                    true
                }
                else -> false
            }
        }
    }

    override val toolbar get() = materialToolbarApp

    override val fragmentContainer get() = frameLayoutFragmentContainer

    override fun setToolbarTitle(destination: Destination) {
        materialToolbarApp.title = when (destination) {
            Destination.CONTESTANTS_TABLE -> getString(R.string.contestants_table)
            Destination.WEEK_HIGHLIGHTS -> getString(R.string.week_highlights)
        }
    }

    override fun setBottomNavigationHighlightedItem() {
        val itemToBeChecked = when (currentCheckedBottomNavigationItem) {
            BottomNavigationItem.CONTESTANTS_TABLE ->
                bottomNavigationViewApp.menu.findItem(R.id.bottom_navigation_item_contestants_table)
            BottomNavigationItem.WEEK_HIGHLIGHTS ->
                bottomNavigationViewApp.menu.findItem(R.id.bottom_navigation_item_week_highlights)
        }

        bottomNavigationViewApp.menu.findItem(itemToBeChecked.itemId).isChecked = true
    }

    private fun onBottomNavigationItemClicked(item: BottomNavigationItem) {
        notifyListener { it.onBottomNavigationItemClicked(item) }
    }
}