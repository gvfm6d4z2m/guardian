
import Foundation

/**
 * Represents a single educational article within the app.
 */
struct EducationArticle: Identifiable {
    let id = UUID()
    let title: String
    let content: String
    let category: String // e.g., "Prevention", "Detection", "Response"
}
