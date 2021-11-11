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

package br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.ViewFactory
import br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.view.listitem.ContestantsListItemView

class ContestantsTableAdapter(
    private val listener: Listener,
    private val viewFactory: ViewFactory
) : RecyclerView.Adapter<ContestantsTableAdapter.ViewHolder>(),
    ContestantsListItemView.Listener {

    interface Listener {
        fun onContestantClicked(contestant: ASMRContestant)
    }

    private val contestantsList = mutableListOf<ASMRContestant>()

    fun submitData(data: List<ASMRContestant>) {
        if (contestantsList.isNotEmpty())
            contestantsList.clear()

        contestantsList.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(private val listItemView: ContestantsListItemView) :
        RecyclerView.ViewHolder(listItemView.rootView) {

        fun bind(contestant: ASMRContestant, contestantPosition: Int) =
            listItemView.bindContestant(contestant, contestantPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(viewFactory.getContestantsListItemView(parent).also { listItemView ->
            listItemView.addListener(this)
        })
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contestantsList[position], position.plus(1))
    }

    override fun getItemCount(): Int {
        return contestantsList.size
    }

    override fun onContestantClicked(contestant: ASMRContestant) {
        listener.onContestantClicked(contestant)
    }
}
