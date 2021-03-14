package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.common.setDisabledState
import br.com.mathsemilio.asmrcontestanttable.common.setEnabledState
import br.com.mathsemilio.asmrcontestanttable.common.showErrorState
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.BaseObservableView
import com.google.android.material.button.MaterialButton

class AddContestantView(layoutInflater: LayoutInflater, container: ViewGroup?) :
    BaseObservableView<AddContestantContract.View.Listener>(), AddContestantContract.View {

    private lateinit var editTextContestantName: EditText
    private lateinit var buttonAddContestant: MaterialButton

    init {
        rootView = layoutInflater.inflate(R.layout.bottom_sheet_add_contestant, container, false)
        initializeViews()
        attachAddContestantButtonOnClickListener()
    }

    private fun initializeViews() {
        editTextContestantName = findViewById(R.id.edit_text_contestant_name)
        buttonAddContestant = findViewById(R.id.button_add_contestant)
    }

    private fun attachAddContestantButtonOnClickListener() {
        buttonAddContestant.setOnClickListener {
            val contestantName = editTextContestantName.text.toString()

            if (contestantName.isBlank()) {
                editTextContestantName.showErrorState(
                    context.getString(R.string.please_enter_contestant_name)
                )
                revertAddButtonState()
                return@setOnClickListener
            } else {
                listeners.forEach { it.onAddButtonClicked(contestantName) }
            }
        }
    }

    override fun changeAddButtonState() {
        buttonAddContestant.setDisabledState(context.getString(R.string.adding_contestant))
    }

    override fun revertAddButtonState() {
        buttonAddContestant.setEnabledState(context.getString(R.string.add))
    }
}