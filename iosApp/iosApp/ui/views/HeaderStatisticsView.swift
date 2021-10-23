//
//  HeaderStatisticsView.swift
//  iosApp
//
//  Created by Simon Lehmann on 23.10.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import FASwiftUI
import shared

struct HeaderStatisticsView: View {
 
    var totalDeaths: Int32
    var newDeaths: Int32
    
    var totalConfirmed: Int32
    var newConfirmed: Int32
    
    var totalRecovered: Int32
    var newRecovered: Int32
    
    var body: some View {
        HStack(alignment: VerticalAlignment.center, spacing: 20) {
            SingleGlobalStatisticsView(totalValues: totalDeaths,
                                       newValues: newDeaths,
                                       iconName: "skull-crossbones",
                                       iconSize: 20,
                                       totalValuesSize: 16)
            SingleGlobalStatisticsView(totalValues: totalConfirmed,
                                       newValues: newConfirmed,
                                       iconName: "virus",
                                       iconSize: 24,
                                       totalValuesSize: 18)
            SingleGlobalStatisticsView(totalValues: totalRecovered,
                                       newValues: newRecovered,
                                       iconName: "shield-virus",
                                       iconSize: 20,
                                       totalValuesSize: 16)
                
        }
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
        .frame(maxWidth: .infinity)
    }
}

