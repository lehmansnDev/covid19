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
        
        repository.getCovid19SummaryIos(success: { summary in
            if(summary.isEmpty) {
                self.state = HomeState.companion.FAILED
            } else {
                self.allCountries = summary.countries
                self.state = HomeState(loading: false, failed: false, globalSummary: summary.global, filteredCountries: self.allCountries, input: "")
            }
        })
    }
}
