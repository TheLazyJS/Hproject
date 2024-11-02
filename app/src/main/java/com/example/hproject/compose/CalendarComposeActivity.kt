package com.example.hproject.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.hproject.R
import com.example.hproject.compose.SharedComponents.MonthAndWeekCalendarTitle
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.launch
import java.time.Month


@Composable
fun MainCalendar() {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    val startMonth = remember { currentMonth.minusMonths(100) }
    val endMonth = remember { currentMonth.plusMonths(100) }
    val daysOfWeek = remember { daysOfWeek() }
    val selections = remember { mutableStateListOf<LocalDate>() }
    val monthState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first()
    )

    // Calendar Box
    Box(
        modifier = Modifier
            .wrapContentSize()
    ) {
        // Background
        Image(
            painter = painterResource(id = R.drawable.backgroundcalendar),
            contentDescription = "Background Image",
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(25.dp)),
            contentScale = ContentScale.Crop
        )
        // Column include title -> header -> dates
        Column(
            modifier = Modifier
                .wrapContentSize(),
        ) {
            CalendarTitle(monthState = monthState)
            CalendarHeader(daysOfWeek = daysOfWeek)
            Box {
                HorizontalCalendar(
                    state = monthState,
                    dayContent = { day ->
                        val isSelectable = day.position == DayPosition.MonthDate
                        Day(
                            day.date,
                            isSelected = isSelectable && selections.contains(day.date),
                            isSelectable = isSelectable,
                        ) { clicked ->
                            if (selections.contains(clicked)) {
                                selections.remove(clicked)
                            } else {
                                selections.add(clicked)
                            }
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun CalendarTitle(monthState: CalendarState) {
    val visibleMonth = rememberFirstVisibleMonthAfterScroll(monthState)
    MonthAndWeekCalendarTitle(
        currentMonth = visibleMonth.yearMonth,
        monthState = monthState,
    )
}

@Composable
fun CalendarHeader(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                text = dayOfWeek.displayText(),
                color = Color.White,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
fun Day(
    day: LocalDate,
    isSelected: Boolean,
    isSelectable: Boolean,
    onClick: (LocalDate) -> Unit,
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f) // square-sizing - each case ratio
            .padding(4.dp)
            .clip(CircleShape)
            .background(color = if (isSelected) colorResource(R.color.yellow_select) else Color.Transparent)
            .clickable(
                enabled = isSelectable,
                showRipple = !isSelected,
                onClick = { onClick(day) },
            ),
        contentAlignment = Alignment.Center,
    ) {
        val textColor = when {
            isSelected -> Color.Black
            isSelectable -> Color.White
            else -> colorResource(R.color.inactive_text_color)
        }
        Text(
            text = day.dayOfMonth.toString(),
            color = textColor,
            fontSize = 14.sp,
        )
    }
}

fun DayOfWeek.displayText(uppercase: Boolean = false, narrow: Boolean = false): String {
    val style = if (narrow) TextStyle.NARROW else TextStyle.SHORT
    return getDisplayName(style, Locale.ENGLISH).let { value ->
        if (uppercase) value.uppercase(Locale.ENGLISH) else value
    }
}

object SharedComponents {
    @Composable
    fun MonthAndWeekCalendarTitle(
        currentMonth: YearMonth,
        monthState: CalendarState,
    ) {
        val coroutineScope = rememberCoroutineScope()

        SimpleCalendarTitle(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 6.dp),
            currentMonth = currentMonth,
            goToPrevious = {
                coroutineScope.launch {
                    val targetMonth = monthState.firstVisibleMonth.yearMonth.previousMonth
                    monthState.animateScrollToMonth(targetMonth)

                }
            },
            goToNext = {
                coroutineScope.launch {
                    val targetMonth = monthState.firstVisibleMonth.yearMonth.nextMonth
                    monthState.animateScrollToMonth(targetMonth)
                }
            },
        )
    }
}


@Composable
fun MonthDropdownMenu(monthState: YearMonth, onMonthSelected: (YearMonth) -> Unit) {
    //val visibleMonth = rememberFirstVisibleMonthAfterScroll(YearMonth.)
    //TODO: use calendar title as it update the name/year

    val months = Month.entries.map { it.name }
    var selectedMonthIndex by remember { mutableIntStateOf(monthState.month.value - 1) }
    var selectedMonth by remember { mutableStateOf(months[selectedMonthIndex]) }
    var expanded by remember { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()

    if (expanded) {
        LaunchedEffect(selectedMonthIndex) {
            lazyListState.scrollToItem(selectedMonthIndex)
        }
    }
    Box( modifier = Modifier
        .fillMaxWidth()
        .offset(x = (-20).dp)
    ){
        Button(
            onClick = { expanded = !expanded },
            modifier = Modifier // submenu modifier
                .align(Alignment.TopEnd)
                .clip(RoundedCornerShape(25.dp))
                .requiredWidth(140.dp)
        ) {
            Text(
                text = selectedMonth,
                modifier = Modifier
            )
        }

        if (expanded) {
            Popup(
                alignment = Alignment.TopEnd,
                onDismissRequest = { expanded = false },
                properties = PopupProperties(focusable = true) // Allow the popup to capture input
            ) {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .offset(y = 40.dp)
                        .requiredWidth(140.dp)
                        .border(1.dp, Color.Gray)
                        .background(color = Color.White)
                        .heightIn(max = 200.dp)
                        .padding(bottom = 24.dp)
                ) {
                    items(months) { month ->
                        Text(
                            text = month,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(4.dp)
                                .clickable {
                                    selectedMonth = month
                                    expanded = false
                                    selectedMonthIndex = months.indexOf(month)
                                    val newMonthIndex = selectedMonthIndex + 1
                                    val newMonth = YearMonth.of(monthState.year, newMonthIndex)
                                    onMonthSelected(newMonth) // Update the current month
                                }
                        )
                    }
                }
            }
        }
    }
}

