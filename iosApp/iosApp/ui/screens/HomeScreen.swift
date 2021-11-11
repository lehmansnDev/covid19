//
//  HomeView.swift
//  iosApp
//
//  Created by Simon Lehmann on 01.10.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import FASwiftUI
import shared

var gradient = LinearGradient(gradient: Gradient(colors: [
    Color("PrimaryVariant"), Color("Primary")]),
    startPoint: .topLeading, endPoint: .bottomTrailing)

struct HomeScreen: View {
    
    @ObservedObject var viewModel: HomeViewModel = HomeViewModel(repository: Repository())
    
    var body: some View {
        NavigationView {
            ZStack {
                Color("Background")
                    .edgesIgnoringSafeArea(.all)
                switch viewModel.state.type {
                case StateType.failed:
                    gradient
                        .scaledToFill()
                        .edgesIgnoringSafeArea(.all)
                    Text("The covid data could not be loaded. \n Check your internet connection and start the app again.")
                        .padding(8)
                        .multilineTextAlignment(.center)
                        .foregroundColor(.white)
                        .font(Font.custom("product_sans_regular", size: 14))
                case StateType.succeeded:
                    VStack {
                        GlobalStatisticsView(
                            globalSummary: viewModel.state.globalSummary,
                            date: viewModel.dateString,
                            viewModel: viewModel)
                        GeometryReader { geometry in
                            ScrollView(.vertical) {
                                VStack(alignment: HorizontalAlignment.center, spacing: 12) {
                                    ForEach(viewModel.state.filteredCountries, id: \.self) { country in
                                        CountryView(country: country)
                                    }
                                }
                                .offset(y: 40)
                                .padding(.bottom, 60 + geometry.safeAreaInsets.bottom)
                            }
                            .frame(width: geometry.size.width, height: geometry.size.height + 40)
                            .offset(y: -40)
                            .edgesIgnoringSafeArea(.bottom)
                        }
                        .zIndex(-1)
                    }
                    .edgesIgnoringSafeArea(.bottom)
                default:
                    // Loading state type
                    gradient
                        .scaledToFill()
                        .edgesIgnoringSafeArea(.all)
                    Image("Virus")
                        .frame(width: 160, height: 160, alignment: .center)
                        .foregroundColor(.white)
                }
            }
            .navigationBarHidden(true)
            .onAppear {
                viewModel.fetchCountries()
            }
        }
        
    }
}

struct GlobalStatisticsView: View {
    
    var globalSummary: GlobalSummary
    var date: String
    var viewModel: HomeViewModel
    
    var body: some View {
        ZStack {
            VStack(spacing: 20) {
                Text(date)
                    .foregroundColor(.white)
                    .font(Font.custom("product_sans_regular", size: 12))
                HeaderStatisticsView(
                    totalDeaths: globalSummary.totalDeaths,
                    newDeaths: globalSummary.newDeaths,
                    totalConfirmed: globalSummary.totalConfirmed,
                    newConfirmed: globalSummary.newConfirmed,
                    totalRecovered: globalSummary.totalRecovered,
                    newRecovered: globalSummary.newRecovered)
                CountrySearchField(viewModel: viewModel)
            }
            .padding(8)
        }
        .background(BottomRoundedCornersShape(radius: 24)
                        .fill(gradient)
                        .edgesIgnoringSafeArea(.all)
                        .shadow(color: Color("Shadow"), radius: 3, x: 0, y: 3))
        .scaledToFit()
    }
}

struct CountryView: View {

    var country: CountrySummary

    var body: some View {
        ZStack {
            NavigationLink(destination: DetailScreen(countryCode: country.countryCode)) {
                HStack {
                    IndexView(index: country.index)
                    CountryStatisticsView(country: country)
                    Spacer()
                    FlagView(flagUrl: country.flagUrl)
                }
            }.scaledToFill()
        }
        .background(Capsule().fill(Color("Surface"))
        .shadow(color: Color("Shadow"), radius: 2, x: 0, y: 2))
        .padding(.horizontal, 10)
    }
}

struct IndexView: View {

    var index: Int32

    var body: some View {
        ZStack {
            Text("\(index)")
                .frame(width: 24, height: 24)
                .font(Font.custom("product_sans_regular", size: 14))
                .foregroundColor(.white)
                .padding(4)
                .overlay(Circle().stroke(.white, lineWidth: 2))
                .padding(16)
        }
        .background(gradient)
        .clipShape(CircleLeftShape())
        .scaledToFit()
    }
}

struct CountryStatisticsView: View {

    var country: CountrySummary

    var body: some View {
        ZStack {
            VStack(alignment: .leading, spacing: 5) {
                Text(country.country)
                    .foregroundColor(Color("OnSurface"))
                    .font(Font.custom("product_sans_bold", size: 16))
                    .fontWeight(.bold)
                HStack {
                    StatisticsView(color: .red, totalValues: country.totalConfirmed, newValues: country.newConfirmed, iconName: "virus")
                        .frame(maxWidth: .infinity, alignment: .leading)
                    StatisticsView(color: .gray, totalValues: country.totalDeaths, newValues: country.newDeaths, iconName: "skull-crossbones")
                        .frame(maxWidth: .infinity, alignment: .leading)
                }
            }
        }.scaledToFit()
    }
}

struct StatisticsView: View {

    var color: Color
    var totalValues: Int32
    var newValues: Int32
    var iconName: String

    var body: some View {
        HStack {
            FAText(iconName: iconName, size: 14)
                .foregroundColor(.white)
                .padding(4)
                .background(Circle().fill(color))
            VStack {
                VStack(spacing: 2) {
                    Text("\(totalValues)")
                        .foregroundColor(Color("OnSurface"))
                        .font(Font.custom("product_sans_regular", size: 10))
                    Text("+ \(newValues)")
                        .foregroundColor(color)
                        .font(Font.custom("product_sans_regular", size: 10))
                }
            }
        }
    }
}

struct CountrySearchField: View {
    
    var viewModel: HomeViewModel
    
    @State private var input: String = ""
    @State private var isEditing = false

    var body: some View {
        HStack {
            FAText(iconName: "search", size: 20)
                .foregroundColor(.white)
                .padding(8)
                .padding(.leading, 10)
            Spacer()
            TextField(
                 "",
                 text: $input,
                 onEditingChanged: { isEditing in
                     self.isEditing = isEditing
                 }
            )
            .onChange(of: input) { newValue in
                viewModel.onAction(action: HomeAction.InputChanged(input: self.input))
            }
            .foregroundColor(.white)
            .accentColor(.white)
            .multilineTextAlignment(.center)
            .font(Font.custom("product_sans_regular", size: 16))
            .autocapitalization(.none)
            .disableAutocorrection(true)
            .padding(8)
            .placeholder(when: !isEditing && input.isEmpty) {
                Text("Search country")
                    .font(Font.custom("product_sans_regular", size: 12))
                    .foregroundColor(.white)
            }
            Spacer()
            FAText(iconName: "times", size: 20)
                .foregroundColor(input.isEmpty ? Color.init(white: 0, opacity: 0) : .white)
                .padding(8)
                .padding(.trailing, 10)
                .onTapGesture {
                    input = ""
                    viewModel.onAction(action: HomeAction.InputDeleted())
                }
        }
        .background(Capsule().fill(Color.init(red: 0, green: 0, blue: 0, opacity: 0.5)))
    }
}

extension View {
    func placeholder<Content: View>(
        when shouldShow: Bool,
        alignment: Alignment = .center,
        @ViewBuilder placeholder: () -> Content) -> some View {

        ZStack(alignment: alignment) {
            placeholder().opacity(shouldShow ? 1 : 0)
            self
        }
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeScreen()
    }
}
