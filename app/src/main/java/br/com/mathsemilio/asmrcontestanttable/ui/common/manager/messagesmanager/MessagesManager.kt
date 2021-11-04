package br.com.mathsemilio.asmrcontestanttable.ui.common.manager.messagesmanager

interface MessagesManager {
    fun showAllContestantsDeletedSuccessfullyMessage()

    fun showUnexpectedErrorOccurredMessage()

    fun showReadExternalStoragePermissionDeniedMessage()

    fun showReadExternalStoragePermissionDeniedPermanentlyMessage()
}