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

package br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.update

import br.com.mathsemilio.asmrcontestanttable.domain.model.*
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.ContestantsEndpoint

open class UpdateContestantTimesDidNotSleptUseCase(private val endpoint: ContestantsEndpoint?) {

    sealed class UpdateContestantTimesDidNotSleptResult {
        object Completed : UpdateContestantTimesDidNotSleptResult()
        object Failed : UpdateContestantTimesDidNotSleptResult()
    }

    open suspend fun updateContestantTimesDidNotSlept(
        contestant: ASMRContestant
    ): UpdateContestantTimesDidNotSleptResult {
        var result: UpdateContestantTimesDidNotSleptResult

        val updatedContestant = contestant.copy(
            timesDidNotSlept = contestant.timesDidNotSlept.inc()
        )

        endpoint?.updateContestant(updatedContestant).also { endpointResult ->
            result = when (endpointResult) {
                is Result.Completed -> UpdateContestantTimesDidNotSleptResult.Completed
                is Result.Failed -> UpdateContestantTimesDidNotSleptResult.Failed
                null -> UpdateContestantTimesDidNotSleptResult.Failed
            }
        }

        return result
    }
}
