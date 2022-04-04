package it.unibo.ise.lab.thermostat

import it.unibo.tuprolog.core.Atom
import it.unibo.tuprolog.solve.Solution
import it.unibo.tuprolog.solve.Solver
import it.unibo.tuprolog.solve.channel.OutputChannel
import it.unibo.tuprolog.solve.library.Libraries
import it.unibo.tuprolog.theory.parsing.ClausesParser
import java.io.File

object Thermostat {

    private val environment = File(this::class.java.getResource("environment.txt")!!.file)

    private val specification = this::class.java.getResource("thermostat.pl")!!
        .readText()
        .replace("/path/to/environment.txt", environment.absolutePath)
        .let { ClausesParser.withStandardOperators.parseTheory(it) }

    @JvmStatic
    fun main(args: Array<String>) {
        val solver = Solver.prolog.solverWithDefaultBuiltins(
            staticKb = specification,
            otherLibraries = Libraries.of(AgentLib),
            stdOut = OutputChannel.stdOut()
        )
        val solution = solver.solveOnce(Atom.of("start"))
        if (solution is Solution.Halt) {
            throw solution.exception
        }
    }
}