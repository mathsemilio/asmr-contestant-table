package br.com.mathsemilio.asmrcontestanttable.ui.common.helper

import android.content.Context
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.common.showLongToast
import br.com.mathsemilio.asmrcontestanttable.common.showShortToast

class MessagesManager(private val context: Context) {

    fun showFetchContestantsUseCaseErrorMessage(errorMessage: String) =
        context.showLongToast(errorMessage)

    fun showFetchWeekHighlightsUseCaseErrorMessage(errorMessage: String) =
        context.showLongToast(errorMessage)

    fun showAddContestantUseCaseErrorMessage(errorMessage: String) =
        context.showLongToast(errorMessage)

    fun showAddWeekHighlightsUseCaseErrorMessage(errorMessage: String) =
        context.showLongToast(errorMessage)

    fun showContestantUpdateUseCaseErrorMessage(errorMessage: String) =
        context.showLongToast(errorMessage)

    fun showAllContestantsDeleteUseCaseSuccessMessage() =
        context.showShortToast(context.getString(R.string.contest_rebooted_successfully))

    fun showAllContestantsDeletedUseCaseErrorMessage(errorMessage: String) =
        context.showLongToast(errorMessage)
}