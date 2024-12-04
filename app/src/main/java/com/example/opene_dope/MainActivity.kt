package com.example.opene_dope

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : ComponentActivity() {

    // Make tableData accessible to FirstFragment
    lateinit var tableData: List<List<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Your Compose UI content here
                }
        }

        // Handle incoming file intent
        if (intent?.action == Intent.ACTION_VIEW) {
            handleIncomingFile(intent)
        }
    }

    private fun handleIncomingFile(intent: Intent) {
        val uri = intent.data
        val csvContent: String? = uri?.let {
            contentResolver.openInputStream(it)?.use { inputStream ->
                BufferedReader(InputStreamReader(inputStream)).use { reader ->
                    reader.readText()
                }
            }
        }

        csvContent?.let { content ->
            tableData = parseCSV(content) // Assign parsed data to tableData
            val tableImage = createTableImage(tableData)
            // ... (rest of the code) ...
        }
    }

    // Function to parse CSV content into a list of lists
    private fun parseCSV(content: String): List<List<String>> {
        val rows = content.trim().split("\n")
        return rows.map { row ->
            row.split(",").map { it.trim() }
        }
    }

    // Function to create a Bitmap image of the table
    fun createTableImage(tableData: List<List<String>>): Bitmap {
        val cellWidth = 200 // Adjust as needed
        val cellHeight = 50 // Adjust as needed
        val padding = 10 // Adjust as needed

        val numRows = tableData.size
        val numCols = tableData[0].size // Assuming all rows have the same number of columns

        val totalWidth = numCols * cellWidth + (numCols + 1) * padding
        val totalHeight = numRows * cellHeight + (numRows + 1) * padding

        val bitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()

        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f

        var y = padding.toFloat()
        for (i in 0 until numRows) {
            var x = padding.toFloat()
            for (j in 0 until numCols) {
                canvas.drawRect(x, y, x + cellWidth, y + cellHeight, paint)

                paint.color = Color.BLACK
                paint.style = Paint.Style.FILL
                paint.textSize = 20f

                val cellText = tableData[i][j]
                canvas.drawText(cellText, x + padding, y + cellHeight / 2 + paint.textSize / 3, paint)

                x += cellWidth + padding
            }
            y += cellHeight + padding
        }

        return bitmap
    }
}