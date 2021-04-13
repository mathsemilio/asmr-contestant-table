package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant

import br.com.mathsemilio.asmrcontestanttable.ui.common.view.BaseObservableView

abstract class AddContestantView : BaseObservableView<AddContestantView.Listener>() {

    interface Listener {
        fun onAddButtonClicked(contestantName: String)
    }

    abstract fun changeAddButtonState()

    abstract fun revertAddButtonState()
}