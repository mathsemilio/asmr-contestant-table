package br.com.mathsemilio.asmrcontestanttable.common.di

class ControllerCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {

    val coroutineScopeProvider get() = activityCompositionRoot.coroutineScopeProvider

    val eventPoster get() = activityCompositionRoot.eventPoster

    val dialogHelper get() = activityCompositionRoot.dialogHelper

    val messagesHelper get() = activityCompositionRoot.messagesHelper

    val viewFactory get() = activityCompositionRoot.viewFactory

    val addContestantUseCase get() = activityCompositionRoot.addContestantUseCase

    val addWeekHighlightsUseCase get() = activityCompositionRoot.addWeekHighlightsUseCase

    val updateContestantUseCase get() = activityCompositionRoot.updateContestantUseCase

    val fetchContestantsUseCase get() = activityCompositionRoot.fetchContestantsUseCase

    val fetchWeekHighlightsUseCase get() = activityCompositionRoot.fetchWeekHighlightsUseCase

    val deleteContestantsUseCase get() = activityCompositionRoot.deleteContestantsUseCase
}