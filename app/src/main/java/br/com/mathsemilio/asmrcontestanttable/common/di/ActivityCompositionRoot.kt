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
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.DialogManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.FragmentContainerManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.FragmentManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.navigation.NavigationEventListener
import br.com.mathsemilio.asmrcontestanttable.ui.common.navigation.ScreensNavigator
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.ViewFactory

class ActivityCompositionRoot(
    private val activity: AppCompatActivity,
    private val compositionRoot: CompositionRoot
) {
    private val database get() = AppDatabase.getDatabase(activity)

    private val contestantsDao by lazy { database.contestantDAO }

    private val weekHighlightsDAO by lazy { database.weekHighlightsDAO }

    private val contestantsEndpoint by lazy {
        ContestantsEndpoint(contestantsDao, weekHighlightsDAO, dispatcherProvider)
    }

    private val weekHighlightsEndpoint by lazy {
        WeekHighlightsEndpoint(weekHighlightsDAO, dispatcherProvider)
    }

    private val dispatcherProvider get() = compositionRoot.dispatcherProvider

    private val fragmentManager by lazy {
        FragmentManager(activity.supportFragmentManager, activity as FragmentContainerManager)
    }

    private val _dialogManager by lazy {
        DialogManager(activity.supportFragmentManager, activity)
    }

    private val _messagesManager by lazy {
        MessagesManager(activity)
    }

    private val _screensNavigator by lazy {
        ScreensNavigator(fragmentManager, activity as NavigationEventListener)
    }

    private val _viewFactory by lazy {
        ViewFactory(activity.layoutInflater)
    }

    private val _addContestantUseCase by lazy {
        AddContestantUseCase(contestantsEndpoint)
    }

    private val _addWeekHighlightsUseCase by lazy {
        AddWeekHighlightsUseCase(weekHighlightsEndpoint)
    }

    private val _updateContestantUseCase by lazy {
        UpdateContestantUseCase(contestantsEndpoint)
    }

    private val _fetchContestantsUseCase by lazy {
        FetchContestantsUseCase(contestantsEndpoint)
    }

    private val _fetchWeekHighlightsUseCase by lazy {
        FetchWeekHighlightsUseCase(weekHighlightsEndpoint)
    }

    private val _deleteContestantsUseCase by lazy {
        DeleteContestantsUseCase(contestantsEndpoint)
    }

    val coroutineScopeProvider get() = compositionRoot.coroutineScopeProvider

    val dialogManager get() = _dialogManager

    val eventPublisher get() = compositionRoot.eventPublisher

    val eventSubscriber get() = compositionRoot.eventSubscriber

    val messagesManager get() = _messagesManager

    val screensNavigator get() = _screensNavigator

    val viewFactory get() = _viewFactory

    val addContestantUseCase get() = _addContestantUseCase

    val addWeekHighlightsUseCase get() = _addWeekHighlightsUseCase

    val updateContestantUseCase get() = _updateContestantUseCase

    val fetchContestantsUseCase get() = _fetchContestantsUseCase

    val fetchWeekHighlightsUseCase get() = _fetchWeekHighlightsUseCase

    val deleteContestantsUseCase get() = _deleteContestantsUseCase
}