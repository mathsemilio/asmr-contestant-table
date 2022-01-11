/*
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addweekhighlights.view

import android.view.*
import android.widget.EditText
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.common.*
import com.google.android.material.button.MaterialButton

class AddWeekHighlightsViewImpl(
    inflater: LayoutInflater,
    container: ViewGroup?
) : AddWeekHighlightsView() {

    private lateinit var editTextFirstContestantName: EditText
    private lateinit var editTextSecondContestantName: EditText
    private lateinit var buttonAddWeekHighlight: MaterialButton

    init {
        rootView = inflater.inflate(R.layout.bottom_sheet_add_week_highlights, container, false)

        initializeViews()

        buttonAddWeekHighlight.setOnClickListener { onAddWeekHighlightButtonClicked() }
    }

    private fun initializeViews() {
        editTextFirstContestantName = findViewById(R.id.edit_text_first_contestant_name)
        editTextSecondContestantName = findViewById(R.id.edit_text_second_contestant_name)
        buttonAddWeekHighlight = findViewById(R.id.button_add_week_highlight)
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

        notify { observer ->
            observer.onAddButtonClicked(listOf(firstContestantName, secondContestantName))
        }
    }

    override fun changeAddButtonState() {
        buttonAddWeekHighlight.setDisabledState(getString(R.string.adding_week_highlights))
    }

    override fun revertAddButtonState() {
        buttonAddWeekHighlight.setEnabledState(getString(R.string.add))
    }
}
