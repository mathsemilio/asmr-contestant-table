package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.contestantdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.BaseObservableView

class ContestantDetailsView(layoutInflater: LayoutInflater, container: ViewGroup?) :
    BaseObservableView<ContestantDetailsContract.View.Listener>(), ContestantDetailsContract.View {

    private lateinit var textViewContestantsDetailsName: TextView
    private lateinit var textViewNumberTimesSlept: TextView
    private lateinit var textViewNumberTimesDidNotSlept: TextView
    private lateinit var textViewNumberTimesFeltTired: TextView
    private lateinit var buttonIncrementTimesSlept: ImageButton
    private lateinit var buttonIncrementTimesDidNotSlept: ImageButton
    private lateinit var buttonIncrementTimesFeltTired: ImageButton

    init {
        rootView = layoutInflater.inflate(R.layout.bottom_sheet_contestant_details, container, false)
        initializeViews()
        attachClickListeners()
    }

    private fun initializeViews() {
        textViewContestantsDetailsName = findViewById(R.id.text_view_contestants_details_name)
        textViewNumberTimesSlept = findViewById(R.id.text_view_number_times_slept)
        textViewNumberTimesDidNotSlept = findViewById(R.id.text_view_number_times_did_not_slept)
        textViewNumberTimesFeltTired = findViewById(R.id.text_view_number_times_felt_tired)
        buttonIncrementTimesSlept = findViewById(R.id.image_button_increment_times_slept)
        buttonIncrementTimesDidNotSlept = findViewById(R.id.image_button_increment_times_did_not_slept)
        buttonIncrementTimesFeltTired = findViewById(R.id.image_button_increment_times_felt_tired)
    }

    private fun attachClickListeners() {
        buttonIncrementTimesSlept.setOnClickListener { onIncrementTimesSleptButtonClicked() }
        buttonIncrementTimesDidNotSlept.setOnClickListener { onIncrementTimesDidNotSleptClicked() }
        buttonIncrementTimesFeltTired.setOnClickListener { onIncrementTimesFeltTired() }
    }

    override fun bindContestantsDetails(contestant: ASMRContestant) {
        textViewContestantsDetailsName.text = contestant.name
        textViewNumberTimesSlept.text = contestant.timesSlept.toString()
        textViewNumberTimesDidNotSlept.text = contestant.timesDidNotSlept.toString()
        textViewNumberTimesFeltTired.text = contestant.timesFeltTired.toString()
    }

    private fun onIncrementTimesSleptButtonClicked() {
        listeners.forEach { it.onIncrementTimesSleptButtonClicked() }
    }

    private fun onIncrementTimesDidNotSleptClicked() {
        listeners.forEach { it.onIncrementTimesDidNotSleptButtonClicked() }
    }

    private fun onIncrementTimesFeltTired() {
        listeners.forEach { it.onIncrementTimesFeltTiredButtonClicked() }
    }
}