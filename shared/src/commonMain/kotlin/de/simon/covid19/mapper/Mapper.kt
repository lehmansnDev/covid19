package de.simon.covid19.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}