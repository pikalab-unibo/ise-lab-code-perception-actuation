package it.unibo.ise.lab.thermostat

import it.unibo.tuprolog.core.*
import it.unibo.tuprolog.solve.Solution
import it.unibo.tuprolog.solve.Solver
import java.io.File
import java.util.UUID
import kotlin.random.Random
import kotlin.test.*

class TestReadText {

    @Test
    fun testReadAtom() {
        val file = TempFiles.next()
        val value = Atom.of(UUID.randomUUID().toString())

        testReading(file, value)
    }

    @Test
    fun testReadInteger() {
        val random = Random(1)
        val file = TempFiles.next()
        val value = Integer.of(random.nextInt())

        testReading(file, value)
    }

    @Test
    fun testReadReal() {
        val random = Random(1)
        val file = TempFiles.next()
        val value = Real.of(random.nextDouble())

        testReading(file, value)
    }

    @Test
    fun testReadFailure() {
        val file = TempFiles.next().parentFile.resolve("missing.txt")
        val solver = Solver.prolog.solverOf(libraries = AgentLib)

        assertFalse { file.exists() }
        val solutions = solver.solve(Struct.of("read_text", Atom.of(file.absolutePath), Var.anonymous())).toList()
        assertEquals(1, solutions.size)
        assertIs<Solution.No>(solutions[0])
    }

    @Suppress("LocalVariableName")
    private fun testReading(file: File, value: Term) {
        val solver = Solver.prolog.solverOf(libraries = AgentLib)
        val path = Atom.of(file.absolutePath)
        val content = value.toString()

        val X = Var.of("X")

        file.writeText(content)
        val solutions = solver.solve(Struct.of("read_text", path, X)).toList()
        assertEquals(1, solutions.size)
        assertIs<Solution.Yes>(solutions[0])
        assertEquals(value, solutions[0].substitution[X])
    }
}