package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addweekhighlights

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.common.setDisabledState
import br.com.mathsemilio.asmrcontestanttable.common.setEnabledState
import br.com.mathsemilio.asmrcontestanttable.common.showErrorState
import com.google.android.material.button.MaterialButton

class AddWeekHighlightsViewImpl(inflater: LayoutInflater, container: ViewGroup?) :
    AddWeekHighlightsView() {

    private var editTextFirstContestantName: EditText
    private var editTextSecondContestantName: EditText
    private var buttonAddWeekHighlight: MaterialButton

    init {
        rootView = inflater.inflate(R.layout.bottom_sheet_add_week_highlights, container, false)
        editTextFirstContestantName = findViewById(R.id.edit_text_first_contestant_name)
        editTextSecondContestantName = findViewById(R.id.edit_text_second_contestant_name)
        buttonAddWeekHighlight = findViewById(R.id.button_add_week_highlight)
        buttonAddWeekHighlight.setOnClickListener { onAddWeekHighlightButtonClicked() }
    }

    override fun changeAddButtonState() {
        buttonAddWeekHighlight.setDisabledState(getString(R.string.adding_week_highlights))
    }

    override fun revertAddButtonState() {
        buttonAddWeekHighlight.setEnabledState(getString(R.string.add))
    }

    private fun onAddWeekHighlightButtonClicked() {
        val firstContestantName = editTextFirstContestantName.text.toString()
        val secondContestantName = editTextSecondContestantName.text.toString()

        if (firstContestantName.isEmpty()) {
            editTextFirstContestantName.showErrorState(
                context.getString(R.string.please_enter_contestant_name)
            )
            return
        }

        if (secondContestantName.isEmpty()) {
            editTextSecondContestantName.showErrorState(
                getString(R.string.please_enter_contestant_name)
            )
            return
        }

        listeners.forEach { listener ->
            listener.onAddButtonClicked(firstContestantName, secondContestantName)
        }
    }
}