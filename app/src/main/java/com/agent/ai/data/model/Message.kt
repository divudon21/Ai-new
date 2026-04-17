package com.agent.ai.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val id: String,
    val content: String,
    val isFromUser: Boolean,
    val timestamp: Long,
    val agentId: String? = null,
)

@Serializable
data class Conversation(
    val id: String,
    val title: String,
    val messages: List<Message>,
    val createdAt: Long,
    val updatedAt: Long,
)

@Serializable
data class Agent(
    val id: String,
    val name: String,
    val description: String,
    val persona: String,
    val icon: String,
    val systemPrompt: String,
    val isActive: Boolean = false,
)
