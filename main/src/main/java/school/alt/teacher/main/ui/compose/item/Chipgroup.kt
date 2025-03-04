package school.alt.teacher.main.ui.compose.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SingleChipGroup(
    modifier: Modifier = Modifier,
    chipList: MutableList<String>,
    selectIndex: MutableIntState,
    contentPaddingValues: PaddingValues = PaddingValues(horizontal = 18.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(16.dp),
    onChange: (selectedIndex: Int) -> Unit
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = contentPaddingValues,
        horizontalArrangement = horizontalArrangement
    ) {
        itemsIndexed(chipList) { index, item ->
            SingleCategoryTag(isBorder = index == selectIndex.intValue, text = item) {
                selectIndex.intValue = index
                onChange(index)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TestSingleChipGroup() {

    val selectIndex = remember {
        mutableIntStateOf(0)
    }

    val tagList = remember {
        mutableListOf("전체", "1학년", "2학년", "3학년")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        SingleChipGroup(chipList = tagList, selectIndex = selectIndex) {
        }
    }
}