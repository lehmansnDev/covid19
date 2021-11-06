//
//  FlagView.swift
//  iosApp
//
//  Created by Simon Lehmann on 06.11.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import URLImage

struct FlagView: View {

    var flagUrl: String
    var size: Int = 48

    var body: some View {
        let url = URL(string: flagUrl)
        let width = CGFloat((size/6) * 4);
        ZStack {
            Circle()
                .foregroundColor(Color("FlagBackground"))
                .frame(width: CGFloat(size), height: CGFloat(size))
                .padding(CGFloat(size/6))
            if let wrappedURL = url {
                URLImage(wrappedURL) { image in
                    image
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .foregroundColor(Color("OnSurface"))
                }
                .frame(width: width, height: (width / 5) * 3)
            }
        }
    }
}
