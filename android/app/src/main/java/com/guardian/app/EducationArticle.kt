
package com.guardian.app

/**
 * Represents a single educational article within the app.
 */
data class EducationArticle(
    val id: String,
    val title: String,
    val content: String,
    val category: String // e.g., "Prevention", "Detection", "Response"
)
