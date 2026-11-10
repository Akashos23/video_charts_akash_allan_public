package com.example.skills.viewmodel


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.skills.model.Repositories.`interface`.IListingRepository
import com.example.skills.model.Repositories.repository.ListingCatalog
import com.example.skills.model.data.Listing
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate


data object Loading
data class  Success(
    val listings : Listing
)
data class Error(
    val message : String
)


class ListingsViewModel(private val repository: IListingRepository = ListingCatalog.instance) : ViewModel() {
    private val _uiState = MutableStateFlow(Loading)


    fun getListingActivity() : List<Listing>{
        return repository.allListings()
    }

    fun getStatByYearByArticle() : HashMap<String, Int> {
        var result =  repository.getStatByCurrentYearByArticle()
        return result
    }

    fun getStatEvolutionSoldByMouth() : LinkedHashMap<String, Int> {
        var result =  repository.getStatEvolutionSoldByMouth()
        return result
    }

    fun getMouthSold() : HashMap<String, Int> {
        var result =  repository.getMonthSold()
        return result
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentMonth() : String {
        var today = LocalDate.now()
        return today.getMonth().toString();
    }

    fun getProductName(id : Int) : String {
        return repository.allListings().first(){x -> x.id == id}.title
    }

    fun getSoldProductEurosByMouth(id : Int) : LinkedHashMap<String, Double> {
        return repository.getSoldProductEurosByMouth(id)
    }

    fun getSoldProductMouth(id : Int) : LinkedHashMap<String, Int>{
        return repository.getSoldProductMouth(id)
    }



}