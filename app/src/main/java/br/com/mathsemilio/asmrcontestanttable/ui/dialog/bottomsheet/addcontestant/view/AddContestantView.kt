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

package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant.view

import android.net.Uri
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.BaseObservableView

abstract class AddContestantView : BaseObservableView<AddContestantView.Listener>() {

    interface Listener {
        fun onAddProfilePictureButtonClicked()

        fun onAddButtonClicked(contestantName: String, profilePictureUri: Uri?)
    }

    abstract fun bind(profilePictureUri: Uri?)

    abstract fun changeAddButtonState()

    abstract fun revertAddButtonState()
}
