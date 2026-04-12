package models

import kotlinx.serialization.Serializable

@Serializable
data class ejercicios(val nombreEjercicio: String, val musculoentrenado: String) {
}