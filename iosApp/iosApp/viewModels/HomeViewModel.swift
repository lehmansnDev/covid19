//
//  HomeViewModel.swift
//  iosApp
//
//  Created by Simon Lehmann on 16.10.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class HomeViewModel: ObservableObject {
    @Published var state: HomeState
    @Published var allCountries = [CountrySummary]()
    
    private let repository: Repository
    init(repository: Repository) {
        state = HomeState.companion.LOADING
        self.repository = repository
    }
    
    func fetchCountries() {
        repository.getCovid19Summary { data, error in
            if let summary = data {
                print("fetchCountries success")
                print("Summary is Empty: \(summary.isEmpty)")
                print("Summary countries count: \(summary.countries.count)")
                self.allCountries = summary.countries
                self.state = HomeState(loading: false, failed: false, globalSummary: summary.global, filteredCountries: self.allCountries, input: "")
            }
            if let _ = error {
                print("fetchCountries failure")
                self.state = HomeState.companion.FAILED
            }
        }
    }    
}
