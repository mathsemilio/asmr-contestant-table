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

import android.widget.FrameLayout
import androidx.annotation.StringRes
import br.com.mathsemilio.asmrcontestanttable.ui.common.others.BottomNavigationItem
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.BaseObservableView
import com.google.android.material.appbar.MaterialToolbar

abstract class MainActivityView : BaseObservableView<MainActivityView.Listener>() {

    interface Listener {
        fun onBottomNavigationItemClicked(item: BottomNavigationItem)
    }

    abstract val toolbar: MaterialToolbar

    abstract val fragmentContainer: FrameLayout

    abstract fun setToolbarTitle(@StringRes titleId: Int?)

    abstract fun setBottomNavigationHighlightedItemBasedOn(destinationName: String)
}
