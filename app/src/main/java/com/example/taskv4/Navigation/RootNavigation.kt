import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.taskv4.Screens.MainScreen
import com.example.taskv4.Screens.TaskDetailsScreen
import com.example.taskv4.ViewModel.TaskViewModel
import kotlinx.serialization.Serializable

@Serializable
data object MainScreenKey : NavKey

@Serializable
data object NewTaskScreenKey : NavKey

@Serializable
data class TaskDetailsScreenKey(val taskId: Int) : NavKey

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@SuppressLint("ViewModelConstructorInComposable")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavigation() {
    val backStack = rememberNavBackStack(MainScreenKey)
    val sharedViewModel = TaskViewModel()

    NavDisplay(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        backStack = backStack,
        entryProvider = { key ->

            NavEntry(key) {
                AnimatedContent(
                    targetState = key,
                    transitionSpec = {
                        val duration = 25
                        (slideInHorizontally(
                                                initialOffsetX = { it },
                                                animationSpec = tween(durationMillis = duration)
                                            ) + fadeIn()).togetherWith(
                            slideOutHorizontally(
                                                        targetOffsetX = { -it },
                                                        animationSpec = tween(durationMillis = duration)
                                                    ) + fadeOut()
                        )
                    },
                    label = "NavTransition"
                ) { targetKey ->
                    when (targetKey) {
                        is MainScreenKey -> MainScreen(
                            onfloatingActionButtonClick = { backStack.add(NewTaskScreenKey) },
                            viewModel = sharedViewModel,
                            onTaskClick = { taskId -> backStack.add(TaskDetailsScreenKey(taskId)) }
                        )

                        is NewTaskScreenKey -> NewTaskScreen(
                            onNavigationButtonClick = { backStack.add(MainScreenKey) },
                            viewModel = sharedViewModel
                        )

                        is TaskDetailsScreenKey -> TaskDetailsScreen(
                            taskId = targetKey.taskId,
                            viewModel = sharedViewModel,
                            onBackClick = {
                                backStack.removeLast()
                            },
                            onDeleteClick = {
                                backStack.removeLast()
                                sharedViewModel.deleteTask(targetKey.taskId)
                            }
                        )

                    }
                }
            }
        }
    )
}

