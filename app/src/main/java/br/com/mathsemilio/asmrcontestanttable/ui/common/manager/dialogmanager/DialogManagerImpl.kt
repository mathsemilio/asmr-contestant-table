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

package br.com.mathsemilio.asmrcontestanttable.ui.common.manager.dialogmanager

import android.content.Context
import androidx.fragment.app.FragmentManager
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.promptdialog.builder.PromptDialogBuilder
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant.controller.AddContestantBottomSheet
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addweekhighlights.controller.AddWeekHighlightsBottomSheet
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.contestantdetails.controller.ContestantDetailsBottomSheet

class DialogManagerImpl(
    private val fragmentManager: FragmentManager,
    private val context: Context
) : DialogManager {

    override fun showAddContestantBottomSheet() {
        val addContestantBottomSheet = AddContestantBottomSheet()
        addContestantBottomSheet.show(fragmentManager, null)
    }

    override fun showAddWeekHighlightsBottomSheet() {
        val addWeekHighlightsBottomSheet = AddWeekHighlightsBottomSheet()
        addWeekHighlightsBottomSheet.show(fragmentManager, null)
    }

    override fun showContestantDetailsBottomSheet(contestant: ASMRContestant) {
        val contestantsDetailsBottomSheet = ContestantDetailsBottomSheet.with(contestant)
        contestantsDetailsBottomSheet.show(fragmentManager, null)
    }

    override fun showResetContestDialog() {
        val promptDialog = PromptDialogBuilder
            .withTitle(context.getString(R.string.dialog_title_reset_contest))
            .withMessage(context.getString(R.string.dialog_message_reset_contest))
            .withPositiveButtonText(context.getString(R.string.dialog_positive_button_reset))
            .withNegativeButtonText(context.getString(R.string.dialog_negative_button_cancel))
            .setIsCancelable(true)
            .build()

        promptDialog.show(fragmentManager, null)
    }
}
