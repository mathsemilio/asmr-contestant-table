package br.com.mathsemilio.asmrcontestanttable.common.di

class ControllerCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {

    val coroutineScopeProvider get() = activityCompositionRoot.coroutineScopeProvider

    val eventPoster get() = activityCompositionRoot.eventPoster

    val dialogHelper get() = activityCompositionRoot.dialogManager

    val messagesHelper get() = activityCompositionRoot.messagesManager

    val viewFactory get() = activityCompositionRoot.viewFactory

    val addContestantUseCase get() = activityCompositionRoot.addContestantUseCase

    val addWeekHighlightsUseCase get() = activityCompositionRoot.addWeekHighlightsUseCase

    val updateContestantUseCase get() = activityCompositionRoot.updateContestantUseCase

    val fetchContestantsUseCase get() = activityCompositionRoot.fetchContestantsUseCase

    val fetchWeekHighlightsUseCase get() = activityCompositionRoot.fetchWeekHighlightsUseCase

    val deleteContestantsUseCase get() = activityCompositionRoot.deleteContestantsUseCase
}