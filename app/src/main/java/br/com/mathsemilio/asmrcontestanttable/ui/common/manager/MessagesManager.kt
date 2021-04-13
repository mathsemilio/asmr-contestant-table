package br.com.mathsemilio.asmrcontestanttable.ui.common.manager

import android.content.Context
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.common.showLongToast
import br.com.mathsemilio.asmrcontestanttable.common.showShortToast

class MessagesManager(private val context: Context) {

    fun showDeleteAllContestantsUseCaseSuccessMessage() =
        context.showShortToast(context.getString(R.string.message_contest_rebooted_successfully))

    fun showUseCaseErrorMessage(errorMessage: String) =
        context.showLongToast(errorMessage)

    fun showUnexpectedErrorOccurredMessage() =
        context.showLongToast(context.getString(R.string.message_unexpected_error_occurred))
}