package br.com.mathsemilio.asmrcontestanttable.ui.common.manager

import android.content.Context
import androidx.fragment.app.FragmentManager
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant.AddContestantBottomSheet
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addweekhighlights.AddWeekHighlightsBottomSheet
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.contestantdetails.ContestantDetailsBottomSheet
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogManager(private val fragmentManager: FragmentManager, private val context: Context) {

    fun showAddContestantBottomSheet() {
        val addContestantBottomSheet = AddContestantBottomSheet()
        addContestantBottomSheet.show(fragmentManager, null)
    }

    fun showAddWeekHighlightsBottomSheet() {
        val addWeekHighlightsBottomSheet = AddWeekHighlightsBottomSheet()
        addWeekHighlightsBottomSheet.show(fragmentManager, null)
    }

    fun showContestantDetailsBottomSheet(contestant: ASMRContestant) {
        val contestantsDetailsBottomSheet = ContestantDetailsBottomSheet.newInstance(contestant)
        contestantsDetailsBottomSheet.show(fragmentManager, null)
    }

    fun showResetContestDialog(onPositiveButtonClicked: () -> Unit) {
        MaterialAlertDialogBuilder(context).apply {
            setTitle(context.getString(R.string.dialog_title_reset_contest))
            setMessage(context.getString(R.string.dialog_message_reset_contest))
            setPositiveButton(context.getString(R.string.dialog_positive_button_reset)) { _, _ ->
                onPositiveButtonClicked()
            }
            setNegativeButton(context.getString(R.string.dialog_negative_button_cancel), null)
            setCancelable(true)
            show()
        }
    }
}