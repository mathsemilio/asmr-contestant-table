package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.contestantdetails

import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.BaseObservableView

abstract class ContestantDetailsView : BaseObservableView<ContestantDetailsView.Listener>() {

    interface Listener {
        fun onIncrementTimesSleptButtonClicked()
        fun onIncrementTimesDidNotSleptButtonClicked()
        fun onIncrementTimesFeltTiredButtonClicked()
    }

    abstract fun bindContestantsDetails(contestant: ASMRContestant)
}