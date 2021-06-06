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

package br.com.mathsemilio.asmrcontestanttable

import android.app.Application
import br.com.mathsemilio.asmrcontestanttable.common.di.CompositionRoot
import kotlinx.coroutines.IO_PARALLELISM_PROPERTY_NAME

class ASMRContestantsTableApplication : Application() {

    private lateinit var _compositionRoot: CompositionRoot
    val compositionRoot get() = _compositionRoot

    override fun onCreate() {
        super.onCreate()

        // Removes Dispatchers.IO parallelism level limitation (Number of CPU cores or 64 threads)
        System.setProperty(IO_PARALLELISM_PROPERTY_NAME, Int.MAX_VALUE.toString())

        _compositionRoot = CompositionRoot()
    }
}