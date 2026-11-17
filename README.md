# II.3510 Skills: Displaying videos and charts

Unfinished project for the class exercise. Complete the TODOs to make the app work.



## Part 1: Charts
Library: Vico (https://github.com/patrykandpatrick/vico)

Each product has a list of sales for every month of 2026 (`ListingState`: year, month, number sold).
You have to prepare the data, then draw the charts.

For each chart there are two things to do:

1. Write the data function in `CatalogueListings.kt` (replace `TODO("Not yet implemented")`).
2. Draw the chart in the screen file.

| Chart | Data function | Screen file | Type of chart |
|---|---|---|---|
| Annual sold by product (%) | `getStatByCurrentYearByArticle` | `AnnualSold.kt` | Pie chart |
| Sold evolution by month | `getStatEvolutionSoldByMouth` | `EvolutionStat.kt` | Line chart |
| Sold this month by product | `getMonthSold` | `MonthSoldProduct.kt` | Column chart |
| Sales of one product in euros | `getSoldProductEurosByMouth` | `SoldProducteEuro.kt` | Line chart with points |
| Number sold of one product | `getSoldProductMouth` | `SoldProductMouth.kt` | Line chart with points |

Where to find the charts in the app:

- "View stats" on the list opens the first three charts.
- "Stats" on a product opens the last two charts.


For the pie chart, you also have to compute the percentage of each product in `AnnualSold.kt`.
For the other charts, add a marker that shows the value when you touch the chart.


## Part 2: Videos
On the list, each product has a "Video" button that opens its presentation video.
The page is already drawn. You have to make the player work with the Media3 library.

| Step | File | What to do | Result when it is done |
|---|---|---|---|
| TODO 1 | `AndroidManifest.xml` | Add the Internet permission | Videos from a link can be loaded |
| TODO 2 | `VideoViewModel.kt` | Give the video to the player | Nothing visible yet |
| TODO 3 | `VideoPlayer.kt` | Show the video on the screen | Product 1 shows the first image of the video |
| TODO 4 | `VideoViewModel.kt` | Play or pause | The big round button plays and pauses the video |
| TODO 5 | `VideoViewModel.kt` | Move in the video | The progress bar and the 10 seconds buttons work |
| TODO 6 | `VideoDetailScreen.kt` | Publish a video from the gallery | "Choose from gallery" plays your own video |
| TODO 7 | `VideoViewModel.kt` | Free the player | The sound stops when you leave the page |
| BONUS A, B, C | `VideoViewModel.kt` | Speed, sound, loop | The three small buttons work |
| BONUS D, E, F | `VideoDetailScreen.kt`, `VideoPlayer.kt` | Camera, fullscreen, hide the controls | Recording publishes the video, fullscreen turns the screen, the controls hide after 5 seconds |


Documentation: https://developer.android.com/media/media3/ui/compose