package it.unibo.ise.lab.thermostat

import it.unibo.tuprolog.solve.library.AliasedLibrary
import it.unibo.tuprolog.solve.library.Library

object AgentLib : AliasedLibrary by Library.aliased(
    alias = "prolog.agency",
    primitives = listOf(
        ReadText,
        WriteText,
        Update
    ).associate { it.descriptionPair }
)