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

package br.com.mathsemilio.asmrcontestanttable.ui.screens.container.view

import android.view.*
import android.widget.FrameLayout
import androidx.annotation.StringRes
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.common.*
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import br.com.mathsemilio.asmrcontestanttable.ui.common.others.BottomNavigationItem

class MainActivityViewImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : MainActivityView() {

    private lateinit var materialToolbarApp: MaterialToolbar
    private lateinit var frameLayoutFragmentContainer: FrameLayout
    private lateinit var bottomNavigationViewApp: BottomNavigationView

    private var selectedBottomNavigationItem = BottomNavigationItem.CONTESTANTS_TABLE

    init {
        rootView = inflater.inflate(R.layout.activity_main, parent, false)

        initializeViews()

        attachBottomNavigationViewOnNavigationItemSelectedListener()
    }

    private fun initializeViews() {
        materialToolbarApp = findViewById(R.id.material_toolbar_app)
        frameLayoutFragmentContainer = findViewById(R.id.frame_layout_fragment_container)
        bottomNavigationViewApp = findViewById(R.id.bottom_navigation_view_app)
    }

    private fun attachBottomNavigationViewOnNavigationItemSelectedListener() {
        bottomNavigationViewApp.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.bottom_navigation_item_contestants_table -> {
                    onBottomNavigationItemClicked(BottomNavigationItem.CONTESTANTS_TABLE)
                    true
                }
                R.id.bottom_navigation_item_week_highlights -> {
                    onBottomNavigationItemClicked(BottomNavigationItem.WEEK_HIGHLIGHTS)
                    true
                }
                else -> false
            }
        }
    }

    private fun onBottomNavigationItemClicked(item: BottomNavigationItem) {
        selectedBottomNavigationItem = item
        notify { observer -> observer.onBottomNavigationItemClicked(item) }
    }

    override val toolbar
        get() = materialToolbarApp

    override val fragmentContainerId: Int
        get() = frameLayoutFragmentContainer.id

    override fun setToolbarTitle(@StringRes titleId: Int?) {
        titleId?.let { id -> materialToolbarApp.title = getString(id) }
    }

    override fun setBottomNavigationHighlightedItemBasedOn(destinationName: String) {
        val itemToBeChecked = when (destinationName) {
            DESTINATION_CONTESTANTS_TABLE ->
                bottomNavigationViewApp.menu.findItem(R.id.bottom_navigation_item_contestants_table)
            DESTINATION_WEEK_HIGHLIGHTS ->
                bottomNavigationViewApp.menu.findItem(R.id.bottom_navigation_item_week_highlights)
            else ->
                bottomNavigationViewApp.menu.findItem(R.id.bottom_navigation_item_contestants_table)
        }

        bottomNavigationViewApp.menu.findItem(itemToBeChecked.itemId).isChecked = true
    }
}
