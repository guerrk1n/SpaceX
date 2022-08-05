package com.app.spacexapp.util

interface Mapper<Input, Output> {
    fun map(input: Input): Output
}