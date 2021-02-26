package br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable

import androidx.recyclerview.widget.DiffUtil
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant

class ContestantsDiffUtilCallback : DiffUtil.ItemCallback<ASMRContestant>() {
    override fun areItemsTheSame(oldItem: ASMRContestant, newItem: ASMRContestant): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ASMRContestant, newItem: ASMRContestant): Boolean {
        return oldItem == newItem
    }
}