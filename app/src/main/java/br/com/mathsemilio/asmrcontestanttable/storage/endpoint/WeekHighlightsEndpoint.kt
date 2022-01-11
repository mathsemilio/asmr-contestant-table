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

package br.com.mathsemilio.asmrcontestanttable.storage.endpoint

import kotlinx.coroutines.*
import br.com.mathsemilio.asmrcontestanttable.domain.model.*
import br.com.mathsemilio.asmrcontestanttable.data.dao.WeekHighlightsDAO

open class WeekHighlightsEndpoint(private val weekHighlightsDAO: WeekHighlightsDAO?) {

    open suspend fun addWeekHighlights(weekHighlights: WeekHighlights): Result<Nothing> {
        return withContext(Dispatchers.IO) {
            try {
                weekHighlightsDAO?.insert(weekHighlights)
                Result.Completed(data = null)
            } catch (exception: Exception) {
                Result.Failed(exception = exception)
            }
        }
    }

    open suspend fun fetchWeekHighlights(): Result<List<WeekHighlights>> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Completed(data = weekHighlightsDAO?.fetchWeekHighlights())
            } catch (exception: Exception) {
                Result.Failed(exception = exception)
            }
        }
    }

    open suspend fun getWeekNumber(): Result<Int> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Completed(data = weekHighlightsDAO?.fetchWeekHighlights()?.size?.plus(1))
            } catch (exception: Exception) {
                Result.Failed(exception = exception)
            }
        }
    }
}
