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

package br.com.mathsemilio.asmrcontestanttable.ui.common.navigation.destination

import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.common.NAV_DESTINATION_CONTESTANTS_TABLE
import br.com.mathsemilio.asmrcontestanttable.common.NAV_DESTINATION_WEEK_HIGHLIGHTS

object Destinations {

    val CONTESTANTS_TABLE = NavDestination(
        name = NAV_DESTINATION_CONTESTANTS_TABLE,
        titleId = R.string.contestants_table
    )

    val WEEK_HIGHLIGHTS = NavDestination(
        name = NAV_DESTINATION_WEEK_HIGHLIGHTS,
        titleId = R.string.week_highlights
    )
}
