//
//  DetailViewModel.swift
//  iosApp
//
//  Created by Simon Lehmann on 23.10.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class DetailViewModel: ObservableObject {
    
    @Published var state: DetailState
    
    private let repository: Repository
    
    init(repository: Repository) {
        self.repository = repository
        state = DetailState.companion.LOADING
    }
    
    func fetchCountrySummary(countryCode: String) {
        repository.getCountrySummary(countryCode: countryCode) { data, error in
            if let summary = data {
                self.state = DetailState(
                    type: StateType.succeeded,
                    countrySummary: summary,
                    countryDetails: self.state.countryDetails)
            }
            if let _ = error {
                self.state = DetailState.companion.FAILED
            }
        }
    }
    
}

