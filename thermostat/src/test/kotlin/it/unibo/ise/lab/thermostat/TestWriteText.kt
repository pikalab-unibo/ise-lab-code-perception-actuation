package it.unibo.ise.lab.thermostat

import it.unibo.tuprolog.core.Atom
import it.unibo.tuprolog.core.Struct
import it.unibo.tuprolog.core.parsing.TermReader
import it.unibo.tuprolog.solve.Solution
import it.unibo.tuprolog.solve.Solver
import it.unibo.tuprolog.solve.library.Libraries
import java.io.File
import java.util.UUID
import kotlin.test.*

class TestWriteText {

    @Test
    fun testWriteActuallyWrites() {
        val file = TempFiles.next()
        val value = UUID.randomUUID()

        testWriting(file, value)
    }

    @Test
    fun testWriteActuallyOverwrites() {
        val file = TempFiles.next()
        val value1 = UUID.randomUUID()
        val value2 = UUID.randomUUID()

        testWriting(file, value1)
        testWriting(file, value2)
    }

    private fun testWriting(file: File, value: UUID) {
        val solver = Solver.prolog.solverOf(Libraries.of(AgentLib))
        val path = Atom.of(file.absolutePath)
        val content = Atom.of(value.toString())

        assertTrue { file.canWrite() }
        val solutions = solver.solve(Struct.of("write_text", path, content)).toList()
        assertEquals(1, solutions.size)
        assertIs<Solution.Yes>(solutions[0])
        assertEquals(content, TermReader.withNoOperator.readTerm(file.inputStream()))
    }
}