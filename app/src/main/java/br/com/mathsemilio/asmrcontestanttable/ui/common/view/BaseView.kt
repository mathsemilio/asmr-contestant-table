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

package br.com.mathsemilio.asmrcontestanttable.ui.common.view

import android.content.Context
import androidx.annotation.StringRes

abstract class BaseView : View {

    private lateinit var _rootView: android.view.View

    override var rootView: android.view.View
        get() = _rootView
        set(view) { _rootView = view }

    protected val context: Context
        get() = _rootView.context

    protected fun getString(@StringRes id: Int) = context.getString(id)

    protected fun <T : android.view.View> findViewById(id: Int): T = _rootView.findViewById(id)
}
