
// swift-tools-version: 5.7
import PackageDescription

let package = Package(
    name: "Guardian",
    platforms: [
        .iOS(.v15)
    ],
    products: [
        .executable(name: "GuardianApp", targets: ["Guardian"])
    ],
    dependencies: [],
    targets: [
        .target(
            name: "Guardian",
            dependencies: []
        )
    ]
)
