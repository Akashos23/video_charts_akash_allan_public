package com.example.skills.model.business

import com.example.skills.model.data.ListingState
import kotlin.random.Random

class ListingStatGenerator {

    public fun generateListingStat() : ArrayList<ListingState>{
        var list = ArrayList<ListingState>()
        for (i in 1..12) {
            list.add(ListingState(2026, i, Random.nextInt(0,1000)))
        }
        return list
    }
}