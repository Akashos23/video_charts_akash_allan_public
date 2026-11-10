package com.example.skills.view.persoStat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.skills.viewmodel.ListingsViewModel

@Composable
fun GeneralePersonalStat(
    navController: NavController,
    id : Int,
    viewModel: ListingsViewModel = viewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        var productName = viewModel.getProductName(id)

        Button(
            onClick = {
                navController.navigate("listing")
            }
        ) {
            Text("Return")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("SoldProductEuro/${id}")
            }
        ) {
            Text("Sold ${productName} in (€) by mouth")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("SoldProductMouth/${id}")
            }
        ) {
            Text("Number of ${productName} sold by mouth")
        }
    }

}