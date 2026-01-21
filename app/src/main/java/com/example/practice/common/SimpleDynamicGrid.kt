import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SimpleDynamicGrid(
    modifier: Modifier = Modifier,
    items: List<String>, // Assume max of 4 items
    columns: Int = 2, // Number of columns in the grid
) {
    // Check edge case: If there are no items, display nothing
    if (items.isEmpty()) return

    Column(modifier = modifier) {
        // Partition items into rows
        items.chunked(columns).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Add spacing between columns
            ) {
                rowItems.forEach { item ->
                    Box(
                        modifier = Modifier
                            .weight(1f) // Equal width for items in the row
                            .aspectRatio(1f) // Make each item square
                            .background(Color.LightGray)
                            .padding(4.dp)
                    ) {
                        Text(
                            text = item,
                            modifier = Modifier.align(Alignment.Center) // Center content
                        )
                    }
                }

                // Add empty boxes if the row is not full (needed for the last row)
                val emptyColumns = columns - rowItems.size
                repeat(emptyColumns) {
                    Spacer(modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f))
                }
            }
        }
    }
}