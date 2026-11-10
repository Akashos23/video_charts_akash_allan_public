package com.example.skills.model.business

import android.graphics.Picture
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.skills.model.data.Listing
import com.example.skills.model.data.ListingState
import com.example.skills.model.data.User
import com.example.skills.model.data.enums.Category
import java.time.LocalDate

class CatalogueListings(var ListingStateGenerator: ListingStatGenerator) {
    @RequiresApi(Build.VERSION_CODES.O)
    private val Listings: List<Listing> = listOf(
        mockListing(
            1,
            "robert",
            "frederic",
            "frederic.robert@gmail.com",
            "Apple",
            "This is a book about apple",
            23.50,
            Category.BOOK,
            null,
            LocalDate.of(2025, 3, 12),
            ListingState = ListingStateGenerator.generateListingStat()
        ),
        mockListing(
            2,
            "Benoit",
            "Mars",
            "benoir.mars@gmail.com",
            "Samsung Galaxy",
            "This is a Samsung Galaxy",
            199.99,
            Category.ELECTRONIC,
            null,
            LocalDate.of(2024, 12, 12),
            ListingState = ListingStateGenerator.generateListingStat()
        ),
        mockListing(
            1,
            "robert",
            "frederic",
            "frederic.robert@gmail.com",
            "Soccer ball",
            "This is a soccer ball",
            10.0,
            Category.SPORTS_LEISURE,
            null,
            LocalDate.of(2022, 3, 12),
            ListingState = ListingStateGenerator.generateListingStat()
        ),
    )
    private val months = hashMapOf(
        1 to "January",
        2 to "February",
        3 to "March",
        4 to "April",
        5 to "May",
        6 to "June",
        7 to "July",
        8 to "August",
        9 to "September",
        10 to "October",
        11 to "November",
        12 to "December"
    )
    @RequiresApi(Build.VERSION_CODES.O)
    fun getAllListings(): List<Listing> = Listings

    private  fun mockListing(
        id: Int,
        nameUser: String,
        firstNameUser: String,
        emailUser: String,
        titleListing: String,
        descriptionListing : String,
        price : Double,
        category: Category,
        pictureUrl: String?,
        CreationDate: LocalDate,
        ListingState: ArrayList<ListingState>
    ): Listing{
        var user = User(nameUser, firstNameUser, emailUser, ArrayList<Listing>())
        var Listing = Listing(id, titleListing, descriptionListing, price, category, pictureUrl, CreationDate, user, ListingState)
        return Listing
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getStatByCurrentYearByArticle(): LinkedHashMap<String, Int> {
        //Define a method who return the number of product sold in a year for each product
        //Return it in LinkedHashMap with the title of product and value (the number of sold product during the year)

        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getStatEvolutionSoldByMouth(): LinkedHashMap<String, Int>{
        //Define a method who return the number of sold of all product confused during each mouth
        //Return it in LinkedHashMap with the mouth and value (the number of sold of all product confused during each mouth)

        TODO("Not yet implemented")

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMonthSold(): LinkedHashMap<String, Int>{
        //Define a method who return for the current mouth, the number of sold of each product
        //Return it in LinkedHashMap with the title of the product and value (the number of sold product during current mouth)

        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getSoldProductEurosByMouth(id : Int) : LinkedHashMap<String, Double>{
        //Define a method who return for a product by id, the amount of sold product in euros by mouth
        //Return it in LinkedHashMap with the mouth and value (the amount of sold product by mouth)

        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getSoldProductMouth(id : Int) : LinkedHashMap<String, Int>{
        //Define a method who return for a product by id, the number of sold product by mouth
        //Return it in LinkedHashMap with the mouth and value (the number of sold product)

        TODO("Not yet implemented")
    }
}