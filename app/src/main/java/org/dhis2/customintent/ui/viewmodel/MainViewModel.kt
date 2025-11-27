package org.dhis2.customintent.ui.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.dhis2.customintent.ui.state.ExtraReturnType
import org.dhis2.customintent.ui.state.IntentUiEvent
import org.dhis2.customintent.ui.state.IntentUiState

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<IntentUiState>(IntentUiState.Loading)
    val uiState: StateFlow<IntentUiState> = _uiState.asStateFlow()

    private var resultCallback: ((String, ExtraReturnType) -> Unit)? = null

    fun processIntent(intent: Intent?) {
        try {
            // For our use case we wanted to validate that the capture app could recover different data types so we configured a
            // custom intent request parameter to indicate the expected return type which we will  use to
            // generate a default response that we will pass on to the ui state to be displayed and sent back to the calling app
            val intentReturnType = getExtraReturnType( intent?.getStringExtra("EXTRA_RETURN_VALUE_TYPE")) ?: ExtraReturnType.STRING

            // Here we process the incoming intent based on its action
            val receivedText = when (intent?.action) {
                PROCESS_RETURN_VALUE_ACTION-> {
                    // Example of processing received extras of different types
                    intent.getStringExtra(Intent.EXTRA_TEXT) + "\n" +
                    intent.getBooleanExtra("boolean", false) + "\n" +
                    intent.getIntExtra("integer", 0) + "\n" +
                    intent.getStringExtra("string2")
                }

                Intent.ACTION_SEND -> {
                    if (intent.type == "text/plain") {
                        intent.getStringExtra(Intent.EXTRA_TEXT)
                    } else null
                }

                else -> null
            }

            // Update UI state with received data and default response
            _uiState.value = IntentUiState.Success(
                receivedText = receivedText,
                responseText = generateDefaultResponse(intentReturnType),
                extraReturnType = intentReturnType

            )
        } catch (e: Exception) {
            _uiState.value = IntentUiState.Error("Error processing intent: ${e.message}")
        }
    }

    fun getExtraReturnType(action: String?): ExtraReturnType? {
        return when (action) {

            RETURN_STRING_INTENT_ACTION -> {
                ExtraReturnType.STRING
            }

            RETURN_INTEGER_INTENT_ACTION -> {
                ExtraReturnType.INTEGER

            }

            RETURN_FLOAT_INTENT_ACTION -> {
                ExtraReturnType.FLOAT

            }

            RETURN_BOOLEAN_INTENT_ACTION -> {
                ExtraReturnType.BOOLEAN
            }

            RETURN_OBJECT_INTENT_ACTION -> {
                ExtraReturnType.OBJECT

            }

            RETURN_LIST_OF_OBJECTS_INTENT_ACTION -> {
                ExtraReturnType.LIST_OF_OBJECTS
            }

            else -> {
                ExtraReturnType.STRING
            }
        }
    }

    fun onEvent(event: IntentUiEvent) {
        when (event) {
            is IntentUiEvent.UpdateResponseText -> {
                val currentState = _uiState.value
                if (currentState is IntentUiState.Success) {
                    _uiState.value = currentState.copy(responseText = event.text)
                }
            }

            is IntentUiEvent.SendResponse -> {
                val currentState = _uiState.value
                if (currentState is IntentUiState.Success) {
                    resultCallback?.invoke(currentState.responseText, currentState.extraReturnType)
                }
            }
        }
    }

    fun setResultCallback(callback: (String, ExtraReturnType) -> Unit) {
        resultCallback = callback
    }

    private fun generateDefaultResponse(responseType: ExtraReturnType): String {
        // Generate default response based on the expected return type
        return when (responseType) {
            ExtraReturnType.STRING -> STRING_RETURN_VALUE
            ExtraReturnType.INTEGER -> INTEGER_RETURN_VALUE.toString()
            ExtraReturnType.FLOAT -> FLOAT_RETURN_VALUE.toString()
            ExtraReturnType.BOOLEAN -> BOOLEAN_RETURN_VALUE.toString()
            ExtraReturnType.OBJECT -> OBJECT_RETURN_VALUE
            ExtraReturnType.LIST_OF_OBJECTS -> "[$LIST_OF_OBJECTS_RETURN_VALUE]"
        }
    }

    companion object {
        const val PROCESS_RETURN_VALUE_ACTION = "org.dhis2.customintent.ACTION_PROCESS_RETURN_VALUE"
        const val RETURN_STRING_INTENT_ACTION = "ACTION_RETURN_STRING"
        const val RETURN_INTEGER_INTENT_ACTION = "ACTION_RETURN_INTEGER"
        const val RETURN_FLOAT_INTENT_ACTION = "ACTION_RETURN_FLOAT"
        const val RETURN_BOOLEAN_INTENT_ACTION = "ACTION_RETURN_BOOLEAN"
        const val RETURN_OBJECT_INTENT_ACTION = "ACTION_RETURN_OBJECT"
        const val RETURN_LIST_OF_OBJECTS_INTENT_ACTION = "ACTION_RETURN_LIST_OF_OBJECTS"
        const val OBJECT_RETURN_VALUE = "{\"value1\": \"ejemplo1\", \"value2\": \"ejemplo2\"}"
        const val STRING_RETURN_VALUE = "string sample response"
        const val BOOLEAN_RETURN_VALUE = true
        const val FLOAT_RETURN_VALUE = 0.5f
        const val INTEGER_RETURN_VALUE = 9
        const val LIST_OF_OBJECTS_RETURN_VALUE =
            "{\"value1\": \"ejemplo1\", \"value2\": \"ejemplo2\"},{\"value1\": \"ejemplo3\", \"value2\": \"ejemplo4\"}"
    }
}
