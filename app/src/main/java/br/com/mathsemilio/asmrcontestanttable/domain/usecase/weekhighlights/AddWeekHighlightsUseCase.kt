/*
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package br.com.mathsemilio.asmrcontestanttable.domain.usecase.weekhighlights

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable
import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.WeekHighlightsEndpoint

open class AddWeekHighlightsUseCase(
    private val endpoint: WeekHighlightsEndpoint?
) : BaseObservable<AddWeekHighlightsUseCase.Listener>() {

    interface Listener {
        fun onWeekHighlightsAddedSuccessfully()

        fun onAddWeekHighlightsFailed()
    }

    open suspend fun insertWeekHighlights(
        firstContestantName: String,
        secondContestantName: String
    ) {
        endpoint?.insertWeekHighlights(
            WeekHighlights(0, getWeekNumber(), firstContestantName, secondContestantName)
        ).also { result ->
            when (result) {
                is Result.Completed -> notifyListener { listener ->
                    listener.onWeekHighlightsAddedSuccessfully()
                }
                is Result.Failed -> notifyListener { listener ->
                    listener.onAddWeekHighlightsFailed()
                }
            }
        }
    }

    private suspend fun getWeekNumber(): Int {
        var weekNumber = 0

        endpoint?.getWeekNumber().also { result ->
            when (result) {
                is Result.Completed -> weekNumber = result.data!!
                is Result.Failed -> throw RuntimeException(result.errorMessage!!)
            }
        }

        return weekNumber
    }
}