package br.com.mathsemilio.asmrcontestanttable.ui.common.event

import br.com.mathsemilio.asmrcontestanttable.ui.ToolbarAction

sealed class ToolbarActionEvent(val action: ToolbarAction) {
    class OnActionClicked(action: ToolbarAction) : ToolbarActionEvent(action)
}