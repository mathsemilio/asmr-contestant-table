package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.common.setDisabledState
import br.com.mathsemilio.asmrcontestanttable.common.setEnabledState
import br.com.mathsemilio.asmrcontestanttable.common.showErrorState
import com.google.android.material.button.MaterialButton

class AddContestantViewImpl(inflater: LayoutInflater, container: ViewGroup?) : AddContestantView() {

    private var editTextContestantName: EditText
    private var buttonAddContestant: MaterialButton

    init {
        rootView = inflater.inflate(R.layout.bottom_sheet_add_contestant, container, false)
        editTextContestantName = findViewById(R.id.edit_text_contestant_name)
        buttonAddContestant = findViewById(R.id.button_add_contestant)
        buttonAddContestant.setOnClickListener { onAddContestantButtonClicked() }
    }

    override fun changeAddButtonState() {
        buttonAddContestant.setDisabledState(getString(R.string.adding_contestant))
    }

    override fun revertAddButtonState() {
        buttonAddContestant.setEnabledState(getString(R.string.add))
    }

    private fun onAddContestantButtonClicked() {
        val contestantName = editTextContestantName.text.toString()

        if (contestantName.isBlank()) {
            editTextContestantName.showErrorState(getString(R.string.please_enter_contestant_name))
            revertAddButtonState()
            return
        } else {
            listeners.forEach { listener ->
                listener.onAddButtonClicked(contestantName)
            }
        }
    }
}