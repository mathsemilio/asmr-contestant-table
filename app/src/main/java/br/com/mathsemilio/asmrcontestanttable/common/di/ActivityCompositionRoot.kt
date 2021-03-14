package br.com.mathsemilio.asmrcontestanttable.common.di

import androidx.appcompat.app.AppCompatActivity
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.AddContestantUseCase
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.DeleteContestantsUseCase
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.FetchContestantsUseCase
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.UpdateContestantUseCase
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.weekhighlights.AddWeekHighlightsUseCase
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.weekhighlights.FetchWeekHighlightsUseCase
import br.com.mathsemilio.asmrcontestanttable.storage.database.AppDatabase
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.ContestantsEndpoint
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.WeekHighlightsEndpoint
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

    private val contestantsDao by lazy { database.contestantDAO }

    private val weekHighlightsDAO by lazy { database.weekHighlightsDAO }

    private val contestantsEndpoint by lazy {
        ContestantsEndpoint(
            contestantsDao,
            weekHighlightsDAO,
            dispatcherProvider
        )
    }

    private val weekHighlightsEndpoint by lazy {
        WeekHighlightsEndpoint(weekHighlightsDAO, dispatcherProvider)
    }

    private val dispatcherProvider get() = compositionRoot.dispatcherProvider

    val coroutineScopeProvider get() = compositionRoot.coroutineScopeProvider

    val eventPoster get() = compositionRoot.eventPoster

    private val _dialogManager by lazy {
        DialogManager(activity.supportFragmentManager, activity)
    }
    val dialogManager get() = _dialogManager

    private val _messagesManager by lazy {
        MessagesManager(activity)
    }
    val messagesManager get() = _messagesManager

    private val _screensNavigator by lazy {
        ScreensNavigator(
            eventPoster,
            activity.supportFragmentManager,
            activity as FragmentContainerManager
        )
    }
    val screensNavigator get() = _screensNavigator

    private val _viewFactory by lazy {
        ViewFactory(activity.layoutInflater)
    }
    val viewFactory get() = _viewFactory

    private val _addContestantUseCase by lazy {
        AddContestantUseCase(contestantsEndpoint)
    }
    val addContestantUseCase get() = _addContestantUseCase

    private val _addWeekHighlightsUseCase by lazy {
        AddWeekHighlightsUseCase(weekHighlightsEndpoint)
    }
    val addWeekHighlightsUseCase get() = _addWeekHighlightsUseCase

    private val _updateContestantUseCase by lazy {
        UpdateContestantUseCase(contestantsEndpoint)
    }
    val updateContestantUseCase get() = _updateContestantUseCase

    private val _fetchContestantsUseCase by lazy {
        FetchContestantsUseCase(contestantsEndpoint)
    }
    val fetchContestantsUseCase get() = _fetchContestantsUseCase

    private val _fetchWeekHighlightsUseCase by lazy {
        FetchWeekHighlightsUseCase(weekHighlightsEndpoint)
    }
    val fetchWeekHighlightsUseCase get() = _fetchWeekHighlightsUseCase

    private val _deleteContestantsUseCase by lazy {
        DeleteContestantsUseCase(contestantsEndpoint)
    }
    val deleteContestantsUseCase get() = _deleteContestantsUseCase
}