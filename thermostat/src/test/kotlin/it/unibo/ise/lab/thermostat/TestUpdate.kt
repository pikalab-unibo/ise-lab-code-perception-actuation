package it.unibo.ise.lab.thermostat

import it.unibo.tuprolog.core.Struct
import it.unibo.tuprolog.core.parsing.parse
import it.unibo.tuprolog.solve.Solution
import it.unibo.tuprolog.solve.Solver
import it.unibo.tuprolog.theory.Theory
import it.unibo.tuprolog.theory.parsing.parse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class TestUpdate {
    private val testTheory = Theory.parse(
        """
        f(1).
        f(2).
        f(3).
        """.trimIndent()
    )

    @Test
    fun testSuccessfulUpdate() {
        val solver = Solver.prolog.solverOf(dynamicKb = testTheory, libraries = AgentLib)
        val query = Struct.parse("update(f(a)), update(f(b)), update(f(c))")

        val solutions = solver.solve(query).toList()
        assertEquals(1, solutions.size)
        assertIs<Solution.Yes>(solutions[0])

        assertEquals(
            listOf(
                Struct.parse("f(a)"),
                Struct.parse("f(b)"),
                Struct.parse("f(c)"),
            ),
            solver.dynamicKb[Struct.parse("f(X)")].map { it.head }.toList()
        )
    }

    @Test
    fun testFailingUpdate() {
        val solver = Solver.prolog.solverOf(libraries = AgentLib)
        val query = Struct.parse("update(f(a))")

        val solutions = solver.solve(query).toList()
        assertEquals(1, solutions.size)
        assertIs<Solution.No>(solutions[0])

        assertEquals(0, solver.dynamicKb.size)
    }
}