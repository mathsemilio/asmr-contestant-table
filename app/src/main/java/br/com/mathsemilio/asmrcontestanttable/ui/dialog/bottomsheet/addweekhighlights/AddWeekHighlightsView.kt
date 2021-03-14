package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addweekhighlights

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.common.setDisabledState
import br.com.mathsemilio.asmrcontestanttable.common.setEnabledState
import br.com.mathsemilio.asmrcontestanttable.common.showErrorState
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.BaseObservableView
import com.google.android.material.button.MaterialButton

class AddWeekHighlightsView(layoutInflater: LayoutInflater, container: ViewGroup?) :
    BaseObservableView<AddWeekHighlightsContract.View.Listener>(), AddWeekHighlightsContract.View {

    private lateinit var editTextFirstContestantName: EditText
    private lateinit var editTextSecondContestantName: EditText
    private lateinit var buttonAddWeekHighlight: MaterialButton

    init {
        rootView =
            layoutInflater.inflate(R.layout.bottom_sheet_add_week_highlights, container, false)
        initializeViews()
        attachButtonAddWeekHighlightOnClickListener()
    }

    private fun initializeViews() {
        editTextFirstContestantName = findViewById(R.id.edit_text_first_contestant_name)
        editTextSecondContestantName = findViewById(R.id.edit_text_second_contestant_name)
        buttonAddWeekHighlight = findViewById(R.id.button_add_week_highlight)
    }

    private fun attachButtonAddWeekHighlightOnClickListener() {
        buttonAddWeekHighlight.setOnClickListener {
            val firstContestantName = editTextFirstContestantName.text.toString()
            val secondContestantName = editTextSecondContestantName.text.toString()

            if (firstContestantName.isEmpty()) {
                editTextFirstContestantName.showErrorState(
                    context.getString(R.string.please_enter_contestant_name)
                )
                return@setOnClickListener
            }

            if (secondContestantName.isEmpty()) {
                editTextSecondContestantName.showErrorState(
                    context.getString(R.string.please_enter_contestant_name)
                )
                return@setOnClickListener
            }

            listeners.forEach { it.onAddButtonClicked(firstContestantName, secondContestantName) }
        }
    }

    override fun changeAddButtonState() {
        buttonAddWeekHighlight.setDisabledState(context.getString(R.string.adding_week_highlights))
    }

    override fun revertAddButtonState() {
        buttonAddWeekHighlight.setEnabledState(context.getString(R.string.add))
    }
}