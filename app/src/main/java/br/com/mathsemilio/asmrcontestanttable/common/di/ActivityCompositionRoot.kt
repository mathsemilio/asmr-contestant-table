package br.com.mathsemilio.asmrcontestanttable.common.di

import androidx.appcompat.app.AppCompatActivity
import br.com.mathsemilio.asmrcontestanttable.data.repository.ContestantsRepository
import br.com.mathsemilio.asmrcontestanttable.data.repository.PreferencesRepository
import br.com.mathsemilio.asmrcontestanttable.data.repository.WeekHighlightsRepository
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.FetchContestantsUseCase
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.FetchWeekHighlightsUseCase
import br.com.mathsemilio.asmrcontestanttable.storage.database.AppDatabase
import br.com.mathsemilio.asmrcontestanttable.storage.preferences.PreferencesSource
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.*
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.ViewFactory

class ActivityCompositionRoot(private val activity: AppCompatActivity) {

    private val database get() = AppDatabase.getDatabase(activity)

    private val contestantsDao by lazy { database.contestantDAO }

    private val weekHighlightsDAO by lazy { database.weekHighlightsDAO }

    private val contestantsRepository by lazy { ContestantsRepository(contestantsDao) }

    private val weekHighlightsRepository by lazy { WeekHighlightsRepository(weekHighlightsDAO) }

    private val dispatcherProvider get() = DispatcherProvider

    val coroutineScopeProvider get() = CoroutineScopeProvider

    private val _dialogHelper by lazy {
        DialogHelper(activity.supportFragmentManager, activity)
    }
    val dialogHelper get() = _dialogHelper

    private val _messagesHelper by lazy {
        MessagesHelper(activity)
    }
    val messagesHelper get() = _messagesHelper

    private val _screensNavigator by lazy {
        ScreensNavigator(activity.supportFragmentManager, activity as FragmentContainerHelper)
    }
    val screensNavigator get() = _screensNavigator

    private val _viewFactory by lazy {
        ViewFactory(activity.layoutInflater)
    }
    val viewFactory get() = _viewFactory

    private val _preferencesRepository by lazy {
        PreferencesRepository(PreferencesSource(activity))
    }
    val preferencesRepository get() = _preferencesRepository

    private val _fetchContestantsUseCase by lazy {
        FetchContestantsUseCase(contestantsRepository, dispatcherProvider)
    }
    val fetchContestantsUseCase get() = _fetchContestantsUseCase

    private val _fetchWeekHighlightsUseCase by lazy {
        FetchWeekHighlightsUseCase(weekHighlightsRepository, dispatcherProvider)
    }
    val fetchWeekHighlightsUseCase get() = _fetchWeekHighlightsUseCase
}