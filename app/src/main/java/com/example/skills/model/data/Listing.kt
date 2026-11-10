package com.example.skills.model.data

import com.example.skills.model.data.enums.Category
import java.time.LocalDate

data class Listing(var id : Int, var title : String, var description : String, var price : Double, var category : Category, var pictureUrl : String?, var creationDate : LocalDate, var user: User, var state : ArrayList<ListingState>) {
}