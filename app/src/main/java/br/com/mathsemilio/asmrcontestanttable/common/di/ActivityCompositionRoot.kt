package br.com.mathsemilio.asmrcontestanttable.common.di

import androidx.appcompat.app.AppCompatActivity
import br.com.mathsemilio.asmrcontestanttable.data.repository.ContestantsRepository
import br.com.mathsemilio.asmrcontestanttable.data.repository.WeekHighlightsRepository
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.*
import br.com.mathsemilio.asmrcontestanttable.storage.database.AppDatabase
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.DialogManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.FragmentContainerManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.ScreensNavigator
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.ViewFactory

class ActivityCompositionRoot(
    private val activity: AppCompatActivity,
    private val compositionRoot: CompositionRoot
) {
    private val database get() = AppDatabase.getDatabase(activity)

    private val contestantsDao by lazy {
        database.contestantDAO
    }

    private val weekHighlightsDAO by lazy {
        database.weekHighlightsDAO
    }

    private val contestantsRepository by lazy {
        ContestantsRepository(contestantsDao)
    }

    private val weekHighlightsRepository by lazy {
        WeekHighlightsRepository(weekHighlightsDAO)
    }

    private val dispatcherProvider get() = compositionRoot.dispatcherProvider

    val coroutineScopeProvider get() = compositionRoot.coroutineScopeProvider

    val eventPoster get() = compositionRoot.eventPoster

    private val _dialogHelper by lazy {
        DialogManager(activity.supportFragmentManager, activity)
    }
    val dialogHelper get() = _dialogHelper

    private val _messagesHelper by lazy {
        MessagesManager(activity)
    }
    val messagesHelper get() = _messagesHelper

    private val _screensNavigator by lazy {
        ScreensNavigator(activity.supportFragmentManager, activity as FragmentContainerManager)
    }
    val screensNavigator get() = _screensNavigator

    private val _viewFactory by lazy {
        ViewFactory(activity.layoutInflater)
    }
    val viewFactory get() = _viewFactory

    private val _addContestantUseCase by lazy {
        AddContestantUseCase(contestantsRepository, dispatcherProvider)
    }
    val addContestantUseCase get() = _addContestantUseCase

    private val _addWeekHighlightsUseCase by lazy {
        AddWeekHighlightsUseCase(weekHighlightsRepository, dispatcherProvider)
    }
    val addWeekHighlightsUseCase get() = _addWeekHighlightsUseCase

    private val _updateContestantUseCase by lazy {
        UpdateContestantUseCase(contestantsRepository, dispatcherProvider)
    }
    val updateContestantUseCase get() = _updateContestantUseCase

    private val _fetchContestantsUseCase by lazy {
        FetchContestantsUseCase(contestantsRepository, dispatcherProvider)
    }
    val fetchContestantsUseCase get() = _fetchContestantsUseCase

    private val _fetchWeekHighlightsUseCase by lazy {
        FetchWeekHighlightsUseCase(weekHighlightsRepository, dispatcherProvider)
    }
    val fetchWeekHighlightsUseCase get() = _fetchWeekHighlightsUseCase

    private val _deleteContestantsUseCase by lazy {
        DeleteContestantsUseCase(contestantsRepository, weekHighlightsRepository, dispatcherProvider)
    }
    val deleteContestantsUseCase get() = _deleteContestantsUseCase
}