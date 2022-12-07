package com.nhnacademy.jpa.family.domain.household;

import com.nhnacademy.jpa.family.Base;
import com.nhnacademy.jpa.family.entity.code.CompositionReason;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
public class HouseholderViewDto {
    private final String householderName;
    private final CompositionReason reason;
    private final LocalDate compositionDate;
    private final List<HouseholderAddress> addresses;

    @Getter
    @ToString
    public static class HouseholderAddress{
        private String presentStatus;
        private String address;
        private LocalDate reportDate;

        public HouseholderAddress(String yesOrNo, String address, LocalDate reportDate) {
            this.presentStatus = yesOrNo.equals(Base.YES) ? "현주소" : "주소";
            this.address = address;
            this.reportDate = reportDate;
        }
    }
}
