package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addweekhighlights

import br.com.mathsemilio.asmrcontestanttable.ui.common.view.BaseObservableView

abstract class AddWeekHighlightsView : BaseObservableView<AddWeekHighlightsView.Listener>() {

    interface Listener {
        fun onAddButtonClicked(firstContestantName: String, secondContestantName: String)
    }

    abstract fun changeAddButtonState()

    abstract fun revertAddButtonState()
}