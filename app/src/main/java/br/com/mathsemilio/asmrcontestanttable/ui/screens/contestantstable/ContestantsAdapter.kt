package br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant

class ContestantsAdapter(
    private val layoutInflater: LayoutInflater,
    private val listener: Listener
) : RecyclerView.Adapter<ContestantsAdapter.ViewHolder>(),
    ContestantsTableContract.ListItemView.Listener {

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

    class ViewHolder(listItemView: ContestantsListItemView) :
        RecyclerView.ViewHolder(listItemView.rootView) {
        val contestantsListItemView = listItemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val contestantsListItemView = ContestantsListItemView(layoutInflater, parent)
        contestantsListItemView.addListener(this)
        return ViewHolder(contestantsListItemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.contestantsListItemView.bindContestant(contestantsList[position], position + 1)
    }

    override fun getItemCount(): Int {
        return contestantsList.size
    }

    override fun onContestantClicked(contestant: ASMRContestant) {
        listener.onContestantClicked(contestant)
    }
}