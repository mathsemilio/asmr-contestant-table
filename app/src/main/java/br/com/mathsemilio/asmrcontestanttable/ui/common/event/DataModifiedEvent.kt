package br.com.mathsemilio.asmrcontestanttable.ui.common.event

sealed class DataModifiedEvent {
    object OnDataModified : DataModifiedEvent()
}