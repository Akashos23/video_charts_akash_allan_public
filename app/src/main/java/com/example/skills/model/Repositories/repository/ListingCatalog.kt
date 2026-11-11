package com.example.skills.model.Repositories.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.skills.model.Repositories.`interface`.IListingRepository
import com.example.skills.model.business.CatalogueListings
import com.example.skills.model.business.ListingStatGenerator
import com.example.skills.model.data.Listing

class ListingCatalog(private val catalogueListings: CatalogueListings) : IListingRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getSoldProductMouth(id: Int): LinkedHashMap<String, Int> {
        return catalogueListings.getSoldProductMouth(id)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun allListings(): List<Listing> {
        return catalogueListings.getAllListings()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getStatByCurrentYearByArticle(): LinkedHashMap<String, Int> {
        return catalogueListings.getStatByCurrentYearByArticle()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getStatEvolutionSoldByMouth(): LinkedHashMap<String, Int> {
        return catalogueListings.getStatEvolutionSoldByMouth()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getMonthSold(): LinkedHashMap<String, Int> {
        return catalogueListings.getMonthSold()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getSoldProductEurosByMouth(id: Int): LinkedHashMap<String, Double> {
        return catalogueListings.getSoldProductEurosByMouth(id)
    }

    companion object{
        val instance: IListingRepository by lazy(LazyThreadSafetyMode.NONE) { ListingCatalog(CatalogueListings(ListingStateGenerator = ListingStatGenerator())) }
    }


}
