package util

import java.io.File

object Util {

    fun getInstance(file: String): List<String> {
        return File(Thread.currentThread().contextClassLoader.getResource(file).file).readLines()
    }

    fun List<String>.toMatrix(): List<List<Int>> {
        return this.map { it.map { c -> (c + "").toInt() } }
    }
    fun List<String>.toStrMatrix(): List<List<String>> {
        return this.map { it.map { c -> (c + "") } }
    }
}