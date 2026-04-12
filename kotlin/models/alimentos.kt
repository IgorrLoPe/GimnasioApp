package models
import kotlinx.serialization.Serializable

@Serializable
data class alimentos(val nombreAlimento: String, val kcal: Int) {
}