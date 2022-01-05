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

package br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist

import android.view.ViewGroup
import androidx.recyclerview.widget.*
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.ViewFactory
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights
import br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.view.listitem.WeekHighlightsListItemViewImpl

class WeekHighlightsListAdapter(
    private val viewFactory: ViewFactory
) : ListAdapter<WeekHighlights, WeekHighlightsListAdapter.ViewHolder>(
    WeekHighlightsDiffUtilCallback()
) {
    class ViewHolder(
        private val listItemView: WeekHighlightsListItemViewImpl
    ) : RecyclerView.ViewHolder(listItemView.rootView) {

        fun bind(weekHighlight: WeekHighlights) {
            listItemView.bindWeekHighlight(weekHighlight)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(viewFactory.getWeekHighlightsListItemView(parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
