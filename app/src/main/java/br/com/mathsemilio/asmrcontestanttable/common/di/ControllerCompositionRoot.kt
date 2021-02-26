package br.com.mathsemilio.asmrcontestanttable.common.di

class ControllerCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {

    val coroutineScopeProvider get() = activityCompositionRoot.coroutineScopeProvider

    val dialogHelper get() = activityCompositionRoot.dialogHelper

    val messagesHelper get() = activityCompositionRoot.messagesHelper

    val viewFactory get() = activityCompositionRoot.viewFactory

    val preferencesRepository get() = activityCompositionRoot.preferencesRepository

    val fetchContestantsUseCase get() = activityCompositionRoot.fetchContestantsUseCase

    val fetchWeekHighlightsUseCase get() = activityCompositionRoot.fetchWeekHighlightsUseCase
}