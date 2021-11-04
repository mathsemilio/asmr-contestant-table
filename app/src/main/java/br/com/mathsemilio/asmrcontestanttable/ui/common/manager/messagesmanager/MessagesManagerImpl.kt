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

package br.com.mathsemilio.asmrcontestanttable.ui.common.manager.messagesmanager

import android.content.Context
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.common.showLongToast
import br.com.mathsemilio.asmrcontestanttable.common.showShortToast

class MessagesManagerImpl(private val context: Context) : MessagesManager {

    override fun showAllContestantsDeletedSuccessfullyMessage() =
        context.showShortToast(context.getString(R.string.message_contest_rebooted_successfully))

    override fun showUnexpectedErrorOccurredMessage() =
        context.showLongToast(context.getString(R.string.message_unexpected_error_occurred))

    override fun showReadExternalStoragePermissionDeniedMessage() {
        context.showLongToast(context.getString(R.string.message_read_external_storage_permission_denied))
    }

    override fun showReadExternalStoragePermissionDeniedPermanentlyMessage() {
        context.showLongToast(
            context.getString(R.string.message_read_external_storage_permission_denied_permanently)
        )
    }
}