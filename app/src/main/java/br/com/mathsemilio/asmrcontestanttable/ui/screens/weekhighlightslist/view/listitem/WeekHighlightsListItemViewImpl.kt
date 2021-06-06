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

package br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.view.listitem

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights

class WeekHighlightsListItemViewImpl(inflater: LayoutInflater, parent: ViewGroup?) :
    WeekHighlightsListItemView() {

    private var textViewWeekHighlightsItemWeekNumber: TextView
    private var textViewWeekHighlightsItemContestants: TextView

    init {
        rootView = inflater.inflate(R.layout.week_highlights_list_item, parent, false)

        textViewWeekHighlightsItemWeekNumber =
            findViewById(R.id.text_view_week_highlights_item_week)
        textViewWeekHighlightsItemContestants =
            findViewById(R.id.text_view_week_highlights_item_contestants)
    }

    override fun bindWeekHighlight(weekHighlight: WeekHighlights) {
        textViewWeekHighlightsItemWeekNumber.text = context.getString(
            R.string.week_highlights_item_week_number,
            weekHighlight.weekNumber
        )
        textViewWeekHighlightsItemContestants.text = context.getString(
            R.string.week_highlights_item_contestants_names,
            weekHighlight.firstContestantName,
            weekHighlight.secondContestantName
        )
    }
}