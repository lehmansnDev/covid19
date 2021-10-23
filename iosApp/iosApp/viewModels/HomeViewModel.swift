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
    @Published var dateString: String
    
    private let repository: Repository
    
    init(repository: Repository) {
        self.repository = repository
        state = HomeState.companion.LOADING
        dateString = ""
    }
    
    func fetchCountries() {
        repository.getCovid19Summary { data, error in
            if let summary = data {
                self.allCountries = summary.countries
                self.state = HomeState(loading: false, failed: false, globalSummary: summary.global, filteredCountries: self.allCountries, input: "")
                
                // Transform dateInstant to dateString
                if let dateInstant = summary.dateInstant {
                    let formatter = DateFormatter()
                    formatter.dateStyle = .medium
                    formatter.timeStyle = .medium
                    let date = Date(timeIntervalSince1970: TimeInterval(dateInstant.epochSeconds))
                    self.dateString = formatter.string(from: date)
                }
            }
            if let _ = error {
                print("fetchCountries failure")
                self.state = HomeState.companion.FAILED
            }
        }
    }
    
    func onAction(action: HomeAction) {
        switch action {
        case .InputChanged(let value):
            updateInput(input: value)
        case .InputDeleted:
            updateInput(input: "")
        }
    }
    
    private func updateInput(input: String) {
        if(state.input == input) {
            return
        }
        print("updateInput \(input)")
        var filteredCountries = self.allCountries;
        if(!input.isEmpty) {
            filteredCountries = self.allCountries.filter { country in
                let countryName = country.country.lowercased()
                let contains = countryName.contains(input.lowercased())
                return contains
            }
        }
        state = HomeState(
            loading: state.loading,
            failed: state.failed,
            globalSummary: state.globalSummary,
            filteredCountries: filteredCountries,
            input: input)
    }
}
