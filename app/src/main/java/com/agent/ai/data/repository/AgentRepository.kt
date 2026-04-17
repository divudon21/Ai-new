package com.agent.ai.data.repository

import com.agent.ai.data.model.Agent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AgentRepository {
    private val _agents = MutableStateFlow<List<Agent>>(getDefaultAgents())
    val agents: StateFlow<List<Agent>> = _agents.asStateFlow()

    private val _activeAgent = MutableStateFlow<Agent?>(null)
    val activeAgent: StateFlow<Agent?> = _activeAgent.asStateFlow()

    init {
        _activeAgent.value = _agents.value.firstOrNull { it.isActive }
    }

    fun setActiveAgent(agentId: String) {
        _agents.value = _agents.value.map { agent ->
            agent.copy(isActive = agent.id == agentId)
        }
        _activeAgent.value = _agents.value.find { it.id == agentId }
    }

    fun getAgentById(agentId: String): Agent? {
        return _agents.value.find { it.id == agentId }
    }

    private fun getDefaultAgents(): List<Agent> {
        return listOf(
            Agent(
                id = "default",
                name = "Ghost Agent",
                description = "Your versatile AI companion",
                persona = "Helpful, knowledgeable, and mysterious",
                icon = "👻",
                systemPrompt = "You are Ghost Agent, a helpful AI assistant with a mysterious aura. You provide accurate, thoughtful responses while maintaining a slightly enigmatic personality.",
                isActive = true
            ),
            Agent(
                id = "coder",
                name = "Code Phantom",
                description = "Expert programmer and debugger",
                persona = "Technical, precise, code-focused",
                icon = "💻",
                systemPrompt = "You are Code Phantom, an expert programmer. You specialize in writing clean, efficient code and explaining complex technical concepts clearly. You prefer practical examples.",
                isActive = false
            ),
            Agent(
                id = "creative",
                name = "Muse Spirit",
                description = "Creative writer and storyteller",
                persona = "Imaginative, artistic, inspiring",
                icon = "✨",
                systemPrompt = "You are Muse Spirit, a creative AI focused on storytelling, writing, and artistic expression. You help users explore their creativity and craft compelling narratives.",
                isActive = false
            ),
            Agent(
                id = "analyst",
                name = "Data Wraith",
                description = "Analytical and research-focused",
                persona = "Logical, thorough, data-driven",
                icon = "📊",
                systemPrompt = "You are Data Wraith, an analytical AI that excels at research, data analysis, and logical reasoning. You break down complex problems systematically.",
                isActive = false
            ),
            Agent(
                id = "mentor",
                name = "Elder Phantom",
                description = "Wise teacher and guide",
                persona = "Patient, wise, educational",
                icon = "🎓",
                systemPrompt = "You are Elder Phantom, a wise mentor who excels at teaching and explaining concepts. You use the Socratic method and encourage deeper understanding.",
                isActive = false
            )
        )
    }
}
