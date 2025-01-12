package com.dogancanemek.capitalsoftheworld

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CapitalsViewModel: ViewModel() {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.apiKey
    )

    private val _aiResult = MutableStateFlow<String?>(null) // Store AI result
    val aiResult: StateFlow<String?> = _aiResult.asStateFlow() // Expose as StateFlow

    fun makePrompt(prompt: String) {
        viewModelScope.launch {
            try {
                val result = generativeModel.generateContent(
                    prompt)
                _aiResult.emit(result.text) // Update aiResult
            } catch (e: Exception) {
                e.printStackTrace()
                _aiResult.emit("Error: ${e.message}") // Update with error message
            }
        }
    }
}