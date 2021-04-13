package br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.view

import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.BaseObservableView

abstract class ContestantsTableScreenView :
    BaseObservableView<ContestantsTableScreenView.Listener>() {

    interface Listener {
        fun onAddContestantButtonClicked()
        fun onContestantClicked(contestant: ASMRContestant)
    }

    abstract fun bindContestants(contestants: List<ASMRContestant>)

    abstract fun showProgressIndicator()

    abstract fun hideProgressIndicator()
}