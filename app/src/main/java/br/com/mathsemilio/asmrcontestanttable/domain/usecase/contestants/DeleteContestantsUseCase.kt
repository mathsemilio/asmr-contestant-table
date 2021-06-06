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

package br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable
import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.ContestantsEndpoint

class DeleteContestantsUseCase(private val endpoint: ContestantsEndpoint) :
    BaseObservable<DeleteContestantsUseCase.Listener>() {

    interface Listener {
        fun onAllContestantsDeletedSuccessfully()

        fun onDeleteAllContestantsFailed()
    }

    suspend fun deleteAllContestants() {
        endpoint.deleteAllContestants().also { result ->
            when (result) {
                is Result.Completed -> notifyListener { it.onAllContestantsDeletedSuccessfully() }
                is Result.Failed -> notifyListener { it.onDeleteAllContestantsFailed() }
            }
        }
    }
}