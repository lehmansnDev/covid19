//
//  Shapes.swift
//  iosApp
//
//  Created by Simon Lehmann on 09.10.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct BottomRoundedCornersShape: Shape {
    let radius: CGFloat
    
    func path(in rect: CGRect) -> Path {
        var path = Path()
        
        let tl = CGPoint(x: rect.minX, y: rect.minY)
        let tr = CGPoint(x: rect.maxX, y: rect.minY)
        let brs = CGPoint(x: rect.maxX, y: rect.maxY - radius)
        let brc = CGPoint(x: rect.maxX - radius, y: rect.maxY - radius)
        let bls = CGPoint(x: rect.minX + radius, y: rect.maxY)
        let blc = CGPoint(x: rect.minX + radius, y: rect.maxY - radius)
        
        path.move(to: tl)
        path.addLine(to: tr)
        path.addLine(to: brs)
        path.addRelativeArc(center: brc, radius: radius,
          startAngle: Angle.degrees(0), delta: Angle.degrees(90))
        path.addLine(to: bls)
        path.addRelativeArc(center: blc, radius: radius,
          startAngle: Angle.degrees(90), delta: Angle.degrees(90))
        
        return path
    }
}

struct CircleLeftShape: Shape {
    
    func path(in rect: CGRect) -> Path {
        var path = Path()
        
        let t = CGPoint(x: rect.maxX / 2, y: rect.minY)
        let tr = CGPoint(x: rect.maxX, y: rect.minY)
        let br = CGPoint(x: rect.maxX, y: rect.maxY)
        let b = CGPoint(x: rect.maxX / 2, y: rect.maxY)
        let c = CGPoint(x: rect.maxX / 2, y: rect.maxY / 2)
        
        path.move(to: t)
        path.addLine(to: tr)
        path.addLine(to: br)
        path.addLine(to: b)
        path.addRelativeArc(center: c, radius: rect.maxY / 2,
          startAngle: Angle.degrees(90), delta: Angle.degrees(90))
        path.addRelativeArc(center: c, radius: rect.maxY / 2,
          startAngle: Angle.degrees(180), delta: Angle.degrees(90))
        
        return path
    }
}
