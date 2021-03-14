package br.com.mathsemilio.asmrcontestanttable.ui.common.helper

import android.content.Context
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.common.showLongToast
import br.com.mathsemilio.asmrcontestanttable.common.showShortToast

class MessagesManager(private val context: Context) {

    fun showContestantsDeletedUseCaseSuccessMessage() =
        context.showShortToast(context.getString(R.string.contest_rebooted_successfully))

    fun showUseCaseErrorMessage(errorMessage: String) =
        context.showLongToast(errorMessage)
}