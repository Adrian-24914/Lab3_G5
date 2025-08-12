package com.example.lab4

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab4.ui.theme.Lab4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab4Theme {
                TaskApp()
            }
        }
    }
}

@Composable
fun TaskApp() {
    val tasks: SnapshotStateList<String> = remember { mutableStateListOf<String>() }
    var newTask by rememberSaveable { mutableStateOf(EMPTY_STRING) }
    val currentContext = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo ocupando toda la pantalla
        BackgroundImage()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(DEFAULT_PADDING),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            AppTitle()

            Spacer(modifier = Modifier.height(LARGE_SPACING))

            // Lista de tareas existentes
            TasksDisplayWithSnapshotList(tasksList = tasks)

            Spacer(modifier = Modifier.height(LARGE_SPACING))

            // Campo de texto y botón para agregar
            TaskInputSectionWithSnapshot(
                inputText = newTask,
                onTextChange = { newTask = it },
                onAddButtonClick = {
                    if (isTaskValid(newTask)) {
                        addTaskToSnapshotList(tasks, newTask)
                        newTask = EMPTY_STRING
                    } else {
                        displayEmptyTaskError(currentContext)
                    }
                }
            )
        }
    }
}

@Composable
private fun BackgroundImage() {
    Image(
        painter = painterResource(id = R.drawable.background_image1),
        contentDescription = stringResource(R.string.background_description),
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun AppTitle() {
    Text(
        text = stringResource(R.string.app_title),
        fontSize = TITLE_SIZE,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun TasksDisplayWithSnapshotList(tasksList: SnapshotStateList<String>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(TASKS_DISPLAY_HEIGHT),
        elevation = CardDefaults.cardElevation(defaultElevation = CARD_ELEVATION)
    ) {
        if (isTasksListEmpty(tasksList)) {
            EmptyTasksMessage()
        } else {
            TasksListContentWithSnapshot(tasksList = tasksList)
        }
    }
}

@Composable
private fun EmptyTasksMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.empty_tasks_message),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun TasksListContentWithSnapshot(tasksList: SnapshotStateList<String>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(DEFAULT_PADDING),
        verticalArrangement = Arrangement.spacedBy(SMALL_SPACING)
    ) {
        items(tasksList) { singleTask ->
            SingleTaskItem(taskText = singleTask)
        }
    }
}

@Composable
private fun SingleTaskItem(taskText: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = TASK_CARD_ELEVATION)
    ) {
        Text(
            text = taskText,
            modifier = Modifier.padding(TASK_PADDING),
            fontSize = TASK_TEXT_SIZE
        )
    }
}

@Composable
private fun TaskInputSectionWithSnapshot(
    inputText: String,
    onTextChange: (String) -> Unit,
    onAddButtonClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = CARD_ELEVATION)
    ) {
        Column(
            modifier = Modifier.padding(DEFAULT_PADDING),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TaskInputField(
                currentText = inputText,
                onTextChange = onTextChange
            )

            Spacer(modifier = Modifier.height(MEDIUM_SPACING))

            AddTaskButton(onClick = onAddButtonClick)
        }
    }
}

@Composable
private fun TaskInputField(
    currentText: String,
    onTextChange: (String) -> Unit
) {
    TextField(
        value = currentText,
        onValueChange = onTextChange,
        label = { Text(stringResource(R.string.task_input_label)) },
        placeholder = { Text(stringResource(R.string.task_input_hint)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
private fun AddTaskButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()

    ) {
        Text(
            text = stringResource(R.string.add_button_text),
            fontSize = BUTTON_TEXT_SIZE
        )
    }
}

// Funciones
private fun isTaskValid(taskText: String): Boolean {
    return taskText.trim().isNotEmpty()
}

private fun addTaskToSnapshotList(tasksList: SnapshotStateList<String>, newTask: String) {
    tasksList.add(newTask.trim())
}

private fun isTasksListEmpty(tasksList: SnapshotStateList<String>): Boolean {
    return tasksList.isEmpty()
}

private fun displayEmptyTaskError(context: android.content.Context) {
    Toast.makeText(
        context,
        context.getString(R.string.empty_task_error),
        Toast.LENGTH_SHORT
    ).show()
}

@Preview(showBackground = true)
@Composable
fun TaskAppPreview() {
    Lab4Theme {
        TaskApp()
    }
}

// Constantes
private const val EMPTY_STRING = ""
private val DEFAULT_PADDING = 16.dp
private val TASK_PADDING = 12.dp
private val LARGE_SPACING = 24.dp
private val MEDIUM_SPACING = 16.dp
private val SMALL_SPACING = 8.dp
private val TASKS_DISPLAY_HEIGHT = 300.dp
private val CARD_ELEVATION = 4.dp
private val TASK_CARD_ELEVATION = 2.dp
private val TITLE_SIZE = 28.sp
private val BUTTON_TEXT_SIZE = 16.sp
private val TASK_TEXT_SIZE = 16.sp