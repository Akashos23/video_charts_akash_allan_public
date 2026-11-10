package com.example.skills.model.Repositories.`interface`

import com.example.skills.model.data.Listing

interface IListingRepository {
    fun getSoldProductMouth(id : Int) : LinkedHashMap<String, Int>
    fun allListings(): List<Listing>
    fun getStatByCurrentYearByArticle() : LinkedHashMap<String, Int>
    fun getStatEvolutionSoldByMouth(): LinkedHashMap<String, Int>
    fun getMonthSold(): LinkedHashMap<String, Int>
    fun getSoldProductEurosByMouth(id : Int) : LinkedHashMap<String, Double>
}