package com.radwaelsahn.currencyapp.data.models

data class RatesMap(
    var rates: Map<String, Double>
) : Map<String, Double> {
    override val entries: Set<Map.Entry<String, Double>>
        get() = rates.entries
    override val keys: Set<String>
        get() = rates.keys
    override val size: Int
        get() = rates.size
    override val values: Collection<Double>
        get() = rates.values

    override fun containsKey(key: String): Boolean {
        return rates.containsKey(key)
    }

    override fun containsValue(value: Double): Boolean {
        return rates.containsValue(value)
    }

    override fun get(key: String): Double? {
        return rates[key]
    }

    override fun isEmpty(): Boolean {
        return rates.isEmpty()
    }
}