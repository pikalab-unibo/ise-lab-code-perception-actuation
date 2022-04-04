package it.unibo.ise.lab.thermostat

import it.unibo.tuprolog.core.Atom
import it.unibo.tuprolog.core.Substitution
import it.unibo.tuprolog.core.Term
import it.unibo.tuprolog.core.parsing.TermParser
import it.unibo.tuprolog.solve.ExecutionContext
import it.unibo.tuprolog.solve.primitive.BinaryRelation
import it.unibo.tuprolog.solve.primitive.Solve
import it.unibo.tuprolog.unify.Unificator.Companion.mguWith
import java.io.File
import java.io.IOException

object ReadText : BinaryRelation.Functional<ExecutionContext>("read_text") {
    override fun Solve.Request<ExecutionContext>.computeOneSubstitution(first: Term, second: Term): Substitution {
        ensuringArgumentIsAtom(0)
        val path = File((first as Atom).value)
        if (path.exists() && path.isFile && path.canRead()) {
            try {
                val text = path.readText()
                val term = TermParser.withOperators(context.operators).parseTerm(text)
                return second mguWith term
            } catch (_: IOException) {
                return Substitution.failed()
            }
        } else {
            return Substitution.failed()
        }
    }
}