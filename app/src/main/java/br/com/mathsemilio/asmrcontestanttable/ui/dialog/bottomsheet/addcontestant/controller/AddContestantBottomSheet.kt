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

package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant.controller

import android.view.*
import android.os.Bundle
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.common.BaseBottomSheetDialogFragment

class AddContestantBottomSheet : BaseBottomSheetDialogFragment(),
    AddContestantControllerEventDelegate {

    private lateinit var controller: AddContestantBottomSheetController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = compositionRoot.controllerFactory.addContestantBottomSheetController
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = compositionRoot.viewFactory.getAddContestView(container)
        controller.bindView(view)
        return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller.addDelegate(this)
    }

    override fun onDismissBottomSheetRequested() = dismiss()

    override fun onStart() {
        super.onStart()
        controller.onStart()
    }

    override fun onStop() {
        super.onStop()
        controller.onStop()
        controller.removeDelegate()
    }
}
