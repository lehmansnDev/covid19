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
    Color(red: 0, green: 122/255.0, blue: 122/255.0),
    Color(red: 0, green: 168/255.0, blue: 149/255.0)]),
    startPoint: .topLeading, endPoint: .bottomTrailing)

struct HomeScreen: View {
    
    @ObservedObject var viewModel: HomeViewModel = HomeViewModel(repository: Repository())
    
    init() {
        viewModel.fetchCountries()
    }
    
    var body: some View {
        ZStack {
            Color(red: 0.96, green: 0.96, blue: 0.96)
                .edgesIgnoringSafeArea(.all)
            VStack {
                GlobalStatisticsView(globalSummary: viewModel.state.globalSummary)
                GeometryReader { geometry in
                    ScrollView(.vertical) {
                        VStack(alignment: HorizontalAlignment.center, spacing: 12) {
                            ForEach(viewModel.allCountries, id: \.self) { country in
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
        }
    }
}

struct GlobalStatisticsView: View {
    
    var globalSummary: GlobalSummary
    
    var body: some View {
        ZStack {
            VStack(spacing: 20) {
                Text("28.09.2021 18:09:09")
                    .foregroundColor(.white)
                    .font(Font.custom("product_sans_regular", size: 12))
                HStack(alignment: VerticalAlignment.center, spacing: 20) {
                    SingleGlobalStatisticsView(totalValues: globalSummary.totalDeaths,
                                               newValues: globalSummary.newDeaths,
                                               iconName: "skull-crossbones",
                                               iconSize: 20,
                                               totalValuesSize: 16)
                        .frame(maxWidth: .infinity)
                    SingleGlobalStatisticsView(totalValues: globalSummary.totalConfirmed,
                                               newValues: globalSummary.newConfirmed,
                                               iconName: "virus",
                                               iconSize: 24,
                                               totalValuesSize: 18)
                        .frame(maxWidth: .infinity)
                    SingleGlobalStatisticsView(totalValues: globalSummary.totalRecovered,
                                               newValues: globalSummary.newRecovered,
                                               iconName: "shield-virus",
                                               iconSize: 20,
                                               totalValuesSize: 16)
                        .frame(maxWidth: .infinity)
                }
                CountrySearchField()
            }
            .padding(8)
        }
        .background(BottomRoundedCornersShape(radius: 24)
                        .fill(gradient)
                        .edgesIgnoringSafeArea(.all)
                        .shadow(color: .gray, radius: 3, x: 0, y: 3))
        .scaledToFit()
    }
}

struct SingleGlobalStatisticsView: View {
    var totalValues: Int32
    var newValues: Int32
    var iconName: String
    var iconSize: CGFloat
    var totalValuesSize: CGFloat

    var body: some View {
        VStack(spacing: 8) {
            FAText(iconName: iconName, size: iconSize)
                .foregroundColor(.white)
            Text("\(totalValues)")
                .foregroundColor(.white)
                .font(Font.custom("product_sans_regular", size: totalValuesSize))
            Text("+ \(newValues)")
                .foregroundColor(.white)
                .font(Font.custom("product_sans_regular", size: 14))
        }
    }
}

struct CountryView: View {

    var country: CountrySummary

    var body: some View {
        ZStack {
            HStack {
                IndexView(index: country.index)
                CountryStatisticsView(country: country)
                Spacer()
                FlagView(flagUrl: country.flagUrl)
            }.background(Capsule()
                            .fill(Color.white)
                            .shadow(color: .gray, radius: 2, x: 0, y: 2))
        }
            .scaledToFit()
            .padding(EdgeInsets(top: 0, leading: 10, bottom: 0, trailing: 10))
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

struct FlagView: View {

    var flagUrl: String
    
    var body: some View {
        ZStack {
            Circle()
                .foregroundColor(Color(red: 221/255.0,
                                       green: 221/255.0,
                                       blue: 221/255.0))
                .frame(width: 48, height: 48)
                .padding(8)
            URLImage(url: flagUrl)
                .frame(width: 32, height: 32)
        }
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

    @State private var country: String = ""
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
                 text: $country
            ) { isEditing in
                self.isEditing = isEditing
            }
            .foregroundColor(.white)
            .accentColor(.white)
            .multilineTextAlignment(.center)
            .font(Font.custom("product_sans_regular", size: 16))
            .autocapitalization(.none)
            .disableAutocorrection(true)
            .padding(8)
            .placeholder(when: !isEditing) {
                Text("Search country")
                    .font(Font.custom("product_sans_regular", size: 12))
                    .foregroundColor(.white)
            }
            Spacer()
            FAText(iconName: "times", size: 20)
                .foregroundColor(country.isEmpty ? Color.init(white: 0, opacity: 0) : .white)
                .padding(8)
                .padding(.trailing, 10)
                .onTapGesture {
                    country = ""
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
