package com.radwaelsahn.currencyapp.domain

import android.provider.Telephony
import com.radwaelsahn.currencyapp.data.datasources.local.utils.Mapper
import com.radwaelsahn.currencyapp.data.models.Currencies
import com.radwaelsahn.currencyapp.data.models.Rates
import com.radwaelsahn.currencyapp.data.models.RatesMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrenciesMapper @Inject constructor() : Mapper<Rates, RatesMap> {

    override fun from(map: RatesMap): Rates {
        var rates = Rates()
        val model = map.rates
        rates.EGP = model["EGP"] ?: 0.0
        rates.KWD = model["KWD"] ?: 0.0
        rates.SAR = model["SAR"] ?: 0.0
        rates.USD = model["USD"] ?: 0.0
        rates.AED = model["AED"] ?: 0.0
        rates.AFN = model["AFN"] ?: 0.0

        return Rates()
    }

    override fun to(entity: Rates): RatesMap {

        var map = mapOf<String, Double>(
            "EGP" to entity.EGP,
            "KWD" to entity.KWD,
            "SAR" to entity.SAR,
            "USD" to entity.USD,
            "AED" to entity.AED,
            "AFN" to entity.AFN,


            "EGP" to entity.EGP,
            "KWD" to entity.KWD,
            "SAR" to entity.SAR,
            "USD" to entity.USD,
            "AED" to entity.AED,
            "AFN" to entity.AFN,

            "ALL" to entity.ALL,
            "AMD" to entity.AMD,
            "ANG" to entity.ANG,
            "AOA" to entity.AOA,
            "ARS" to entity.ARS,
            "AUD" to entity.AUD,

            "AWG" to entity.AWG,
            "AZN" to entity.AZN,
            "BAM" to entity.BAM,
            "BBD" to entity.BBD,
            "BDT" to entity.BDT,
            "BMD" to entity.BMD,
        )

        return RatesMap(map)

    }
}
