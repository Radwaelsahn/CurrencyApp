package com.radwaelsahn.currencyapp.data.source.local.utils

interface Mapper<Entity, Model> {

    fun from(model: Model): Entity

    fun to(entity: Entity): Model

}
