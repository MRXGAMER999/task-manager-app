import androidx.compose.foundation.background
import androidx.compose.foundation.border

import androidx.compose.foundation.clickable

import androidx.compose.foundation.interaction.MutableInteractionSource

import androidx.compose.foundation.interaction.PressInteraction

import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxHeight

import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.automirrored.filled.ArrowBack

import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material.icons.filled.Close

import androidx.compose.material3.Button

import androidx.compose.material3.CenterAlignedTopAppBar

import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Icon

import androidx.compose.material3.IconButton

import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Scaffold

import androidx.compose.material3.SegmentedButton

import androidx.compose.material3.SegmentedButtonDefaults

import androidx.compose.material3.SingleChoiceSegmentedButtonRow

import androidx.compose.material3.Text

import androidx.compose.material3.TextField

import androidx.compose.material3.TextFieldDefaults

import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.material3.darkColorScheme

import androidx.compose.runtime.Composable

import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableIntStateOf

import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.remember

import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow

import androidx.compose.ui.graphics.Brush

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.taskv4.Components.DatePickerModal

import com.example.taskv4.ViewModel.TaskViewModel

import java.text.SimpleDateFormat

import java.util.Date

import java.util.Locale



@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun NewTaskScreen(

    onNavigationButtonClick : () -> Unit,

    viewModel: TaskViewModel = viewModel()

){
    val haptic = LocalHapticFeedback.current

    var text by remember { mutableStateOf("") }

    var text1 by remember { mutableStateOf("") }

    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }

    var showDatePicker by remember { mutableStateOf(false) }

    var selectedIndex by remember { mutableIntStateOf(0) }



    val priorities = listOf("Low", "Medium", "High")

    val selectedPriority = priorities[selectedIndex]





    val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }

    val formattedDate = selectedDateMillis?.let { dateFormatter.format(Date(it)) } ?: ""



    Scaffold(

        modifier = Modifier

            .fillMaxSize()

            .background(MaterialTheme.colorScheme.background),

        topBar = {

            CenterAlignedTopAppBar(

                colors = TopAppBarDefaults.topAppBarColors(

                    containerColor = MaterialTheme.colorScheme.background,

                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer

                ),

                title = {

                    Text("New Task",

                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.ExtraBold)

                },

                navigationIcon = {

                    IconButton(onClick = {

                        onNavigationButtonClick()

                    }) {

                        Icon(

                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,

                            contentDescription = "Close",

                            tint = MaterialTheme.colorScheme.onPrimaryContainer

                        )

                    }

                }

            )



        },

        bottomBar = {

            Button(

                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModel.addTask(

                        title = text,

                        priority = selectedPriority,

                        description = text1,

                        dueDate = formattedDate

                    )

                    onNavigationButtonClick()

                },

                modifier = Modifier

                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(bottom = 30.dp , start = 16.dp , end = 16.dp),
                shape = RoundedCornerShape(12.dp)

            ) {

                Text("Add Task",

                    style = MaterialTheme.typography.bodyLarge)

            }

        }

    ) { values ->

        Column(

            modifier = Modifier

                .fillMaxWidth()

                .fillMaxHeight(0.9f)

                .padding(values)

                .padding(16.dp),

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.SpaceEvenly

        ) {

            val brush = remember {

                Brush.linearGradient(

                    colors = listOf(Color.Red, Color.Yellow, Color.Green, Color.Blue, Color.Magenta)

                )

            }



            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(12.dp))
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp)),
                placeholder = { Text("e.g., Finalize project report",
                    color = MaterialTheme.colorScheme.onSecondaryContainer) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = MaterialTheme.colorScheme.onSurface,
                )
            )

            TextField(modifier = Modifier.fillMaxWidth()

                .fillMaxHeight(0.4f)
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp)),

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = MaterialTheme.colorScheme.onSurface,
                ),

                value = text1,

                onValueChange = { text1 = it },

                textStyle = TextStyle(brush = brush),

                label = {

                    Text("Description",

                        color = MaterialTheme.colorScheme.onSecondaryContainer)

                }

            )

            TextField(

                modifier = Modifier.fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(12.dp)),

                value = formattedDate,

                onValueChange = { },

                readOnly = true,

                label = { Text("Due Date") },

                textStyle = TextStyle(brush = brush),

                placeholder = { Text("Select a date") },

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = MaterialTheme.colorScheme.onSurface,
                ),

                interactionSource = remember { MutableInteractionSource() }

                    .also { interactionSource ->

                        LaunchedEffect(interactionSource) {

                            interactionSource.interactions.collect {

                                if (it is PressInteraction.Press) {

                                    showDatePicker = true

                                }

                            }

                        }

                    }

            )

            PrioritySegmentedButton(

                selectedIndex = selectedIndex,

                onSelectionChange = { selectedIndex = it }

            )



        }



// Show DatePicker Modal when needed

        if (showDatePicker) {

            DatePickerModal(

                onDateSelected = { dateMillis ->

                    selectedDateMillis = dateMillis

                },

                onDismiss = { showDatePicker = false }

            )

        }

    }

}



@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun PrioritySegmentedButton(

    selectedIndex: Int,

    onSelectionChange: (Int) -> Unit

) {

    val options = listOf("Low", "Medium", "High")



    Column(

        horizontalAlignment = Alignment.Start

    ) {

        Text(

            text = "Priority",

            style = MaterialTheme.typography.titleMedium,

            modifier = Modifier.padding(bottom = 8.dp)

        )



        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {

            options.forEachIndexed { index, label ->

                SegmentedButton(

                    shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),

                    onClick = { onSelectionChange(index) },

                    selected = index == selectedIndex

                ) {

                    Text(label)

                }

            }

        }

    }

}



@Preview(showSystemUi = true)

@Composable

fun NewTaskScreenPreview(){

    MaterialTheme(colorScheme = darkColorScheme()) {

        NewTaskScreen(onNavigationButtonClick = { })

    }

}