package com.example.skills.view.stat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.skills.viewmodel.ListingsViewModel
import com.patrykandpatrick.vico.compose.common.Fill
import com.patrykandpatrick.vico.compose.common.component.TextComponent
import com.patrykandpatrick.vico.compose.common.vicoTheme
import com.patrykandpatrick.vico.compose.pie.PieChart
import com.patrykandpatrick.vico.compose.pie.PieChartHost
import com.patrykandpatrick.vico.compose.pie.data.PieChartModelProducer
import com.patrykandpatrick.vico.compose.pie.data.PieValueFormatter
import com.patrykandpatrick.vico.compose.pie.data.pieModel
import com.patrykandpatrick.vico.compose.pie.rememberPieChart

@Composable
fun AnnualSold(
    navController: NavController,
    viewModel: ListingsViewModel = viewModel()
) {
    val generalValue = viewModel.getStatByYearByArticle();

    val total = generalValue.values.sum()

    val percentages = generalValue.mapValues { (_, value) ->
        (value.toDouble() / total.toDouble()) * 100
    }

    val modelProducer = remember { PieChartModelProducer() }
    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            pieModel {
                series(
                    percentages.values.map { it.toFloat() }
                )
            }
        }
    }


    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("GeneraleStat")
            }
        ) {
            Text("Return")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Annual sold by product (%)",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        var productName = percentages.keys.toList()

        PieChartHost(
            chart = rememberPieChart(
                sliceProvider =
                    PieChart.SliceProvider.series(
                        vicoTheme.pieChartColors.mapIndexed { index, color ->
                            PieChart.Slice(
                                fill = Fill(color),
                                label = PieChart.SliceLabel.Inside(
                                    TextComponent(TextStyle(if (index == 2) Color.Black else Color.White))
                                )
                            )
                        }
                    ),
                valueFormatter = PieValueFormatter { _, value, _ -> "${value.toInt()}%" },
            ),
            modelProducer = modelProducer,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        percentages.forEach { (product, percentage) ->
            Text(
                text = "$product : %.2f%%".format(percentage)
            )
        }
    }
}
