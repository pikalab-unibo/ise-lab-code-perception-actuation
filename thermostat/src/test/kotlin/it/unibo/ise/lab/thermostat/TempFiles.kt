package it.unibo.ise.lab.thermostat

import java.io.File

object TempFiles : Iterator<File> {
    private val generator = generateSequence { File.createTempFile("environment", ".txt") }.iterator()

    override fun hasNext(): Boolean = true

    @Synchronized
    override fun next(): File = generator.next()
}