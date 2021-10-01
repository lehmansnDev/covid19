//
//  HomeView.swift
//  iosApp
//
//  Created by Simon Lehmann on 01.10.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct HomeView: View {
    var body: some View {
        ZStack {
            Color(red: 0.98, green: 0.98, blue: 0.98)
                .edgesIgnoringSafeArea(.all)
            VStack {
                GlobalStatisticsView()
                VStack(alignment: HorizontalAlignment.center, spacing: 10) {
                    CountryStatisticsView(countryName: "USA")
                    CountryStatisticsView(countryName: "Germany")
                    CountryStatisticsView(countryName: "France")
                }
                Spacer()
            }
        }
    }
}

struct GlobalStatisticsView: View {
    
    var body: some View {
        ZStack {
            LinearGradient(gradient: Gradient(colors: [.blue, .white]),
                           startPoint: .topLeading,
                           endPoint: .bottomTrailing)
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
        }
    }
}

struct SingleGlobalStatisticsView: View {
    var totalValues: Int
    var newValues: Int
    
    var body: some View {
        VStack(spacing: 5) {
            Text("\(totalValues)")
                .foregroundColor(.white)
            Text("+ \(newValues)")
                .foregroundColor(.white)
        }
    }
}

struct CountryStatisticsView: View {
    
    var countryName: String
    
    var body: some View {
        Text(countryName)
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
