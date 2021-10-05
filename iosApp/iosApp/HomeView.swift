//
//  HomeView.swift
//  iosApp
//
//  Created by Simon Lehmann on 01.10.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

var gradient = LinearGradient(gradient: Gradient(colors: [.blue, .white]),
                             startPoint: .topLeading,
                             endPoint: .bottomTrailing)

struct HomeView: View {
    var body: some View {
        ZStack {
            Color(red: 0.96, green: 0.96, blue: 0.96)
                .edgesIgnoringSafeArea(.all)
            VStack {
                GlobalStatisticsView()
                ScrollView(.vertical) {
                    VStack(alignment: HorizontalAlignment.center, spacing: 12) {
                        CountryView(countryName: "USA", index: 0)
                        CountryView(countryName: "Germany", index: 1)
                        CountryView(countryName: "France", index: 2)
                    }
                }
            }.edgesIgnoringSafeArea(.bottom)
        }
    }
}

struct GlobalStatisticsView: View {
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 24)
                .fill(gradient)
                .edgesIgnoringSafeArea(.all)
            
            VStack(spacing: 20) {
                Text("28.09.2021 18:09:09")
                    .foregroundColor(.white)
                HStack(alignment: VerticalAlignment.center, spacing: 20) {
                    SingleGlobalStatisticsView(totalValues: 4753057, newValues: 4585)
                    SingleGlobalStatisticsView(totalValues: 232001832, newValues: 325341)
                    SingleGlobalStatisticsView(totalValues: 0, newValues: 0)
                }
            }
        }.scaledToFit()
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
            Text("+ \(newValues)")
                .foregroundColor(.white)
        }
    }
}

struct CountryView: View {

    var countryName: String
    var index: Int

    var body: some View {
        ZStack {
            HStack {
                IndexView(index: index)
                CountryStatisticsView(countryName: countryName)
                Spacer()
                FlagView()
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
        }
            .scaledToFit()
            .padding()
    }
}

struct CountryStatisticsView: View {

    var countryName: String

    var body: some View {
        ZStack {
            VStack(alignment: .leading) {
                Text(countryName)
                HStack {
                    StatisticsView(color: .red, totalValues: 47362899, newValues: 242134)
                    StatisticsView(color: .gray, totalValues: 84748, newValues: 2151)
                }
            }
        }.scaledToFit()
    }
}

struct FlagView: View {

    var body: some View {
        ZStack {
            Circle()
                .foregroundColor(Color(red: 0.80, green: 0.80, blue: 0.80))
                .frame(width: 40, height: 40)
                .padding()
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
                VStack(spacing: 5) {
                    Text("\(totalValues)")
                    Text("+ \(newValues)")
                        .foregroundColor(color)
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
