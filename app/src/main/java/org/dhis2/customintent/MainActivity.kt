package org.dhis2.customintent

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.dhis2.customintent.ui.screen.IntentScreen
import org.dhis2.customintent.ui.state.ExtraReturnType
import org.dhis2.customintent.ui.theme.CustomIntentTheme
import org.dhis2.customintent.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Set up result callback
        viewModel.setResultCallback { responseText, returnType ->
            sendResponseToCallingApp(responseText, returnType)
        }

        // Process the intent that started this activity
        viewModel.processIntent(intent)

        setContent {
            CustomIntentTheme {
                val uiState by viewModel.uiState.collectAsState()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IntentScreen(
                        uiState = uiState,
                        onEvent = viewModel::onEvent,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Handle new intents when the activity is already running
        viewModel.processIntent(intent)
    }

    private fun sendResponseToCallingApp(responseText: String, responseType: ExtraReturnType) {
        setResult(RESULT_OK, generateResponseIntent(responseText, responseType))
        finish()
    }

    private fun generateResponseIntent(
        responseText: String,
        responseType: ExtraReturnType
    ) : Intent {
        val resultIntent = Intent()
        when (responseType) {
            ExtraReturnType.STRING -> {
                resultIntent.apply {
                    putExtra("CUSTOM_STRING_RESPONSE", responseText)
                }
            }

            ExtraReturnType.OBJECT -> {
                resultIntent.apply {
                    putExtra("CUSTOM_OBJECT_RESPONSE", responseText)
                }
            }

            ExtraReturnType.LIST_OF_OBJECTS -> {
                resultIntent.apply {
                    putExtra("CUSTOM_LIST_OF_OBJECTS_RESPONSE", responseText)
                }
            }

            ExtraReturnType.INTEGER -> {
                resultIntent.apply {
                    putExtra("CUSTOM_INTEGER_RESPONSE", responseText.toIntOrNull() ?: 0)
                }
            }

            ExtraReturnType.FLOAT -> {
                resultIntent.apply {
                    putExtra("CUSTOM_FLOAT_RESPONSE", responseText.toFloatOrNull() ?: 0)
                }
            }

            ExtraReturnType.BOOLEAN -> {
                resultIntent.apply {
                    putExtra("CUSTOM_BOOLEAN_RESPONSE", responseText.toBoolean())
                }
            }

        }
        return resultIntent
    }
}