package br.com.mathsemilio.asmrcontestanttable.ui.common.helper

import android.content.Context
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.common.showLongToast
import br.com.mathsemilio.asmrcontestanttable.common.showShortToast

class MessagesHelper(private val context: Context) {

    fun showFetchContestantsUseCaseErrorMessage(errorMessage: String) {
        context.showLongToast(errorMessage)
    }

    fun showFetchWeekHighlightsUseCaseErrorMessage(errorMessage: String) {
        context.showLongToast(errorMessage)
    }

    fun showAddContestantUseCaseErrorMessage(errorMessage: String) {
        context.showLongToast(errorMessage)
    }

    fun showAddWeekHighlightsUseCaseErrorMessage(errorMessage: String) {
        context.showLongToast(errorMessage)
    }

    fun showContestantUpdateUseCaseErrorMessage(errorMessage: String) {
        context.showLongToast(errorMessage)
    }

    fun showAllContestantsDeletedUseCaseSuccessMessage() {
        context.showShortToast(context.getString(R.string.all_contestants_deleted_successfully))
    }

    fun showAllContestantsDeletedUseCaseErrorMessage(errorMessage: String) {
        context.showLongToast(errorMessage)
    }

    fun showAllWeekHighlightsDeletedUseCaseErrorMessage(errorMessage: String) {
        context.showLongToast(errorMessage)
    }
}