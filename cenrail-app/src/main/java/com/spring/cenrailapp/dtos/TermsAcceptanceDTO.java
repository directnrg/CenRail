package com.spring.cenrailapp.dtos;

import jakarta.validation.constraints.AssertTrue;

public class TermsAcceptanceDTO {

    @AssertTrue(message = "To travel with us, it is required to accept our terms and conditions")
    private boolean termsAcceptance;

    public boolean isTermsAcceptance() {
        return termsAcceptance;
    }

    public void setTermsAcceptance(boolean termsAcceptance) {
        this.termsAcceptance = termsAcceptance;
    }
}

