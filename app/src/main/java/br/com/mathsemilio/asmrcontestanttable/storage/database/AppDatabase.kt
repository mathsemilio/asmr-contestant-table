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

package br.com.mathsemilio.asmrcontestanttable.storage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.mathsemilio.asmrcontestanttable.common.APP_DATABASE
import br.com.mathsemilio.asmrcontestanttable.data.dao.ContestantDAO
import br.com.mathsemilio.asmrcontestanttable.data.dao.WeekHighlightsDAO
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights

@Database(
    entities = [ASMRContestant::class, WeekHighlights::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract val contestantDAO: ContestantDAO
    abstract val weekHighlightsDAO: WeekHighlightsDAO

    companion object {
        private val LOCK = Any()

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null)
                return tempInstance

            synchronized(LOCK) {
                val databaseInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    APP_DATABASE
                ).build()

                INSTANCE = databaseInstance
                return INSTANCE!!
            }
        }
    }
}