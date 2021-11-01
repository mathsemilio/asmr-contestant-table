package br.com.mathsemilio.asmrcontestanttable.testdata

import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant

object ContestantsTestDataProvider {

    val contestants = listOf(
        ASMRContestant(0, "Sirius Eyes ASMR", 3, 1, 0, 0),
        ASMRContestant(0, "Pelagea ASMR", 1, 0, 0, 1),
        ASMRContestant(0, "ASMR With Kim", 0, 0, 1, 0)
    )

    val updatedContestants = listOf(
        ASMRContestant(0, "Sirius Eyes ASMR", 6, 2, 0, 0),
        ASMRContestant(0, "Pelagea ASMR", 2, 0, 0, 2),
        ASMRContestant(0, "ASMR With Kim", 0, 0, 2, 0)
    )
}