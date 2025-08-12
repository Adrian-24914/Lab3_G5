package com.example.lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab4.ui.theme.Lab4Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagerApp()
        }
    }
}

@Composable
fun TaskApp(){
    val tasks: SnapshotStateList<String> = remember { mutableStateListOf<String>() }
    var newTask by rememberSaveable {mutableStateOf(EMPTY_STRING)}
}

@Composable
private fun BackgroundImage() {
    Image(
        painter = painterResource(id = R.drawable.background_image),
        contentDescription = null,
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
        color = MaterialTheme.colorScheme.primary
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab4Theme {
        Greeting("Android")
    }
}



//constantes para la pantalla
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