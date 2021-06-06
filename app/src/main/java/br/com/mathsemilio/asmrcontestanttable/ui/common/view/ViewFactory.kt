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

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.mathsemilio.asmrcontestanttable.ui.MainActivityViewImpl
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant.AddContestantViewImpl
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addweekhighlights.AddWeekHighlightsViewImpl
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.contestantdetails.ContestantDetailsViewImpl
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.promptdialog.PromptDialogViewImpl
import br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.view.ContestantsTableScreenViewImpl
import br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.view.listitem.ContestantsListItemViewImpl
import br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.view.WeekHighlightsViewImpl
import br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.view.listitem.WeekHighlightsListItemViewImpl

class ViewFactory(private val inflater: LayoutInflater) {

    val promptDialogView get() = PromptDialogViewImpl(inflater)

    fun getMainActivityView(parent: ViewGroup?) =
        MainActivityViewImpl(inflater, parent)

    fun getContestantsTableScreenView(container: ViewGroup?) =
        ContestantsTableScreenViewImpl(inflater, container, this)

    fun getContestantsListItemView(parent: ViewGroup?) =
        ContestantsListItemViewImpl(inflater, parent)

    fun getWeekHighlightsListScreenView(container: ViewGroup?) =
        WeekHighlightsViewImpl(inflater, container, this)

    fun getWeekHighlightsListItemView(parent: ViewGroup?) =
        WeekHighlightsListItemViewImpl(inflater, parent)

    fun getAddContestView(container: ViewGroup?) =
        AddContestantViewImpl(inflater, container)

    fun getAddWeekHighlightsView(container: ViewGroup?) =
        AddWeekHighlightsViewImpl(inflater, container)

    fun getContestantsDetailsView(container: ViewGroup?) =
        ContestantDetailsViewImpl(inflater, container)
}