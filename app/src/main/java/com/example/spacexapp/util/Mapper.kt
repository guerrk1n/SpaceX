package com.example.spacexapp.util

interface Mapper<Input, Output> {
    fun map(input: Input): Output
}