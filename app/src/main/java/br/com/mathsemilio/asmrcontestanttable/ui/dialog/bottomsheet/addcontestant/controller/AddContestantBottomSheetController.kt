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

import android.Manifest
import android.net.Uri
import br.com.mathsemilio.asmrcontestanttable.common.READ_EXTERNAL_STORAGE_REQUEST_CODE
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventPublisher
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.AddContestantUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.ContestantsModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.PermissionsHelper
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.PermissionsHelper.PermissionResult
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.messagesmanager.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant.view.AddContestantView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class AddContestantBottomSheetController(
    private val permissionsHelper: PermissionsHelper,
    private val messagesManager: MessagesManager,
    private val coroutineScope: CoroutineScope,
    private val eventPublisher: EventPublisher,
    private val addContestantUseCase: AddContestantUseCase
) : AddContestantView.Listener,
    AddContestantUseCase.Listener,
    PermissionsHelper.Listener {

    private lateinit var view: AddContestantView

    private lateinit var delegate: AddContestantControllerEventDelegate

    override fun onAddButtonClicked(contestantName: String, profilePictureUri: Uri?) {
        coroutineScope.launch {
            view.changeAddButtonState()
            addContestantUseCase.addContestant(contestantName, profilePictureUri)
        }
    }

    override fun onAddProfilePictureButtonClicked() {
        if (permissionsHelper.hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            delegate.onLaunchImagePickerRequested()
        } else {
            permissionsHelper.requestPermission(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_STORAGE_REQUEST_CODE
            )
        }
    }

    override fun onContestantAddedSuccessfully() {
        delegate.onDismissBottomSheetRequested()
        eventPublisher.publish(ContestantsModifiedEvent.ContestantAdded)
    }

    override fun onAddContestantFailed() {
        view.revertAddButtonState()
        messagesManager.showUnexpectedErrorOccurredMessage()
    }

    override fun onPermissionRequestResult(result: PermissionResult) {
        when (result) {
            PermissionResult.GRANTED ->
                delegate.onLaunchImagePickerRequested()
            PermissionResult.DENIED ->
                messagesManager.showReadExternalStoragePermissionDeniedMessage()
            PermissionResult.DENIED_PERMANENTLY ->
                messagesManager.showReadExternalStoragePermissionDeniedPermanentlyMessage()
        }
    }

    fun bindView(view: AddContestantView) {
        this.view = view
    }

    fun bindImageUri(imageUri: Uri?) {
        view.bind(imageUri)
    }

    fun addEventDelegate(delegate: AddContestantControllerEventDelegate) {
        this.delegate = delegate
    }

    fun onStart() {
        view.addListener(this)
        permissionsHelper.addListener(this)
        addContestantUseCase.addListener(this)
    }

    fun onStop() {
        view.removeListener(this)
        permissionsHelper.removeListener(this)
        addContestantUseCase.removeListener(this)
        coroutineScope.coroutineContext.cancelChildren()
    }
}