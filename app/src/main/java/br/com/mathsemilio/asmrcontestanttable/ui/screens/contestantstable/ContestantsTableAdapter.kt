package br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.ViewFactory
import br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.view.ContestantsListItemView
import br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.view.ContestantsListItemViewImpl

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

    class ViewHolder(private val listItemView: ContestantsListItemViewImpl) :
        RecyclerView.ViewHolder(listItemView.rootView) {

        fun bind(contestant: ASMRContestant, contestantPosition: Int) {
            listItemView.bindContestant(contestant, contestantPosition)
        }
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