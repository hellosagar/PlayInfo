package dev.sagar.playinfo.core.mappers

interface Mapper<in In, out Out> {
    fun convert(data: In): Out
}
