package it.unibo.ise.lab.thermostat

import it.unibo.tuprolog.core.Atom
import it.unibo.tuprolog.core.Term
import it.unibo.tuprolog.solve.ExecutionContext
import it.unibo.tuprolog.solve.primitive.BinaryRelation
import it.unibo.tuprolog.solve.primitive.Solve
import java.io.File
import java.io.IOException

object WriteText : BinaryRelation.Predicative<ExecutionContext>("write_text") {
    override fun Solve.Request<ExecutionContext>.compute(first: Term, second: Term): Boolean {
        ensuringArgumentIsAtom(0)
        val path = File((first as Atom).value)
        return if (path.canWrite()) {
            try {
                val text = second.toString()
                path.writeText(text)
                true
            } catch (_: IOException) {
                false
            }
        } else {
            false
        }
    }

}