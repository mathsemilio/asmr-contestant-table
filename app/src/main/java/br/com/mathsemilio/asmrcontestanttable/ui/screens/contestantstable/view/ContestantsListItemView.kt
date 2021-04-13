package br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.view

import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.BaseObservableView

abstract class ContestantsListItemView : BaseObservableView<ContestantsListItemView.Listener>() {

    interface Listener {
        fun onContestantClicked(contestant: ASMRContestant)
    }

    abstract fun bindContestant(contestant: ASMRContestant, position: Int)
}