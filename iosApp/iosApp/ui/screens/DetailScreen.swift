//
//  DetailScreen.swift
//  iosApp
//
//  Created by Simon Lehmann on 23.10.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import FASwiftUI
import shared


struct DetailScreen: View {
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    @ObservedObject var viewModel: DetailViewModel = DetailViewModel(repository: Repository())
    
    let countryCode: String
    
    init(countryCode: String) {
        self.countryCode = countryCode
        viewModel.fetchCountrySummary(countryCode: countryCode)
    }
    
    var body: some View {
        ZStack {
            VStack(spacing: 20) {
                HStack(alignment: .center) {
                    FAText(iconName: "arrow-left", size: 20)
                        .foregroundColor(.white)
                        .onTapGesture {
                            self.presentationMode.wrappedValue.dismiss()
                        }
                    ZStack {
                        CountryChip(
                            name: viewModel.state.countrySummary.country,
                            flagUrl: viewModel.state.countrySummary.flagUrl
                        )
                    }
                    .frame(maxWidth: .infinity)
                    // Placeholder for symmetry
                    FAText(iconName: "arrow-left", size: 20)
                        .foregroundColor(Color.init(white: 0, opacity: 0))
                }
                .padding(EdgeInsets(top: 0, leading: 10, bottom: 0, trailing: 10))
                .scaledToFit()
                HeaderStatisticsView(
                    totalDeaths: viewModel.state.countrySummary.totalDeaths,
                    newDeaths: viewModel.state.countrySummary.newDeaths,
                    totalConfirmed: viewModel.state.countrySummary.totalConfirmed,
                    newConfirmed: viewModel.state.countrySummary.newConfirmed,
                    totalRecovered: viewModel.state.countrySummary.totalRecovered,
                    newRecovered: viewModel.state.countrySummary.newRecovered)
                
                
                Spacer()
            }
            .padding(8)
        }
        .background(Rectangle()
                        .fill(gradient)
                        .edgesIgnoringSafeArea(.all))
        .navigationTitle("")
        .navigationBarHidden(true)
        .navigationBarBackButtonHidden(true)
    }
}

struct CountryChip: View {

    var name: String
    var flagUrl: String

    var body: some View {
        
            HStack {
                Text(name)
                    .foregroundColor(Color("OnSurface"))
                    .font(Font.custom("product_sans_bold", size: 16))
                    .fontWeight(.bold)
                FlagView(flagUrl: flagUrl, size: 32)
            }
            .padding(.leading, 12)
            .background(Capsule()
                            .fill(Color("Surface"))
                            .shadow(color: .black, radius: 2, x: 0, y: 2))
    }
}
