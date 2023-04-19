package it.unibo.ise.lab.thermostat

import it.unibo.tuprolog.solve.library.Runtime
import it.unibo.tuprolog.solve.runtimeOf

object AgentLib : Runtime by runtimeOf(
    alias = "prolog.agency",
    ReadText,
    WriteText,
    Update
)
