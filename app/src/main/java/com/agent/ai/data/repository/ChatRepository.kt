package com.agent.ai.data.repository

import com.agent.ai.data.model.Agent
import com.agent.ai.data.model.Conversation
import com.agent.ai.data.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class ChatRepository {
    private val _conversations = MutableStateFlow<List<Conversation>>(emptyList())
    val conversations: StateFlow<List<Conversation>> = _conversations.asStateFlow()

    private val _currentMessages = MutableStateFlow<List<Message>>(emptyList())
    val currentMessages: StateFlow<List<Message>> = _currentMessages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _activeAgent = MutableStateFlow<Agent?>(null)
    val activeAgent: StateFlow<Agent?> = _activeAgent.asStateFlow()

    init {
        // Initialize with sample conversations
        _conversations.value = getSampleConversations()
    }

    fun setActiveAgent(agent: Agent) {
        _activeAgent.value = agent
    }

    fun sendMessage(content: String) {
        val userMessage = Message(
            id = UUID.randomUUID().toString(),
            content = content,
            isFromUser = true,
            timestamp = System.currentTimeMillis()
        )

        _currentMessages.value = _currentMessages.value + userMessage
        _isLoading.value = true

        // Simulate AI response
        val agent = _activeAgent.value
        val response = generateAIResponse(content, agent)

        val aiMessage = Message(
            id = UUID.randomUUID().toString(),
            content = response,
            isFromUser = false,
            timestamp = System.currentTimeMillis(),
            agentId = agent?.id
        )

        _currentMessages.value = _currentMessages.value + aiMessage
        _isLoading.value = false
    }

    private fun generateAIResponse(userMessage: String, agent: Agent?): String {
        val agentName = agent?.name ?: "Ghost Agent"
        
        return when {
            userMessage.contains("hello", ignoreCase = true) || 
            userMessage.contains("hi", ignoreCase = true) ->
                "Greetings. I am $agentName, your digital companion in the shadows. How may I assist you today?"
            
            userMessage.contains("help", ignoreCase = true) ->
                "I can help you with:\n• Answering questions\n• Writing and editing text\n• Analyzing information\n• Creative tasks\n• Code assistance\n\nWhat do you need?"
            
            userMessage.contains("code", ignoreCase = true) || 
            userMessage.contains("program", ignoreCase = true) ->
                "I can assist with coding. Whether you need debugging help, algorithm design, or code review, I'm here to help. What language or framework are you working with?"
            
            userMessage.contains("who are you", ignoreCase = true) ->
                "I am $agentName - an AI assistant designed to help you navigate the digital realm. I exist in the space between bits and bytes, ready to assist with knowledge, creativity, and problem-solving."
            
            else -> {
                val responses = listOf(
                    "Interesting. Tell me more about that.",
                    "I've analyzed your input. Here's my perspective: ${userMessage.take(20)}... is a fascinating topic. Would you like me to elaborate?",
                    "As $agentName, I sense there's more to explore here. What specific aspect interests you most?",
                    "Your query resonates in the digital ether. Let me process that for you...",
                    "I understand. Let me provide some insights on this matter.",
                )
                responses.random()
            }
        }
    }

    fun clearConversation() {
        _currentMessages.value = emptyList()
    }

    fun loadConversation(conversationId: String) {
        val conversation = _conversations.value.find { it.id == conversationId }
        conversation?.let {
            _currentMessages.value = it.messages
        }
    }

    fun saveCurrentConversation(title: String) {
        if (_currentMessages.value.isEmpty()) return

        val conversation = Conversation(
            id = UUID.randomUUID().toString(),
            title = title,
            messages = _currentMessages.value,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )

        _conversations.value = _conversations.value + conversation
    }

    fun deleteConversation(conversationId: String) {
        _conversations.value = _conversations.value.filter { it.id != conversationId }
    }

    private fun getSampleConversations(): List<Conversation> {
        return listOf(
            Conversation(
                id = "1",
                title = "Getting Started",
                messages = listOf(
                    Message(
                        id = "m1",
                        content = "Hello, what can you do?",
                        isFromUser = true,
                        timestamp = System.currentTimeMillis() - 86400000
                    ),
                    Message(
                        id = "m2",
                        content = "Greetings! I'm Ghost Agent, your AI assistant. I can help with writing, coding, analysis, and much more.",
                        isFromUser = false,
                        timestamp = System.currentTimeMillis() - 86300000
                    )
                ),
                createdAt = System.currentTimeMillis() - 86400000,
                updatedAt = System.currentTimeMillis() - 86300000
            )
        )
    }
}
