//
//  HomeView.swift
//  iosApp
//
//  Created by Simon Lehmann on 01.10.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

var gradient = LinearGradient(gradient: Gradient(colors: [
    Color(red: 0, green: 122/255.0, blue: 122/255.0),
    Color(red: 0, green: 168/255.0, blue: 149/255.0)]),
    startPoint: .topLeading, endPoint: .bottomTrailing)

struct HomeView: View {
    var body: some View {
        ZStack {
            Color(red: 0.96, green: 0.96, blue: 0.96)
                .edgesIgnoringSafeArea(.all)
            VStack {
                GlobalStatisticsView()
                GeometryReader { geometry in
                    ScrollView(.vertical) {
                        VStack(alignment: HorizontalAlignment.center, spacing: 12) {
                            CountryView(countryName: "USA", index: 1, flagUrl: "https://www.countryflags.io/US/flat/64.png")
                            CountryView(countryName: "USA", index: 2, flagUrl: "https://www.countryflags.io/US/flat/64.png")
                            CountryView(countryName: "USA", index: 3, flagUrl: "https://www.countryflags.io/US/flat/64.png")
                            CountryView(countryName: "USA", index: 4, flagUrl: "https://www.countryflags.io/US/flat/64.png")
                            CountryView(countryName: "USA", index: 5, flagUrl: "https://www.countryflags.io/US/flat/64.png")
                            CountryView(countryName: "USA", index: 6, flagUrl: "https://www.countryflags.io/US/flat/64.png")
                            CountryView(countryName: "USA", index: 7, flagUrl: "https://www.countryflags.io/US/flat/64.png")
                            CountryView(countryName: "USA", index: 8, flagUrl: "https://www.countryflags.io/US/flat/64.png")
                            CountryView(countryName: "Germany", index: 21, flagUrl: "https://www.countryflags.io/DE/flat/64.png")
                            CountryView(countryName: "France", index: 122, flagUrl: "https://www.countryflags.io/FR/flat/64.png")
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
    
    var body: some View {
        ZStack {
            BottomRoundedCornersShape(radius: 24)
                .fill(gradient)
                .edgesIgnoringSafeArea(.all)
                .shadow(color: .gray, radius: 3, x: 0, y: 3)
            
            VStack(spacing: 20) {
                Text("28.09.2021 18:09:09")
                    .foregroundColor(.white)
                    .font(Font.custom("product_sans_regular", size: 12))
                HStack(alignment: VerticalAlignment.center, spacing: 20) {
                    SingleGlobalStatisticsView(totalValues: 4753057, newValues: 4585)
                    SingleGlobalStatisticsView(totalValues: 232001832, newValues: 325341)
                    SingleGlobalStatisticsView(totalValues: 0, newValues: 0)
                }
            }
        }
        .scaledToFit()
    }
}

struct SingleGlobalStatisticsView: View {
    var totalValues: Int
    var newValues: Int

    var body: some View {
        VStack(spacing: 8) {
            Image(systemName: "circle")
            Text("\(totalValues)")
                .foregroundColor(.white)
                .font(Font.custom("product_sans_regular", size: 18))
            Text("+ \(newValues)")
                .foregroundColor(.white)
                .font(Font.custom("product_sans_regular", size: 14))
        }
    }
}

struct CountryView: View {

    var countryName: String
    var index: Int
    var flagUrl: String

    var body: some View {
        ZStack {
            HStack {
                IndexView(index: index)
                CountryStatisticsView(countryName: countryName)
                Spacer()
                FlagView(flagUrl: flagUrl)
            }.background(Capsule()
                            .fill(Color.white)
                            .shadow(color: .gray, radius: 2, x: 0, y: 2))
        }
            .scaledToFit()
            .padding(EdgeInsets(top: 0, leading: 10, bottom: 0, trailing: 10))
    }
}

struct IndexView: View {

    var index: Int

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

    var countryName: String

    var body: some View {
        ZStack {
            VStack(alignment: .leading, spacing: 5) {
                Text(countryName)
                    .font(Font.custom("product_sans_bold", size: 16))
                    .fontWeight(.bold)
                HStack {
                    StatisticsView(color: .red, totalValues: 47362899, newValues: 242134)
                    StatisticsView(color: .gray, totalValues: 84748, newValues: 2151)
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
    var totalValues: Int
    var newValues: Int

    var body: some View {
        HStack {
            Image(systemName: "circle")
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

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
