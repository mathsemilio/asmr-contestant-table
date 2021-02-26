package br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant

class ContestantsListAdapter(
    private val layoutInflater: LayoutInflater,
    private val listener: Listener
) : ListAdapter<ASMRContestant, ContestantsListAdapter.ViewHolder>(ContestantsDiffUtilCallback()),
    ContestantsTableContract.ListItemView.Listener {

    interface Listener {
        fun onContestantClicked(contestant: ASMRContestant)
    }

    class ViewHolder(listItem: ContestantsListItemView) :
        RecyclerView.ViewHolder(listItem.rootView) {
        val contestantsListItemView = listItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val contestantsListItemView = ContestantsListItemView(layoutInflater, parent)
        contestantsListItemView.addListener(this)
        return ViewHolder(contestantsListItemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.contestantsListItemView.bindContestant(getItem(position), position + 1)
    }

    override fun onContestantClicked(contestant: ASMRContestant) {
        listener.onContestantClicked(contestant)
    }
}