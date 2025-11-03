
import SwiftUI

struct CrisisModeView: View {
    @State var steps: [CrisisStep]
    @Environment(\.presentationMode) var presentationMode

    var body: some View {
        NavigationView {
            VStack {
                Text("Crisis Mode Protocol")
                    .font(.largeTitle)
                Text("Follow these steps to secure your device immediately.")
                List($steps) { $step in
                    CrisisStepRow(step: $step)
                }
                Button("Deactivate Crisis Mode") {
                    presentationMode.wrappedValue.dismiss()
                }
                .padding()
            }
            .navigationTitle("Crisis Mode")
            .navigationBarHidden(true)
        }
    }
}

struct CrisisStepRow: View {
    @Binding var step: CrisisStep

    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Text(step.title)
                    .font(.headline)
                Text(step.description)
                    .font(.subheadline)
                if let deepLink = step.deepLink {
                    Button("OPEN SETTINGS") {
                        if UIApplication.shared.canOpenURL(deepLink) {
                            UIApplication.shared.open(deepLink)
                        }
                    }
                    .buttonStyle(.bordered)
                }
            }
            Spacer()
            Image(systemName: step.isCompleted ? "checkmark.square.fill" : "square")
                .onTapGesture {
                    step.isCompleted.toggle()
                }
        }
    }
}
