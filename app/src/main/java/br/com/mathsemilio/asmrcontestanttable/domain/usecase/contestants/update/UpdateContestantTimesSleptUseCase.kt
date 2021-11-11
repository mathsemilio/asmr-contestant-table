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

import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.ContestantsEndpoint

open class UpdateContestantTimesSleptUseCase(private val endpoint: ContestantsEndpoint?) {

    sealed class UpdateContestantTimesSleptResult {
        object Completed : UpdateContestantTimesSleptResult()
        object Failed : UpdateContestantTimesSleptResult()
    }

    open suspend fun updateContestantTimesSlept(
        contestant: ASMRContestant
    ): UpdateContestantTimesSleptResult {
        var updateContestantTimesSleptResult: UpdateContestantTimesSleptResult

        val updatedContestant = contestant.copy(
            score = contestant.score.plus(3),
            timesSlept = contestant.timesSlept.inc()
        )

        endpoint?.updateContestant(updatedContestant).also { result ->
            updateContestantTimesSleptResult = when (result) {
                is Result.Completed -> UpdateContestantTimesSleptResult.Completed
                is Result.Failed -> UpdateContestantTimesSleptResult.Failed
                null -> UpdateContestantTimesSleptResult.Failed
            }
        }

        return updateContestantTimesSleptResult
    }
}
