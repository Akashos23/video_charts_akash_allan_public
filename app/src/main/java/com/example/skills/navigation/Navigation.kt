package com.example.skills.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.skills.view.listing.ListingActivity
import com.example.skills.view.persoStat.GeneralePersonalStat
import com.example.skills.view.persoStat.SoldProductEuro
import com.example.skills.view.persoStat.SoldProductMouth
import com.example.skills.view.stat.AnnualSold
import com.example.skills.view.stat.EvolutionStat
import com.example.skills.view.stat.GeneraleStatActivity
import com.example.skills.view.stat.MonthSoldProduct

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "listing") {
        composable("listing") { ListingActivity(navController) }
        composable("GeneraleStat") { GeneraleStatActivity(navController) }
        composable("AnnualSold") { AnnualSold(navController) }
        composable("EvolutionStat") { EvolutionStat(navController) }
        composable("MonthSoldProduct") { MonthSoldProduct(navController) }
        composable("GeneralePersonalStat/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            } )){
            val id = requireNotNull(it.arguments).getInt("id")
                    GeneralePersonalStat(navController, id)
        }
        composable("SoldProductEuro/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            } )){
            val id = requireNotNull(it.arguments).getInt("id")
            SoldProductEuro(navController, id)
        }
        composable("SoldProductMouth/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            } )){
            val id = requireNotNull(it.arguments).getInt("id")
            SoldProductMouth(navController, id)
        }
    }
}