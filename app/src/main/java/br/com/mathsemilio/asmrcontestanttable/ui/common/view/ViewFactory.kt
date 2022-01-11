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

package br.com.mathsemilio.asmrcontestanttable.ui.common.view

import android.view.*
import br.com.mathsemilio.asmrcontestanttable.ui.screens.container.view.MainActivityViewImpl
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.promptdialog.view.PromptDialogViewImpl
import br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.view.WeekHighlightsViewImpl
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant.view.AddContestantViewImpl
import br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.view.ContestantsTableScreenViewImpl
import br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.view.listitem.ContestantsListItemViewImpl
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addweekhighlights.view.AddWeekHighlightsViewImpl
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.contestantdetails.view.ContestantDetailsViewImpl
import br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.view.listitem.WeekHighlightsListItemViewImpl

class ViewFactory(private val layoutInflater: LayoutInflater) {

    val promptDialogView
        get() = PromptDialogViewImpl(layoutInflater)

    fun getMainActivityView(parent: ViewGroup?) =
        MainActivityViewImpl(layoutInflater, parent)

    fun getContestantsTableScreenView(container: ViewGroup?) =
        ContestantsTableScreenViewImpl(layoutInflater, container, this)

    fun getContestantsListItemView(parent: ViewGroup?) =
        ContestantsListItemViewImpl(layoutInflater, parent)

    fun getWeekHighlightsListScreenView(container: ViewGroup?) =
        WeekHighlightsViewImpl(layoutInflater, container, this)

    fun getWeekHighlightsListItemView(parent: ViewGroup?) =
        WeekHighlightsListItemViewImpl(layoutInflater, parent)

    fun getAddContestView(container: ViewGroup?) =
        AddContestantViewImpl(layoutInflater, container)

    fun getAddWeekHighlightsView(container: ViewGroup?) =
        AddWeekHighlightsViewImpl(layoutInflater, container)

    fun getContestantsDetailsView(container: ViewGroup?) =
        ContestantDetailsViewImpl(layoutInflater, container)
}
